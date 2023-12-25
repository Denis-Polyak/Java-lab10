package com.example.web114.beans;

import com.example.web114.entities.Tabulation;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;

import static java.lang.Math.round;

@Data
@Named
@SessionScoped
public class TabulationBean implements Serializable {
    private Tabulation tabulation = new Tabulation();

    private double maxiY;
    private double miniY;
    private double maxiX;
    private double miniX;
    private double suma;
    private double arifmetic;
    private int Steps;


    public int steps(double end, double start, double step){
        return (int) round ((end-start)/step)+1;
    }
    public double[] massiveX (double end, double start, double step) {
        double [] arr = new double[steps(end, start, step)];
        for (int i = 0; i < arr.length; i++)
            arr[i] = start + step*i;
        return arr;
    }
    public double calcY (double x, double a, double p){
        double y, eps = 0.0001;
        if(x < 1.3 - eps)
            y = p*x*x-(7/(x*x));
        else if (x > 1.3 + eps)
            y = Math.log(x+7*Math.sqrt(Math.abs(x+a)));
        else
            y = a * Math.pow(x,3)+7*Math.sqrt(x);
        return y;
    }
    public double[] massiveY (double [] arr, double a, double p) {
        double [] ara = new double[arr.length];
        for (int i = 0; i < ara.length; i++)
            ara[i] = calcY(arr[i], a, p);
        return ara;
    }
    public int maxY (double [] ara) {
        int maxi = 0;
        double max = ara[0];
        for (int i = 1; i < ara.length; i++)
            if (ara[i] > max) {
                max = ara[i];
                maxi = i;
            }
        return maxi;
    }
    public double getMaxY(double[] ara) {
        int maxY = maxY(ara);
        return ara[maxY];
    }
    public double getMaxX(double[] ara, double[] arr) {
        int maxY = maxY(ara);
        return arr[maxY];
    }
    public int minY (double [] ara) {
        int mini = 0;
        double min = ara[0];
        for (int i = 1; i < ara.length; i++)
            if (ara[i] < min) {
                min = ara[i];
                mini = i;
            }
        return mini;
    }
    public double getMinY(double[] ara) {
        int minY = minY(ara);
        return ara[minY];
    }
    public double getMinX(double[] ara, double[] arr) {
        int minY = minY(ara);
        return arr[minY];
    }
    public double sum (double [] ara) {
        double sum = 0.0;
        for (int i = 0; i < ara.length; i++)
            sum += ara[i];
        return sum;
    }
    public double arifm (double [] ara) {
        double sum = sum(ara);
        double average = 0;
        average = sum / ara.length;
        return average;
    }



    public String calculate() {
        double p = 3.14;
        Steps = steps(tabulation.getEnd(),tabulation.getStart(),tabulation.getStep());
        double [] arr = massiveX(Steps, tabulation.getStart(), tabulation.getStep());
        double [] ara = massiveY (arr, tabulation.getA(), p);
        maxiY = getMaxY(ara);
        miniY = getMinY(ara);
        maxiX = getMaxX(ara,arr);
        miniX = getMinX(ara,arr);
        suma = sum(ara);
        arifmetic = arifm(ara);

        return "tab";
    }
}
