/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postio.dao;
import postio.model.Opening;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author root
 */
public class OpeningDAO {
    
    

	public static int voegOpeningToe(Opening nieuweOpening) {
		int aantalAangepasteRijen = 0;
		try {
			aantalAangepasteRijen = Database.voerSqlUitEnHaalAantalAangepasteRijenOp("INSERT INTO Openingen (brievenbusId, toegangscodeId, gebruikerId, datum_tijdstip) VALUES (?,?,?,?)", new Object[] { nieuweOpening.getBrievenbusId(), nieuweOpening.getToegangscodeId(), nieuweOpening.getGebruikerId(), nieuweOpening.getDatum_tijdstip()});
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return aantalAangepasteRijen;
	}

        
}
