package com.trailerHunt;

import java.awt.Taskbar.State;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userActivity")
public class userActivity extends HttpServlet {
	public void service(HttpServletRequest request ,HttpServletResponse response) throws IOException {
		String query ="select * from useractivity";
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
	                "jdbc:mysql://localhost:3306/trailerhunt?useSSL=false&allowPublicKeyRetrieval=true",
	                "root",
	                "sagar@120"
	            );
			response.setContentType("text/html");
			PrintWriter pr = response.getWriter();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			pr.print("<h1 align=center>Users Login Activity </h1>");
			pr.print("<center style=\"margin-top:150px;\">"
					+ "<table border=1px solid black cellspacing=0px cellpadding=10px>"+
					"<tr>"+
					"<td> id </td>"+
					"<td> Name </td>"+
					"<td> Email </td>"+
					"<td> Date </td>"+
					"<td> Login Time</td>"+
					"</tr>"
					);
			while(rs.next()) {
				pr.print(
						"<tr>"+
				"<td>"+rs.getString(1)+"</td>"+
				"<td>"+rs.getString(2)+"</td>"+	
				"<td>"+rs.getString(3)+"</td>"+
				"<td>"+rs.getString(4)+"</td>"+
				"<td>"+rs.getString(5)+"</td>"+
				 "</tr>"
						);
			}
			pr.print("</table></center>");
		} catch (ClassNotFoundException e) {
			System.out.println("Problem Occured While Opening the Driver Files ");
			e.printStackTrace();
		}catch (SQLException e) {
			System.out.println("Problem Occured While Loading the Driver Files");
			e.printStackTrace();
		}
	}
}
