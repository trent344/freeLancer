package provider;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/provider_remove_workbase")
public class providerRemoveWorkbase extends HttpServlet{
	
	HttpSession session;
	
	public void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		session=request.getSession(false);
		if(session.equals(null)) {
			response.sendRedirect("launch.html");return;
		}
		int id=(int)session.getAttribute("id");
		String wid=request.getParameter("wid");
		String works=null;
		String w=(String)session.getAttribute("works");
		session.removeAttribute("works");
		dataConnection con=new dataConnection();
		con.connect("common");
		works="";
		w=","+w;
		if(w.contains(","+wid+",")) {
			String ar[]=w.split(","+wid+",");
			for(String a : ar) {
				if(!a.equals(null)) {
					works=works.concat(",");
					works=works.concat(a);
				}
			}
		}
		StringBuilder sb=new StringBuider(works);
		if(works.length()<=2)works="0";
		else {
			while(sb.charAt(0) == ',') {
				sb.deleteCharAt(0);
			}
			works=sb.toString();
		}
		session.setAttribute("works", works);
		try {
			con.makePreStatement("update base set wid = '"+works+"' where pid = "+id);
			int wId=Integer.parseInt(wid);
			ResultSet set=con.makeStatement("select pid from prov where wid = "+wId);
			if(set.next()) {
				w=set.getString(1);
				con.closeSt();set.close();
				works="";
				w=","+w;
				if(w.contains(","+id+",")) {
					String ar[]=w.split(","+id+",");
					for(String a : ar) {
						if(!a.equals(null))works=works.concat(","+a);
					}
				}
				
				if(works.length()<=2)works="0";
				else {
					sb=new StringBuilder(works);
					while(sb.charAt(0) == ',') {
						sb.deleteCharAt(0);
					}
					works=sb.toString();
				}
				con.makePreStatement("update prov set pid = '"+works+"' where wid = "+wId);
			}
			else {
				con.closeSt();set.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.disconnect();
		
		response.sendRedirect("provider_workbase");
	}
	
}
