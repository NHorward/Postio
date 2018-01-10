/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


Geraadpleegde bronnen code Raspberry Pi
IoT Device Team 1

LCD Scherm:

Raspberry Pi Spy, LCD Module Control Using Python, via https://www.raspberrypi-spy.co.uk/2012/07/16x2-lcd-module-control-using-python/ Datum van raadpleging: 19 december 2017. 

Elmicro, GDM1602a Datasheet https://elmicro.com/files/lcd/gdm1602a_datasheet.pdf Datum van raadpleging: 19 december 2017. 

Pi4J GitHub, Wiring LCD Example https://github.com/Pi4J/pi4j/blob/master/pi4j-example/src/main/java/WiringPiLcdExample.java Datum van raadpleging: 19 december 2017. 

Sparkfun, Datasheet LCD HD44780, via https://www.sparkfun.com/datasheets/LCD/HD44780.pdf Datum van raadpleging: 19 december 2017. 


 */
package postio;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.Lcd;

/**
 *
 * @author root
 */
public class Hd44780 {
    
    // Globale variabelen
    public final static int LCD_ROWS = 2;
    public final static int LCD_COLUMNS = 16;
    public final static int LCD_BITS = 4;
    public int lcdHandle;
    public Hd44780(){
        
        
      
        // LCD dispaly initialiseren
        lcdHandle= Lcd.lcdInit(LCD_ROWS,     // Aantal rijen dat het LCD display ondersteunt
                                   LCD_COLUMNS,  // Aantal kolommen dat het LCD display ondersteunt
                                   LCD_BITS,     // Aantal bits dat gebruikt worden om te communiceren met het LCD                                   
                                   28,           // LCD RS pin
                                   27,           // LCD strobe pin
                                   25,            // LCD data bit 1
                                   24,            // LCD data bit 2
                                   22,            // LCD data bit 3
                                   21,            // LCD data bit 4
                                   0,            // LCD data bit 5 (Wordt op 0 gezet bij een 4 bit communicatie)                          
                                   0,            // LCD data bit 6 (Wordt op 0 gezet bij een 4 bit communicatie)
                                   0,            // LCD data bit 7 (Wordt op 0 gezet bij een 4 bit communicatie)
                                   0);           // LCD data bit 8 (Wordt op 0 gezet bij een 4 bit communicatie)


        // Controleren dat het LCD display bestaat
        if (lcdHandle == -1) {
            System.out.println(" ==>> LCD INIT FAILED");
            return;
        }
       
        // LCD display leegmaken
        Lcd.lcdClear(lcdHandle);
       

        // Zet de cursor van het LCD display terug naar de home positie
        Lcd.lcdHome(lcdHandle);
        
        // Schrijf de eerste rij van het LCD display naar de LCD
        Lcd.lcdPosition (lcdHandle, 0, 0) ;
       
        // Schrijf de tweede rij van het LCD display naar de LCD
        Lcd.lcdPosition (lcdHandle, 0, 1) ;
      }
    
    // Functie om het Lcd display leeg te maken
    public void clearLcd(){
        Lcd.lcdClear(lcdHandle);
        
    }
    
    // Functie om een mee te geven string op de eerste rij van het LCD display te schrijven
    public void writeFirstRow(String message){
        
        // Cursor van de LCD display terug op de startpositie zetten
        Lcd.lcdHome(lcdHandle);
        
        // Tekst op het LCD display te schrijven
        Lcd.lcdPuts (lcdHandle, message) ;
    }
    
     // Functie om een mee te geven string op de tweede rij van het LCD display te schrijven
    public void writeSecondRow(String message){
        
        // Cursor van de LCD display terug op de startpositie van de tweede rij zetten
        Lcd.lcdPosition (lcdHandle, 0, 1) ;
        
        // Tekst op het LCD display te schrijven
        Lcd.lcdPuts (lcdHandle, message) ;
    }
}

   
    

