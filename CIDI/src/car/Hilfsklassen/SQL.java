package car.Hilfsklassen;

import java.sql.*;
import java.util.Scanner;

public class SQL {

	private PreparedStatement pst = null;
	private ResultSet rst = null;
	private ResultSet rst2 = null;

	private Connection connection = null;
	private String sqlBefehl = "";
	private String datenbankpfad, user, passwort;
	private String id;

	public SQL() {
		datenbankpfad = "jdbc:mysql://localhost:3306/cidi";
		user = "root";
		passwort = "mysql";

		verbindungAufbauen();
	}

	private void verbindungAufbauen() {
		// Verbindung DB cidi
		try {
			connection = DriverManager.getConnection(datenbankpfad, user,passwort);
			pst = connection.prepareStatement("SELECT id FROM Benutzer WHERE nutzername=? AND passwort=?");
		} catch (SQLException e) {
			System.out.println("-> Zonk keine Verbindung DB");
		}
	}

	public void verbindungAbbauen() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Problem Verbindungsabbau");
		}
	}

	public boolean pruefeLogin(String nutzerName, String pw) {
		try {
			// Abfrage
			
			pst.setString(1, nutzerName);
			pst.setString(2, pw);
			rst = pst.executeQuery();

			if (rst.next()) {
				id = rst.getString(1);
				if (login(id)) { // Übergeben der ID des Nutzers
					System.out.println("Sitzung frei");
				} else {
					System.out.println("Sitzung belegt -> warteSchlange");
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rst != null) {
				try {
					rst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	private boolean login(String nutzerID) {
		try {
			// Prüfen ob irgendwo ein true gesetzt ist
			rst=null;
			pst = connection.prepareStatement("SELECT id FROM benutzer WHERE useFlag=true");
			rst = pst.executeQuery();	
			System.out.println(rst.next());
			if ( rst.next() ) {
				System.out.println("Es Fährt jemand, ab in die Warteschlange");
				pst.executeUpdate("INSERT INTO sitzung (id_benutzer, warteSchlange) VALUES ("+ nutzerID + ", NOW() )");
				return true;
			}
			
			if ( !(rst.next()) ) { // Abfrage ob rst ein Element beinhaltet
				System.out.println("in if loginMethode");
				// useFlag frei ... daher in SQL-Benutzer dieses setzen
				sqlBefehl = "UPDATE benutzer SET useFlag=true WHERE id=" + nutzerID;
				pst.executeUpdate(sqlBefehl); // Abfrage absetzen
				// Eintrag in SQL-Sitzugn ohne warteSchlange
				sqlBefehl = "INSERT INTO Sitzung (id_benutzer, beginnSitzung) VALUES (" + nutzerID + ", NOW()";
				//sqlBefehl = "UPDATE sitzung SET beginnSitzung=NOW() WHERE id_benutzer="	+ nutzerID + " AND beginnSitzung='0000-00-00 00:00:00')";
				pst.executeUpdate(sqlBefehl); // Abfrage absetzen
				return true;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pst != null){
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rst != null) {
				try {
					rst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		// useFlag gesetzt -> also Sitzung besetzt
		return false;
	}

	public boolean logout() { // ausstempln wird gesetzt und useFlag wieder auf false
		try {
			// Hole Nutzer ID, wo useFlag gesetzt ist
			pst = connection.prepareStatement("SELECT * FROM benutzer WHERE useFlag=true");
			rst = pst.executeQuery();
			if (rst.next()) {
				// useFlag freigeben ... auf false setzen
				sqlBefehl = "UPDATE benutzer SET useFlag=false WHERE id="+ rst.getString(1);
				pst.executeUpdate(sqlBefehl); // Abfrage absetzen

				// Eintrag in SQL-Sitzugn bzw. ausgangsstempel
				sqlBefehl = "UPDATE sitzung SET endeSitzung=NOW() WHERE id_benutzer='" + rst.getString(1) + "'endeSitzung='0000-00-00 00:00:00')";
				pst.executeUpdate(sqlBefehl); // Abfrage absetzen
				return true;
			} else { // useFlag nirgendswo gesetzt
				System.out
						.println("-> Zonk .. niemand ist/war angemeldet (SQL-logout-Methode)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rst != null) {
				try {
					rst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public int getTime() { // Return wert Restzeit in sek
		try {
			pst = connection
					.prepareStatement("SELECT s.id_sitzung from sitzung s left join benutzer b ON(s.id_benutzer=b.id) WHERE s.endeSitzung='0000-00-00 00:00:00' AND b.useFlag=true");
			rst = pst.executeQuery();

			if (rst.next()) {
				temp = rst.getString(1); // zwischenspeichern der ID der Sitzung
											// ->bessere Lesbarkeit

				// Abfrage - Datum wann sitzung abläuft - return restzeit
				pst = connection
						.prepareStatement("SELECT TIMEDIFF(TIME(date_add(beginnSitzung, interval 15 minute)), curtime() ) from sitzung where id_Sitzung=?");
				pst.setString(1, temp);
				rst = pst.executeQuery();

				if (rst.next()) { // Liefert Zeit zurück wann Sitzung abläuft
					String t = rst.getString(1).toString();
					int min = Integer.parseInt(t.substring(3, 5));
					int testtemp = (Integer.parseInt(t.substring(6, 8)) + (min * 60));
					System.out.println(testtemp);
					return testtemp;
				} else {
					System.out
							.println("->FEHLER / keine Endzeit bei SQL.java getTime()");
				}
			} else {
				System.out
						.println("->FEHLER / keine SitzungID bei SQL.java getTime()");
				return 6666;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rst != null) {
				try {
					rst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 6666; // Falls Info von DAtenbank in die Hose geht return 6666
	}

	public static void main(String[] args) {
		SQL s = new SQL();

		s.login("2");
		
	}
}
