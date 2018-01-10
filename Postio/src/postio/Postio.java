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

// Importeren libraries 
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Date;

// Importeren andere eigen klasses, modellen en DAOs 
import postio.dao.ToegangscodeDAO;
import postio.model.Toegangscode;
import postio.dao.OpeningDAO;
import postio.model.Opening;
import postio.dao.PostmeldingDAO;
import postio.model.Postmelding;


/**
 *
 * @author IoT Device 1 - Postio
 */
public class Postio {
    // Globale variabelen
    public static ArrayList<Toegangscode> codes;
    public static Toegangscode correctCode;
    public static boolean correct;
    public static boolean vPressed;
    public static boolean buttonPressed;
    public static boolean post;
    public static boolean pressedTimer;
    public static boolean postDelivered;
    public static String insertedCode = "";
    public static Hd44780 display;
    public static Servo lock;
    
    public static void main(String[] args) throws InterruptedException {
        // Aanmaken instantie van de servo klasse (slot brievenbus)
        lock = new Servo();
        // Aanmaken instantie Hd44780 LCD Display klasse 
        display = new Hd44780();
        // LCD display leegmaken
        display.clearLcd();
        // Alle correcte toegangscodes uit de database ophalen
        getCodeFromDatabase();
        
        // Input van de knopjes uitlezen en reageren
        leesKnopjes();
         
    }
    
    // Functie die na het indrukken van een knop de thread pauzeert
    // Om haperen van knopjes te voorkomen
    public static void timer() throws InterruptedException{
        pressedTimer = true;
        Thread.sleep(400);
        pressedTimer = false;
    }
    
    // Postmelding na 400ms uitzetten
    public static void timerPost() throws InterruptedException{
        postDelivered = true;
        Thread.sleep(400);
        postDelivered = false;
    }
    
    // Alle correcte toegangscode uit de database ophalen
    public static void getCodeFromDatabase(){
        codes = ToegangscodeDAO.getToegangscodes(); 
    }
    
    // Functie die de deur openmaakt
    public static void openDoor(){
        // Servo gaat omhoog 
        lock.openLock();
        // Huidige datum & tijdstip ophalen
        Date currentTime = new Date();
        // Nieuw toegangscode object aanmaken met gegevens nieuwe opening
        Opening newOpening = new Opening(1, correctCode.getCode(), correctCode.getGebruikerId(), currentTime);
        // De nieuwe opening aan de database toevoegen
        OpeningDAO.voegOpeningToe(newOpening);
    }
    
