/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postio.dao;
import postio.model.Postmelding;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author root
 */
public class PostmeldingDAO {
    
    

	public static int voegPostmeldingToe(Postmelding nieuwePostmelding) {
		int aantalAangepasteRijen = 0;
		try {
			aantalAangepasteRijen = Database.voerSqlUitEnHaalAantalAangepasteRijenOp("INSERT INTO Postmeldingen (brievenbusId, datum_tijdstip) VALUES (?,?)", new Object[] { nieuwePostmelding.getBrievenbusId(), nieuwePostmelding.getDatum_tijdstip()});
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return aantalAangepasteRijen;
	}

        
}
