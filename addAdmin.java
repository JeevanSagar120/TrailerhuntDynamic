package com.trailerHunt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addadmin")
public class addAdmin extends HttpServlet {
    
    public void service(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException{
        
        Connection connection = null;
        
        String chkquery = "SELECT * FROM admins where umail=?";
        String query = "INSERT INTO admins (name, umail, password , registereddate) VALUES (?, ?, ?,?)";
        
        LocalDate ld = LocalDate.now();
        String date = ld.toString();
        PrintWriter pr = response.getWriter();
        try {
            
            String name = request.getParameter("name");
            String umail = request.getParameter("email");
            String password = request.getParameter("password");  // Updated here
            
            // Creating DriverFiles 
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/trailerhunt?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "sagar@120"
            );
            
            // Check if the email already exists
            PreparedStatement psmt = connection.prepareStatement(chkquery);
            psmt.setString(1, umail);
            ResultSet rs = psmt.executeQuery();
            
            if (rs.next()) {
            	pr.print("<script>" +
           	            "window.onload = function() {" +
           	            "  document.getElementById('error-details').innerHTML = 'Email already Exists';" +
           	            "};" +
           	            "</script>");
                RequestDispatcher rd = request.getRequestDispatcher("addAdmin.html");
                rd.include(request, response);
            } else {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, umail);
                ps.setString(3, password);
                ps.setString(4, date);
                int rows = ps.executeUpdate();
                
                if (rows > 0) {
                    response.sendRedirect("adminDashboard.html");
                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("addAdmin.html");
                    rd.include(request, response);
                    pr.print("<script>" +
               	            "window.onload = function() {" +
               	            "  document.getElementById('error-details').innerHTML = 'Failed to Add Admin';" +
               	            "};" +
               	            "</script>");
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Problem occurred while opening the Driver Class");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
