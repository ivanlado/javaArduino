package javaarduino.exceptions;

/**
 *
 * @author Iván
 */

public class IncorrectPinError extends ArduinoError{

    public IncorrectPinError() {
        super("El pin no existe.");
    }

}
