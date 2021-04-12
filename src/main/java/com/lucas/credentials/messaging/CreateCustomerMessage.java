package com.lucas.credentials.messaging;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateCustomerMessage implements Serializable {
    private long credentialsId;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<String> phoneNumbers;

    public CreateCustomerMessage() {

    }

    public CreateCustomerMessage(long credentialsId, String firstName, String middleName, String lastName, List<String> phoneNumbers) {
        this.credentialsId = credentialsId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumbers = phoneNumbers;
    }
}
