package com.mborg.mailMerge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;


/*
    @author monChrome
 */

@NoArgsConstructor
@Data
@Entity
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    @Pattern(regexp = "^([a-zA-Z ]+$)", message = "Only alphabetical characters, with spaces, permitted in firstName.")
    String firstName;

    @Pattern(regexp = "^([a-zA-Z ]+$)", message = "Only alphabetical characters, with spaces, permitted in middleName.")
    String middleName;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z ]+$)", message = "Only alphabetical characters, with spaces, permitted in lastName.")
    String lastName;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$",
            message = "Invalid email address.")
    String emailAddress;

    public Recipient(String firstName, String middleName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }
}
