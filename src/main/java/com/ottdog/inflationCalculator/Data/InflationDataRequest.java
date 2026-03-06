package com.ottdog.inflationCalculator.Data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InflationDataRequest {
    private String normalizationLabel;

    private List<Datapoint> data;
}
