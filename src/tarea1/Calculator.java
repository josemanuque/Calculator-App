/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea1;

import Controlador.OperationController;
import Modelo.OperationModel;
import Modelo.MemoryModel;
import Vista.PantallaPrincipal;
import Vista.PantallaData;


/**
 *
 * @author jmque
 */


public class Calculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PantallaPrincipal vista = new PantallaPrincipal();
        OperationModel modelo = new OperationModel();
        PantallaData vistaData = new PantallaData();
        OperationController controlador = new OperationController(vista, modelo, vistaData);
        
        
        controlador.showView();
        
    }
    
}