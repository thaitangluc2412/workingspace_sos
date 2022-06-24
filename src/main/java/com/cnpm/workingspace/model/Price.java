package com.cnpm.workingspace.model;

import javax.persistence.*;

@Entity
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id", nullable = false)
    private Integer id;

    @Column(name = "hour_price")
    private Double hourPrice;

    @Column(name = "day_price")
    private Double dayPrice;

    @Column(name = "week_price")
    private Double weekPrice;

    @Column(name = "month_price")
    private Double monthPrice;

    public Price() {
    }

    public Price(Double hourPrice, Double dayPrice, Double weekPrice, Double monthPrice) {
        this.hourPrice = hourPrice;
        this.dayPrice = dayPrice;
        this.weekPrice = weekPrice;
        this.monthPrice = monthPrice;
    }

    public Double getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(Double monthPrice) {
        this.monthPrice = monthPrice;
    }

    public Double getWeekPrice() {
        return weekPrice;
    }

    public void setWeekPrice(Double weekPrice) {
        this.weekPrice = weekPrice;
    }

    public Double getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(Double dayPrice) {
        this.dayPrice = dayPrice;
    }

    public Double getHourPrice() {
        return hourPrice;
    }

    public void setHourPrice(Double hourPrice) {
        this.hourPrice = hourPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
               "id = " + id + ", " +
               "hourPrice = " + hourPrice + ", " +
               "dayPrice = " + dayPrice + ", " +
               "weekPrice = " + weekPrice + ", " +
               "monthPrice = " + monthPrice + ")";
    }
}