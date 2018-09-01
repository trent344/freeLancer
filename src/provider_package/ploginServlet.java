package provider;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/plogin")
public class ploginServlet extends HttpServlet{
	
	providers proData;
	PrintWriter out;
	HttpSession session;
	
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String user=request.getParameter("user");
		String key=request.getParameter("key");
		
		proData=new providers();

		int id=Integer.parseInt(proData.isValid("uname",user, key));
		proData.disconnect();
		
		out=response.getWriter();
		
		if(id==0) {out.println("wrong password!!");return;}
		else if(id==-1) {out.println("wrong userID provided!!");return;}
		else if(id==-2) {out.println("internal error!!\n try again !!!");return;}
		
		else {
			session=request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("uid", user);
			
			response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "0");
			
			response.sendRedirect("provider_home");
			
		}
		
	}


}
