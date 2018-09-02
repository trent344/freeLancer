package seeker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import provider.dataConnection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/seeker_added_work")
public class deployWorkbase extends HttpServlet{
	
	HttpSession session;
	seekers base;
	PrintWriter out;
	sframe sFrame;
	
	public void doPost(HttpServletRequest request ,HttpServletResponse response) throws IOException {
		
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		session=request.getSession(false);
		if(session.equals(null)) {
			response.sendRedirect("launch.html");
			return;
		}
		
		String company,title,tags,description,deadline,payment,mail;
		company=request.getParameter("company");
		title=request.getParameter("title");
		tags=request.getParameter("tags");
		description=request.getParameter("description");
		deadline=request.getParameter("deadline");
		payment=request.getParameter("payment");
		
		mail=(String) session.getAttribute("mail");
		
		base=new seekers();
		company.replaceAll("'", "\'");
		title.replaceAll("'", "\'");
		tags.replaceAll("'", "\'");
		description.replaceAll("'", "\'");
		deadline.replaceAll("'", "\'");
		payment.replaceAll("'", "\'");
		int result=base.insertData(mail, title, description, company, tags, deadline, payment);
		base.disconnect();
		
		System.out.println("result = "+result);
		
		out=response.getWriter();
		
		if(result<=0) {
			response.sendError(503, "internal error while updating the data");return;
		}
		
		dataConnection con =new dataConnection();
		con.connect("common");
		try {
			con.makePreStatement("insert into prov (wid , pid) values ("+result+" , "+"'0'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.disconnect();
		response.setContentType("text/html");
		String ids=(String) session.getAttribute("ids");
		session.removeAttribute("ids");
		session.setAttribute("ids", result+","+ids);
		
		sFrame=new sframe(out);
		sFrame.startFrame(0);
		out.print("<b> added new work \""+title+"\" to the workbase</b><hr/>");
		sFrame.endFrame();
		
	}

}
