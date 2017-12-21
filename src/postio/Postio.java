/*
 * Pi4J Eventlistener http://pi4j.com/example/listener.html Datum van raadpleging: 19 december 2017
 */
package postio;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class Postio {
    

    /**
     * @param args the command line arguments
     */
    
    public static String ingevoerdeCode = "code: ";
    public static Hd44780 display;
    public static String juisteCode;
    public static boolean correct;
    public static boolean vPressed;
    public static boolean buttonPressed;
    public static boolean post;
    
    public static void main(String[] args) throws InterruptedException {
        display = new Hd44780(); 
        display.clearLcd();
        juisteCode = "code: 123789";
        leesKnopjes();
         
    }
    
    public static void leesKnopjes() throws InterruptedException{
        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput knop1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop5 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop6 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop7 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop8 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_08, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop9 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_09, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop0 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knopX = gpio.provisionDigitalInputPin(RaspiPin.GPIO_10, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knopV = gpio.provisionDigitalInputPin(RaspiPin.GPIO_11, PinPullResistance.PULL_UP);
   
        
        final GpioPinDigitalInput klepSensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_15, PinPullResistance.PULL_UP);

        
           
        GpioPinListenerDigital klepLuisteraar = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                //String status = event.getState().toString();
               
                  System.out.println("klepje");
                  post = true;
                }
        };
        
              
        
        GpioPinListenerDigital knopLuisteraar1 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        ingevoerdeCode += "1";
                        
                    }
                }
        };
        
         GpioPinListenerDigital knopLuisteraar2 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        ingevoerdeCode += "2";
                        
                    }
                }
        };
         
          GpioPinListenerDigital knopLuisteraar3 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        ingevoerdeCode += "3";
                        
                    }
                }
        };
          
          
            GpioPinListenerDigital knopLuisteraar4 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        ingevoerdeCode += "4";
                        
                    }
                }
        };
            
              GpioPinListenerDigital knopLuisteraar5 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        ingevoerdeCode += "5";
                        
                    }
                }
        };
              
                GpioPinListenerDigital knopLuisteraar6 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        ingevoerdeCode += "6";
                        
                    }
                }
        };
                
                  GpioPinListenerDigital knopLuisteraar7 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        ingevoerdeCode += "7";
                        
                    }
                }
        };
                  
                    GpioPinListenerDigital knopLuisteraar8 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        ingevoerdeCode += "8";
                        
                    }
                }
        };
                    
                      GpioPinListenerDigital knopLuisteraar9 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        ingevoerdeCode += "9";
                        
                    }
                }
        };
                      
                        GpioPinListenerDigital knopLuisteraar0 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        ingevoerdeCode += "0";
                        
                    }
                }
        };
                        
                          GpioPinListenerDigital knopLuisteraarX = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                        buttonPressed = true;
                        String nieuweCode = null;
                        
                        if(ingevoerdeCode != null && ingevoerdeCode.length() > 1) {
                            nieuweCode = ingevoerdeCode.substring(0, ingevoerdeCode.length() - 1);
                            ingevoerdeCode = nieuweCode;
                        }
                        
                        
                    
                        
                    }
                }
        };
                          
                            GpioPinListenerDigital knopLuisteraarV = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                    vPressed = true;
                        if(ingevoerdeCode.equals(juisteCode)){
                            correct = true;
                        }else{
                            correct = false;
                        }
                        
                    }
                }
        };
        
         // create and register gpio pin listener
        knop1.addListener(knopLuisteraar1);
        knop2.addListener(knopLuisteraar2);
        knop3.addListener(knopLuisteraar3);
        knop4.addListener(knopLuisteraar4);
        knop5.addListener(knopLuisteraar5);
        knop6.addListener(knopLuisteraar6);
        knop7.addListener(knopLuisteraar7);
        knop8.addListener(knopLuisteraar8);
        knop9.addListener(knopLuisteraar9);
        knop0.addListener(knopLuisteraar0);
        knopX.addListener(knopLuisteraarX);
        knopV.addListener(knopLuisteraarV);
        
        klepSensor.addListener(klepLuisteraar);

        System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)
        while(true) {
          Thread.sleep(500);
          display.clearLcd();
          if(!buttonPressed && !post){
              display.writeFirstRow("welcome to");
              display.writeSecondRow("Postio");
          }else if(!buttonPressed && post){
              display.writeFirstRow("you've got");
              display.writeSecondRow("mail");
          }
          else{
              //"code: " is 6 lang, max lengte wordt dus 12 (code 6 characters lang)
          if(ingevoerdeCode.length() == 12){
              display.writeSecondRow("Press V");
              display.writeFirstRow(ingevoerdeCode);
              //System.out.println("Press V");
              if(vPressed && correct){
              display.writeFirstRow("code correct");
              display.writeSecondRow("mailbox is open");
              //System.out.println("correct");
              Thread.sleep(5000);
              vPressed = false;
              correct = false;
              ingevoerdeCode = "code: ";
              buttonPressed = false;
                }else if(vPressed && !correct){
              display.writeFirstRow("code incorrect");
              display.writeSecondRow("try again");
              //System.out.println("incorrect");
              Thread.sleep(5000);
              vPressed = false;
              ingevoerdeCode = "code: ";
            }
          }
          else if(ingevoerdeCode.length() < 12 && vPressed){
              display.writeFirstRow("code incorrect");
              display.writeSecondRow("try again");
              //System.out.println("incorrect");
              Thread.sleep(5000);
              vPressed = false;
              display.clearLcd();
              display.writeFirstRow(ingevoerdeCode);
          }
   
          else if(ingevoerdeCode.length() > 12){
              display.writeFirstRow("code too long");
              ingevoerdeCode = "code: ";
          }else{
             display.writeFirstRow(ingevoerdeCode);
          }
          }  
          //System.out.println(ingevoerdeCode);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
        
        
    }
    
    }
    

