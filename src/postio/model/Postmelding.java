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
public class Postmelding {
   private int meldingId;
   private int brievenbusId;
   private Date datum_tijdstip;

    public int getMeldingId() {
        return meldingId;
    }

    public void setMeldingId(int meldingId) {
        this.meldingId = meldingId;
    }

    public int getBrievenbusId() {
        return brievenbusId;
    }

    public void setBrievenbusId(int brievenbusId) {
        this.brievenbusId = brievenbusId;
    }

    public Date getDatum_tijdstip() {
        return datum_tijdstip;
    }

    public void setDatum_tijdstip(Date datum_tijdstip) {
        this.datum_tijdstip = datum_tijdstip;
    }

    public Postmelding(int brievenbusId, Date datum_tijdstip) {
        this.brievenbusId = brievenbusId;
        this.datum_tijdstip = datum_tijdstip;
    }
   
   
}
