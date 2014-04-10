package car.Hilfsklassen;

import java.sql.*;
import java.util.Scanner;

public class SQL {

	private PreparedStatement pst = null;
	private ResultSet rst = null;
	private Connection connection = null;
	private String sqlBefehl = "";
	private String datenbankpfad, user, passwort;
		
	public SQL() {
		datenbankpfad = "jdbc:mysql://localhost:3306/cidi" ;
		user = "root" ;
		passwort = "mysql" ;
	}

	private void verbindung() {
		//Verbindung DB cidi
		try {
			connection = DriverManager.getConnection(datenbankpfad, user, passwort);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("-> Zonk keine Verbindung DB");
		}
	}
	
	public String pruefeLogin(String nutzerName, String pw){
		try {
			verbindung();
			//Abfrage
			pst =  connection.prepareStatement("SELECT id FROM Benutzer WHERE nutzername=? AND passwort=?");
			pst.setString(1,nutzerName);
			pst.setString(2, pw);
			rst = pst.executeQuery();
			
			if(rst.next()){
				String NutzerID = rst.getString(1);  //zwischenspeichern der ID des Nutzers der fährt  ->bessere Lesbarkeit
				
				//Prüfen ob irgendwo ein true gesetzt ist
				pst = connection.prepareStatement("SELECT id FROM benutzer WHERE useFlag=true");
				rst = pst.executeQuery();

				if(!(rst.next())){	//Abfrage ob restTemp ein Element beinhaltet
				//useFlag frei ... daher in SQL-Benutzer dieses setzen 
					sqlBefehl = "UPDATE benutzer SET useFlag=true WHERE id=" + NutzerID;
					pst.executeUpdate(sqlBefehl);  //Abfrage absetzen
				//Eintrag in SQL-Sitzugn 
					sqlBefehl = "INSERT into Sitzung (id_benutzer, beginnSitzung) VALUES ("+ NutzerID +", NOW())";
					pst = connection.prepareStatement(sqlBefehl);
					pst.executeUpdate(sqlBefehl);  //Abfrage absetzen

					return "Logindaten korrekt | Sitzung wird belegt";
				} else { //useFlag besetzt/true
					return "Logindaten korrekt | Sitzung ist belegt";
				}
			} else {
				System.out.println("->Nutzername oder Passwort falsch, bitte nochmal versuchen!");
				return "Logindaten falsch";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rst != null){
				try {
					rst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return "zonk -> Kein Benutzerzeug gefunden";
	}

	public boolean logout(){ //ausstempln wird gesetzt und useFlag wieder auf false
		try {
			verbindung();
			//Prüfen ob irgendwo ein true gesetzt ist
			pst = connection.prepareStatement("SELECT * FROM benutzer WHERE useFlag=true");
			rst = pst.executeQuery();
			if( rst.next() ){	//Abfrage ob rst bei Tabelle Bnutzer ein TRUE beinhaltet
					//useFlag freigeben ... auf false setzen 
					sqlBefehl = "UPDATE benutzer SET useFlag=false WHERE id=" + rst.getString(1);
					pst.executeUpdate(sqlBefehl);  //Abfrage absetzen
					
					//Eintrag in SQL-Sitzugn bzw. ausgangsstempel 
					sqlBefehl = "UPDATE Sitzung SET endeSitzung=NOW() ORDER BY beginnSitzung DESC LIMIT 1";
//					UPDATE status_log SET zeit=now() ORDER BY zeit DESC LIMIT 1;
					pst = connection.prepareStatement(sqlBefehl);
					pst.executeUpdate(sqlBefehl);  //Abfrage absetzen
					return true;
					
			} else { //useFlag besetzt/true
				System.out.println("-> Zonk .. niemand ist angemeldet (SQL-logout-Methode)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	public int getTime(){
		//Abfrage - Wer fährt im Moment -> Return id
		try {
			verbindung();
			pst =  connection.prepareStatement("SELECT s.id_sitzung from sitzung s left join benutzer b ON(s.id_benutzer=b.id) WHERE s.endeSitzung='0000-00-00 00:00:00' AND b.useFlag=true");
			rst = pst.executeQuery();
			
			if(rst.next()){
				String sitzungsID = rst.getString(1);  //zwischenspeichern der ID des Nutzers der fährt  ->bessere Lesbarkeit

				//Abfrage - Datum wann sitzung abläuft - return restzeit
				pst =  connection.prepareStatement("SELECT TIMEDIFF(TIME(date_add(beginnSitzung, interval 15 minute)), curtime() ) from sitzung where id_Sitzung=?");
				pst.setString(1,sitzungsID);
				rst = pst.executeQuery();

				if(rst.next()){	//Liefert Zeit zurück wann Sitzung abläuft
			        String t = rst.getString(1).toString();
					int min = Integer.parseInt(t.substring(3,5));
					int testtemp =(Integer.parseInt(t.substring(6,8))+(min*60));
					System.out.println(testtemp);
					return testtemp;
				} else {
					System.out.println("->FEHLER / keine Endzeit bei SQL.java getTime()");
				}
			} else {
				System.out.println("->FEHLER / keine SitzungID bei SQL.java getTime()");
				return 6666;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rst != null){
				try {
					rst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 6666; //Falls Info von DAtenbank in die Hose geht return 6666
	}
	
	public static void main(String[] args) {
		SQL s = new SQL();
//		System.out.println(s.getTime()/60.0);
//		System.out.println(s.pruefeLogin("becker", "becker"));
		
//		Scanner sc = new Scanner(System.in);
//		String text = sc.nextLine();
//		System.out.println("2");
		s.logout();
//		System.out.println("3");
	
//    	System.out.println(543%60);
	}
}




