package org.example;

public class Payment {
    private int month;
    private double principalPayment;
    private double interestPayment;
    private double remainingBalance;

    public Payment(int month, double principalPayment, double interestPayment, double remainingBalance) {
        this.month = month;
        this.principalPayment = principalPayment;
        this.interestPayment = interestPayment;
        this.remainingBalance = remainingBalance;
    }

    // Геттеры и сеттеры

    @Override
    public String toString() {
        return "Payment{" +
                "month=" + month +
                ", principalPayment=" + principalPayment +
                ", interestPayment=" + interestPayment +
                ", remainingBalance=" + remainingBalance +
                '}';
    }

    // Геттеры и сеттеры

    public double getTotalPayment() {
        return principalPayment + interestPayment;
    }
    public int getMonth(){
        return month;
    }
    public double getPrincipalPayment() {
        return principalPayment;
    }

    public double getInterestPayment() {
        return interestPayment;
    }
}
