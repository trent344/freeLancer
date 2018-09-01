package provider;

import java.sql.ResultSet;
import java.sql.SQLException;

public class providers extends dataConnection{
	
String proData[];
	
	
	providers(){
		
		connect("providers");
		proData=new String[3];
		proData[1]="prodetails";proData[2]="propers";
	}
	
	//updating information of database
		public boolean updateData(int id,int pro_type,String sec,String third) {
			String a=null,b=null,c="where id = "+id;
			
			switch(pro_type) {
				case 1:a="pname";b="tags";break;
				case 2:a="mob";b="mail";break;
				default:return false;
			}
			
			try {
				
				if(sec.equals(null))
					makePreStatement("update "+proData[pro_type]+" set "+b+" = '"+third+"' "+c);
				else if(third.equals(null))
					makePreStatement("update "+proData[pro_type]+" set "+a+" = '"+sec+"' "+c);
				else
					makePreStatement("update "+proData[pro_type]+" set "+a+" = '"+sec+"', "+b+" = '"+third+"' "+c);

			}catch(SQLException e) {
				
				e.printStackTrace();
				System.out.println("updateData() exception");
				return false;
				
			}
			
			return true;
		}
	

		//inserting  new information in prolog table of database 
		synchronized public int insertLog(String uid,String key) {
			int res=0;
			if(isAvailable("uname",uid)) {
				try {
					ResultSet set=makeStatement("select max(id) from log");
					set.next();res=set.getInt(1)+1;
					closeSt();
					makePreStatement("insert into log (id, uname, pass) values("+res+", '"+uid+"', '"+key+"' )");
					
				}catch(SQLException e) {
					e.printStackTrace();
					System.out.println("insertLog() error");
					return -1;
				}
			}
			
			
			return res;
		}
		
		
		//inserting data
		public boolean insertData(String sec,String third,int id,int pro_type) {
			
			try {
				
				makePreStatement("insert into "+proData[pro_type]+" values ("+id+", '"+sec+"', '"+third+"' )");
				
			}catch(SQLException e) {
				e.printStackTrace();
				System.err.println("insertData() error");
				return false;
			}
			
			return true;
		}


}
