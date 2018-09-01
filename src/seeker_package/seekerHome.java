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


@WebServlet("/seeker_home")
public class seekerHome extends HttpServlet{
	
	PrintWriter out;
	seekers base;
	sframe sFrame;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		HttpSession session=request.getSession(false);
		if(session.equals(null)) {
			response.sendRedirect("launch.html");return;			
		}
		
		String ids=(String)session.getAttribute("ids");String id[]=null;
		
		if(!ids.equals("0")) {
			if(!ids.equals(null))id=ids.split(",");
			else ids="0";
		}
		
		out=response.getWriter();
		response.setContentType("text/html");
		
		base=new seekers(); // database which store seeker info 
		
		sFrame=new sframe(out);
		sFrame.startFrame(0);
		
		out.print("hey !! <br> welcome<br>");
		out.print("your email : <small>"+session.getAttribute("mail")+"</small> <br><hr/>");
		if(ids.equals("0")) {
			out.print("<b>you have not any running work</b>");
		}
		else {
			
			for(String k : id) {
				int uid=Integer.parseInt(k);
				if(uid==0)continue;
				ResultSet set=base.retrieveData(uid, "sworks");
				
				String company=null,title=null,deadline=null,details=null;
				try {
					set.next();
					company=set.getString(3);
					title=set.getString(4);
					details=set.getString(5);
					deadline=set.getString(8);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("seekerHome error");
				}
				
				out.print("<b># "+title+"</b><br>");
				out.print("<small>@"+company+"</small><br>");
				out.print("%% "+deadline+"<br><br>");
				out.print("<small>Details : "+details+"</small><br><hr/>");
				
			}
			
		}
		out.print("<br><hr/>");
		
		sFrame.endFrame();
		
		base.disconnect();
		
	}

}
