/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import tarea1.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author diemo
 */
public class FileManagerModel {
    public void createFile (String path){
        File myObj = new File(path);
    }
    
    
    public void writeToFile(String path, String text){
        try {
          FileWriter myWriter = new FileWriter(path, true);
          myWriter.write(text);
          myWriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
    
    public String readFile(String filePath) 
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
        {
 
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) 
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } 
        catch (IOException e) 
        {
            //System.out.println("File not found");
            //e.printStackTrace();
        }
        return contentBuilder.toString();
    }
            
}
