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
        
    }

}
