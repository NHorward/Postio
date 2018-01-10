/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


Geraadpleegde bronnen code Raspberry Pi
IoT Device Team 1

Servo slot

GitHub Pi4J, PWM Example, via https://github.com/Pi4J/pi4j/blob/master/pi4j-example/src/main/java/PwmExample.java Datum van raadpleging: 21 december 2017. 
 */
package postio;
import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class Servo {
    
    // Globale variabelen
    public GpioPinPwmOutput pwm;
    public GpioController gpio;
    
    
    public Servo(){
        
        // Proberen om de thread even te laten slapen
        try {
            Thread.sleep(9000);
                  
        // Als de thread niet kan slapen wordt er een error gestuurd
        } catch (InterruptedException ex) {
            Logger.getLogger(Servo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Nieuwe GpioFactory-instantie aanmaken
        gpio = GpioFactory.getInstance();
            
            // Pinnen voor een nieuwe GPIO instantie te maken
            Pin pin = CommandArgumentParser.getPin(
                    RaspiPin.class,    
                    RaspiPin.GPIO_23  
            );          
            
            // GPIO pinnen omzetten naar pwm-output pinnen
            pwm = gpio.provisionPwmOutputPin(pin);
            
            // Omzetten naar PWM waarden
            com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
           
            // Het bereik van de pwm instellen
            com.pi4j.wiringpi.Gpio.pwmSetRange(1000);
            
            // Stel de deler in voor de pwmClock
            com.pi4j.wiringpi.Gpio.pwmSetClock(500);
    }
    
    
    // Functie om servo te laten bewegen waardoor deze rechtop komt te staan (verticaal)
    // Hierdoor kan de deur van de brievenbus open
    public void openLock(){
        pwm.setPwm(60);
        
    }
    
    // Functie om servo te laten bewegen waardoor deze plat komt te liggen (horizontaal)
    // Hierdoor kan de deur van de brievenbus niet meer open
    public void closeLock(){
        pwm.setPwm(95);
        
    }
}
