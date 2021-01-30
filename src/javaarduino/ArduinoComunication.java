/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaarduino;

/**
 *
 * @author Iván
 */
public interface ArduinoComunication {
    public void inicializeComunication();
    public void finishComunication();
    public void turnOn(int pin);
    public void turnOff(int pin);
    public void readData();
    public void readData(boolean print);
}
