/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Modelo.OperationModel;
import Vista.PantallaPrincipal;
import Vista.PantallaData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
/**
 *
 * @author jmque
 */
public class OperationController extends AbstractAction implements ActionListener{
    private OperationModel modelo;
    private PantallaPrincipal vista;
    private PantallaData vistaData;
    private String symbol = "=";
    private boolean operatorPressed;
    private boolean numPressed;
    private boolean binaryPressed;
    
    public OperationController(PantallaPrincipal vista, OperationModel modelo, PantallaData vistaData) {
        this.modelo = modelo;
        this.vista = vista;
        this.vistaData = vistaData;
        this.vistaData.setTxa(modelo.givePastOperations());
        initControls();
        btnSetFocusable();
        init();
    }
    
    private void init(){
        vista.txfInputDisplay.setEditable(false);
        //btnSetFocusable();
        vista.btnNum0.addActionListener(this);
        vista.btnNum1.addActionListener(this);
        vista.btnNum2.addActionListener(this);
        vista.btnNum3.addActionListener(this);
        vista.btnNum4.addActionListener(this);
        vista.btnNum5.addActionListener(this);
        vista.btnNum6.addActionListener(this);
        vista.btnNum7.addActionListener(this);
        vista.btnNum8.addActionListener(this);
        vista.btnNum9.addActionListener(this);
        vista.btnClear.addActionListener(this);
        vista.btnDecimal.addActionListener(this);
        vista.btnSum.addActionListener(this);
        vista.btnSubstract.addActionListener(this);
        vista.btnMultiplication.addActionListener(this);
        vista.btnDivision.addActionListener(this);
        vista.btnEquals.addActionListener(this);
        vista.btnAddMem.addActionListener(this);
        vista.btnAvg.addActionListener(this);
        vista.btnBinary.addActionListener(this);
        vista.btnPrime.addActionListener(this);
        vista.btnData.addActionListener(this);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(e.getActionCommand());
        if (e.getActionCommand().equals("0")){
            addDigit(0);
        }
        if (e.getActionCommand().equals("1")){
            addDigit(1);
        }
        if (e.getActionCommand().equals("2")){
            addDigit(2);
        }
        if (e.getActionCommand().equals("3")){
            addDigit(3);
        }
        if (e.getActionCommand().equals("4")){
            addDigit(4);
        }
        if (e.getActionCommand().equals("5")){
            addDigit(5);
        }
        if (e.getActionCommand().equals("6")){
            addDigit(6);
        }
        if (e.getActionCommand().equals("7")){
            addDigit(7);
        }
        if (e.getActionCommand().equals("8")){
            addDigit(8);
        }
        if (e.getActionCommand().equals("9")){
            addDigit(9);
        }
        if (e.getSource().equals(vista.btnClear) || e.getActionCommand().equals("c")){
            vista.setText("0");
            operatorPressed = false;
            numPressed = false;
        }
        if (e.getSource().equals(vista.btnDecimal) || e.getActionCommand().equals(".")){
            if (operatorPressed){
                vista.setText("0");
                operatorPressed = false;
                numPressed = true;
            }
            if (!vista.getText().contains(".")) {    
                String number = vista.getText() + vista.btnDecimal.getText();
                vista.setText(number);
            }
        }
        if (e.getSource().equals(vista.btnEquals) || e.getActionCommand().equals("=") || e.getActionCommand().equals("\n")){
            if (!vista.getText().contains("false") && !vista.getText().contains("true")){
                operatorPressed = true;
                doOperation();
            }
        }
        if (e.getSource().equals(vista.btnSum) || e.getActionCommand().equals("+")){
            operator("+");
        }
        if (e.getSource().equals(vista.btnSubstract) || e.getActionCommand().equals("-")){
            if (!numPressed && !operatorPressed)
                vista.setText("-");
            else
                operator("-");
        }
        if (e.getSource().equals(vista.btnMultiplication) || e.getActionCommand().equals("*") || e.getActionCommand().equals("x")){
            operator("*");
        }
        if (e.getSource().equals(vista.btnDivision) || e.getActionCommand().equals("/")){
            operator("/");
        }
        if (e.getSource().equals(vista.btnData)){
            vistaData.setTxa(modelo.givePastOperations());
            vistaData.setVisible(true);
        }
        if (e.getSource().equals(vista.btnAddMem)){
            String data = vista.getText();
            operatorPressed = true;
            if (!data.equals("") && numPressed) {
                if(data.replace(".", "").length() <= 12){
                    vistaData.setTxa(modelo.addNumberToMemory(data));
                }else
                    vista.errorGuardado();
            }
        }
        if (e.getSource().equals(vista.btnAvg)){
                if (!modelo.memoryEmpty()){
                double average = modelo.obtainAverage();
                NumberFormat format = new DecimalFormat("0.#####");
                if (format.format(average).replace(".", "").length() > 21){
                    vista.setText("ERROR");
                    vista.showMaxErrorMessage();
                    modelo.setNumber1(0);
                    symbol = "=";
                    return;
                }
                vista.setText(format.format(average));
                vistaData.setTxa(modelo.givePastOperations());
            }
        }
        if (e.getSource().equals(vista.btnPrime)){
            doRemainingOperation();
            String input = vista.getText();
            if (isNumeric(input) && numPressed){ 
                if (!input.contains(".")){
                    double number = Double.parseDouble(input);
                    boolean isPrime = modelo.isPrime(number);
                    vista.setText("" + isPrime);
                    modelo.datalog("prime", number);
                    vistaData.setTxa(modelo.givePastOperations());
                    operatorPressed = true;
                    numPressed = false;
                }
                else
                    vista.errorNoEntero();

            }
        }
        if (e.getSource().equals(vista.btnBinary)){
            doRemainingOperation();
            String input = vista.getText();
            if (isNumeric(input) && numPressed){ 
                if (!input.contains(".")){
                    double number = Double.parseDouble(input);;
                    String binary = modelo.toBinary(number);
                    vista.setText(binary);
                    modelo.datalog("bin", number);
                    vistaData.setTxa(modelo.givePastOperations());
                    operatorPressed = true;
                    numPressed = false;
                    binaryPressed = true;
                }
                else
                    vista.errorNoEntero();

            }
        }
    }
    
