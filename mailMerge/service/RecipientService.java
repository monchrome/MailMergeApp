package com.mborg.mailMerge.service;

import com.mborg.mailMerge.model.Recipient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
    @author monChrome
 */
public interface RecipientService {
    public void addRecipients(List<Recipient> recipientList) ;

    public void createRecipients(MultipartFile csvFile) ;
}
