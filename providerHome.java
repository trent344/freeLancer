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

@WebServlet("/provider_home")
public class providerHome extends HttpServlet{
	
	HttpSession session;
	PrintWriter out;
	providers proData;
	proframe proFrame;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		out=response.getWriter();
		
		session=request.getSession(false);
		if(session.equals(null)) {
			response.sendRedirect("launch.html");;return;
		}
		
		
		proData= new providers();
		
		int id=(int) session.getAttribute("id");
		ResultSet ski_Set=proData.retrieveData(id, "prodetails"); //data-table containing skills,name
		ResultSet con_Set=proData.retrieveData(id, "propers");	  //data-table containing contacts mail,mob.
		
		String name=null,skills=null,mob=null,mail=null;
		try {
			ski_Set.next();con_Set.next();
			name=ski_Set.getString(2);skills=ski_Set.getString(3);
			mob=con_Set.getString(2);mail=con_Set.getString(3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		proData.closeSt();proData.disconnect();
		dataConnection con=new dataConnection(); // super-class of providers.class 
		con.connect("common");					 // database common for accessing providers and seeker common field of view
		String works="";
		try {
			ResultSet set=con.makeStatement("select wid from base where pid = "+id); //wid=work-ids of provider pid of base data-table  
			if(set.next()) {
				works=set.getString(1);
				set.close();con.closeSt();
			}
			else{
				set.close();con.closeSt();
				works="0";
				con.makePreStatement("insert into base (pid , wid) values ("+id+" , '"+works+"' )");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.disconnect();
		
		session.setAttribute("name", name);
		session.setAttribute("skills", skills);
		session.setAttribute("mob", mob);
		session.setAttribute("mail", mail);
		session.setAttribute("works", works);
		
		proFrame=new proframe(out); // proframe(PrintWriter).class provides a basic html frame of providers
		
		response.setContentType("text/html");
		proFrame.startFrame(0); //this method start the html frame of providers with argument(0) null-selected nav-button
		
		out.print("<b>your resume</b><br>");
		//resume area
		
		out.print("<br><hr/><br>");//
		
		out.print("<b>your skills</b><br>");
		//skills
		out.print("name : <small>"+name+"</small><br>");
		out.print("skills : <small>"+skills+"</small><br>");
		out.print("<form action=\"provider_updates\" method=\"post\">");
		out.print("<input type=\"submit\" value=\"update\">");out.print("</form>");
		
		out.print("<br><hr/><br>");//
		
		out.print("<b>your contact information</b><br>");
		//contacts
		out.print("mobile/phone : <small>"+mob+"</small><br>");
		out.print("email : <small>"+mail+"</small><br>");
		out.print("<form action=\"provider_updatec\" method=\"post\">");
		out.print("<input type=\"submit\" value=\"update\">");out.print("</form>");
		
		out.print("<br><hr/><br>");//
		
		proFrame.endFrame(); //ending provider frame
		
	}


}
