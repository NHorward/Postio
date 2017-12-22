/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postio;
import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;

/**
 *
 * @author root
 */
public class Servo {
    public GpioPinPwmOutput pwm;
    public GpioController gpio;
    public Servo(){
        gpio = GpioFactory.getInstance();
        
        Pin pin = CommandArgumentParser.getPin(
                RaspiPin.class,    // pin provider class to obtain pin instance from
                RaspiPin.GPIO_23  // default pin if no pin argument found
               );             // argument array to search in

        pwm = gpio.provisionPwmOutputPin(pin);
        
        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(1000);
        com.pi4j.wiringpi.Gpio.pwmSetClock(500);
    }
    
    public void openLock(){
        pwm.setPwm(60);
        gpio.shutdown();
    }
    
    public void closeLock(){
        pwm.setPwm(95);
        gpio.shutdown();
    }
}
