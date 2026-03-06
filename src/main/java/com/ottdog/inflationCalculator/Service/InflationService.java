package com.ottdog.inflationCalculator.Service;

import com.ottdog.inflationCalculator.Data.InflationDataRequest;
import com.ottdog.inflationCalculator.Data.InflationDataResponse;
import com.ottdog.inflationCalculator.Data.MonetaryData;
import org.springframework.stereotype.Service;

@Service
public class InflationService {

    //todo change to Try<> with database update
    //todo javadocs
    public InflationDataResponse adjustForInflation(InflationDataRequest request){
        MonetaryData nominalIncomeData = new MonetaryData(request);
        MonetaryData filledInNominalIncomeData = nominalIncomeData.fillInDataGaps();
        MonetaryData inflationData = getInflationData();
        MonetaryData inflationAdjustedData = filledInNominalIncomeData.adjustForInflation(inflationData, request.getNormalizationLabel());

        InflationDataResponse response = new InflationDataResponse(inflationAdjustedData);
        response.setNormalizationLabel(request.getNormalizationLabel());
        return response;
    }


    //Todo make this get data from database/BLS API
    //Todo Try<>
    //Todo javadocs
    private MonetaryData getInflationData(){
        MonetaryData inflationData = new MonetaryData();
        inflationData.addDataPoint("2023-01", 299.170);
        inflationData.addDataPoint("2023-02", 300.840);
        inflationData.addDataPoint("2023-03", 301.836);
        inflationData.addDataPoint("2023-04", 303.363);
        inflationData.addDataPoint("2023-05", 304.127);
        inflationData.addDataPoint("2023-06", 305.109);
        inflationData.addDataPoint("2023-07", 305.691);
        inflationData.addDataPoint("2023-08", 307.026);
        inflationData.addDataPoint("2023-09", 307.789);
        inflationData.addDataPoint("2023-10", 307.671);
        inflationData.addDataPoint("2023-11", 307.051);
        inflationData.addDataPoint("2023-12", 306.746);
        inflationData.addDataPoint("2024-01", 308.417);
        inflationData.addDataPoint("2024-02", 310.326);
        inflationData.addDataPoint("2024-03", 312.332);
        inflationData.addDataPoint("2024-04", 313.548);
        inflationData.addDataPoint("2024-05", 314.069);
        inflationData.addDataPoint("2024-06", 314.175);
        inflationData.addDataPoint("2024-07", 314.540);
        inflationData.addDataPoint("2024-08", 314.796);
        inflationData.addDataPoint("2024-09", 315.301);
        inflationData.addDataPoint("2024-10", 315.664);
        inflationData.addDataPoint("2024-11", 315.493);
        inflationData.addDataPoint("2024-12", 315.605);
        inflationData.addDataPoint("2025-01", 317.671);
        inflationData.addDataPoint("2025-02", 319.082);
        inflationData.addDataPoint("2025-03", 319.799);
        inflationData.addDataPoint("2025-04", 320.795);
        inflationData.addDataPoint("2025-05", 321.465);
        inflationData.addDataPoint("2025-06", 322.561);
        inflationData.addDataPoint("2025-07", 323.048);
        inflationData.addDataPoint("2025-08", 323.976);
        inflationData.addDataPoint("2025-09", 324.800);
        inflationData.addDataPoint("2025-10", 324.800);
        inflationData.addDataPoint("2025-11", 324.122);
        inflationData.addDataPoint("2025-12", 324.054);
        inflationData.addDataPoint("2026-01", 325.252);

        return inflationData;
    }
}
