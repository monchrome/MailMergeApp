package com.mborg.mailMerge.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
    @author monChrome
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorRecords {
    List<String> firstNames;
    List<String> middleNames;
    List<String> lastNames;
    List<String> emailAddresses;
}
