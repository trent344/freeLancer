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

@WebServlet("/provider_workbase")
public class providerWorkbase extends HttpServlet{
	
	HttpSession session;
	PrintWriter out;
	
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		out=response.getWriter();
		
		session=request.getSession(false);
		if(session.equals(null)) {
			response.sendRedirect("launch.html");return;
		}
		int id=(int)session.getAttribute("id");
		String works=null;
		ResultSet set=null;
		works=(String)session.getAttribute("works"); // works is a string of work-id of all personal workbases of provider separated by comma ","
		
    proframe proFrame=new proframe(out);
		proFrame.startFrame(1);  //topnav index of provider_workbase is 1 as provided in argument  of proframe
    
		String w[]=works.split(",");
		workBase con=new workBase();
		out.print("your current workbases----->><br>");
		for(String work : w) {
			if(!work.equals(null)){
				try {
					set=null;
					set=con.makeStatement("select * from sworks where id="+Integer.parseInt(work));
					if(set.next()) {
						out.println("<div>");
						out.print("<b> "+set.getString(4)+"</b><br><small>@"+set.getString(3)+"<br>%%"+set.getString(8)+"</small><br>");
						out.print("$"+set.getString(7)+"<br><br>Details : "+set.getString(5)+"");
						out.println("<form action=\"provider_remove_workbase\" method=\"post\">"
								+"<input type=\"hidden\" value=\""+work+"\" name=\"wid\">"
								+ "<input type=\"submit\" value=\"Remove\" ></form>");
						out.print("</div>");
					}
					con.closeSt();set.close();
				} catch (NumberFormatException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		out.print("<br>//your past work's documentation <br>");
    //past work area
		proFrame.endFrame();
		con.disconnect();
		
	}

}
