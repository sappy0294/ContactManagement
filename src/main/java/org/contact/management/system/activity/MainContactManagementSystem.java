package org.contact.management.system.activity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.contact.management.system.exception.ContactDetailsExistException;
import org.contact.management.system.exception.InvalidInputException;
import org.contact.management.system.model.UserDetail;
import org.contact.management.system.module.ContactManagementModule;

import java.sql.SQLException;

public class MainContactManagementSystem {

    public static void main(String args[]) throws SQLException, InvalidInputException, ContactDetailsExistException {
        Injector injector = Guice.createInjector(new ContactManagementModule());
        Activity activity = injector.getInstance(Activity.class);

        UserDetail userDetail = activity.getUserDetails("Test1");
        System.out.println("Userdetails: " + userDetail);
    }
}
