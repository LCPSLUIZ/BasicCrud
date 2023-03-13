package com.AB3.API.AB3.API.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class UserDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 8, max = 20)
    private String passWord;

    @NotBlank
    @Email
    @JsonProperty("eMail")
    private String eMail;

    @NotBlank
    private String phoneNumber;

}
