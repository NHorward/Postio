/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postio.dao;
import postio.model.Toegangscode;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author root
 */
public class ToegangscodeDAO {
    // Hele tabel uitlezen en teruggeven
    public static ArrayList<Toegangscode> getToegangscodes() {
		ArrayList<Toegangscode> resultaat = new ArrayList<Toegangscode>();
		try {
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT * from Toegangscode");
			if (mijnResultset != null) {
				while (mijnResultset.next()) {
					Toegangscode huidigeToegangscode = converteerHuidigeRijNaarObject(mijnResultset);
					resultaat.add(huidigeToegangscode);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}

		return resultaat;
	}

	public static Toegangscode getToegangscodeById(int id) {
		Toegangscode resultaat = null;
		try {
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT * from Toegangscode where toegangscodeId = ?", new Object[] { id });
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

	public static int voegToegangscodeToe(Toegangscode nieuweToegangscode) {
		int aantalAangepasteRijen = 0;
		try {
			aantalAangepasteRijen = Database.voerSqlUitEnHaalAantalAangepasteRijenOp("INSERT INTO Toegangscode (code, omschrijving, leverancier, gebruikerId, brievenbusId) VALUES (?,?,?,?,?)", new Object[] { nieuweToegangscode.getCode(), nieuweToegangscode.getOmschrijving(), nieuweToegangscode.getLeverancier(), nieuweToegangscode.getGebruikerId(), nieuweToegangscode.getBrievenbusId()});
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return aantalAangepasteRijen;
	}

	public static int updateToegangscode(Toegangscode nieuweToegangscode) {
		int aantalAangepasteRijen = 0;
		try {
			aantalAangepasteRijen = Database.voerSqlUitEnHaalAantalAangepasteRijenOp("UPDATE Toegangscode SET code = ?, omschrijving = ?, leverancier = ?, gebruikerId = ?, brievenbusId = ? WHERE toegangscodeId = ?", new Object[] { nieuweToegangscode.getCode(), nieuweToegangscode.getOmschrijving(), nieuweToegangscode.getLeverancier(), nieuweToegangscode.getGebruikerId(), nieuweToegangscode.getBrievenbusId(), nieuweToegangscode.getToegangscodeId()});
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return aantalAangepasteRijen;
	}

	public static int verwijderToegangscode(int toegangscodeId) {
		int aantalAangepasteRijen = 0;
		try {
			aantalAangepasteRijen = Database.voerSqlUitEnHaalAantalAangepasteRijenOp("DELETE FROM Toegangscode WHERE ToegangscodeId = ?", new Object[] { toegangscodeId });
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return aantalAangepasteRijen;
	}
        
        // Rij uit database omzetten naar Java object :) 
	private static Toegangscode converteerHuidigeRijNaarObject(ResultSet mijnResultset) throws SQLException {
		return new Toegangscode(mijnResultset.getInt("toegangscodeId"), mijnResultset.getInt("code"), mijnResultset.getString("omschrijving"), mijnResultset.getString("leverancier"), mijnResultset.getInt("gebruikerId"), mijnResultset.getInt("brievenbusId"));
	}
        
        
}
