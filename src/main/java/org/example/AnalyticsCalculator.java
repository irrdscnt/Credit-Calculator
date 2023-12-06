package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AnalyticsCalculator {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/loans";
    private static final String JDBC_USER = "admin";
    private static final String JDBC_PASSWORD = "0000";

    public static LoanSummary calculateLoanSummary(int loanId) {
        String query = "SELECT id, amount FROM loans WHERE id = ?";
        LoanSummary loanSummary = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int loanIdFromDB = resultSet.getInt("id");
                    double loanAmount = resultSet.getDouble("amount");

                    List<Payment> paymentSchedule = PaymentScheduleGenerator.generatePaymentSchedule(loanIdFromDB);

                    double totalPayments = paymentSchedule.stream().mapToDouble(Payment::getTotalPayment).sum();
                    double totalPrincipalPayments = paymentSchedule.stream().mapToDouble(Payment::getPrincipalPayment).sum();
                    double totalInterestPayments = paymentSchedule.stream().mapToDouble(Payment::getInterestPayment).sum();
                    double averageInterestRate = totalInterestPayments / totalPayments * 100;

                    loanSummary = new LoanSummary(loanIdFromDB, loanAmount, totalPayments, totalPrincipalPayments, totalInterestPayments, averageInterestRate);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loanSummary;
    }
}
