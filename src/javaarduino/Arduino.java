package javaarduino;

import javaarduino.exceptions.ArduinoError;
import javaarduino.exceptions.IncorrectPinError;
import javaarduino.exceptions.WritingError;
import javaarduino.exceptions.ReadingError;
import javaarduino.exceptions.IniError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.fazecast.jSerialComm.SerialPort;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Esta clase implementa el interfaz #ArduinoComunication
 * @author Iván
 */



public class Arduino implements ArduinoComunication{
    private SerialPort comPort;
    private int[] digitalPins;
    private int[] analogPins;
    private int[] analogReadPins;

    
    /**
     * Constructor
     * @throws InterruptedException 
     */
    public Arduino() throws ArduinoError {
        inicializeComunication();
        obtainAvailablePorts();
    }
    
    /**
     * Se inicializa la comunicación con la placa Arduino
     * @throws javaarduino.ArduinoError
     */
    public void inicializeComunication() throws ArduinoError{
        //Se inicia la comunicación
        comPort = SerialPort.getCommPorts()[0];
        comPort.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        comPort.openPort();
        //Se deja un cierto tiempo de margen
        try {
            Thread.sleep(ArduinoComunication.iniTime);
        } catch (InterruptedException ex) {
            throw new IniError();
        }
    }
    
    /**
     * Se obtienen los puertos disponibles
     * @throws ArduinoError 
     */
    private void obtainAvailablePorts() throws ArduinoError{
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
            throw new IniError();
        }
    }

    /**
     * Se da voltaje a un pin digital
     * @param pin pin al que se le quiere dar voltaje
     * @throws javaarduino.IncorrectPinError
     */
    @Override
    public void turnOn(int pin) throws ArduinoError{
        turn(pin, true);
    }

    /**
     * Se quita voltaje a un pin digital
     * @param pin pin al que se le quiere dar voltaje
     * @throws javaarduino.IncorrectPinError
     */
    @Override
    public void turnOff(int pin) throws ArduinoError{
        turn(pin, false);
    }
    
    /**
     * Se da un cierto voltaje a un pin determinado
     * @param pin
     * @param value
     * @throws ArduinoError 
     */
    @Override
    public void turn(int pin, int value) throws ArduinoError{
        try {
            OutputStream out = comPort.getOutputStream();
            //Se comprueba que el pin exista
            if(contains(analogPins, pin)){
                out.write(2);
                out.write(pin);
                out.write(value);
            } else{
                throw new IncorrectPinError();
            }
        } catch (IOException e) {
            throw new WritingError();
        }
    }

    /**
     * Método que devuelve el valor leido por un pin analógico
     * @param pin
     * @return 
     */
    @Override
    public int readAnalogPin(int pin) throws ArduinoError{
        int c = 0;
        try{
            OutputStream out = comPort.getOutputStream();
            InputStream io = comPort.getInputStream();
            //Se comprueba si existe el pin
            if(contains(analogReadPins, pin)){
                out.write(3);
                out.write(pin);
            } else{
                throw new IncorrectPinError();
            }
            //Damos tiempo a que se produzca una respuesta
            Thread.sleep(ArduinoComunication.tiempoDeEsperaParaRespuesta);
            c = (int) io.read();
            System.out.println(c);
            return c;
        } catch(InterruptedException eIn){
            throw new ReadingError();
        } catch (IOException ex) {
            throw new ReadingError();
        }
    }
    
    /**
     * Método que finaliza la comunicación con la placa
     */
    @Override
    public void finishComunication() {
        comPort.closePort();
    }
    
    
    //MÉTODOS PRIVADOS
    
    /**
     * Método privado que enciende o apaga un pin digital
     * @param pin
     * @param value 
     * @throws IncorrectPinError
     */
    private void turn(int pin, boolean value) throws ArduinoError{
        try {
            OutputStream out = comPort.getOutputStream();
            //Se comprueba que el pin existe
            if(contains(digitalPins, pin)){
                out.write(1);
                out.write(pin);
                out.write(1);
            } else{
                throw new IncorrectPinError();
            }
        } catch (IOException e) {
            throw new WritingError();
        }
    }
    
    /**
     * Método privado que dice si existe un pin en un array dado
     * @param pines array en el que se comprueba @see pin
     * @param pin pin a comprobar
     * @return 
     */
    private boolean contains(int[] pines, int pin){
        for(int i=0; i<pines.length; i++){
            if(pin==pines[i])
                return true;
        }
        return false;
    }

}
