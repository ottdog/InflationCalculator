package com.ottdog.inflationCalculator.Data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class InflationDataResponse {
    private String normalizationLabel;

    private List<Datapoint> data;

    /**
     * Constructor from MonetaryData class
     * @param inflationAdjustedData computed inflation data
     */
    public InflationDataResponse(MonetaryData inflationAdjustedData){
        data = new ArrayList<>();

        List<String> labels = inflationAdjustedData.getDataLabels();
        labels.forEach(label -> {
            data.add(new Datapoint(label, inflationAdjustedData.get(label)));
        });
    }

}
