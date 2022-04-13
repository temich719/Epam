package com.epam.esm.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDTO {

    long id;

    @NotEmpty(message = "Name should not be empty.")
    @NotNull(message = "Name should not be null.")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters.")
    String name;

    @NotEmpty(message = "Description should not be empty.")
    @NotNull(message = "Description should not be null.")
    @Size(min = 1, max = 100, message = "Description should be between 1 and 100 characters.")
    String description;

    @NotEmpty(message = "Price should not be empty.")
    @NotNull(message = "Price should not be null.")
    @Size(min = 1, max = 20, message = "Price should be between 1 and 20 characters.")
    String price;

    @NotEmpty(message = "Duration should not be empty.")
    @NotNull(message = "Duration should not be null.")
    @Size(min = 1, max = 30, message = "Duration should be between 1 and 30 characters.")
    @Pattern(regexp = "\\d+ days", message = "Duration should contain amount of days(example:2 days).")
    String duration;

    @Pattern(regexp = "(\\d{4}-\\d{2}-\\d{2})[A-Z]+(\\d{2}:\\d{2}:\\d{2}).([0-9+-:]+)", message = "ISO8601 format")
    String createDate;

    @Pattern(regexp = "(\\d{4}-\\d{2}-\\d{2})[A-Z]+(\\d{2}:\\d{2}:\\d{2}).([0-9+-:]+)", message = "ISO8601 format")
    String lastUpdateDate;

    @NotEmpty(message = "Tags should be inputted")
    @NotNull(message = "Tags should be not null")
    TagDTO[] tags;

}
