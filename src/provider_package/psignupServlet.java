package provider;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/psignup")
public class psignupServlet extends HttpServlet{
	
	providers proData;
	PrintWriter out;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		out=response.getWriter();
		
		String pname=request.getParameter("pname");
		if(pname.equals(null) || pname.length()<2) {
			out.println("your name should have length greater than 1");
			return;
		}
		
		String tags=request.getParameter("tags");
		if(tags.equals(null)) {
				out.println("you must provide your skills");
				return;
		}
		
		String mob=request.getParameter("mob");
		String mail=request.getParameter("mail");
		if(mob.length()<10 && mail.equals(null)) {
			out.println("your contacts are not up to mark..");
			out.println("check your email box and phone no. box");
			return;
		}
		
		String uid=request.getParameter("uid");
		
		String key=request.getParameter("key");
		String rekey=request.getParameter("rekey");
		if(key.length()<8 || (!key.equals(rekey)) || key.equals(uid)) {
			out.println("precautions : ");
			out.println("1. your passwords must be at least of length 8 characters");
			out.println("2. your passwords must not be same as user ID");
			out.println("3. your both passwords must match");
			return;
		}
		
		proData=new providers();//providers.class is a way to connect to database of providers
		
		int id=proData.insertLog(uid, key);//create and returns an id associated with this data (or say user)
		
		if(id==0) {out.println("user ID is not unique.");return;}
		else if(id==-1) {out.println("internal problem try again.");return ;}
		else {
      //insert all personal data to database following arguments 1 and 2 are for different tables 
			proData.insertData(pname, tags, id, 1);
			proData.insertData(mob, mail, id, 2);
		}
		
		proData.disconnect();
		
		HttpSession session=request.getSession();
		session.setAttribute("id", id);
		session.setAttribute("uid", uid);
		
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		response.sendRedirect("provider_home");
	}

}
