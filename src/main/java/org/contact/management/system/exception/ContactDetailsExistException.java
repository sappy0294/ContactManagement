package org.contact.management.system.exception;

public class ContactDetailsExistException extends Exception {

    public ContactDetailsExistException(final String message, String personName){
        super(message);
    }

    public ContactDetailsExistException(final String message,final Throwable throwable){

        super(message,throwable);
    }
}
