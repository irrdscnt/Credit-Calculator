package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Кредитный калькулятор");
            System.out.println("2. Генерация графика погашений");
            System.out.println("3. Расчет просрочки с штрафами");
            System.out.println("4. Аналитика по кредитным показателям");
            System.out.println("0. Выход");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    calculateCredit();
                    break;
                case 2:
                    generatePaymentSchedule();
                    break;
                case 3:
                    calculateOverdue();
                    break;
                case 4:
                    calculateAnalytics();
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте еще раз.");
            }
        }
    }

    private static void calculateCredit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id кредита:");
        int loanId = scanner.nextInt();

        double monthlyPayment = CreditCalculator.calculateMonthlyPayment(loanId);
        System.out.println("Ежемесячный платеж: $" + monthlyPayment);
    }
    private static void generatePaymentSchedule() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id кредита:");
        int loanId = scanner.nextInt();

        scanner.nextLine();

        List<Payment> paymentSchedule = PaymentScheduleGenerator.generatePaymentSchedule(loanId);
        System.out.println("График погашений:");
        for (Payment payment : paymentSchedule) {
            System.out.println("Месяц: " + payment.getMonth() +
                    " Основной платеж: " + payment.getPrincipalPayment() +
                    " Выплата процентов: " + payment.getInterestPayment() +
                    " Итоговый платеж: " + payment.getTotalPayment());
        }
    }

//    private static void generatePaymentSchedule() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Введите id кредита:");
//        int loanId = scanner.nextInt();
//
//        List<Payment> paymentSchedule = PaymentScheduleGenerator.generatePaymentSchedule(loanId);
//        System.out.println("График погашений:");
//        for (Payment payment : paymentSchedule) {
//            System.out.println(payment);
//        }
//    }

    private static void calculateOverdue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id кредита:");
        int loanId = scanner.nextInt();

        List<OverduePayment> overduePayments = OverdueCalculator.calculateOverduePayments(loanId);
        System.out.println("Просроченные платежи:");
        for (OverduePayment overduePayment : overduePayments) {
            System.out.println("Месяц: " + overduePayment.getMonth() +
                    " Штраф: "+ overduePayment.getPenalty());
        }
    }

    private static void calculateAnalytics() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id кредита:");
        int loanId = scanner.nextInt();

        LoanSummary loanSummary = AnalyticsCalculator.calculateLoanSummary(loanId);
        System.out.println("Аналитика по кредиту:");
        System.out.println("Общая сумма платежей: $" + loanSummary.getTotalPayments());
        System.out.println("Сумма основного долга: $" + loanSummary.getTotalPrincipalPayments());
        System.out.println("Сумма процентов: $" + loanSummary.getTotalInterestPayments());
        System.out.println("Средняя процентная ставка: " + loanSummary.getAverageInterestRate() + "%");
    }
}