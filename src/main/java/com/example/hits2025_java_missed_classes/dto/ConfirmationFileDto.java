package com.example.hits2025_java_missed_classes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Attachment")
public class ConfirmationFileDto {
    @NotBlank
    @Max(value = 256)
    @JsonProperty("fileName")
    private String name;

    @NotBlank
    @Max(value = 10 * 1024 * 1024, message = "File size must be less than 10 MB")
    @JsonProperty("file")
    private byte[] data;
}