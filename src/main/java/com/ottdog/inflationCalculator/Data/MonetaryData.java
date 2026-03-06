package com.ottdog.inflationCalculator.Data;

import com.ottdog.inflationCalculator.Util.DateLabelUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Data
@NoArgsConstructor
public class MonetaryData {
    HashMap<String, Double> data = new HashMap<>();

    /**
     * Constructor from request structure to fill out a user's nominal income data
     * @param request request from front end
     */
    public MonetaryData(InflationDataRequest request) {
        request.getData().forEach(datapoint -> {
            this.addDataPoint(datapoint.getLabel(), datapoint.getValue());
        });
        this.fillInDataGaps();

    }

    /**
     * Wrapper class for Map.get()
     * @param label Label for data to get
     * @return data point
     */
    public Double get(String label){
        return data.get(label);
    }

    /**
     * Gets an alphabetized list of data labels
     * @return alphabetized list of data labels
     */
    public List<String> getDataLabels(){
        return data.keySet().stream().sorted().toList();
    }

    /**
     * Adds a datapoint to the hashmap.
     * Doesn't accept null data or blank labels
     * Will overwrite a data point if it matches an existing label
     * @param label The label of the data point
     * @param value The value of the data point
     */
    public void addDataPoint(String label, Double value){
        if(StringUtils.isBlank(label) || Objects.isNull(value)){
            return;
        }
        data.put(label, value);
    }

    /**
     * Creates a console friendly table of data for printing
     * Sorted alphabetically by label
     * @return table string
     */
    public String toString(){
        List<String> labelList = getDataLabels();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\t======================\n");
        labelList.forEach(label-> {
            stringBuilder.append(getDataPointString(label, true));
        });
        stringBuilder.append("\t======================\n");
        return stringBuilder.toString();
    }

    /**
     * Crates a string that can be put into a CSV document with the table's data
     * Sorted alphabetically by label
     * Needs to be written to a file elsewhere
     * @return table CSV string
     */
    public String toCSV(){
        List<String> labelList = getDataLabels();

        StringBuilder stringBuilder = new StringBuilder();
        labelList.forEach(label-> {
            stringBuilder.append(getDataPointString(label, false));
        });
        return stringBuilder.toString();
    }

    /**
     * Generates a line for a table based on a key/value pair in the data hashmap
     * @param label The string representing the key of the data to make a row for
     * @param printToConsole True if this row is for a logging table, false for CSV
     * @return a string representing a row in a table
     */
    private String getDataPointString(String label, boolean printToConsole) {
        if(Objects.isNull(data.get(label))){
            return "";
        }

        if(printToConsole) {
            return String.format("\t%s\t|\t%s\n", label, data.get(label).toString());
        }
        return String.format("%s,%s\n", label, data.get(label).toString());
    }

    /**
     * Produces a new monetary data where the values in this monetary data (assumed to be nominal values)
     * are converted into inflation adjusted values for the specified year-month data label
     * @param inflationData MonetaryData object holding CPI inflation data
     * @param normalizationLabel The year-month to normalize to
     * @return
     */
    public MonetaryData adjustForInflation(MonetaryData inflationData, String normalizationLabel)
            throws IllegalArgumentException{
        //error if label doesn't exist
        if(Objects.isNull(inflationData.get(normalizationLabel))){
            String errorString = String.format("%s is not a valid inflation data label", normalizationLabel);
            System.err.println(errorString);
            throw new IllegalArgumentException(errorString);
        }

        double referenceCPI = inflationData.get(normalizationLabel);
        List<String> nominalDataLabels = this.getDataLabels();
        MonetaryData inflationAdjustedData = new MonetaryData();

        nominalDataLabels.forEach(label -> {
            if(Objects.isNull(inflationData.get(label))){
                System.out.printf("No inflation data for %s%n", label);
                return;
            }

            Double nominalDataPoint = this.get(label);
            Double contemporaryCPI = inflationData.get(label);
            Double adjustedDataPoint = nominalDataPoint * referenceCPI / contemporaryCPI;

            inflationAdjustedData.addDataPoint(label, adjustedDataPoint);
        });

        return inflationAdjustedData;
    }

    /**
     * Creates a new MonetaryData object with data for each month since the first month present in the
     * original data set. If there is no data for a specific month, it's assumed to be the same
     * as the previous month.
     * @return new MonetaryData with gaps filled in
     */
    public MonetaryData fillInDataGaps(){
        if (data.isEmpty()){
            return new MonetaryData();
        }

        List<String> monthLabels = DateLabelUtil.generateMonthLabels();
        MonetaryData filledInData = new MonetaryData();

        AtomicReference<Double> lastDataPoint = new AtomicReference<>();
        monthLabels.forEach(month ->{
            if(Objects.isNull(data.get(month)) && Objects.isNull(lastDataPoint.get())){
                return;
            }

            if (!Objects.isNull(data.get(month))){
                lastDataPoint.set(data.get(month));
            }

            filledInData.addDataPoint(month, lastDataPoint.get());
        });

        return filledInData;
    }

}
