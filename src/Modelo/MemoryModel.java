/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

//import java.text.DecimalFormat;
//import java.text.NumberFormat;
import tarea1.*;
import java.util.LinkedList;

/**
 *
 * @author jmque
 */
public class MemoryModel {
    LinkedList<Double> memory;
    int size;

    public MemoryModel() {
        this.memory = new LinkedList<Double>();
    }
    
    public void addNumber(Double n){
        if (memory.size() == 10)
            memory.removeFirst();
        memory.add(n);
        //System.out.println(memory.toString());
    }
    public double average(){
        double sum = 0;
        for (int i = 0; i < memory.size(); i++)
            sum += memory.get(i);
        return sum / memory.size();
    }
    public boolean isEmpty(){
        return memory.isEmpty();
    }
    
    public String toString(String operation) {
        //NumberFormat format = new DecimalFormat("0.#");
        String str = "";
        for (int i = 0; i < memory.size(); i++)
            str += fmt(memory.get(i)) + " ";
        
        if (operation.equals("add"))
            return "M+ " + fmt(memory.getLast()) + " > " + str + "\n";
        return "Avg " + str + "= " + fmt(average()) + "\n";
    }
    public String fmt(double d){
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
    
}
