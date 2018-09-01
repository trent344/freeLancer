package provider;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/provider_finish_update")
public class finishUpdate extends HttpServlet{
	
	PrintWriter out;
	providers data;
	HttpSession session;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		out=response.getWriter();
		
		data=new providers();
		
		session=request.getSession(false);
		if(session.equals(null)) {
			response.sendError(530, "you should goto login or signup page first");return;
		}
		int id=(int) session.getAttribute("id");
		int type=(int) session.getAttribute("up_type");
		session.removeAttribute("up_type");
		
		if(type==1) {
			
			String name=request.getParameter("name");
			String skills=request.getParameter("skills");
			
			if(name.length()<2) {
				out.println("your name is too short");return;
			}
			if(skills.length()<2) {
				out.println("provide your skills clearly.");return;
			}
			
			data.updateData(id, 1, name, skills);
			
		}
		else if(type==2) {
			
			String mob=request.getParameter("mob");
			String mail=request.getParameter("mail");
			
			if(mob.length()<10 || mail.length()<5) {
				out.println("your contact information are not upto the mark.");return;
			}
			

			data.updateData(id, 2, mob, mail);
			
		}
		
		data.disconnect();
		
		response.sendRedirect("provider_home");
		
	}


}
