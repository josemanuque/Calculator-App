/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javax.swing.JOptionPane;



/**
 *
 * @author jmque
 */
public class OperationModel {
    private double number1;
    private double number2;
    private double result;
    private boolean numIsSet;
    private String binary;
    private boolean boolPrime;
    private FileManagerModel file = new FileManagerModel();
    private MemoryModel memory = new MemoryModel();
    String filePath = System.getProperty("user.dir") + "\\Bitacora.txt";

    public OperationModel() {
    }

    
    public void sum(){
        //System.out.println("Numero 1: " + number1 + "\t Numero 2: "+ number2);
        result = number1 + number2;
    }
    public void sub(){
        result = number1 - number2;
    }
    public void mul(){
        result = number1 * number2;
    }
    public void div(){
        result = number1 / number2;
    }
    
    public boolean number1IsSet(){
       return numIsSet;
    }
    
    public boolean isPrime(double num){
        for (int i = 2; i <= num / 2; ++i) {
        // for nonprime number
            if (num % i == 0) {
                return false;
            } // prime number
        }boolPrime = true;
        return true;
    }
    
    public String toBinary(double num){
        binary = Integer.toBinaryString((int)num);
        return binary;

    }

    public double getNumber1() {
        return number1;
    }

    public void setNumber1(double number1) {
        this.number1 = number1;
        numIsSet = true;
    }

    public double getNumber2() {
        return number2;
    }

    public void setNumber2(double number2) {
        this.number2 = number2;
    }

    public double getResult() {
        return result;
    }

    public void setNumIsSet(boolean numIsSet) {
        this.numIsSet = numIsSet;
    }

    public String fmt(double d){
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
    
    public String dataLog(String symbol) {
        String str;
        if (symbol.equals("=")){
            str = fmt(number2) + " = " + fmt(number2) + "\n";
            file.writeToFile(filePath, str);
            return file.readFile(filePath);
        }
        str = fmt(number1) + " " +symbol + " "+ fmt(number2) + " = " + fmt(result) + "\n";
        file.writeToFile(filePath, str);
        //dataFrame.setTxa(dataSave);
        return file.readFile(filePath);
        //System.out.println(file.readFile(filePath));
    }
    public String givePastOperations(){
        return file.readFile(filePath);
    }
    
    public String addNumberToMemory(String data){
        memory.addNumber(Double.parseDouble(data));
        String line = memory.toString("add");
        file.writeToFile(filePath, line);
        return line;
    }

    public String datalog(String symbol, double num){
        //NumberFormat format = new DecimalFormat("0.#");
        String str;
        if (symbol.equals("bin")){
            str = "Binario " + fmt(num) + " = " + binary + "\n";
            file.writeToFile(filePath, str);
            return str;
        }
        if (symbol.equals("prime")){
            str = "Primo " + fmt(num) + " " + boolPrime + "\n";
            file.writeToFile(filePath, str);
            return str;
        }
        return "";
    }
    public void setResult(double result) {
        this.result = result;
    }
    public Double obtainAverage(){
        file.writeToFile(filePath, memory.toString("avg"));
        return memory.average();
    }
    public boolean memoryEmpty(){
        return memory.isEmpty();
    }
}
