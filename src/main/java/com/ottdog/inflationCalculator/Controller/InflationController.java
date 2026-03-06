package com.ottdog.inflationCalculator.Controller;


import com.ottdog.inflationCalculator.Data.InflationDataRequest;
import com.ottdog.inflationCalculator.Data.InflationDataResponse;
import com.ottdog.inflationCalculator.Service.InflationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InflationController {

    private InflationService inflationService;

    @Autowired
    public InflationController(InflationService inflationService){
        this.inflationService = inflationService;
    }

    @PostMapping(value = "/inflation", produces = MediaType.APPLICATION_JSON_VALUE)
    public InflationDataResponse adjustForInflation(@RequestBody InflationDataRequest request) {
        return inflationService.adjustForInflation(request);
    }
}