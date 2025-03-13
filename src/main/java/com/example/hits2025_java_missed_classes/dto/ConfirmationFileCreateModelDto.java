package com.example.hits2025_java_missed_classes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "AttachmentDto")
public class ConfirmationFileCreateModelDto {
    @NotBlank
    @Size(min = 1, max = 255)
    @JsonProperty("fileName")
    private String name;

    @NotNull
    @JsonProperty("file")
    private byte[] data;
}