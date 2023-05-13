package org.contact.management.system.activity;

import org.contact.management.system.TestData;
import org.contact.management.system.database.ContactManagementDAO;
import org.contact.management.system.exception.ContactDetailsExistException;
import org.contact.management.system.exception.InvalidInputException;
import org.contact.management.system.model.ContactDetail;
import org.contact.management.system.model.UserDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ActivityTest {

    @Mock
    private ContactManagementDAO contactManagementDAO;


    @InjectMocks
    private Activity activity;

    @Test(expected = InvalidInputException.class)
    public void getUserDetails_nullparamater() throws InvalidInputException {
        try {
            activity.getUserDetails(null);
        } catch (Exception ex) {
            verify(contactManagementDAO, never()).getUserdetail(any());
            throw ex;
        }
    }

    @Test(expected = InvalidInputException.class)
    public void getUserdetails_emptyparameter() throws InvalidInputException, SQLException {
        try {
            activity.getUserDetails("");
        } catch (Exception ex) {
            verify(contactManagementDAO, never()).getUserdetail(any());
            throw ex;
        }
    }

    @Test
    public void getUserdetails_happycase() throws InvalidInputException {
        final String userName = "TestUser";
        UserDetail expectedUserDetail = TestData.getUserDetail("Test1", "8378939475", "Redmond", "WA");
        when(contactManagementDAO.getUserdetail(userName)).thenReturn(expectedUserDetail);
        UserDetail userDetail = activity.getUserDetails(userName);
        Assert.assertThat(userDetail, samePropertyValuesAs(expectedUserDetail));
    }

    @Test(expected = InvalidInputException.class)
    public void getUserContactdetails_nullparameter() throws InvalidInputException, SQLException {
        try {
            activity.getUserContactDetails(null);
        } catch (Exception ex) {
            verify(contactManagementDAO, never()).getUserdetail(any());
            throw ex;
        }
    }

    @Test(expected = InvalidInputException.class)
    public void getUserContactdetails_emptyparameter() throws InvalidInputException, SQLException {
        try {
            activity.getUserContactDetails("");
        } catch (Exception ex) {
            verify(contactManagementDAO, never()).getUserdetail(any());
            throw ex;
        }
    }

    @Test
    public void getUserContactdetails_happycase() throws SQLException, InvalidInputException {
        ContactDetail expectedContactDetail = TestData.getContactDetail(1, "Test2", "2243434422", "Test1");
        List<ContactDetail> contactDetailList = new ArrayList<>();
        contactDetailList.add(expectedContactDetail);
        when(contactManagementDAO.getContactdetails("Test1")).thenReturn(contactDetailList);
        List<ContactDetail> contactDetail = activity.getUserContactDetails("Test1");
        Assert.assertNotNull(contactDetail);
        Assert.assertEquals(contactDetail.size(), 1);
        Assert.assertThat(contactDetail.get(0), samePropertyValuesAs(contactDetailList.get(0)));
    }
    @Test(expected = InvalidInputException.class)
    public void getUserSpecificContactdetails_nulluserparameter() throws InvalidInputException{
        try {
            activity.getUserSpecificContactDetails(null,"Test2");
        } catch (Exception ex) {
            verify(contactManagementDAO, never()).getContactnumberdetail(any(),any());
            throw ex;
        }
    }

    @Test(expected = InvalidInputException.class)
    public void getUserSpecificContactdetails_emptyuserparameter() throws InvalidInputException {
        try {
            activity.getUserSpecificContactDetails("","Test2");
        } catch (Exception ex) {
            verify(contactManagementDAO, never()).getContactnumberdetail(any(),any());
            throw ex;
        }
    }
    @Test(expected = InvalidInputException.class)
    public void getUserSpecificContactdetails_nullcontactparameter() throws InvalidInputException{
        try {
            activity.getUserSpecificContactDetails("Test1",null);
        } catch (Exception ex) {
            verify(contactManagementDAO, never()).getContactnumberdetail(any(),any());
            throw ex;
        }
    }

    @Test(expected = InvalidInputException.class)
    public void getUserSpecificContactdetails_emptycontactparameter() throws InvalidInputException {
        try {
            activity.getUserSpecificContactDetails("Test1","");
        } catch (Exception ex) {
            verify(contactManagementDAO, never()).getContactnumberdetail(any(),any());
            throw ex;
        }
    }
    @Test
    public void getUserSpecificContactdetails_happycase() throws InvalidInputException {

        ContactDetail expectedContactDetail = TestData.getContactDetail(1, "Test2", "2243434422", "Test1");
        when(contactManagementDAO.getContactnumberdetail("Test1","Test2")).thenReturn(expectedContactDetail);
        ContactDetail contactDetail = activity.getUserSpecificContactDetails("Test1","Test2");
        Assert.assertNotNull(contactDetail);
        Assert.assertThat(contactDetail, samePropertyValuesAs(expectedContactDetail));

    }
    @Test(expected = ContactDetailsExistException.class)
    public void addContactDetails_exception() throws SQLException, ContactDetailsExistException {
        ContactDetail contactDetail = TestData.getContactDetail(1,"Test2","3939389949","Test1");
        when(contactManagementDAO.getContactdetail("Test1","Test2","3939389949"))
                .thenReturn(contactDetail);
        activity.addContactDetails("Test1", "Test2", "3939389949");
    }
    @Test(expected = ContactDetailsExistException.class)
    public void addContactDetailsPhonenumber_exception() throws SQLException, ContactDetailsExistException {
        when(contactManagementDAO.getContactdetail("Test1","Test2","3939389949"))
                .thenReturn(null);
       ContactDetail contactdetail = TestData.getContactDetail(1,"Test2","3939389949","Test1");
        when(contactManagementDAO.getContactnumberdetail("Test1","Test2")).thenReturn(contactdetail);
        activity.addContactDetails("Test1","Test2","3939389949");
    }
    @Test
    public void addContactDetailsPhonenumber_happycase() throws SQLException, ContactDetailsExistException {
        when(contactManagementDAO.getContactdetail("Test1","Test2","3939389949"))
                .thenReturn(null);
        when(contactManagementDAO.getContactnumberdetail("Test1","Test2")).thenReturn(null);
        when(contactManagementDAO.addContactdetails("Test1","Test2","3939389949")).thenReturn(1);
        activity.addContactDetails("Test1","Test2","3939389949");
    }
}