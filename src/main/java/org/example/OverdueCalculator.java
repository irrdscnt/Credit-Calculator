package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class OverdueCalculator {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/loans";
    private static final String JDBC_USER = "admin";
    private static final String JDBC_PASSWORD = "0000";

//    public static List<OverduePayment> calculateOverduePayments(int loanId) {
//        String query = "SELECT month, payment_date, remaining_balance FROM payments WHERE loan_id = ?";
//        List<OverduePayment> overduePayments = new ArrayList<>();
//
//        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setInt(1, loanId);
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    int month = resultSet.getInt("month");
//                    LocalDate paymentDate = resultSet.getDate("payment_date").toLocalDate();
//                    double remainingBalance = resultSet.getDouble("remaining_balance");
//
//                    if (remainingBalance > 0) {
//                        int daysOverdue = calculateDaysOverdue(paymentDate);
//                        double penalty = calculatePenalty(remainingBalance, daysOverdue);
//                        OverduePayment overduePayment = new OverduePayment(month, penalty);
//                        overduePayments.add(overduePayment);
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return overduePayments;
//    }
    public static List<OverduePayment> calculateOverduePayments(int loanId) {
        String query = "SELECT month, payment_date, remaining_balance FROM payments WHERE loan_id = ?";
        List<OverduePayment> overduePayments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int month = resultSet.getInt("month");
                    LocalDate paymentDate = resultSet.getDate("payment_date").toLocalDate();
                    double remainingBalance = resultSet.getDouble("remaining_balance");

                    int daysOverdue = calculateDaysOverdue(paymentDate);

                    if (daysOverdue > 0) {
                        double penalty = calculatePenalty(remainingBalance, daysOverdue);
                        OverduePayment overduePayment = new OverduePayment(month, penalty);
                        overduePayments.add(overduePayment);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return overduePayments;
    }

    private static int calculateDaysOverdue(LocalDate paymentDate) {
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(paymentDate, currentDate);
    }

    private static double calculatePenalty(double remainingBalance, int daysOverdue) {
        double penaltyRate = 0.01; // Начальный штрафный процент
        double additionalPenaltyRate = 0.005; // Дополнительный штрафный процент за каждый день просрочки
        double maxPenaltyRate = 0.2; // Максимальный штрафный процент

        double penalty = remainingBalance * penaltyRate + additionalPenaltyRate * daysOverdue;
        return Math.min(penalty, remainingBalance * maxPenaltyRate);
    }
}

