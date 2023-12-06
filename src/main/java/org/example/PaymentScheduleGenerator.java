package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentScheduleGenerator {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/loans";
    private static final String JDBC_USER = "admin";
    private static final String JDBC_PASSWORD = "0000";

    public static List<Payment> generatePaymentSchedule(int loanId) {
        String query = "SELECT amount, interest_rate, term_years FROM loans WHERE id = ?";
        List<Payment> paymentSchedule = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double loanAmount = resultSet.getDouble("amount");
                    double interestRate = resultSet.getDouble("interest_rate");
                    int loanTerm = resultSet.getInt("term_years");

                    paymentSchedule = calculatePaymentSchedule(loanAmount, interestRate, loanTerm);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentSchedule;
    }

    private static List<Payment> calculatePaymentSchedule(double loanAmount, double interestRate, int loanTerm) {
        List<Payment> paymentSchedule = new ArrayList<>();
        double remainingBalance = loanAmount;

        for (int month = 1; month <= loanTerm * 12; month++) {
            double interestPayment = remainingBalance * interestRate / 100 / 12;
            double principalPayment = CreditCalculator.calculatePayment(loanAmount, interestRate, loanTerm) - interestPayment;

            remainingBalance -= principalPayment;

            Payment payment = new Payment(month, principalPayment, interestPayment, remainingBalance);
            paymentSchedule.add(payment);
        }

        return paymentSchedule;
    }
}
