package com.cnpm.workingspace.utils;

import com.cnpm.workingspace.exception.DayMonthIntervalException;
import com.cnpm.workingspace.model.Price;
import com.cnpm.workingspace.service.PriceService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

public class PriceUtils {
    public static double calcPrice(int id, String startDate, String endDate, PriceService priceService){
        Pair<Integer,Integer> dayMonthInterval=getDayMonthInterval(startDate,endDate);
        int cntDay=dayMonthInterval.first;
        int cntMonth=dayMonthInterval.second;
        Optional<Price> priceOptional=priceService.getPriceById(id);
        if(priceOptional.isPresent()){
            Price price=priceOptional.get();
            double ans=price.getDayPrice()*cntDay+price.getMonthPrice()*cntMonth;
            return ans;
        }
        return -1.0;
    }

    public static Pair<Integer,Integer> getDayMonthInterval(String startDate, String endDate) {
        LocalDate stDate = LocalDate.parse(startDate);
        LocalDate enDate = LocalDate.parse(endDate);
        if (stDate.isAfter(enDate)) {
            throw new DayMonthIntervalException();
        } else {
            int cntDay = 0;
            int extraMonth = 0;
            if (stDate.getDayOfMonth() <= enDate.getDayOfMonth()) {
                cntDay = enDate.getDayOfMonth() - stDate.getDayOfMonth() + 1;
                if(stDate.getMonthValue()<enDate.getMonthValue()) extraMonth = 1;
            } else {
                YearMonth stMonth = YearMonth.from(stDate);
                YearMonth enMonth = YearMonth.from(enDate);
                LocalDate enDateOfMonth = stMonth.atEndOfMonth();
                LocalDate stDateOfMonth = enDate.withDayOfMonth(1);
                int cntDayFirst = enDateOfMonth.getDayOfMonth() - stDate.getDayOfMonth() + 1;
                int cntDayLast = enDate.getDayOfMonth() - stDateOfMonth.getDayOfMonth() + 1;
                System.out.println("cnt day first : " + cntDayFirst);
                System.out.println("cnt day last : " + cntDayLast);
                cntDay += cntDayFirst + cntDayLast;
            }
            int cntMonth = enDate.getMonthValue() - stDate.getMonthValue() - 1;
            int extraYear = 0;
            if (cntMonth < -1 && enDate.getYear() > stDate.getYear()) {
                cntMonth += 12;
            } else if(cntMonth>-1) {
                if(enDate.getYear() > stDate.getYear()) extraYear += 1;
            } else{
                cntMonth=0;
            }
            System.out.println("after : "+cntMonth);
            cntMonth += extraMonth;
            if (cntMonth >= 12) {
                extraYear += 1;
                cntMonth -= 12;
            }
            if (cntMonth < 0) {
                cntMonth += 12;
                extraYear -= 1;
            }
            System.out.println("debug : extra year : "+extraYear);
            int cntYear = Math.max(enDate.getYear() - stDate.getYear() - 1, 0);
            cntYear += extraYear;
            System.out.println("cntDay : " + cntDay);
            System.out.println("cntMonth : " + cntMonth);
            System.out.println("cntYear : " + cntYear);
            int totalMonth = cntYear * 12 + cntMonth;
            return new Pair<>(cntDay,totalMonth);
        }
    }

    static class Pair<T,U>{
        public T first;
        public U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }
}
