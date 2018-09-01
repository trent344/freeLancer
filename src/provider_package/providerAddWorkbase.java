package provider;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/provider_add")
public class providerAddWorkbase extends HttpServlet{
	HttpSession session;
	dataConnection con;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		session=request.getSession(false);
		if(session.equals(null)) {
			response.sendRedirect("launch.html");return;
		}
		int wid=Integer.parseInt(request.getParameter("wid"));
		int id=(int)session.getAttribute("id");
		
		System.out.println(wid+"\n");
		
		con=new dataConnection();
		con.connect("common");
		try {
			String works=(String)session.getAttribute("works");
			session.removeAttribute("works");
			works=wid+","+works;
			con.makePreStatement("update base set wid= '"+works+"' where pid= "+id+"");
			session.setAttribute("works", works);
			ResultSet set=con.makeStatement("select pid from prov where wid= "+wid+"");
			if(set.next()) {
				String provs=set.getString(1);con.closeSt();set.close();
				con.makePreStatement("update prov set pid= '"+id+","+provs+"' where wid= "+wid+"");
			}
			else {
				System.err.println("\nwork "+wid+" has problem in providerAdd()\n");
			}
			con.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("provider_recent_workbase?byTags=");
		
	}

}
