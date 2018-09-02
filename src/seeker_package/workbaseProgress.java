package seeker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import provider.dataConnection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/workbase_progress")
public class workbaseProgress extends HttpServlet {
	
	public void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		HttpSession session=request.getSession(false);
		if(session.equals(null)) {
			response.sendRedirect("index.html");return;
		}
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		sframe sFrame=new sframe(out);
		sFrame.startFrame(0);
		
		int wid=Integer.parseInt(request.getParameter("wid"));
		String provs[] = null;
		
		dataConnection con=new dataConnection();
		con.connect("common");
		ResultSet set;
		try {
			set=con.makeStatement("select pid from prov where wid = "+wid+"");
			if(set.next())provs=set.getString(1).split(",");
			else{
				provs=new String[0];
			}
			set.close();con.closeSt();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.disconnect();
		con = new dataConnection();
		con.connect("providers");
		if(provs.length<=1) {
			out.println("no any active user<br>");
			sFrame.endFrame();
			con.disconnect();return;
		}
		for(String pro : provs) {
			if(!pro.equals(null)) {
				int pid= Integer.parseInt(pro);
				if(pid!=0)set=con.retrieveData(pid, "prodetails");
				else set=null;
				try {
					if(set.next()) {
						out.println("<div><b>Provider name </b> : "+set.getString(2)+"<br>");
						out.println("skills : <small>"+set.getString(3)+"</small><br>");
						out.println("progress tags : </div>");
					}
					set.close();con.closeSt();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		sFrame.endFrame();
		con.disconnect();
		
	}

}
