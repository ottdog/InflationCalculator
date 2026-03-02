package Util;

public class WagesUtil {
    private static final Integer STANDARD_WORK_WEEK = 40;
    private static final Double STANDARD_OVERTIME_RATE = 1.5;
    private static final Integer WEEKS_IN_YEAR = 52;

    public static Double hourlyToSalary(Double hourlyRate){
        return hourlyRate * STANDARD_WORK_WEEK * WEEKS_IN_YEAR;
    }

    public static Double hourlyToSalary(Double hourlyRate, Integer averageHoursPerWeek){
        if (averageHoursPerWeek <= STANDARD_WORK_WEEK){
            return hourlyRate * averageHoursPerWeek * WEEKS_IN_YEAR;
        }

        int overtimeHours = averageHoursPerWeek - STANDARD_WORK_WEEK;
        double overtimePay = overtimeHours * hourlyRate * STANDARD_OVERTIME_RATE * WEEKS_IN_YEAR;
        return overtimePay + hourlyToSalary(hourlyRate);
    }
}
