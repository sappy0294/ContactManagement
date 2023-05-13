package org.contact.management.system.model;

import lombok.*;


/**
 * This class hold User high level details.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDetail {
    /**
     * User Name
     */
    private String Uname;
    /**
     * User PhoneNumber
     */
    private String Uphonenumber;
    /**
     * User City
     */
    private String Ucity;
    /**
     * User State
     */
    private String Ustate;

}
