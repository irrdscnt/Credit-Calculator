package org.example;

public class OverduePayment {
    private int month;
    private double penalty;

    public OverduePayment(int month, double penalty) {
        this.month = month;
        this.penalty = penalty;
    }

    // Геттеры и сеттеры
    public int getMonth(){
        return month;
    }
    public double getPenalty(){
        return penalty;
    }
    @Override
    public String toString() {
        return "OverduePayment{" +
                "month=" + month +
                ", penalty=" + penalty +
                '}';
    }

    // Геттеры и сеттеры
}
