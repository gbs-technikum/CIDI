package car.Hilfsklassen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DAO {

	private Connection con;
	private PreparedStatement pstlogin, psteintragen, pstwarteschlange, pstAnzUserVor, pstlogout, pst, pstgetsitzid;
	private ResultSet rst;
	private int idSitzung, idNutzer;
	
	public DAO() {
		//DAO d = d.verbindungAufbauen("jdbc:mysql://localhost:3306/cidi", "root", "mysql");
	}
	
	public void verbindungAufbauen(String url, String user, String pw) throws SQLException {
		con = DriverManager.getConnection(url, user, pw);
		pstlogin = con.prepareStatement("SELECT id FROM benutzer WHERE nutzername=? AND passwort=?");
		psteintragen = con.prepareStatement("");
		pstgetsitzid = con.prepareStatement("SELECT MAX(id_sitzung) FROM sitzung");
		pstlogout = con.prepareStatement("UPDATE sitzung SET endeSteuerung=NOW() WHERE id_sitzung=?");
		pstwarteschlange = con.prepareStatement("SELECT * FROM benutzer WHERE useFlag=true");
		pstAnzUserVor = con.prepareStatement("SELECT COUNT(id_sitzung) FROM sitzung WHERE endeSteuerung='0000-00-00 00:00:00' GROUP BY(endeSteuerung)");
	}
	
	public void verbindungAbbauen() throws SQLException{
		if(con != null){
			con.close();
			con = null;
		} else {
			System.out.println("Problem: verbindungAbbauen()");
		}
	}
	
	public Boolean anmelden(String benutzername, String pw) throws SQLException{
		pstlogin.setString(1, benutzername);
		pstlogin.setString(2, pw);
		rst = pstlogin.executeQuery();
		if( rst.next() ){
			idNutzer = Integer.parseInt(rst.getString(1)); //geprüft passt
			rst = pstgetsitzid.executeQuery();
			if( rst.next()){
				this.idSitzung = rst.getInt(1)+1;		//abspeichern sitzung in global Variablen
				System.out.println(this.idSitzung);  
			} 
//			
			switch (warteschlange()) {
			case "leer":{
				psteintragen = con.prepareStatement("INSERT INTO sitzung (id_benutzer, login, beginnSteuerung) VALUES(" + this.idNutzer + ", NOW(), NOW())");
				psteintragen.executeUpdate();
				psteintragen = con.prepareStatement("UPDATE benutzer SET useFlag=true WHERE id=" + this.idNutzer);
				psteintragen.executeUpdate();
				rst.close();
				return true;
				}
			case "anstellen":{
				System.out.println("in switch -> anstellen");
				psteintragen = con.prepareStatement("INSERT INTO sitzung (id_benutzer, login) VALUES(" + this.idNutzer + ", NOW())");
				psteintragen.executeUpdate();
				rst.close();
				return true;
				}

			default:{
				System.out.println("Problem: rückgabewert Warteschlange()!");
				rst.close();
				}
			}
		} 
		System.out.println("Logindaten nicht gefunden! anmelden()");
		rst.close();
		return false;
	}
	
	private String warteschlange() throws SQLException {
		rst = pstwarteschlange.executeQuery();
		if( rst.next() ){
			rst.close();
			JOptionPane.showMessageDialog(null, "Fahrzeug wird gerade benutzt ba bla warten!", "Sitzung belegt", JOptionPane.OK_OPTION);
			return "anstellen";
		} 
		rst.close();
		return "leer";
	}

	public int maxWarteZeitsek() {
		try {
			rst = pstAnzUserVor.executeQuery();
			int anzSchlange = 0;
			if(rst.next()){
				anzSchlange = rst.getInt(1)-2;
				pstAnzUserVor = con.prepareStatement("SELECT TIMEDIFF(NOW(), beginnSteuerung) FROM sitzung WHERE endeSteuerung='0000-00-00 00:00:00' AND beginnSteuerung!='0000-00-00 00:00:00'");
				rst = pstAnzUserVor.executeQuery();
				int sek = 0;
				if(anzSchlange == -1){
					System.out.println("Logischer Fehler -> maxWarteZeitsek() wird aufgerufen obwohl niemand in der Schlange ist");
					rst.close();
					return -1;
				}
				System.out.println("123");
				if( rst.next() ){
					String temp = rst.getString(1);
					System.out.println(temp);
					sek = Integer.parseInt(temp.substring(6, 8)) + (Integer.parseInt(temp.substring(3, 5)) * 60); //sekunden + (minuten * 60)
					rst.close();
					return (( 900 - sek )+ (anzSchlange * 900));  // (900sek-ZeitbisJetztGefahren) + AnzWartendeUser * 60Sek * 15 min				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Logischer Fehler -> maxWarteZeitsek() wird aufgerufen obwohl niemand in der Schlange ist");
		try {
			if(rst!=null){
				rst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void abmelden(){
		try {
			pstlogout.setInt(1, this.idSitzung);
			pstlogout.executeUpdate();
			
			pstlogout = con.prepareStatement("UPDATE benutzer SET useFlag= false WHERE id=" + this.idNutzer );
			pst.executeUpdate();

			//	Sitzung wird frei... chekcen wer in Warteschlange als nächstes kommt und dann gleich buchen !!! -> Ab de lutzi!			
			bucheNaechsten(idSitzung);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
		
	private boolean bucheNaechsten(int sitzungsID) throws SQLException {
		pst = con.prepareStatement("SELECT id_benutzer FROM sitzung WHERE id_sitzung="+ (sitzungsID+1));
		rst = pst.executeQuery();
		if (rst.next()){
			pst = con.prepareStatement("UPDATE benutzer SET useFlag=true WHERE id=" + rst.getInt(1) );
			pst.executeUpdate();
			pst = con.prepareStatement("UPDATE sitzung SET beginnSteuerung=NOW() WHERE id_sitzung="+ (sitzungsID+1));
			pst.executeUpdate();
			rst.close();
			return true;
		}
		return false;
	}

	public void wartenAbbrechen(){
		try {
			pst = con.prepareStatement("DELETE FROM sitzung WHERE id_sitzung=" + this.idSitzung );
			pst.executeUpdate();
			pst = con.prepareStatement("UPDATE sitzung SET id_sitzung=id_sitzung-1 WHERE id_sitzung >" + this.idSitzung );
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DAO d = new DAO();
		try {
			d.verbindungAufbauen("jdbc:mysql://localhost:3306/cidi", "root", "mysql");
			
//			System.out.println(d.anmelden("becker", "becker"));
//			System.out.println(d.anmelden("huber", "huber"));
//			System.out.println(d.anmelden("admin", "admin"));
			
	
//			System.out.println(d.maxWarteZeitsek());
			
//			System.out.println(d.abmelden());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				d.verbindungAbbauen();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
