package org.contact.management.system.database;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.contact.management.system.exception.DatabaseDependencyException;
import org.contact.management.system.model.ContactDetail;
import org.contact.management.system.model.UserDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAL implementation to insert/update/query Mysql database.
 */
@Log4j2
public class ContactManagementDAOImpl implements ContactManagementDAO {


    private final Connection connection;

    @Inject
    public ContactManagementDAOImpl(final Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDetail> getAllUsersdetails() throws DatabaseDependencyException {
        log.info("Received a request to get all user details");
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Userdetail");
            log.info("Running a query: {}", resultSet.getStatement());
            List<UserDetail> userDetailList = new ArrayList<>();
            while (resultSet.next()) {
                UserDetail userdetail = getUserdetail(resultSet);
                userDetailList.add(userdetail);
            }
            log.info("Returning user details", userDetailList);
            return userDetailList;
        } catch (SQLException e) {
            final String errorMessage = String.format("Failed to execute get user details query: %s", e.getMessage());
            log.error(errorMessage);
            throw new DatabaseDependencyException(errorMessage, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetail getUserdetail(String uname) {
        log.info("Received a request to get user details based on user name");
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Userdetail WHERE Uname=?");
            log.info("Running a query: {}", ps.toString());
            ps.setString(1, uname);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                UserDetail userdetail = getUserdetail(resultSet);
                log.info("Returning user details based on user name", userdetail);
                return userdetail;
            } else {
                return null;
            }
        } catch (SQLException e) {
            log.error("Failed to execute get user details query: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ContactDetail> getContactdetails(String Uname) throws SQLException {
        log.info("Received a request to get the contact details based on username");

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM Contactdetail WHERE Uname=?");
        log.info("Running a query: {}", ps.toString());
        ps.setString(1, Uname);
        ResultSet resultSet = ps.executeQuery();
        List<ContactDetail> contactDetailList = new ArrayList<>();
        while (resultSet.next()) {
            ContactDetail contactdetail = getContactdetail(resultSet);
            contactDetailList.add(contactdetail);
        }
        log.info("Returning contact details based on user name", contactDetailList);
        return contactDetailList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ContactDetail> getAllContactdetails() {
        log.info("Received a request to get all the contact details");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from Contactdetail");
            log.info("Running a query: {}", resultSet.getStatement());
            List<ContactDetail> contactDetailList = new ArrayList<>();
            while (resultSet.next()) {
                ContactDetail contactdetail = getContactdetail(resultSet);
                contactDetailList.add(contactdetail);
            }
            log.info("Returning all the contact details ", contactDetailList);
            return contactDetailList;
        } catch (SQLException e) {
            log.error("Failed to execute get user details query: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContactDetail getContactnumberdetail(String Uname, String Cname) {
        log.info("Received a request to get the contact details based on user name and contact name");
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Contactdetail WHERE Uname=? and Cname=?");
            log.info("Running a query: {}", ps.toString());
            ps.setString(1, Uname);
            ps.setString(2, Cname);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                ContactDetail contactdetail = getContactdetail(resultSet);
                log.info("Returning the contact details based on user name and contact name", contactdetail);
                return contactdetail;
            }
        } catch (SQLException e) {
            log.error("Failed to execute get user details query: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContactDetail getContactdetail(String Uname, String Cname, String Cphonenumber) throws SQLException {
        log.info("Received a request to get the contact details based on user name and contact name and Contact phonenumber");
        ResultSet resultSet;
        try {
            String sql = "Select * FROM Contactdetail Where Uname=? and Cname=? and Cphonenumber=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            log.info("Running a query: {}", preparedStatement.toString());
            preparedStatement.setString(1, Uname);
            preparedStatement.setString(2, Cname);
            preparedStatement.setString(3, Cphonenumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ContactDetail contactdetail = getContactdetail(resultSet);
                log.info("Returning the contact details based on user name and contact name", contactdetail);
                return contactdetail;
            }
            return null;
        } catch (SQLException e) {
            log.error("Failed to execute get user details query: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addUserdetails(UserDetail userdetail) throws SQLException {
        log.info("Received a request to add the user details ");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Userdetail VALUES (?, ?, ?, ?)");
        log.info("Running a query: {}", preparedStatement.toString());
        preparedStatement.setString(1, userdetail.getUname());
        preparedStatement.setString(2, userdetail.getUphonenumber());
        preparedStatement.setString(3, userdetail.getUcity());
        preparedStatement.setString(4, userdetail.getUstate());
        log.info("Adding the user details ");
        return preparedStatement.executeUpdate();
    }

    @Override
    public int addContactdetails(String Uname, String Cname, String Cphonenumber) throws SQLException {
        log.info("Received a request to add the contact details based on username,contact name and contact phonenumber");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Contactdetail(Cname,Cphonenumber,Uname) VALUES(?, ?, ?)");
        log.info("Running a query: {}", preparedStatement.toString());
        preparedStatement.setString(1, Cname);
        preparedStatement.setString(2, Cphonenumber);
        preparedStatement.setString(3, Uname);
        log.info("Adding the contact details based on username, contact name and contact phonenumber ");
        return preparedStatement.executeUpdate();

    }

    @Override
    public void updateContactdetails(String Cname, String Cphonenumber, String Uname) {
        log.info("Received a request to update the contact details based on username,contact name and contact phonenumber");
        try {
            PreparedStatement ps = connection.prepareStatement("update Contactdetail set Cphonenumber=? where Cname=? and Uname=?");
            log.info("Running a query: {}", ps.toString());
            ps.setString(1, Cphonenumber);
            ps.setString(2, Cname);
            ps.setString(3, Uname);
            log.info("Updating the contact details based on username, contact name and contact phonenumber ");
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to execute get user details query: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteContactdetails(String Uname, String Cname) {
        log.info("Received a request to delete the contact details based on username,contact name");
        try {
            PreparedStatement ps = connection.prepareStatement("Delete from Contactdetail  where Cname=? and Uname=?");
            log.info("Running a query: {}", ps.toString());
            ps.setString(1, Cname);
            ps.setString(2, Uname);
            log.info("Deleting the contact details based on username, contact name");
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to execute get user details query: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private UserDetail getUserdetail(ResultSet resultSet) throws SQLException {
        return UserDetail.builder().Uname(resultSet.getString("Uname"))
                .Uphonenumber(resultSet.getString("Uphonenumber"))
                .Ucity(resultSet.getString("Ucity"))
                .Ustate(resultSet.getString("State")).build();
    }


    private ContactDetail getContactdetail(ResultSet resultSet) throws SQLException {
        return ContactDetail.builder().Uname(resultSet.getString("Uname"))
                .Cname(resultSet.getString("Cname"))
                .Cphonenumber(resultSet.getString("Cphonenumber")).Cid(resultSet.getInt("Cid")).build();
    }
}
