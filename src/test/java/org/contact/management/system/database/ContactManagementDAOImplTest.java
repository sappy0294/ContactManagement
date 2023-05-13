package org.contact.management.system.database;

import org.contact.management.system.TestData;
import org.contact.management.system.exception.DatabaseDependencyException;
import org.contact.management.system.model.ContactDetail;
import org.contact.management.system.model.UserDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactManagementDAOImplTest {

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @InjectMocks
    private ContactManagementDAOImpl contactManagementDAOImpl;

    @Test
    public void getAllUsersdetails_happyCase() throws SQLException, DatabaseDependencyException {

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getString("Uname")).thenReturn("Test1");
        when(resultSet.getString("Uphonenumber")).thenReturn("8378939474");
        when(resultSet.getString("Ucity")).thenReturn("Redmond");
        when(resultSet.getString("State")).thenReturn("WA");
        List<UserDetail> userDetailList = contactManagementDAOImpl.getAllUsersdetails();

        Assert.assertFalse(userDetailList.isEmpty());
        Assert.assertEquals(userDetailList.size(), 1);
        Assert.assertThat(userDetailList.get(0), samePropertyValuesAs(TestData.getUserDetail("Test1", "8378939474", "Redmond", "WA")));
    }

    @Test(expected = DatabaseDependencyException.class)
    public void getAllUsersdetails_exception() throws SQLException, DatabaseDependencyException {
        try {
            when(connection.createStatement()).thenReturn(statement);
            when(statement.executeQuery(any())).thenThrow(SQLException.class);
            contactManagementDAOImpl.getAllUsersdetails();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Test
    public void getUserdetail_happycase() throws SQLException {
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("Uname")).thenReturn("Test1");
        when(resultSet.getString("Uphonenumber")).thenReturn("8378939474");
        when(resultSet.getString("Ucity")).thenReturn("Redmond");
        when(resultSet.getString("State")).thenReturn("WA");
        UserDetail userdetail = contactManagementDAOImpl.getUserdetail("Test1");
        Assert.assertNotNull(userdetail);
        Assert.assertThat(userdetail, samePropertyValuesAs(TestData.getUserDetail("Test1", "8378939474", "Redmond", "WA")));
    }

    @Test(expected = RuntimeException.class)
    public void getUsersdetail_exception() throws SQLException {
        try {
            when(connection.prepareStatement(any())).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
            contactManagementDAOImpl.getUserdetail("Test1");
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Test
    public void getUserdetail_Nodetailsexist() throws SQLException {
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        UserDetail userdetail = contactManagementDAOImpl.getUserdetail("Test1");
        Assert.assertNull(userdetail);
    }

    @Test
    public void getContactdetail_happycase() throws SQLException {
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt("Cid"))
                .thenReturn(1)
                .thenReturn(5)
                .thenReturn(6);
        when(resultSet.getString("Cname"))
                .thenReturn("Test2")
                .thenReturn("Test3")
                .thenReturn("Test4");
        when(resultSet.getString("Cphonenumber"))
                .thenReturn("8484692640")
                .thenReturn("8484692641")
                .thenReturn("8484692642");
        when(resultSet.getString("Uname"))
                .thenReturn("Test1")
                .thenReturn("Test1")
                .thenReturn("Test1");

        List<ContactDetail> contactDetailList = contactManagementDAOImpl.getContactdetails("Test1");

        List<ContactDetail> expectedContactDetailList = new ArrayList<>();
        expectedContactDetailList.add(TestData.getContactDetail(1, "Test2", "8484692640", "Test1"));
        expectedContactDetailList.add(TestData.getContactDetail(5, "Test3", "8484692641", "Test1"));
        expectedContactDetailList.add(TestData.getContactDetail(6, "Test3", "8484692642", "Test1"));

        Assert.assertNotNull(contactDetailList);
        Assert.assertEquals(contactDetailList.size(), 3);
        Assert.assertThat(contactDetailList, samePropertyValuesAs(expectedContactDetailList));

    }

    @Test
    public void getAllContactdetails_happycase() throws SQLException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("Cid")).thenReturn(1);
        when(resultSet.getString("Cname")).thenReturn("Test2");
        when(resultSet.getString("Cphonenumber")).thenReturn("8378939475");
        when(resultSet.getString("Uname")).thenReturn("Test1");
        List<ContactDetail> contactDetailList = contactManagementDAOImpl.getAllContactdetails();
        Assert.assertNotNull(contactDetailList);
        Assert.assertEquals(contactDetailList.size(), 1);
        Assert.assertThat(contactDetailList.get(0), samePropertyValuesAs(TestData.getContactDetail(1, "Test2", "8378939475", "Test1")));
    }

    @Test(expected = RuntimeException.class)
    public void getContactdetails_exception() throws SQLException {
        try {
            when(connection.createStatement()).thenReturn(statement);
            when(statement.executeQuery(any())).thenThrow(SQLException.class);
            contactManagementDAOImpl.getAllContactdetails();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Test
    public void getContactnumberdetail_happycase() throws SQLException {

        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("Cid")).thenReturn(1);
        when(resultSet.getString("Cname")).thenReturn("Test2");
        when(resultSet.getString("Cphonenumber")).thenReturn("8378939475");
        when(resultSet.getString("Uname")).thenReturn("Test1");
        ContactDetail contactdetail = contactManagementDAOImpl.getContactnumberdetail("Test1", "Test2");
        Assert.assertNotNull(contactdetail);
        Assert.assertThat(contactdetail, samePropertyValuesAs(TestData.getContactDetail(1, "Test2", "8378939475", "Test1")));
    }

    @Test(expected = RuntimeException.class)
    public void getContactnumberdetail_exception() throws SQLException {
        try {
            when(connection.prepareStatement(any())).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
            contactManagementDAOImpl.getContactnumberdetail("Test1", "Test2");
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Test
    public void getContactnumberdetail_Nodetailsexist() throws SQLException {
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        ContactDetail contactDetail = contactManagementDAOImpl.getContactnumberdetail("Test1", "Test2");
        Assert.assertNull(contactDetail);
    }

    @Test
    public void getContactdetail_happycase1() throws SQLException {

        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("Cid")).thenReturn(1);
        when(resultSet.getString("Cname")).thenReturn("Test2");
        when(resultSet.getString("Cphonenumber")).thenReturn("8378939475");
        when(resultSet.getString("Uname")).thenReturn("Test1");
        ContactDetail contactdetail = contactManagementDAOImpl.getContactdetail("Test1", "Test2", "8378939475");
        Assert.assertNotNull(contactdetail);
        Assert.assertThat(contactdetail, samePropertyValuesAs(TestData.getContactDetail(1, "Test2", "8378939475", "Test1")));
    }

    @Test
    public void getContactdetail_Nodetailsexist() throws SQLException {
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        ContactDetail contactDetail = contactManagementDAOImpl.getContactdetail("Test1", "Test2", "8378939475");
        Assert.assertNull(contactDetail);
    }

    @Test(expected = RuntimeException.class)
    public void getContactdetail_exception() throws SQLException {
        try {
            when(connection.prepareStatement(any())).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
            contactManagementDAOImpl.getContactdetail("Test1", "Test2", "8378939475");
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Test
    public void addUserdetails_happycase() throws SQLException {
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        UserDetail userDetail = TestData.getUserDetail("Test1", "8378939474", "Redmond", "WA");
        contactManagementDAOImpl.addUserdetails(userDetail);
        Mockito.verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void addContactdetails_happycase() throws SQLException {
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        contactManagementDAOImpl.addContactdetails("Test1", "Test2", "8378939474");
        Mockito.verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void updateContactdetails_happycase() throws SQLException {
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        contactManagementDAOImpl.updateContactdetails("Test2", "8378939474", "Test1");
        Mockito.verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test(expected = RuntimeException.class)
    public void updateContactdetails_exception() throws SQLException {
        try {
            when(connection.prepareStatement(any())).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
            contactManagementDAOImpl.updateContactdetails("Test2", "8378939474", "Test1");
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Test
    public void deleteContactdetails_happycase() throws SQLException {
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        contactManagementDAOImpl.deleteContactdetails("Test1", "Test2");
        Mockito.verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test(expected = RuntimeException.class)
    public void deleteContactdetails_exception() throws SQLException {
        try {
            when(connection.prepareStatement(any())).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
            contactManagementDAOImpl.deleteContactdetails("Test1", "Test2");
        } catch (Exception ex) {
            throw ex;
        }
    }
}