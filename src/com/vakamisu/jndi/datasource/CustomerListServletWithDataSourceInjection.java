package com.vakamisu.jndi.datasource;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/injectedList")
public class CustomerListServletWithDataSourceInjection extends HttpServlet {

    //Tomcat will look up the specified resource name
    // and inject an actual implementation when it
    // discovers this annotation
    @Resource(name = "jdbc/vakamisu")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        try {
            Connection connection = dataSource.getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");

            while (resultSet.next()) {
                out.printf("Customer #%s: %s, %s, %s\n\n",
                        resultSet.getString("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email")
                );
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