    // Functie die de input van de knopjes uitleest en reageert
    public static void leesKnopjes() throws InterruptedException{

        // Een instantie van de GPIO Controller aanmaken
        final GpioController gpio = GpioFactory.getInstance();

        // Input pin aan knopje linken
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
   
        // Input pin aan brievenbusklep sensorlinken
        final GpioPinDigitalInput klepSensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_15, PinPullResistance.PULL_UP);

        
        // Event listener brievenbusklep   
        GpioPinListenerDigital klepLuisteraar = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if(!postDelivered){
                    // De status van de pin opvragen
                    String status = event.getState().toString();
                     // Als de status van de pin HIGH is dan...
                    if(status.equals("HIGH")) {
                      // ... is er post
                      post = true;
                      // Variable met huidige datum en tijdstip aanmaken
                      Date currentTime = new Date();
                      // Een nieuwe postmelding maken met huidige tijdstip
                      Postmelding newPostmelding = new Postmelding(1, currentTime);
                      // Postmelding aan de database toevoegen
                      PostmeldingDAO.voegPostmeldingToe(newPostmelding);
                      // De postmelding voor een bepaalde tijd weergeven
                        try {
                            // Proberen de postmelding na bepaalde tijd uit te zetten
                            timerPost();
                        } catch (InterruptedException ex) {
                            // Anders de error melden
                            Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }}

                }
        };
        
              
        // Eventlistener van knopje 1
        GpioPinListenerDigital knopLuisteraar1 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                
                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        // Aan de code wordt een 1 toegevoegd
                        buttonPressed = true;
                        insertedCode += "1";
                            
                            // Proberen om de thread te laten slapen
                            try {
                                timer();
                            } catch (InterruptedException ex) {
                                // Anders de error melden
                                Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                }
                }
        };
        
        // Eventlistener van knopje 2
        GpioPinListenerDigital knopLuisteraar2 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        // Aan de code wordt een 2 toegevoegd
                        buttonPressed = true;
                        insertedCode += "2";
                       
                            // Proberen om de thread te laten slapen
                            try {
                                timer();
                            } catch (InterruptedException ex) {
                                // Anders de error melden
                                Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
        };
         
        // Eventlistener van knopje 3
        GpioPinListenerDigital knopLuisteraar3 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
  
                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        // Aan de code wordt een 3 toegevoegd
                        buttonPressed = true;
                            insertedCode += "3";
                          
                            // Proberen om de thread te laten slapen
                            try {
                                timer();
                            } catch (InterruptedException ex) {
                                // Anders de error melden
                                Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
        };
          
        // Eventlistener van knopje 4
        GpioPinListenerDigital knopLuisteraar4 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                
                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        // Aan de code wordt een 4 toegevoegd
                        buttonPressed = true;
                        insertedCode += "4";
                       
                            // Proberen om de thread te laten slapen
                            try {
                                timer();
                            } catch (InterruptedException ex) {
                                // Anders de error melden
                                Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
        };
            
        // Eventlistener van knopje 5
        GpioPinListenerDigital knopLuisteraar5 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
               
                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        // Aan de code wordt een 5 toegevoegd
                        buttonPressed = true;
                        insertedCode += "5";
                 
                            // Proberen om de thread te laten slapen
                            try {
                                timer();
                            } catch (InterruptedException ex) {
                                // Anders de error melden
                                Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                
                }
        };
              
        // Eventlistener van knopje 6
        GpioPinListenerDigital knopLuisteraar6 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            
                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        // Aan de code wordt een 6 toegevoegd
                        buttonPressed = true;
                        insertedCode += "6";
                           
                            // Proberen om de thread te laten slapen
                            try {
                                timer();
                            } catch (InterruptedException ex) {
                                // Anders de error melden
                                Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
        };
                
        // Eventlistener van knopje 7
        GpioPinListenerDigital knopLuisteraar7 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                
                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        // Aan de code wordt een 7 toegevoegd
                        buttonPressed = true;
                        insertedCode += "7";
                 
                            // Proberen om de thread te laten slapen
                            try {
                                timer();
                            } catch (InterruptedException ex) {
                                // Anders de error melden
                                Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
        };
                  
        // Eventlistener van knopje 8
        GpioPinListenerDigital knopLuisteraar8 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            
                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        // Aan de code wordt een 8 toegevoegd
                        buttonPressed = true;
                        insertedCode += "8";
                        
                            // Proberen om de thread te laten slapen
                            try {
                                timer();
                            } catch (InterruptedException ex) {
                                // Anders de error melden
                                Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
        };
                    
        // Eventlistener van knopje 9
        GpioPinListenerDigital knopLuisteraar9 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
               
               // Als de thread niet slaapt 
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        // Aan de code wordt een 9 toegevoegd
                        buttonPressed = true;
                        insertedCode += "9";
                  
                            // Proberen om de thread te laten slapen
                            try {
                                timer();
                            } catch (InterruptedException ex) {
                                // Anders de error melden
                                Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
        };
                      
        // Eventlistener van knopje 0
        GpioPinListenerDigital knopLuisteraar0 = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
         
                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        // Aan de code wordt een 0 toegevoegd
                        buttonPressed = true;
                        insertedCode += "0";
                    
                            // Proberen om de thread te laten slapen
                            try {
                                timer();
                            } catch (InterruptedException ex) {
                                // Anders de error melden
                                Logger.getLogger(Postio.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
        };
                        
        // Eventlistener van knopje X
        GpioPinListenerDigital knopLuisteraarX = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            
                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Knopje ingedrukt wordt op true gezet
                        buttonPressed = true;
                        
                        // Maak een nieuwe code aan 
                        String nieuweCode = null;

                            // Als de ingevoerde code niet leeg is
                            if(insertedCode != null && insertedCode.length() > 0) {
                                
                                // De nieuwe code is gelijk aan de ingevoerde code - het laatste karakter
                                nieuweCode = insertedCode.substring(0, insertedCode.length() - 1);
                                
                                // De ingevoerde code is nu gelijk aan de nieuwe code
                                insertedCode = nieuweCode;
                            }
                        }
                    }
                }
        };
                          
        // Eventlistener van knopje V
        GpioPinListenerDigital knopLuisteraarV = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                
                // Als de thread niet slaapt
                if(!pressedTimer){
                    // Huidige status van de pin opvragen
                    String status = event.getState().toString();
                    
                    // Als de status HIGH is dan .....
                    if(status.equals("HIGH")) {
                        // Alle correcte toegangscodes uit de database ophalen
                        getCodeFromDatabase();
                        
                        // Knopje ingedrukt wordt op true gezet
                        buttonPressed = true;
                        
                        // V ingedrukt wordt op true gezet
                        vPressed = true;
                        
                        // Als een correcte toegangscode is ingedrukt
                        if(correct){
                            // Als de code correct is en de deur nog niet gesloten
                            // Als je dan op V drukt dan sluit het slot sneller dan normaal
                            lock.closeLock();
                            
                            // Als het slot gesloten is wordt de ingevoerde code weer op false gezet
                            correct = false;
                            
                            // LCD display leegmaken
                            display.clearLcd();
                            
                            //Op het display wordt op de eerste rij "Welcome to" geschreven
                            display.writeFirstRow("Welcome to");
                            
                            // Op het display wordt op de tweede rij "Postio" geschreven
                            display.writeSecondRow("Postio");
                        }
                        
                        // Elke toegangscode die in de database zit wordt vergelekent met de code die op het display zichtbaar is
                        for(Toegangscode currentCode : codes){
                            // Als deze codes gelijk zijn aan elkaar dan ...
                            if(insertedCode.equals(currentCode.toString())){
                                
                                // Wordt correct op true gezet
                                correct = true;
                                
                                // De currentCode wordt gelijk gesteld aan coorectCode
                                // Om de juiste code mee te geven aan de database
                                correctCode = currentCode;     
                                
                                // De deur wordt geopend
                                openDoor();
                                
                                // Uit deze loop gaan
                                break;
                                
                                // Indien de code niet juist is 
                                }else{
                                
                                // Wordt correct op false gezet
                                correct = false;
                            }  
                        }      
                        
                    }
                }
            }
        };
        
        // Events aan de juiste pinnen koppelen
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

        // Als het true is
        while(true) {
          // Gaat de thread even slapen
          Thread.sleep(500);
          
          // LCD display leegmaken
          display.clearLcd();
          
          // Als er geen knop ingedrukt wordt en er is geen post dan ... 
          if(!buttonPressed && !post){
              
              // Wordt op de eerste rij van de display "Welcome to" geschreven
              display.writeFirstRow("Welcome to");
              
              // Wordt er op de tweede rij van de display "Postio" geschreven
              display.writeSecondRow("Postio");
              
              // Als er geen knop wordt ingedrukt en er wel post aanwezig is in de brievenbus
          }else if(!buttonPressed && post){
              // Display.writeFirstRow("You've got");
              // Display.writeSecondRow("Mail");
          }
          
          // Anders
          else{
              // Ald de code bestaat uit 6 cijfers
            if(insertedCode.length() == 6){

                // Op de tweede rij van het display wordt "Press V" geschreven
                display.writeSecondRow("Press V");
                
                // Op de eerste rij van het display wordt "Code: " + de code die ingevoerd is door de knopjes
                display.writeFirstRow("Code: " + insertedCode);
                   
                // Als er op het knopje v is gedrukt en de code die is ingevoerd correct is dan ...
                if(vPressed && correct){
                    
                    // Wordt op de eerste rij van de display "Code correct" geschreven
                    display.writeFirstRow("Code correct");
                    
                    // En wordt er op de tweede rij van de display "Mailbox is open" geschreven
                    display.writeSecondRow("Mailbox is open");
                    
                    // Dan gaat de thread even slapen
                    Thread.sleep(3000);
                    
                    // vPressed wordt op false gezet
                    vPressed = false;        
                    
                    // De ingevoerde code wordt weer gereset
                    insertedCode = "";
                    
                    // De variabele buttonpressed wordt ook weer op false gezet
                    buttonPressed = false;
                    
                    // Op de eerste rij van het display wordt "Mailbox is open" geschreven
                    display.writeFirstRow("Mailbox is open");
                    
                    // Op de tweede rij van het display wordt "Press V to close" geschreven
                    display.writeSecondRow("Press V to close");
                    
                    // Thread even laten slapen
                    Thread.sleep(20000);
                    
                    
                    
                    // Correct op false zetten
                    correct = false;
                    
                    // Het slot terug sluiten
                    lock.closeLock();
                  
                        // Als knopje V ingedrukt is maar als er geen correcte code is ingevoerd ...
                      }else if(vPressed && !correct){
                            
                          // Dan wordt er op de eerste rij van de display "code incoorect" geschreven
                          display.writeFirstRow("Code incorrect");
                          
                          // Op de tweede rij van het display wordt er "Try again" geschreven
                          display.writeSecondRow("Try again");
                            
                          // De thread gaat even slapen
                          Thread.sleep(3000);
                          
                          // vPressed wordt op false gezet
                          vPressed = false;
                          
                          // En de ingevoerde code wordt weer gereset
                          insertedCode = "";
              }
            }
            
            // Als de lengte van de ingevoerde code kleiner is dan 6 en er wordt op het knopje V gedrukt
            else if(insertedCode.length() < 6 && vPressed){
                
                // Dan wordt er op de eerste rij van de display "Code incorrect" geschreven
                display.writeFirstRow("Code incorrect");
                
                // Dan wordt er op de tweede rij van de display "Try again" geschreven
                display.writeSecondRow("Try again");
                
                // De thread gaat even slapen
                Thread.sleep(3000);
                
                // vPressed wordt terug op false gezet
                vPressed = false;
                
                // LCD display leegmaken
                display.clearLcd();
                
                // Op de eerste rij van het display wordt "Code: " + de code die is ingevoerd geschreven
                display.writeFirstRow("Code: " + insertedCode);
            }

            // Als de ingevoerde code langer is dan 6 karakters
            else if(insertedCode.length() > 6){
                
                // Dan wordt er op de eerste rij van de display "Code too long" geschreven
                display.writeFirstRow("Code too long");
                
                // De thread gaat even slapen
                Thread.sleep(3000);
                
                // De ingevoerde code wordt weer gereset
                insertedCode = "";
                
            // Als de ingevoerde niet voldoet aan de vorige (else) if statements dan  
            }else{
                
                // Wordt er op de eerste rij van het display "Code: " + de ingevoerde code geschreven
               display.writeFirstRow("Code: " + insertedCode);
            }
            }  
           }               
        }    
}
    

