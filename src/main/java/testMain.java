import Data.MonetaryData;
import Util.DateLabelUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;

public class testMain {
    public static void main(String[] args){
        System.out.println("hello world");

        MonetaryData inflationData = new MonetaryData();
        inflationData.addDataPoint("1980-01", 77.8);
        inflationData.addDataPoint("2000-01", 168.8);
        inflationData.addDataPoint("2020-01", 257.971);

        MonetaryData nominalPay = new MonetaryData();
        nominalPay.addDataPoint("1980-01", 100d);
        nominalPay.addDataPoint("2000-01", 100d);
        nominalPay.addDataPoint("2020-01", 100d);

        MonetaryData in1980Dollars = nominalPay.adjustForInflation(inflationData, "1980-01");
        MonetaryData in2000Dollars = nominalPay.adjustForInflation(inflationData, "2000-01");
        MonetaryData in2020Dollars = nominalPay.adjustForInflation(inflationData, "2020-01");

        System.out.println(in1980Dollars);
        System.out.println(in2000Dollars);
        System.out.println(in2020Dollars);

        inflationData = new MonetaryData();
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

        nominalPay = new MonetaryData();
        nominalPay.addDataPoint("2023-01", 65000d);
        nominalPay.addDataPoint("2024-01", 69000d);
        nominalPay = nominalPay.fillInDataGaps();

        MonetaryData in2023Dollars = nominalPay.adjustForInflation(inflationData, "2023-01");
        MonetaryData inCurrentDollars = nominalPay.adjustForInflation(inflationData, "2025-08");

        System.out.println(in2023Dollars);
        System.out.println(inCurrentDollars);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        in2023Dollars.getDataLabels().forEach(label -> {
            dataset.addValue(in2023Dollars.get(label), "salary in 2023 dollars", label);
        });

        JFreeChart lineChart = ChartFactory.createLineChart(
                "My Salary in January 2023 dollars", // Chart title
                "Month", // X-Axis Label
                "2023 Dollar value", // Y-Axis Label
                dataset // Dataset for the Chart
        );

        try {
            ChartUtils.saveChartAsPNG(new File(System.getProperty("user.home") + File.separator
                    + "Pictures" + File.separator + "linechart.png"), lineChart, 1920, 1080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}