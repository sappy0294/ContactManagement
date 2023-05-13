package org.contact.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDetail {

    private Integer Cid;
    private String Cname;
    private String Cphonenumber;
    private String Uname;
}
