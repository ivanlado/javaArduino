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
    private SerialPort comPort;
    private int[] digitalPins;
    private int[] analogPins;
    private int[] analogReadPins;

    public Arduino() {
        inicializeComunication();
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
        try{
            //Contamos el nº de pines de cada tipo
            pin = (int) io.read();
            this.digitalPins = new int[pin];
            System.out.println(pin);
            pin = (int) io.read();
            this.analogPins = new int[pin];
            System.out.println(pin);
            pin = (int) io.read();
            this.analogReadPins = new int[pin];
            System.out.println(pin);
            //Leemos los pines
            for(int i=0; i<digitalPins.length && io.available()>0; i++){
                pin = (int) io.read();
                this.digitalPins[i] = pin;
                System.out.println(pin);
            }
            for(int i=0; i<analogPins.length && io.available()>0; i++){
                pin = (int) io.read();
                this.analogPins[i] = pin;
                System.out.println(pin);
            }
            for(int i=0; i<analogReadPins.length && io.available()>0; i++){
                pin = (int) io.read();
                this.analogReadPins[i] = abs(255-pin);
                System.out.println(pin);
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
                System.out.print(value);
                int kk = value? 1: 0;
                System.out.print(kk);
                out.write(kk);
            }
        } catch (Exception e) {
        }
    }

}
