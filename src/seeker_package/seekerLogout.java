package seeker;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/seeker_logout")
public class seekerLogout extends HttpServlet{
	
	HttpSession session;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		
		response.setHeader("Cache-Control" ,"no-cache, no-store, must-revalidate");
		response.setHeader("Pragma" ,"no-cache");
		response.setHeader("Expires", "0");
		
		session = request.getSession(false);
		if(!session.equals(null)) {
			session.invalidate();
		}
		
		response.sendRedirect("launch.html");
		
	}

}
