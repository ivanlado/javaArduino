package javaarduino.exceptions;

/**
 *
 * @author Iván
 */

public abstract class ArduinoError extends Exception{

    public ArduinoError(String string) {
        super(string);
    }
    
}
