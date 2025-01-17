package com.trailerHunt;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
@WebServlet("/register")
public class register extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
        String name = request.getParameter("name");
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");

        PrintWriter pr = response.getWriter();
        Connection connection = null;
        PreparedStatement psmt = null;
        String sqlQuery = "INSERT INTO users (name, umail, password , registereddate) VALUES (?, ?, ?,?)";
        String chkquery ="select * from users where umail =?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/trailerhunt?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "sagar@120"
            );
           
            LocalDate currentDate = LocalDate.now();
            String date = currentDate.toString();

           PreparedStatement p1 = connection.prepareStatement(chkquery);
           p1.setString(1, mail);
           ResultSet res = p1.executeQuery();
           if(res.next()) {
        	   response.setContentType("text/html;charset=UTF-8");
        	   pr.print("<script>" +
        	            "window.onload = function() {" +
        	            "  document.getElementById('error-details').innerHTML = 'Email / Password already exists';" +
        	            "};" +
        	            "</script>");
        	   RequestDispatcher rd = request.getRequestDispatcher("register.html");
        	   rd.include(request, response);

           }
           else {
        	   psmt = connection.prepareStatement(sqlQuery);
               psmt.setString(1, name);
               psmt.setString(2, mail);
               psmt.setString(3, password);
               psmt.setString(4, date);
               int rowCount = psmt.executeUpdate();
               response.setContentType("text/html;charset=UTF-8");

               if (rowCount > 0) {
            	   RequestDispatcher rd = request.getRequestDispatcher("main.html");
                   rd.forward(request, response);
               } else {
            	   RequestDispatcher rd = request.getRequestDispatcher("main.html");
            	   rd.include(request, response);
            	   pr.print("<script>" +
           	            "window.onload = function() {" +
           	            "  document.getElementById('error-details').innerHTML = 'Registration Failed';" +
           	            "};" +
           	            "</script>");
                   
               }

           }
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
			e.printStackTrace();
		} finally {
            try {
                if (psmt != null) {
                    psmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) { 
                e.printStackTrace();
            } 
        }
    }
}
