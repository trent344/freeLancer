package provider;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/provider_logout")
public class providerLogout extends HttpServlet{
	
	HttpSession session;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		session=request.getSession(false);
		if(!session.equals(null)) {
			session.invalidate();
		}
		
		response.sendRedirect("launch.html");
		return;
		
	}

}
