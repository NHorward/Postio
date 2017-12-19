/*
 * Pi4J Eventlistener http://pi4j.com/example/listener.html Datum van raadpleging: 19 december 2017
 */
package postio;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 *
 * @author root
 */
public class Postio {

    /**
     * @param args the command line arguments
     */
    
    public static String ingevoerdeCode = "code: ";
    
    public static void main(String[] args) throws InterruptedException {
         leesKnopjes();
    }
    
    public static void leesKnopjes() throws InterruptedException{
        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput knop1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput knop3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
        // set shutdown state for this input pin
        knop1.setShutdownOptions(true);

       
        
        GpioPinListenerDigital knopLuisteraar1 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                        ingevoerdeCode += "1";
                        System.out.println(ingevoerdeCode);
                    }
                }
        };
        
         GpioPinListenerDigital knopLuisteraar2 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                        ingevoerdeCode += "2";
                        System.out.println(ingevoerdeCode);
                    }
                }
        };
         
          GpioPinListenerDigital knopLuisteraar3 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                        ingevoerdeCode += "3";
                        System.out.println(ingevoerdeCode);
                    }
                }
        };
        
         // create and register gpio pin listener
        knop1.addListener(knopLuisteraar1);
        knop2.addListener(knopLuisteraar2);
        knop3.addListener(knopLuisteraar3);

        System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)
        while(true) {
          Thread.sleep(500);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
        
        
    }
    
    }
    

