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
public class Toegangscode {
    private int toegangscodeId;
    private int code;
    private String omschrijving;
    private String leverancier;
    private int gebruikerId;
    private int brievenbusId;

    public int getToegangscodeId() {
        return toegangscodeId;
    }

    public void setToegangscodeId(int toegangscodeId) {
        this.toegangscodeId = toegangscodeId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getLeverancier() {
        return leverancier;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public int getGebruikerId() {
        return gebruikerId;
    }

    public void setGebruikerId(int gebruikerId) {
        this.gebruikerId = gebruikerId;
    }

    public int getBrievenbusId() {
        return brievenbusId;
    }

    public void setBrievenbusId(int brievenbusId) {
        this.brievenbusId = brievenbusId;
    }
    
    public Toegangscode(int toegangscodeId, int code, String omschrijving, String leverancier, int gebruikerId, int brievenbusId) {
        this.toegangscodeId = toegangscodeId;
        this.code = code;
        this.omschrijving = omschrijving;
        this.leverancier = leverancier;
        this.gebruikerId = gebruikerId;
        this.brievenbusId = brievenbusId;
    }
    
    public String toString(){
            return Integer.toString(this.code);
        }
}
