/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postio.model;

/**
 *
 * @author root
 */
public class Brievenbus {
    private int brievenbusId;
    private String adres;
    private String referentiecode;
    private boolean booleanOpen;

    public int getBrievenbusId() {
        return brievenbusId;
    }

    public void setBrievenbusId(int brievenbusId) {
        this.brievenbusId = brievenbusId;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getReferentiecode() {
        return referentiecode;
    }

    public void setReferentiecode(String referentiecode) {
        this.referentiecode = referentiecode;
    }

    public boolean isBooleanOpen() {
        return booleanOpen;
    }

    public void setBooleanOpen(boolean booleanOpen) {
        this.booleanOpen = booleanOpen;
    }

    public Brievenbus(int brievenbusId, String adres, String referentiecode, boolean booleanOpen) {
        this.brievenbusId = brievenbusId;
        this.adres = adres;
        this.referentiecode = referentiecode;
        this.booleanOpen = booleanOpen;
    }
    
    
}
