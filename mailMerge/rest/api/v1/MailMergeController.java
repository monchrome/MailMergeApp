package com.mborg.mailMerge.rest.api.v1;

import com.mborg.mailMerge.model.Recipient;
import com.mborg.mailMerge.service.RecipientService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
 @author monChrome
 */
@RestController
public class MailMergeController {
    
    private final RecipientService recipientService;

    @Autowired
    public MailMergeController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }
 
    @PostMapping("/v1/mailMerge/recipients/create")
    public ResponseEntity<String> addRecipients(@RequestBody
                                     @NotEmpty(message= "Recipients list cannot be empty.")
                                     List<@Valid Recipient> recipientList) {
            recipientService.addRecipients(recipientList);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping ("/v1/mailMerge/recipients/upload")
    public ResponseEntity<String> uploadRecipientDetails(@Valid @NotNull @RequestParam("csvfile")MultipartFile csvFile) {
            recipientService.createRecipients(csvFile);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
