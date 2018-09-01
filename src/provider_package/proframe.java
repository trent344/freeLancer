package provider;

import java.io.PrintWriter;

public class proframe {
	PrintWriter out;
	proframe(PrintWriter out){
		this.out=out;
	}
	
	protected void startFrame(int i) {
		String w[]=new String[5];
		w[0]=null;w[1]=null;w[2]=null;w[3]=null;w[4]=null;w[i]="class=\"active\"";
		out.print("<html>\n<head>\n");
		out.print("<title>freeLancer</title>\n");
		out.print("<style>\n");
		out.print("div{\n" + 
				"margin-left: 2em;\n"+ 
				"margin-right: 2em;\n" + 
				"padding-left: 1em;\n" + 
				"padding-right: 1em;\n" + 
				"padding-top: 1em;\n" + 
				"padding-bottom: 1em;\n" + 
				"box-shadow: -5px 5px 10px #aaaaaa;\n" + 
				"}\n");
		out.println("form{\n" + 
				"display: inline;\n" + 
				"}\n" + 
				".topnav {\n" + 
				"  overflow: hidden;\n" + 
				"  background-color: #b0a8d7 ;\n" + 
				"}\n" + 
				"\n" + 
				".topnav a {\n" + 
				"  float: left;\n" + 
				"  color: #f2f2f2;\n" + 
				"  text-align: center;\n" + 
				"  padding: 14px 16px;\n" + 
				"  text-decoration: none;\n" + 
				"  font-size: 17px;\n" + 
				"}\n" + 
				"\n" + 
				".topnav a:hover {\n" + 
				"  background-color: #ddd;\n" + 
				"  color: black;\n" + 
				"}\n" + 
				"\n" + 
				".topnav a.active {\n" + 
				"  background-color:  #0b0237 ;\n" + 
				"  color: white;\n" + 
				"}");
		out.println("</style>\n" + 
				"</head>");
		out.println("<body style= \"margin-left: 2em; margin-right: 2em\">");
		out.println("<p style=\"font-size:300%\"><b>f</b>ree<b>L</b>ancer<p>\n" + 
				"<p align=\"right\"><a href=\"provider_home\" >Profile</a>\n" + 
				"<a href=\"provider_logout\" style=\"margin-left:3em\">Logout</a></p>\n" + 
				"<p class=\"topnav\">\n" + 
				"<a "+w[1]+" href=\"provider_workbase\">My Workbase</a>\n" + 
				"<a "+w[2]+" href=\"provider_recent_workbase?byTags=\">Recent Workbase</a>\n" + 
				"<a "+w[3]+" href=\"provider_groups\">My Groups</a>\n" + 
				"<a "+w[4]+" href=\"provider_documentation\">Documentation</a>\n" + 
				"</p><br>");
		
	}
	
	protected void endFrame() {
		out.println("</body></html>");
	}
	
	
}
