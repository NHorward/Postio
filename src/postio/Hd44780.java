/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public final static int LCD_ROWS = 2;
    public final static int LCD_COLUMNS = 16;
    public final static int LCD_BITS = 4;
    public int lcdHandle;
    public Hd44780(){
      
        // initialize LCD
        lcdHandle= Lcd.lcdInit(LCD_ROWS,     // number of row supported by LCD
                                   LCD_COLUMNS,  // number of columns supported by LCD
                                   LCD_BITS,     // number of bits used to communicate to LCD
                                   28,           // LCD RS pin
                                   27,           // LCD strobe pin
                                   25,            // LCD data bit 1
                                   24,            // LCD data bit 2
                                   22,            // LCD data bit 3
                                   21,            // LCD data bit 4
                                   0,            // LCD data bit 5 (set to 0 if using 4 bit communication)
                                   0,            // LCD data bit 6 (set to 0 if using 4 bit communication)
                                   0,            // LCD data bit 7 (set to 0 if using 4 bit communication)
                                   0);           // LCD data bit 8 (set to 0 if using 4 bit communication)


        // verify initialization
        if (lcdHandle == -1) {
            System.out.println(" ==>> LCD INIT FAILED");
            return;
        }
        Lcd.lcdClear(lcdHandle);
    }
    
    public void clearLcd(){
        Lcd.lcdClear(lcdHandle);
        //Thread.sleep(1000);
    }
    
    public void writeFirstRow(String message){
        Lcd.lcdHome(lcdHandle);
        Lcd.lcdPuts (lcdHandle, message) ;
    }
    
    public void writeSecondRow(String message){
        Lcd.lcdPosition (lcdHandle, 0, 1) ;
        Lcd.lcdPuts (lcdHandle, message) ;
    }
}

   
    

