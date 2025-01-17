package com.trailerHunt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/img")
public class del extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        PrintWriter pr = null;
        Connection connection = null;
        PreparedStatement psmt = null;

        try {
            pr = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/deleete", "root", "sagar@120");
            String name = request.getParameter("name");
            String img = request.getParameter("imgfile");
            File myfile = new File(img);
            
            try (FileInputStream isp = new FileInputStream(myfile)) {
                String Query = "insert into img_test (sname, img) values (?, ?)";
                psmt = connection.prepareStatement(Query);
                psmt.setString(1, name);
                psmt.setBinaryStream(2, isp, (int) myfile.length());
                
                int rowsAffected = psmt.executeUpdate();
                if (rowsAffected > 0) {
                    pr.print("<h1 align=center>Records Inserted Successfully</h1>");
                } else {
                    pr.print("<h1 align=center>Failed to insert the Records</h1>");
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            pr.print("<h1 align=center>Error loading database driver</h1>");
        } catch (SQLException e) {
            e.printStackTrace();
            pr.print("<h1 align=center>Error with SQL operation</h1>");
        } catch (IOException e) {
            e.printStackTrace();
            pr.print("<h1 align=center>Error reading the file</h1>");
        } finally {
            try {
                if (psmt != null) psmt.close();
                if (connection != null) connection.close();
                if (pr != null) pr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
