package org.contact.management.system.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.contact.management.system.database.ContactManagementDAO;
import org.contact.management.system.database.ContactManagementDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ContactManagementModule extends AbstractModule {
    @Override
    public void configure() {
        bind(ContactManagementDAO.class).to(ContactManagementDAOImpl.class);
    }
    private static final String URL = "jdbc:mysql://localhost:3306/Contactmanagementv2";
    private static final String USER = "root";
    private static final String PASS = "sappy";

    @Provides
    @Singleton
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

}
