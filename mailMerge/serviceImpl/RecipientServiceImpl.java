package com.mborg.mailMerge.serviceImpl;

import com.mborg.mailMerge.error.ErrorRecords;
import com.mborg.mailMerge.error.exceptions.InvalidDataException;
import com.mborg.mailMerge.model.Recipient;
import com.mborg.mailMerge.repository.RecipientsRepo;
import com.mborg.mailMerge.service.RecipientService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
    @author monChrome
 */
@Service
public class RecipientServiceImpl implements RecipientService {
    private static final String CSV_FILE_TYPE = "text/csv";
    private static final String CHAR_SET = "UTF-8";
    private static final String[] HEADERS = {"First name", "Last name", "recipient"};

    @Autowired
    RecipientsRepo recipientsRepo;

    @Override
    public void addRecipients(@Valid List<Recipient> recipientList) {
        if (recipientList.size() > 0) {
            recipientsRepo.saveAll(recipientList);
        }
    }

    @Override
    public void createRecipients(@Valid @NotNull MultipartFile csvFile)  {
        if (!csvFile.getContentType().equals(CSV_FILE_TYPE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Given file is not of type " + CSV_FILE_TYPE);
        }

        List<Recipient> recipientList =  readRecipientsFromCsv(csvFile);
        // Save all records in memory
        recipientsRepo.saveAll(recipientList);
    }

    private List<Recipient> readRecipientsFromCsv(MultipartFile csvFile) {
        try {
            InputStream inputStream = csvFile.getInputStream();
            BufferedReader csvReader = new BufferedReader(
                    new InputStreamReader(inputStream, CHAR_SET));
            // First line of input file to be used as header
            CSVParser csvParser = new CSVParser(csvReader,
                    CSVFormat.DEFAULT.builder().setHeader(HEADERS)
                            .setSkipHeaderRecord(true).setTrim(true).build());

            List<Recipient> recipientList = new ArrayList<>();
            
            // Iterate over lines in csv to populate recipient list
            List<CSVRecord> csvRecords = csvParser.getRecords();
            Recipient recipient = null;
            ErrorRecords errorRecords = new ErrorRecords();
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<Recipient>> violations;
            for (int i = 1 ; i < csvRecords.size(); ++i) {
                 if (csvRecords.get(i).values().length == 3 ) {
                      recipient = new Recipient(csvRecords.get(i).get(0), null,
                             csvRecords.get(i).get(1),
                              csvRecords.get(i).get(2));
                 } else if (csvRecords.get(i).values().length == 4) {
                     recipient = new Recipient(csvRecords.get(i).get(0), null,
                             csvRecords.get(i).get(1),
                             csvRecords.get(i).get(2));
                 }

                 // Validate each recipient upfront and capture errors
                violations = validator.validate(recipient);
                if (!violations.isEmpty()) {
                    processErrorRecords(violations, errorRecords);
                } else {
                    recipientList.add(recipient);
                }
            }

            // In case there are error records, throw InvalidDataException
            if (hasErrorRecords(errorRecords)) {
                raiseInvalidDataException(errorRecords);
            }

            // Save all records in memory
            recipientsRepo.saveAll(recipientList);
            return recipientList;
        }  catch(IndexOutOfBoundsException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        catch (IOException ex) {
            throw new RuntimeException("Failed to read file.");
        }
    }

    private void processErrorRecords(Set<ConstraintViolation<Recipient>> violations, ErrorRecords errorRecords) {
        for (ConstraintViolation<Recipient> violation : violations) {
            if (violation.getPropertyPath().toString().equals("firstName")) {
                if (errorRecords.getFirstNames() == null) {
                    errorRecords.setFirstNames(new ArrayList<String>());
                }
                errorRecords.getFirstNames().add((String) violation.getInvalidValue());
            }
            if (violation.getPropertyPath().toString().equals("middleName")) {
                if (errorRecords.getMiddleNames() == null) {
                    errorRecords.setMiddleNames(new ArrayList<String>());
                }
                errorRecords.getMiddleNames().add((String) violation.getInvalidValue());
            }
            if (violation.getPropertyPath().toString().equals("lastName")) {
                if (errorRecords.getLastNames() == null) {
                    errorRecords.setLastNames(new ArrayList<String>());
                }
                errorRecords.getLastNames().add((String) violation.getInvalidValue());
            }
            if (violation.getPropertyPath().toString().equals("emailAddress")) {
                if (errorRecords.getEmailAddresses() == null) {
                    errorRecords.setEmailAddresses(new ArrayList<String>());
                }
                errorRecords.getEmailAddresses().add((String) violation.getInvalidValue());
            }
        }
    }

    private boolean hasErrorRecords(ErrorRecords errorRecords) {
        return ( errorRecords.getFirstNames().size() > 0 ||
                errorRecords.getMiddleNames().size() > 0 ||
                errorRecords.getLastNames().size() > 0 ||
                errorRecords.getEmailAddresses().size() > 0 );
    }

    private void raiseInvalidDataException(ErrorRecords errorRecords) {
        StringBuilder errMsgBuilder = new StringBuilder();
        errMsgBuilder.append("These names and " +
                "email addresses are invalid in the csv uploaded.");
        if (errorRecords.getFirstNames() != null && errorRecords.getFirstNames().size() > 0) {
            errMsgBuilder.append("* Erroneous First Names : ");
            errMsgBuilder.append(errorRecords.getFirstNames());
        }
        if (errorRecords.getMiddleNames() != null && errorRecords.getMiddleNames().size() > 0) {
            errMsgBuilder.append("* Erroneous  Middle Names : ");
            errMsgBuilder.append(errorRecords.getMiddleNames());
        }
        if (errorRecords.getLastNames() != null && errorRecords.getLastNames().size() > 0) {
            errMsgBuilder.append("* Erroneous Last Names : ");
            errMsgBuilder.append(errorRecords.getLastNames());
        }
        if (errorRecords.getEmailAddresses() != null && errorRecords.getEmailAddresses().size() > 0) {
            errMsgBuilder.append("* Erroneous Email Addresses : ");
            errMsgBuilder.append(errorRecords.getEmailAddresses());
        }
        throw new InvalidDataException(errMsgBuilder.toString());
    }
}
