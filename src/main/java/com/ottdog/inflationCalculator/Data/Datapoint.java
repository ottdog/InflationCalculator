package com.ottdog.inflationCalculator.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Datapoint {
    private String label;
    private Double value;
}
