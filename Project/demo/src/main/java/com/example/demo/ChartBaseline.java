package com.example.demo;

import com.example.demo.hessian.SensorReadDto;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChartBaseline extends ApplicationFrame {

    private List<SensorReadDto> sensorReadDtos;

    public ChartBaseline( String applicationTitle , String chartTitle , List<SensorReadDto> sensorReadDtos) {
        super(applicationTitle);
        this.sensorReadDtos = sensorReadDtos;
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Date","kWh",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 1460 , 607 ) );
        setContentPane( chartPanel );

    }

    private DefaultCategoryDataset createDataset( ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        int a = 0;
        String newstring = new String();
        String actual = new String();
        for(int i=0; i<24; i = i + 1) {
            double avg = 0.0;
            double diff = 0L;

            for (SensorReadDto s : sensorReadDtos) {
                java.util.Date time = new java.util.Date((long) s.getTimestamp());
                newstring = new SimpleDateFormat("HH ").format(time);

                if(i < 10){
                    if(newstring.contains('0'+ Integer.toString(i))){
                        avg = avg + s.getMeasurement() - diff;
                        actual = newstring;
                    }
                }else{
                    if(newstring.contains(Integer.toString(i))){
                        avg = avg + s.getMeasurement() - diff;
                        actual = newstring;
                    }
                }


                diff = s.getMeasurement();
            }
            avg = avg/7;

            dataset.addValue(avg, "consumption", actual);
        }

        return dataset;
    }
}