    private void initControls(){
        InputMap im = vista.getRootPane().getInputMap();
        ActionMap am = vista.getRootPane().getActionMap();
        im.put(KeyStroke.getKeyStroke("0"), "");
        im.put(KeyStroke.getKeyStroke("1"), "");
        im.put(KeyStroke.getKeyStroke("2"), "");
        im.put(KeyStroke.getKeyStroke("3"), "");
        im.put(KeyStroke.getKeyStroke("4"), "");
        im.put(KeyStroke.getKeyStroke("5"), "");
        im.put(KeyStroke.getKeyStroke("6"), "");
        im.put(KeyStroke.getKeyStroke("7"), "");
        im.put(KeyStroke.getKeyStroke("8"), "");
        im.put(KeyStroke.getKeyStroke("9"), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0 , 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DECIMAL, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), "");
        am.put("", this);
        
    }
    
    /*
    Método que detecta si un string es numérico.
    -Retorna true si es númerico, false si no.
    */
    private static boolean isNumeric(String str) { 
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    
    /*
    Método encargado de realizar la operación con dos números dados.
    -Manda a guardar en archivo el log de operaciones.
    * Si se le da uno hace num = num
    ** Si no hay operacion retorna
    */
    private void doOperation() {
        if (symbol.equals(""))
            return;
        String number = vista.getText();
        modelo.setNumber2(Double.parseDouble(number));
        switch (symbol) {
            case "+" -> modelo.sum();
            case "-" -> modelo.sub();
            case "*" -> modelo.mul();
            case "/" -> modelo.div();
            case "=" -> modelo.setResult(modelo.getNumber2());
        }
        //Save data
        String result = modelo.getResult() + "";
        if (result.equals("Infinity")){
            vista.setText("Cannot divide by zero");
            modelo.setNumber1(0);
            symbol = "=";
            return;
        }
        if (result.replace(".", "").length() > 21){
            vista.setText("ERROR");
            vista.showMaxErrorMessage();
            modelo.setNumber1(0);
            symbol = "=";
            return;
        }
        String dataSave = modelo.dataLog(symbol);
        
        vistaData.setTxa(dataSave);
        // Rearrange numbers in Operation Class
        modelo.setNumber1(modelo.getResult());
        modelo.setNumber2(0);
        // Clear Display
        vista.setText("");
        // Set display with the result
        //txfInputDisplay.setText(format.format(operations.getResult()));
        vista.setText(fmt(modelo.getResult()));
        symbol = "=";
        //System.out.println(operatorPressed);
        
    }
    public String fmt(double d){
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
    /*
    Método que realiza la operación pendiente cuando se presiona un operador.
    */
    private void doRemainingOperation() {
        if (numPressed && !vista.getText().equals("") && modelo.number1IsSet() && !symbol.equals("=")){
            doOperation();
            operatorPressed = true;
        }
    }
    /* 
    Método encargado de guardar el primer numero del display en atributo 
    number1 de la clase Operation;
    */
    private void saveNum1(){
        String number = vista.getText();
        modelo.setNumber1(Double.parseDouble(number));
        operatorPressed = true;
        //txfInputDisplay.setText("");
    }
    /*
    Método que chequea que un número sea menor o igual a 12
    */
    private boolean checkInputLen(){
        int len = vista.getText().replace(".", "").length();
        return len < 12;
    }
    /*
    Método que prepara el textField cuando un número es presionado
    */
    private boolean prepareTxf(){
        if (operatorPressed || (/*!operations.number1IsSet() && */vista.getText().equals("0")))
            vista.setText("");
        if (!checkInputLen())
            return false;
        operatorPressed = false;
        numPressed = true;
        binaryPressed = false;
        return true;
    }
    
    // ---------------------Operator---------------------   
    /*
    Método que recibe un String del operador y realiza la operacion
    */
    private void operator(String operator){
        doRemainingOperation();
        String input = vista.getText();
        if (!input.equals("") && isNumeric(input) && (numPressed || operatorPressed) && !binaryPressed && !vista.getText().equals("Cannot divide by zero")){
            symbol = operator;
            saveNum1();
        }
        numPressed = false;
        binaryPressed = false;
    }
    //---------------------Input Display---------------------
    /*
    Método encargado de añadir el número presionado al display de la calculadora
    */
    private void addDigit(int digit){
        if (!prepareTxf())
            return;
        vista.btnSum.setBackground(null);
        vista.btnSubstract.setBackground(null);
        vista.btnMultiplication.setBackground(null);
        vista.btnDivision.setBackground(null);
        String number = vista.getText() + digit;
        vista.setText(number);
    }
    
    
    
    public void showView(){
        vista.setVisible(true);
    }

    
    private void btnSetFocusable() {
        vista.btnNum0.setFocusable(false);
        vista.btnNum1.setFocusable(false);
        vista.btnNum2.setFocusable(false);
        vista.btnNum3.setFocusable(false);
        vista.btnNum4.setFocusable(false);
        vista.btnNum5.setFocusable(false);
        vista.btnNum6.setFocusable(false);
        vista.btnNum7.setFocusable(false);
        vista.btnNum8.setFocusable(false);
        vista.btnNum9.setFocusable(false);
        vista.btnSum.setFocusable(false);
        vista.btnSubstract.setFocusable(false);
        vista.btnMultiplication.setFocusable(false);
        vista.btnDivision.setFocusable(false);
        vista.btnEquals.setFocusable(false);
        vista.btnPrime.setFocusable(false);
        vista.btnBinary.setFocusable(false);
        vista.btnAddMem.setFocusable(false);
        vista.btnAvg.setFocusable(false);
        vista.btnData.setFocusable(false);
        vista.btnDecimal.setFocusable(false);
        vista.btnClear.setFocusable(false);
    }
    

}
