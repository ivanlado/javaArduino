/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaarduino.exceptions;

/**
 *
 * @author Iván
 */
public class IniError extends ArduinoError {

    public IniError() {
        super("No se pudo establecer conexión con Arduino");
    }
}
