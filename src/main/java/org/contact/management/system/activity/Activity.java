package org.contact.management.system.activity;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.contact.management.system.database.ContactManagementDAO;
import org.contact.management.system.exception.ContactDetailsExistException;
import org.contact.management.system.exception.InvalidInputException;
import org.contact.management.system.model.ContactDetail;
import org.contact.management.system.model.UserDetail;

import java.sql.SQLException;
import java.util.List;

@Log4j2
public class Activity {

    private final ContactManagementDAO contactManagementDAO;

    @Inject
    public Activity(final ContactManagementDAO contactManagementDAO) {
        this.contactManagementDAO = contactManagementDAO;
    }

    public UserDetail getUserDetails(final String userName) throws InvalidInputException {
        if (userName == null || userName.isEmpty()) {
            throw new InvalidInputException("Username cannot be blank");
        } else {
            return contactManagementDAO.getUserdetail(userName);
        }
    }

    public List<ContactDetail> getUserContactDetails(final String userName) throws InvalidInputException, SQLException {
        if (userName == null || userName.isEmpty()) {
            throw new InvalidInputException("Username cannot be blank");
        } else {
            return contactManagementDAO.getContactdetails(userName);
        }
    }

    public ContactDetail getUserSpecificContactDetails(final String userName, final String personName) throws InvalidInputException {
        if ((userName == null || userName.isEmpty()) || (personName == null || personName.isEmpty())) {
            throw new InvalidInputException("Username and personName cannot be blank");
        } else {
            return contactManagementDAO.getContactnumberdetail(userName, personName);
        }
    }

    public void addContactDetails(final String userName, final String personName, final String personPhonenumber) throws SQLException, ContactDetailsExistException {
        ContactDetail contactDetailInPhoneNumberLevel = contactManagementDAO.getContactdetail(userName, personName, personPhonenumber);
        if (contactDetailInPhoneNumberLevel != null) {
            throw new ContactDetailsExistException("Phonenumber of ContactName already exists against the Username in ContactDetails table ", personName);
        } else {
            ContactDetail contactDetailInUserLevel = contactManagementDAO.getContactnumberdetail("Priya", "Naresh");
            if (contactDetailInUserLevel != null) {
                throw new ContactDetailsExistException("Contact already exists against the Username in ContactDetails table for User:{}", personName);
            } else {
                contactManagementDAO.addContactdetails(userName, personName, personPhonenumber);
                log.info("Added given person:{} and phoneNumber:{} into user:{} contact list:", personName, personPhonenumber, userName);
            }
        }
    }
}

