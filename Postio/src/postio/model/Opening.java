/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postio.model;

import java.util.Date;

/**
 *
 * @author root
 */
public class Opening {
    private int brievenbusId;
    private int toegangscodeId;
    private int gebruikerId;
    private Date datum_tijdstip;

    public int getBrievenbusId() {
        return brievenbusId;
    }

    public void setBrievenbusId(int brievenbusId) {
        this.brievenbusId = brievenbusId;
    }

    public int getToegangscodeId() {
        return toegangscodeId;
    }

    public void setToegangscodeId(int toegangscodeId) {
        this.toegangscodeId = toegangscodeId;
    }

    public int getGebruikerId() {
        return gebruikerId;
    }

    public void setGebruikerId(int gebruikerId) {
        this.gebruikerId = gebruikerId;
    }

    public Date getDatum_tijdstip() {
        return datum_tijdstip;
    }

    public void setDatum_tijdstip(Date datum_tijdstip) {
        this.datum_tijdstip = datum_tijdstip;
    }

    public Opening(int brievenbusId, int toegangscodeId, int gebruikerId, Date datum_tijdstip) {
        this.brievenbusId = brievenbusId;
        this.toegangscodeId = toegangscodeId;
        this.gebruikerId = gebruikerId;
        this.datum_tijdstip = datum_tijdstip;
    }
    
    
}
