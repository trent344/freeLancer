package provider;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/provider_updates")
public class providerUpdates extends HttpServlet{
	
	PrintWriter out;
	HttpSession session;
	proframe proFrame;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		session=request.getSession(false);
		if(session.equals(null)) {
			response.sendRedirect("launch.html");return;
		}
		
		out=response.getWriter();
		
		String name=(String) session.getAttribute("name");
		String skills=(String) session.getAttribute("skills");
		
		proFrame = new proframe(out);
		
		response.setContentType("text/html");
		proFrame.startFrame(0);
		
		out.print("<div><form action=\"provider_finish_update\" method=\"post\">");
		out.print("name : <input type=\"text\" value=\""+name+"\" name=\"name\"><br>");
		out.print("skills : <input type=\"text\" value=\""+skills+"\" name=\"skills\"><br>");
		out.print("<input type=\"submit\" value=\"update\" name=\"sk\">");
		out.print("</form>");
		out.print("<br></div><br>");//
		
		proFrame.endFrame();
		
		session.removeAttribute("name");
		session.removeAttribute("skills");
		session.removeAttribute("mob");
		session.removeAttribute("mail");
		
		session.setAttribute("up_type", 1); // update_type=1
	}


}
