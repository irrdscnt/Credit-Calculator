package org.example;

public class LoanSummary {
    private int loanId;
    private double loanAmount;
    private double totalPayments;
    private double totalPrincipalPayments;
    private double totalInterestPayments;
    private double averageInterestRate;

    public LoanSummary(int loanId, double loanAmount, double totalPayments, double totalPrincipalPayments, double totalInterestPayments, double averageInterestRate) {
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.totalPayments = totalPayments;
        this.totalPrincipalPayments = totalPrincipalPayments;
        this.totalInterestPayments = totalInterestPayments;
        this.averageInterestRate = averageInterestRate;
    }

    public double getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(double totalPayments) {
        this.totalPayments = totalPayments;
    }

    public double getTotalPrincipalPayments() {
        return totalPrincipalPayments;
    }

    public void setTotalPrincipalPayments(double totalPrincipalPayments) {
        this.totalPrincipalPayments = totalPrincipalPayments;
    }

    public double getTotalInterestPayments() {
        return totalInterestPayments;
    }

    public void setTotalInterestPayments(double totalInterestPayments) {
        this.totalInterestPayments = totalInterestPayments;
    }

    public double getAverageInterestRate() {
        return averageInterestRate;
    }

    public void setAverageInterestRate(double averageInterestRate) {
        this.averageInterestRate = averageInterestRate;
    }


    // Геттеры и сеттеры
}
