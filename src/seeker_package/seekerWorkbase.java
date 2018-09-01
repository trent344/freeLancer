package seeker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/seeker_workbase")
public class seekerWorkbase extends HttpServlet{
	
	HttpSession session;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		session=request.getSession(false);
		if(session.equals(null)) {
			response.sendRedirect("launch.html");return;
		}
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		sframe sFrame=new sframe(out);
		String w[]=((String)session.getAttribute("ids")).split(",");
		int wid;
		ResultSet set=null;
		seekers base=new seekers();
		sFrame.startFrame(1);
		for(String work : w) {
			if((!work.equals(null)) && (!work.equals("0"))) {
				wid=Integer.parseInt(work);
				try {
					set=base.retrieveData(wid, "sworks");
					if(set.next()) {
						out.print("<div><b>#"+set.getString(4)+"</b><br><small>@"+set.getString(3)+"<br>%%"+set.getString(8)+"</small><br>");
						out.print("$"+set.getString(7)+"<br>Skills Required : "+set.getString(6)+"<br>Details : "+set.getString(5)+"");
						out.println("<form action=\"edit_added_workbase\" method=\"post\">");
						out.println("<input type=\"hidden\" name=\"wid\" value=\""+wid+"\">");
						out.println("<input type=\"submit\" value=\"Edit\"></form>");
						out.println("<form action=\"workbase_progress\" method=\"post\">");
						out.println("<input type=\"hidden\" name=\"wid\" value=\""+wid+"\">");
						out.println("<input type=\"submit\" value=\"Inspect Progress\">");
						out.println("</form></div>");
					}
					set.close();base.closeSt();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		base.disconnect();
		sFrame.endFrame();
		
	}

}
