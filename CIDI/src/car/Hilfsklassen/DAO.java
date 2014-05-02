package car.Hilfsklassen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

	private Connection con;
	private PreparedStatement pstlogin, psteintragen, pstStartSteuer, pstStoppSteuer, pst;
	
	public DAO() {
		
	
	}

	public void verbinde(String url, String user, String pw) throws SQLException {
		con = DriverManager.getConnection(url, user, pw);
		pstlogin = con.prepareStatement("SELECT id FROM benutzer WHERE nutzername=? AND passwort=?");
		psteintragen = con.prepareStatement("INSERT INTO sitzung (id_benutzer, beginnSteuerung) VALUES(?, date_add(SELECT endeSteuerung FROM sitzung WHERE id_sitzung=( ? -1) , INTERVAL ? minute))");
		pstStartSteuer = con.prepareStatement("");
		pstStoppSteuer = con.prepareStatement("");
	}
	
	public void closeConnection() throws SQLException{
		if(con != null){
			con.close();
			con = null;
		}
	}
	
	public int anmelden(String benutzername, String pw) throws SQLException{
		pstlogin.setString(1, benutzername);
		pstlogin.setString(2, pw);
		ResultSet rst = pstlogin.executeQuery();
		if( rst.next() ){
			return rst.getInt(1);
		}
		return -1;
	}
	
	public int anzUserVorMir() throws SQLException{
		pst = con.prepareStatement("SELECT COUNT(id_sitzung) FROM sitzung WHERE endeSteuerung='0000-00-00 00:00:00' GROUP BY(endeSteuerung)");
		ResultSet rst = pst.executeQuery();
		
		if(rst.next()){
			return rst.getInt(1);
		}
		
		return 0;
	}
	
	public void eintragen(int id) throws SQLException{
		
		int i = this.anzUserVorMir();
		
		psteintragen.setInt(1, id);
		psteintragen.setInt(2, i);
		psteintragen.setInt(3, i*15);
		
		
		psteintragen.executeUpdate();
	}
	
	
	public static void main(String[] args) {
		DAO d = new DAO();
		try {
			d.verbinde("jdbc:mysql://localhost:3306/cidi", "root", "mysql");
			System.out.println("Verbindung erfolgreich");
			
//			System.out.println(d.anmelden("admin", "admin"));
//			System.out.println(d.anmelden("becker", "becker"));
			
			int id = d.anmelden("huber", "huber");
//			System.out.println(d.anzUserVorMir());
			if(id>0){
				d.eintragen(id);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				d.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
