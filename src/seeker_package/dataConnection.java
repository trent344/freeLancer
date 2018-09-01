package seeker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dataConnection {
	
	private Connection con=null;
	protected Statement st=null;private PreparedStatement pst=null;
	private String Url;
	
	//connect to the database
	protected boolean connect(String url){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Url="jdbc:mysql://localhost:3306/"+url;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("driver loading unsuccesfull");
			return false;
		}
		String user="root",password="bolta065";
		try {
			con=DriverManager.getConnection(Url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("connection to database unsuccesfull");
			return false;
		}
		return true;
	}
	
	
	protected void makePreStatement(String operation) throws SQLException {
		
		pst=con.prepareStatement("SET SQL_SAFE_UPDATES = 0");
		pst.executeUpdate();pst.close();pst=null;
		pst=con.prepareStatement(operation);
		pst.executeUpdate();pst.close();pst=null;
		pst=con.prepareStatement("SET SQL_SAFE_UPDATES = 1");
		pst.executeUpdate();pst.close();pst=null;
		
	}
	
	protected ResultSet makeStatement(String query) throws SQLException {
		
		ResultSet set=null;
		st=con.createStatement();
		set=st.executeQuery(query);
		return set;
	}
	
	//checking for validity of user and key provided
	public String isValid(String user,String uid,String key) {
		
		String id=null;
		
		try {
			
			ResultSet rs=makeStatement("select * from log where "+user+" = '"+uid+"'");
			
			if(rs.next()) {
				
				if(key.equals(rs.getString(3)))id= rs.getString(1);
				else id= "0";
				
			}else {
				
				id= "0";
				
			}
			st.close();st=null;
		}catch(SQLException e) {
			
			System.out.println("isValid() gone Exception");
			id="-1";
			
		}
		
		
		
		return id;
	}
	
	//is user  Available?
	protected boolean isAvailable(String user,String uid) {
		int id;
		try {
			ResultSet set=makeStatement("select count(*) from log where "+user+" = '"+uid+"'");
			set.next();id=set.getInt(1);st.close();st=null;
			if(id!=0) return false;
			
		}catch(SQLException e) {
			e.printStackTrace();
			
			System.out.println("isAvailable() error");
			return false;
		}
		
		return true;
		
	}
	
	//retrieving information from database
	public ResultSet retrieveData(int id,String table) {
		
		ResultSet set=null;
		
		try {
			
			if(id==0)
				set=makeStatement("select * from "+table);
			else
				set=makeStatement("select * from "+table+" where id = "+id+"");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("retrieveData() error");
		}
		
		return set;
	}
	
	public boolean closeSt() {
		try {
			st.close();st=null;
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("closeSt() error");
			return false;
		}
		return true;
	}

	//deleting information from database
	public boolean deleteData() {
		
		return true;
	}
	
	public void disconnect() {
		try {
			con.close();con=null;
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("disconnect() error");
		}
	}


}
