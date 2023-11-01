package com.mborg.mailMerge.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 @author monChrome
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailInfo {
    @NotBlank
    String subject;
    @NotBlank
    String messageBody;
    List<String> attachment;
}
