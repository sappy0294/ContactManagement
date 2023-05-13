package org.contact.management.system.database;

import org.contact.management.system.exception.DatabaseDependencyException;
import org.contact.management.system.model.ContactDetail;
import org.contact.management.system.model.UserDetail;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for connecting with Mysql database for add/update/delete/get user contact details.
 */
public interface ContactManagementDAO {

    /**
     * To get the all user details.
     * @return - list of {@link UserDetail}
     */
    public List<UserDetail> getAllUsersdetails() throws DatabaseDependencyException;

    /**
     * To get user details based on username
     * @param Uname - user name.
     * @return - {@link UserDetail}
     */

    public UserDetail getUserdetail(String Uname);

    /**
     * To get contact details based on username
     * @param Uname - user name
     * @return - list of {@link ContactDetail}
     * @throws SQLException
     */

    public List<ContactDetail> getContactdetails(String Uname) throws SQLException;

    /**
     * To get all the contact details
     * @return - list of {@link ContactDetail}
     */

    public List<ContactDetail> getAllContactdetails();

    /**
     * To get contact details based on username and contact name
     * @param Uname - user name
     * @param Cname - contact name
     * @return - {@link ContactDetail}
     */

    public ContactDetail getContactnumberdetail(String Uname, String Cname);


    /**
     * @param Uname        -user name
     * @param Cname        - contact name
     * @param Cphonenumber - contact phonenumber
     * @return
     */
    public ContactDetail getContactdetail(String Uname, String Cname, String Cphonenumber) throws SQLException;

    /**
     * To add the user details to user table
     * @param userdetail - user name, user phonenumber, user city, user state
     * @throws SQLException
     */
    public int addUserdetails(UserDetail userdetail) throws SQLException;

    /**
     * To add contact details to contact table
     *
     * @param Uname        - user name
     * @param Cname        - contact name
     * @param Cphonenumber - contact phonenumber
     * @return
     * @throws SQLException
     */

    public int addContactdetails(String Uname, String Cname, String Cphonenumber) throws SQLException;

    /**
     * To update contact details(phone number) based on username and contact name
     * @param Cname - contact name
     * @param Cphonenumber - contact phonenumber
     * @param Uname - user name
     */

    public void updateContactdetails(String Cname, String Cphonenumber, String Uname);

    /**
     * To delete contact detail based on username and contact name
     * @param Uname - user name
     * @param Cname - contact name
     */

    public void deleteContactdetails(String Uname, String Cname);

}
