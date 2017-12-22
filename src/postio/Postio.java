/*
Geraadpleegde bronnen code Raspberry Pi
IoT Device Team 1

Toetsenbord:

O’reilly Raspberry Pi cookbook, Connecting a Push Switch, via http://razzpisampler.oreilly.com/ch07.html Datum van raadpleging: 19 december 2017. 

The Pi4J Project, GPIO State Listener Example using Pi4J, via http://pi4j.com/example/listener.html Datum van raadpleging: 19 december 2017. 

Level Up Lunch, Remover last character from String, via https://www.leveluplunch.com/java/examples/remove-last-character-from-string/  Datum van raadpleging: 20 december 2017. 

Reed Switch Deur : 

Raspberry Pi Stack Exchange, Reed Switch Wiring, via https://raspberrypi.stackexchange.com/questions/34947/reed-switch-wiring Datum van raadpleging: 21 december 2017. 
 */
package postio;
import postio.dao.ToegangscodeDAO;
import postio.model.Toegangscode;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import postio.dao.ToegangscodeDAO;
import postio.model.Toegangscode;
import postio.dao.OpeningDAO;
import postio.model.Opening;
import postio.dao.PostmeldingDAO;
import postio.model.Postmelding;
import java.util.Date;

/**
 *
 * @author root
 */
public class Postio {
    

    /**
     * @param args the command line arguments
     */
    
    public static String insertedCode = "";
    public static Hd44780 display;
    public static Servo lock;
    public static ArrayList<Toegangscode> codes;
    public static Toegangscode correctCode;
    public static boolean correct;
    public static boolean vPressed;
    public static boolean buttonPressed;
    public static boolean post;
    public static boolean pressedTimer;
    public static boolean postDelivered;
    
    public static void main(String[] args) throws InterruptedException {
        display = new Hd44780();
        lock = new Servo();
        display.clearLcd();
        
        getCodeFromDatabase();
        leesKnopjes();
         
    }
    
    public static void timer() throws InterruptedException{
        pressedTimer = true;
        Thread.sleep(400);
        pressedTimer = false;
    }
    
    public static void timerPost() throws InterruptedException{
        postDelivered = true;
        Thread.sleep(400);
        postDelivered = false;
    }
    
    public static void getCodeFromDatabase(){
        codes = ToegangscodeDAO.getToegangscodes();
        for(Toegangscode currentCode : codes){
            System.out.println(currentCode);
        }   
        
    }
    
    public static void openDoor(){
        lock.openLock();
        Date currentTime = new Date();
        Opening newOpening = new Opening(1, correctCode.getCode(), correctCode.getGebruikerId(), currentTime);
        OpeningDAO.voegOpeningToe(newOpening);
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
                if(!postDelivered){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                  post = true;
                  Date currentTime = new Date();
                  Postmelding newPostmelding = new Postmelding(1, currentTime);
                  PostmeldingDAO.voegPostmeldingToe(newPostmelding);
                    try {
                        timerPost();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }}
                  
                }
        };
        
              
        
        GpioPinListenerDigital knopLuisteraar1 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                if(!pressedTimer){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        insertedCode += "1";
                        try {
                            timer();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                }
        };
        
         GpioPinListenerDigital knopLuisteraar2 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                if(!pressedTimer){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        insertedCode += "2";
                        try {
                            timer();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                }
        };
         
          GpioPinListenerDigital knopLuisteraar3 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                if(!pressedTimer){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        insertedCode += "3";
                        try {
                            timer();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                }
        };
          
          
            GpioPinListenerDigital knopLuisteraar4 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                if(!pressedTimer){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        insertedCode += "4";
                        try {
                            timer();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                }
        };
            
              GpioPinListenerDigital knopLuisteraar5 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                if(!pressedTimer){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        insertedCode += "5";
                        try {
                            timer();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                }
        };
              
                GpioPinListenerDigital knopLuisteraar6 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                if(!pressedTimer){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        insertedCode += "6";
                        try {
                            timer();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                }
        };
                
                  GpioPinListenerDigital knopLuisteraar7 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                if(!pressedTimer){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        insertedCode += "7";
                        try {
                            timer();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                }
        };
                  
                    GpioPinListenerDigital knopLuisteraar8 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                if(!pressedTimer){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        insertedCode += "8";
                        try {
                            timer();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                }
        };
                    
                      GpioPinListenerDigital knopLuisteraar9 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                if(!pressedTimer){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        insertedCode += "9";
                        try {
                            timer();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                }
        };
                      
                        GpioPinListenerDigital knopLuisteraar0 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                
                if(!pressedTimer){
                    String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    buttonPressed = true;
                        insertedCode += "0";
                        try {
                            timer();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                }
        };
                        
                          GpioPinListenerDigital knopLuisteraarX = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                if(!pressedTimer){
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                        buttonPressed = true;
                        String nieuweCode = null;
                        
                        if(insertedCode != null && insertedCode.length() > 0) {
                            nieuweCode = insertedCode.substring(0, insertedCode.length() - 1);
                            insertedCode = nieuweCode;
                        }
                        
                        
                    
                        
                    }}
                }
        };
                          
                            GpioPinListenerDigital knopLuisteraarV = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                if(!pressedTimer){
                String status = event.getState().toString();
                if(status.equals("HIGH")) {
                    getCodeFromDatabase();
                    buttonPressed = true;
                    vPressed = true;
                   for(Toegangscode currentCode : codes){
                     if(insertedCode.equals(currentCode.toString())){
                            correct = true;
                            correctCode = currentCode;
                            openDoor();
                            break;
                        }else{
                            correct = false;
                        }  
                   }
                        
                        
                    }
                }}
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
          if(insertedCode.length() == 6){
              display.writeSecondRow("Press V");
              display.writeFirstRow("code: " + insertedCode);
              //System.out.println("Press V");
              if(vPressed && correct){
              display.writeFirstRow("code correct");
              display.writeSecondRow("mailbox is open");
              Thread.sleep(3000);
              vPressed = false;
              correct = false;
              insertedCode = "";
              buttonPressed = false;
              Thread.sleep(17000);
              lock.closeLock();
              //System.out.println("correct");
                }else if(vPressed && !correct){
              display.writeFirstRow("code incorrect");
              display.writeSecondRow("try again");
              //System.out.println("incorrect");
              Thread.sleep(3000);
              vPressed = false;
              insertedCode = "";
            }
          }
          else if(insertedCode.length() < 6 && vPressed){
              display.writeFirstRow("code incorrect");
              display.writeSecondRow("try again");
              //System.out.println("incorrect");
              Thread.sleep(3000);
              vPressed = false;
              display.clearLcd();
              display.writeFirstRow("code: " + insertedCode);
          }
   
          else if(insertedCode.length() > 6){
              display.writeFirstRow("code too long");
              Thread.sleep(3000);
              insertedCode = "";
          }else{
             display.writeFirstRow("code: " + insertedCode);
          }
          }  
          //System.out.println(insertedCode);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
        
        
    }
    
    }
    

