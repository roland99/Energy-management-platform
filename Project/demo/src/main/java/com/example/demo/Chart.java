package com.example.demo;

import com.example.demo.hessian.SensorReadDto;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Chart extends ApplicationFrame {

    private List<SensorReadDto> sensorReadDtos;

    public Chart( String applicationTitle , String chartTitle , List<SensorReadDto> sensorReadDtos) {
        super(applicationTitle);
        this.sensorReadDtos = sensorReadDtos;
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Date","kWh",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 1560 , 667 ) );
        setContentPane( chartPanel );

    }

    private DefaultCategoryDataset createDataset( ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        int a = 0;
        double diff = 0L;
        for(SensorReadDto s: sensorReadDtos){
            java.util.Date time=new java.util.Date((long)s.getTimestamp());
            String newstring = new SimpleDateFormat("yyyy-MM-dd HH:mm ").format(time);

            dataset.addValue( s.getMeasurement() - diff,"consumption",newstring);
            diff = s.getMeasurement();
//            a = a + 1;
//            if(a > 2){
//                break;
//            }
        }
        /*dataset.addValue( 15 , "schools" , "1970" );
        dataset.addValue( 30 , "schools" , "1980" );
        dataset.addValue( 60 , "schools" ,  "1990" );
        dataset.addValue( 120 , "schools" , "2000" );
        dataset.addValue( 240 , "schools" , "2010" );
        dataset.addValue( 300 , "schools" , "2014" );*/
        return dataset;
    }
}
