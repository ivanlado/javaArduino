package javaarduino;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iván
 */

public class Arduino implements ArduinoComunication{
    private SerialPort comPort;
    private final ArrayList<Integer> pines;

    public Arduino() {
        inicializeComunication();
        this.pines = new ArrayList<Integer>();
        obtainAvailablePorts();
    }

    
    
    @Override
    public void inicializeComunication() {
        comPort = SerialPort.getCommPorts()[0];
        comPort.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        comPort.openPort();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void obtainAvailablePorts(){
        InputStream io = comPort.getInputStream();
        int pin;
        try {
            while(io.available()>0){
                pin = (int) io.read();
                pines.add(pin);
            }
        } catch (IOException ex) {
        }
        
    }

    @Override
    public void finishComunication() {
        comPort.closePort();
    }

    @Override
    public void turnOn(int pin){
        try {
            OutputStream out = comPort.getOutputStream();
            if(pines.contains(pin))
                out.write(pin);
        } catch (Exception e) {
        }
    }

    @Override
    public void turnOff(int pin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void readData() {
        InputStream io = comPort.getInputStream();
        int c;
        try {
            c = (int) io.read();
            System.out.println(c);
        } catch (IOException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void readData(boolean print) {
        
    }

}
