package com.cnpm.workingspace.sdo;

public class DateStatus {
    int day;
    boolean status;

    public DateStatus() {
    }

    public DateStatus(int day, boolean status) {
        this.day = day;
        this.status = status;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DateStatus{" +
                "day=" + day +
                ", status=" + status +
                '}';
    }
}
