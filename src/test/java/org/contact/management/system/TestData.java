package org.contact.management.system;

import org.contact.management.system.model.ContactDetail;
import org.contact.management.system.model.UserDetail;

public class TestData {
    public static UserDetail getUserDetail(final String name, final String phoneNo,
                                           final String city, final String state) {
        return UserDetail.builder().Uname(name)
                .Uphonenumber(phoneNo)
                .Ucity(city)
                .Ustate(state)
                .build();
    }

    public static ContactDetail getContactDetail(final Integer cid, final String cname,
                                                 final String cphonenumber,
                                                 final String uname) {
        return ContactDetail.builder()
                .Cid(cid)
                .Cname(cname)
                .Cphonenumber(cphonenumber)
                .Uname(uname)
                .build();
    }
}
