package ex07;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

class ChartObserver implements Observer {
    private XYChart chart;
    private JFrame chartPanel;
    Collection data = new Collection();

    public ChartObserver() {
        chart = new XYChartBuilder().width(500).height(300).title("Обрахунок енергії").xAxisTitle("Обрахунок").yAxisTitle("Енергія").build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.addSeries("Результат", new double[] {0}, new double[] {0});
        data.addObserver(this);
        chartPanel = new SwingWrapper(chart).displayChart();
    }
    
    public void addPoint(double point){
        data.addData(point);
    }
    
    @Override
    public void update(java.util.Observable o, Object arg) {
        System.out.println("UPDATE");
        List<Double> xData = new ArrayList<Double>();
        for (double i = 0; i < data.getData().size(); i++) {
            xData.add(i);
        }
        chart.updateXYSeries("Результат", xData, data.getData(), null);
        chartPanel.repaint();
    
    }
}

public class Test {
    public static void main(String[] args) {
        ChartObserver chartObserver = new ChartObserver();
        
        while(true){
            EnergyCalculator calc = new EnergyCalculator();
            Scanner in = new Scanner(System.in);
            System.out.println("enter mass:");
            double mass = Double.parseDouble(in.nextLine());
            System.out.println("enter height:");
            double height = Double.parseDouble(in.nextLine());
            var res = calc.calculateEnergy(mass, height);
            System.out.println("result: " + res.getEnergy());
            chartObserver.addPoint(res.getEnergy());
        }
    }
}

class Collection extends Observable{
        private List<Double> data = new ArrayList<>();
        
        Collection(){
            
        }
        
        public void addData(double data){
            this.data.add(data);
            super.setChanged();
            super.notifyObservers();
        }
        
        public List<Double> getData(){
            return data;
        }
    } 
