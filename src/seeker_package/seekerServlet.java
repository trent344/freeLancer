package seeker;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/s_login_create")
public class seekerServlet extends HttpServlet{
	
	PrintWriter out;
	seekers base;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		out=response.getWriter();
		
		String mail=request.getParameter("mail");
		String key=null;
		key=request.getParameter("login");
		int i=0;
		if(key==null) {
			key=request.getParameter("signup");
			i=1;
		}
		
		key=request.getParameter("key");
		
		base=new seekers();
		
		if(i==0) {//login
			
			key=base.isValid("mail", mail, key);
			if(key.equals("0")) {
				out.println("wrong mail or password");return;
			}
			else if(key.equals("-1")) {
				out.println("internal error, try again");return;
			}
			
		}else if(i==1) {//sign-up
			
			if(!base.insertLog(mail, key)) {
				out.println(" perhaps your mail exists, try login");return;
			}
			
			System.out.println("seeker servlet()  && i=1;");
			
			key="0";
			
		}else {
			response.sendRedirect("index.html");return;
		}
		
		base.disconnect();
		
		HttpSession session=request.getSession();
		session.setAttribute("mail", mail);
		session.setAttribute("ids", key);
		
		response.sendRedirect("seeker_home");
		
	}


}
