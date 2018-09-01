package provider;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/provider_updatec")
public class providerUpdatec extends HttpServlet{
	
	HttpSession session; 
	PrintWriter out;
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
		String mob,mail;
		mob=(String) session.getAttribute("mob");mail=(String) session.getAttribute("mail");
		
		proFrame = new proframe(out);
		
		response.setContentType("text/html");
		proFrame.startFrame(0);
		
		out.print("<div><form action=\"provider_finish_update\" method=\"post\">");
		out.print("phone/mobile : <input type=\"tel\" value=\""+mob+"\" name=\"mob\"><br>");
		out.print("email : <input type=\"mail\" value=\""+mail+"\" name=\"mail\"><br>");
		out.print("<input type=\"submit\" value=\"update\" name=\"cont\">");
		out.print("</form>");
		out.print("<br></div><br>");//
		
		proFrame.endFrame();
		
		session.removeAttribute("name");
		session.removeAttribute("skills");
		session.removeAttribute("mob");
		session.removeAttribute("mail");
		
		session.setAttribute("up_type", 2); // update_type=2
		
	}


}
