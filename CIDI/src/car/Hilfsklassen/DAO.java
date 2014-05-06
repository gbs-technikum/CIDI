package car.Hilfsklassen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

	private Connection con;
	private PreparedStatement pstlogin, psteintragen, pstwarteschlange, pstAnzUserVor, pstlogout, pst;
	
	public DAO() {

	}
	
	public void verbindungAufbauen(String url, String user, String pw) throws SQLException {
		con = DriverManager.getConnection(url, user, pw);
		pstlogin = con.prepareStatement("SELECT id FROM benutzer WHERE nutzername=? AND passwort=?");
		psteintragen = con.prepareStatement("");
		pstlogout = con.prepareStatement("UPDATE sitzung SET endeSteuerung=NOW() WHERE beginnSteuerung!='0000-00-00 00:00:00' AND endeSteuerung='0000-00-00 00:00:00' AND id_benutzer=?");
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
		ResultSet rst = pstlogin.executeQuery();
		if( rst.next() ){
			int nutzerID = Integer.parseInt(rst.getString(1)); //geprüft passt
			switch (warteschlange()) {
			case "leer":{
				psteintragen = con.prepareStatement("INSERT INTO sitzung (id_benutzer, login, beginnSteuerung) VALUES(" + nutzerID + ", NOW(), NOW())");
				psteintragen.executeUpdate();
				psteintragen = con.prepareStatement("UPDATE benutzer SET useFlag=true WHERE id=" + nutzerID);
				psteintragen.executeUpdate();
				rst.close();
				return true;
				}
			case "anstellen":{
				System.out.println("in switch -> anstellen");
				psteintragen = con.prepareStatement("INSERT INTO sitzung (id_benutzer, login) VALUES(" + nutzerID + ", NOW())");
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
		ResultSet rst = pstwarteschlange.executeQuery();
		if( rst.next() ){
			rst.close();
			return "anstellen";
		} 
		rst.close();
		return "leer";
	}

	public int maxWarteZeitsek() throws SQLException{
		ResultSet rst = pstAnzUserVor.executeQuery();
		int anzSchlange = 0;
		if(rst.next()){
			pstAnzUserVor = con.prepareStatement("SELECT DATEDIFF(NOW(),*) FROM sitzung WHERE endeSteuerung='0000-00-00 00:00:00' AND beginnSteuerung!='0000-00-00 00:00:00'");
			anzSchlange = rst.getInt(1)-1;
			rst = pstAnzUserVor.executeQuery();
			if(rst.next()){
				return rst.getInt(1)+anzSchlange*900;  //15 Min * 60 Sekunden = 900sek				
			}
		}
		System.out.println("Logischer Fehler -> maxWarteZeitsek() wird aufgerufen obwohl niemand in der Schlange ist");
		return -1; 
	}
	
	public boolean abmelden() throws SQLException{
		ResultSet rst = null;
		pst = con.prepareStatement("SELECT * FROM benutzer WHERE useFlag=true");
		rst = pst.executeQuery();
		if(rst.next()){
			pstlogout.setInt(1, rst.getInt(1));
			pstlogout.executeUpdate();
			//Sitzung wird frei... chekcen wer in Warteschlange als nächstes kommt und dann gleich buchen !!! -> Ab de lutzi!			
			return true;
		}
		return false;
	}	
	
	
	public static void main(String[] args) {
		DAO d = new DAO();
		try {
			d.verbindungAufbauen("jdbc:mysql://localhost:3306/cidi", "root", "mysql");
			System.out.println("Verbindung erfolgreich");
			
			System.out.println(d.anmelden("becker", "becker"));
//			System.out.println(d.warteschlange());
			
			System.out.println(d.abmelden());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				d.verbindungAbbauen();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
