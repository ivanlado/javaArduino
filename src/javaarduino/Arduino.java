package javaarduino;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Scanner;
import com.fazecast.jSerialComm.SerialPort;
import static java.lang.Math.abs;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Iván
 */

public class Arduino implements ArduinoComunication{
    private final static long iniTime = 2000;
    private SerialPort comPort;
    private int[] digitalPins;
    private int[] analogPins;
    private int[] analogReadPins;

    public Arduino() throws InterruptedException {
        inicializeComunication();
        obtainAvailablePorts();
    }
    
    public void inicializeComunication() throws InterruptedException{
        comPort = SerialPort.getCommPorts()[0];
        comPort.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        comPort.openPort();
        Thread.sleep(iniTime);
    }
    
    private void obtainAvailablePorts(){
        InputStream io = comPort.getInputStream();
        int pin;
        try{
            //Number of digital pins
            pin = (int) io.read();
            this.digitalPins = new int[pin];
            //Number of analog pins
            pin = (int) io.read();
            this.analogPins = new int[pin];
            //Number of analog read pins
            pin = (int) io.read();
            this.analogReadPins = new int[pin];
            //Pins are read
            for(int i=0; i<digitalPins.length && io.available()>0; i++){
                pin = (int) io.read();
                this.digitalPins[i] = pin;
            }
            for(int i=0; i<analogPins.length && io.available()>0; i++){
                pin = (int) io.read();
                this.analogPins[i] = pin;
            }
            for(int i=0; i<analogReadPins.length && io.available()>0; i++){
                pin = (int) io.read();
                this.analogReadPins[i] = pin;
            }
        } catch(IOException e){
        }
    }

    @Override
    public void finishComunication() {
        comPort.closePort();
    }

    @Override
    public void turnOn(int pin){
        turn(pin, true);
    }

    @Override
    public void turnOff(int pin) {
        turn(pin, false);
    }
    
    @Override
    public void turn(int pin, int value) {
        try {
            OutputStream out = comPort.getOutputStream();
            if(contains(analogPins, pin)){
                out.write(2);
                out.write(pin);
                out.write(value);
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void readData() {
        InputStream io = comPort.getInputStream();
        int c;
        try {
            c = (int) io.read();
            System.out.println(c);
        } catch (IOException ex) {
        }
    }
    
    private boolean contains(int[] pines, int pin){
        for(int i=0; i<pines.length; i++){
            if(pin==pines[i])
                return true;
        }
        return false;
    }
    
    private void turn(int pin, boolean value){
        try {
            OutputStream out = comPort.getOutputStream();
            if(contains(digitalPins, pin)){
                out.write(1);
                out.write(pin);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void readData(boolean print) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int readAnalogPin(int pin) {
        InputStream io = comPort.getInputStream();
        int c = 0;
        try {
            OutputStream out = comPort.getOutputStream();
            if(contains(analogReadPins, pin)){
                out.write(3);
                out.write(pin);
            }
        } catch (Exception e) {
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            c = (int) io.read();
            System.out.println(c);
        } catch (IOException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

}
