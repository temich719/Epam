package com.epam.esm.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    long id;

    @NotNull(message = "Name should be inputted.")
    @NotEmpty(message = "Name should not be empty.")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters.")
    String name;
}
