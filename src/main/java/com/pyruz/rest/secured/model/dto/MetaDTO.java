package com.pyruz.rest.secured.model.dto;


import com.pyruz.rest.secured.configuration.ApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaDTO {
    private String message;

    public static MetaDTO getInstance(ApplicationProperties applicationProperties) {
        return new MetaDTO(applicationProperties.getProperty("application.message.success.text"));
    }

}
