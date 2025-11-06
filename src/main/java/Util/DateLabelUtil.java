package Util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateLabelUtil {
    //The earliest data that the BLS CUUR0000SA0 dataset has
    private static final Integer START_YEAR = 1913;

    private static final Integer MONTH_COUNT = 12;

    /*
     * Upperbound year to prevent the generation loop from going on too long in case my loop exit logic fails.
     * If this calculator is still being used in the year 2100, the code's open source and you future folk
     * can update it.
     */
    private static final Integer END_YEAR = 2100;

    private static final String MONTH_LABEL_FORMAT = "%04d-%02d";

    public static List<String> generateMonthLabels() {
        LocalDate currentDate = LocalDate.now();
        List<String> monthLabels = new ArrayList<>();

        int activeYear = START_YEAR;
        while (activeYear < END_YEAR){
            int activeMonth = 1;
            while (activeMonth <= MONTH_COUNT){
                monthLabels.add(String.format(MONTH_LABEL_FORMAT, activeYear, activeMonth));

                //return if we reach today
                if(activeYear == currentDate.getYear() && activeMonth == currentDate.getMonthValue()){
                    return monthLabels;
                }
                activeMonth++;
            }
            activeYear++;
        }

        return monthLabels;
    }
}