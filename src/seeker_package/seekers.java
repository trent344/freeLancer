package seeker;

import java.sql.ResultSet;
import java.sql.SQLException;
import provider.dataConnection;

public class seekers extends dataConnection{
	
	seekers(){
		connect("seekers");
	}
	
	//inserting  new information in prolog table of database 
	public boolean insertLog(String mail,String key) {
		
		if(isAvailable("mail",mail)) {
			try {
				makePreStatement("insert into log (id, mail, pass) values('0', '"+mail+"', '"+key+"' )");
				return true;
			}catch(SQLException e) {
				e.printStackTrace();
				System.out.println("insertLog() error");
			}
		}	
				
		return false;
	}
	
	//inserting data
	//datetime  format 'YYYY-MM-DD HH:MM:SS'
	synchronized public int insertData(String mail,String title,String details,String company,String tags,String deadline, String payment) {
		int id=0;
		try {
			ResultSet set=makeStatement("select max(id) from sworks");
			set.next();id=set.getInt(1)+1;
			st.close();st=null;
			String operation = id+", '"+mail+"', '"+company+"', '"+title+"', '"+details+"', '"+tags+"', '"+payment+"', '"+deadline+"'";
			makePreStatement("insert into sworks values ( "+operation +" )");
			set=makeStatement("select id from log where mail= '"+mail+"'");
			set.next();
			String newId=id+","+set.getString(1);
			st.close();
			makePreStatement("update log set id= '"+newId+"' where mail= '"+mail+"'");
			return id;
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("insertData() error");
			return 0;
		}
		
	}
		
	
	//updating data
	public boolean updateData(int id,String title,String details,String tags,String deadline, String payment) {
		String values=null;
		
		if(!title.equals(null))values+=", title= '"+title+"' ";
		if(!details.equals(null))values+=", details= '"+details+"' ";
		if(!tags.equals(null))values+=", tags= '"+tags+"' ";
		if(!payment.equals(null))values+=", payment= '"+payment+"' ";
		if(!deadline.equals(null))values+=", deadline= '"+deadline+"' ";
		
		if(values.equals(null))return true;
		
		StringBuilder sb=new StringBuilder(values);
		sb=sb.deleteCharAt(0);
		values=sb.toString();
		
		try {
			
			makePreStatement("update sworks set "+values+" where id= "+id);
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}


}
