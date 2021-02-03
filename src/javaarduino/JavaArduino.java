package javaarduino;

import com.fazecast.jSerialComm.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Iván
 */
public class JavaArduino {

    /**
     * @param
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Arduino arduino = new Arduino();
        arduino.turnOn(7);
        arduino.turnOn(6);
        arduino.turn(10, 125);
        arduino.turn(11, 200);
        Thread.sleep(500);
        arduino.turn(11, 1);
        arduino.readAnalogPin(0);
        arduino.finishComunication();
    }

}
