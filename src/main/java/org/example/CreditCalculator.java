package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCalculator {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/loans";
    private static final String JDBC_USER = "admin";
    private static final String JDBC_PASSWORD = "0000";

    public static double calculateMonthlyPayment(int loanId) {
        String query = "SELECT amount, interest_rate, term_years FROM loans WHERE id = ?";
        double monthlyPayment = 0.0;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double loanAmount = resultSet.getDouble("amount");
                    double interestRate = resultSet.getDouble("interest_rate");
                    int loanTerm = resultSet.getInt("term_years");

                    monthlyPayment = calculatePayment(loanAmount, interestRate, loanTerm);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthlyPayment;
    }

    static double calculatePayment(double loanAmount, double interestRate, int loanTerm) {
        double monthlyInterestRate = interestRate / 100 / 12;
        int numberOfPayments = loanTerm * 12;

//        double monthlyPayment = (loanAmount * monthlyInterestRate) /
//                (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
        double monthlyPayment = loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments)
                / (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);
        return monthlyPayment;
    }
}
