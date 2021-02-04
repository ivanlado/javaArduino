package javaarduino;

import javaarduino.exceptions.ArduinoError;

/**
 * Interfaz que establece las constantes y los métodos que debe tener
 * una clase para la comunicación serial con la placa Arduino
 */
public interface ArduinoComunication {
    //Constantes
    public final static long iniTime = 2000;
    public final static long tiempoDeEsperaParaRespuesta = 2000;
    
    //Da voltaje a un pin digital
    public void turnOn(int pin) throws ArduinoError;
    
    //Pone a 0 el voltaje a un pin digital
    public void turnOff(int pin) throws ArduinoError;
    
    //Se da un cierto voltaje a un pin determinado
    public void turn(int pin, int value) throws ArduinoError;
    
    //Se lee el valor de un pin analógico
    public int readAnalogPin(int pin) throws ArduinoError;
    
    //Se finaliza la comunicación con la placa Arduino
    public void finishComunication();
}
