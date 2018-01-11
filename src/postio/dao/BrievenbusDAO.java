/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postio.dao;
import postio.model.Brievenbus;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author root
 */
public class BrievenbusDAO {
    // Hele tabel uitlezen en teruggeven
    public static ArrayList<Brievenbus> getBrievenbussen() {
		ArrayList<Brievenbus> resultaat = new ArrayList<Brievenbus>();
		try {
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT * from Brievenbus");
			if (mijnResultset != null) {
				while (mijnResultset.next()) {
					Brievenbus huidigeBrievenbus = converteerHuidigeRijNaarObject(mijnResultset);
					resultaat.add(huidigeBrievenbus);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}

		return resultaat;
	}

	public static Brievenbus getBrievenbusById(int id) {
		Brievenbus resultaat = null;
		try {
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT * from Brievenbus where brievenbusId = ?", new Object[] { id });
			if (mijnResultset != null) {
				mijnResultset.first();
				resultaat = converteerHuidigeRijNaarObject(mijnResultset);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}

		return resultaat;
	}

	public static int updateBrievenbus(Brievenbus nieuweBrievenbus) {
		int aantalAangepasteRijen = 0;
		try {
			aantalAangepasteRijen = Database.voerSqlUitEnHaalAantalAangepasteRijenOp("UPDATE Brievenbus SET adres = ?, referentiecode = ?, booleanOpen = ? WHERE brievenbusId = ?", new Object[] { nieuweBrievenbus.getAdres(), nieuweBrievenbus.getReferentiecode(), nieuweBrievenbus.isBooleanOpen(), nieuweBrievenbus.getBrievenbusId()});
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return aantalAangepasteRijen;
	}
        
        // Rij uit database omzetten naar Java object :) 
	private static Brievenbus converteerHuidigeRijNaarObject(ResultSet mijnResultset) throws SQLException {
		return new Brievenbus(mijnResultset.getInt("brievenbusId"), mijnResultset.getString("adres"), mijnResultset.getString("referentiecode"), mijnResultset.getBoolean("booleanOpen"));
	}
        
        
}
