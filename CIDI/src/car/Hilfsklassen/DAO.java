package car.Hilfsklassen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

	private Connection con;
	private PreparedStatement pstlogin, psteintragen, pstwarteschlange, pstAnzUserVor, pstlogout, pst, pstgetsitzid;
	private ResultSet rst;
	private int idSitzung, idNutzer;
	private boolean statusWarten;
	
	public DAO() {
		//DAO d = d.verbindungAufbauen("jdbc:mysql://localhost:3306/cidi", "root", "mysql");
	}
	
	public void verbindungAufbauen(String url, String user, String pw){
		try {
			con = DriverManager.getConnection(url, user, pw);
			psteintragen = con.prepareStatement("");
			pstgetsitzid = con.prepareStatement("SELECT MAX(id_sitzung) FROM sitzung");
			pstlogout = con.prepareStatement("UPDATE sitzung SET endeSteuerung=NOW() WHERE id_sitzung=?");
			pstwarteschlange = con.prepareStatement("SELECT * FROM benutzer WHERE useFlag=true");
			pstAnzUserVor = con.prepareStatement("SELECT COUNT(id_sitzung) FROM sitzung WHERE endeSteuerung='0000-00-00 00:00:00' GROUP BY(endeSteuerung)");
			pstlogin = con.prepareStatement("SELECT id FROM benutzer WHERE nutzername=? AND passwort=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void verbindungAbbauen() throws SQLException{
		if(con != null){
			con.close();
			con = null;
		} else {
			System.out.println("Problem: verbindungAbbauen()");
		}
	}
	
	public Boolean anmelden(String benutzername, String pw) {
		try {
			pstlogin.setString(1, benutzername);
			pstlogin.setString(2, pw);
			rst = pstlogin.executeQuery();
			if( rst.next() ){
				idNutzer = Integer.parseInt(rst.getString(1)); //geprüft passt
				rst = pstgetsitzid.executeQuery();
				if( rst.next()){
					this.idSitzung = rst.getInt(1)+1;		//abspeichern sitzung in global Variablen
				} 
//				
				switch (warteschlange()) {
				case "leer":{
					psteintragen = con.prepareStatement("INSERT INTO sitzung (id_benutzer, login, beginnSteuerung) VALUES(" + this.idNutzer + ", NOW(), NOW())");
					psteintragen.executeUpdate();
					psteintragen = con.prepareStatement("UPDATE benutzer SET useFlag=true WHERE id=" + this.idNutzer);
					psteintragen.executeUpdate();
					rst.close();
					System.out.println("DAO leer -> anmelden()");
					return true;
					}
				case "anstellen":{
					System.out.println("in switch -> anstellen");
					psteintragen = con.prepareStatement("INSERT INTO sitzung (id_benutzer, login) VALUES(" + this.idNutzer + ", NOW())");
					psteintragen.executeUpdate();
					rst.close();
					System.out.println("DAO anstellen -> anmelden()");
					return true;
					}

				default:{
					System.out.println("Problem: rückgabewert Warteschlange()!");
					rst.close();
					}
				}
			} else {
				System.out.println("Logindaten nicht gefunden! anmelden()");
				return false;
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
		}
		return false;
	}
	
	private String warteschlange() throws SQLException {
		rst = pstwarteschlange.executeQuery();
		if( rst.next() ){
			rst.close();
			this.statusWarten= true;
			return "anstellen";
		} 
		this.statusWarten = false;
		rst.close();
		return "leer";
	}

	public int getMaxWarteZeitsek() {
		try {
			pstAnzUserVor = con.prepareStatement("SELECT TIMEDIFF(NOW(), beginnSteuerung) FROM sitzung WHERE beginnSteuerung!='0000-00-00 00:00:00' AND endeSteuerung='0000-00-00 00:00:00'");
			rst = pstAnzUserVor.executeQuery();
			int sek = 0;
			if( rst.next() ){  //Gibt es jemanden Und wenn wie lange darf er noch fahren?
				String temp = rst.getString(1); //geprüft, geht
//				System.out.println(temp);  //Zeit von Methode
				sek = Integer.parseInt(temp.substring(6, 8)) + (Integer.parseInt(temp.substring(3, 5)) * 60); //sekunden + (minuten * 60)
				//in var sek sind nun wartesekunden in int gespeichert
				
				pstAnzUserVor = con.prepareStatement("SELECT COUNT(id_sitzung) FROM sitzung WHERE beginnSteuerung='0000-00-00 00:00:00' AND endeSteuerung='0000-00-00 00:00:00' GROUP BY(endeSteuerung)");
				rst = pstAnzUserVor.executeQuery();
				int anzSchlange = 0;
				if(rst.next()){	//Gibt und wie viele sind noch in der Warteschlange
					anzSchlange = rst.getInt(1);
					if(this.statusWarten = true){
						anzSchlange--;
					}
					System.out.println("Status warteschlange:  " + statusWarten);
				} 
				return (( 900 - sek ) + (anzSchlange * 900));  // (900sek-ZeitbisJetztGefahren) + AnzWartendeUser * 60Sek * 15 min
			} else {
				// Es gibt keinen der Fährt, daher return -1
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rst!=null){
					rst.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		System.out.println("-1 zurück keine gefunden");
		return -1;
	}
	
	public void abmelden(){
		try {
			pstlogout.setInt(1, this.idSitzung);
			pstlogout.executeUpdate();
			
			pstlogout = con.prepareStatement("UPDATE benutzer SET useFlag=false WHERE id=" + this.idNutzer );
			pstlogout.executeUpdate();

			//	Sitzung wird frei... chekcen wer in Warteschlange als nächstes kommt und dann gleich buchen !!! -> Ab de lutzi!			
			bucheNaechsten(idSitzung);
			this.idNutzer = -1;
			this.idSitzung = -1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.statusWarten = false;   //Rücksetzten der Variablen für ist in Warteschlange
			if(rst != null){
				try {
					rst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}	
		
	private boolean bucheNaechsten(int sitzungsID) throws SQLException {
		pst = con.prepareStatement("SELECT id_benutzer FROM sitzung WHERE id_sitzung="+ (sitzungsID+1));
		rst = pst.executeQuery();
		if (rst.next()){
			System.out.println("Es steht jemand an, nächster wird eingebucht");
			pst = con.prepareStatement("UPDATE benutzer SET useFlag=true WHERE id=" + rst.getInt(1) );
			pst.executeUpdate();
			pst = con.prepareStatement("UPDATE sitzung SET beginnSteuerung=NOW() WHERE id_sitzung="+ (sitzungsID+1));
			pst.executeUpdate();
			return true;
		}
		System.out.println("Nichts neues wird gebucht da keine Ansteht");
		return false;
	}

	public void wartenAbbrechen(){
		try {
			pst = con.prepareStatement("SELECT * FROM sitzung WHERE beginnSteuerung='0000-00-00 00:00:00' AND endeSteuerung='0000-00-00 00:00:00'");
			rst = pst.executeQuery();
			if( rst.next() ){
				System.out.println(this.idSitzung);
				pst = con.prepareStatement("DELETE FROM sitzung WHERE id_sitzung=" + this.idSitzung );
				pst.executeUpdate();
				pst = con.prepareStatement("UPDATE sitzung SET id_sitzung=id_sitzung-1 WHERE id_sitzung >" + this.idSitzung );
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.statusWarten = false;   //Rücksetzten der Variablen für ist in Warteschlange
			if(rst != null){
				try {
					rst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getNutzername() {
		try {
			pst = con.prepareStatement("SELECT nutzername FROM benutzer WHERE id=" + idNutzer);
			rst = pst.executeQuery();
			if( rst.next() ){
				return rst.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rst != null){
				try {
					rst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Fehler bei ziehen des Namens des Benutzers");
		return "möp";
	}
	
	public static void main(String[] args) {
		DAO d = new DAO();
		d.verbindungAufbauen("jdbc:mysql://localhost:3306/cidi", "root", "mysql");
		
//		System.out.println(d.anmelden("becker", "becker"));
//			System.out.println(d.anmelden("huber", "huber"));
//			System.out.println(d.anmelden("admin", "admin"));
//		System.out.println(d.getMaxWarteZeitsek());
//		System.out.println(d.getMaxWarteZeitsek());
//		System.out.println(d.getMaxWarteZeitsek());
			d.abmelden();
			
	}


	
}
