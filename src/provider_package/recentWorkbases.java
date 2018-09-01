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

@WebServlet("/provider_recent_workbase")

public class recentWorks extends HttpServlet{
	
	PrintWriter out;
	HttpSession session;
	ResultSet set;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		out=response.getWriter();
		
		session=request.getSession(false);
		if(session.equals(null)) {
			response.sendRedirect("launch.html");return;
		}
		
		String filtertags=request.getParameter("byTags");
		String w=(String) session.getAttribute("works");
		w=","+w+",";
		set=null;
		workBase base=new workBase();
		
		proframe proFrame=new proframe(out);
		proFrame.startFrame(2);
		out.print("<form action =\"provider_recent_workbase\" method=\"get\">");
		out.print("<input type=\"text\" value=\"skills\" name=\"byTags\">");
		out.print("<input type=\"submit\" value=\"Apply\">");
		out.print("</form>");
		out.print("</hr>");
		//data accordingly
		try {
			if(!filtertags.equals(null))set=base.makeStatement("select * from sworks where tags like '%"+filtertags+"%'");
			else set=base.makeStatement("select * from sworks");
			while(set.next()) {
				String wid=","+set.getInt(1)+",";
				out.print("<div><b>#"+set.getString(4)+"</b><br><small>@"+set.getString(3)+"<br>%%"+set.getString(8)+"</small><br>");
				out.print("$"+set.getString(7)+"<br><br>Details : "+set.getString(5)+"");
				out.print("<form action=\"provider_add\" method=\"post\">");
				if(w.contains(wid)) {
					out.println("<p style=\"color:#00FF00\">Added</p></div>");
				}
				else {
					out.print("<input type=\"hidden\" value=\""+set.getInt(1)+"\" name=\"wid\">");
					out.print("<input type=\"submit\" value=\"Add to my workbase\"></form></div>");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		proFrame.endFrame();
		base.closeSt();base.disconnect();
	}

}
