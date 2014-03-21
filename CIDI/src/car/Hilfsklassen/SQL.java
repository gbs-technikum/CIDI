package car.Hilfsklassen;

import java.sql.*;

public class SQL {

	
	
	public SQL() {
		// TODO Auto-generated constructor stub
	}

//	public Boolean pruefeLoginDaten(String nutzerName, String passwort){
//		
//		String sqlNutzerName = "";
//		String sqlPasswort = "";
//		
//		ResultSet rs = statement.executeQuery("SELECT FROM Tabelle" ) ;
//				while(rs.next()){
//				
//				sqlNutzerName = rs.getString("Nutzername");
//				passwort = rs.getString("Passwort");
//				
//				rs.close();
//				
//				}
//		return true;
//	}
	
	
	public Boolean pruefeLogindaten(String nutzerName, String pw){
		PreparedStatement pst = null;
		Connection connection = null;
		String datenbank = "jdbc:mysql://localhost:3306/cidi" ;
		String user = "root" ;
		String passwort = "mysql" ;
		ResultSet rst = null;
		
		try {
			//Verbindung DB cidi
			connection = DriverManager.getConnection(datenbank , user , passwort);
			System.out.println("-> Verbindung hergestellt!");
			
			//Abfrage
			pst =  connection.prepareStatement("SELECT id FROM user WHERE name=? AND passwort=?");
			pst.setString(1,nutzerName);
			pst.setString(2, pw);
			rst = pst.executeQuery();
			
			if(rst.next()){
				System.out.println("Benutzer in Datenbank vorhanden!");
				
				//useFlag setzen
				String sql = "INSERT INTO user (useFlag) VALUES	(true) WHERE name="+ user);
						statement.executeUpdate(sql); 
				
				return true;
			} else {
			System.out.println("Nutzername oder Passwort falsch, bitte nochmal verwsuchen!");
			}
						
/*			while(rst.next()){
				System.out.println(rst.getInt(1));
				System.out.println(rst.getString(2));
				System.out.println(rst.getString(3));
			}   */
					
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rst != null){
				try {
					rst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public static void treiberLaden(){
		try {
			Class.forName("com. mysql . jdbc . Driver" ).newInstance ();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SQL.verbindungDatenbank();
	}
}
