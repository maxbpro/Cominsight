package com.maxb.cominsight.models.dto;

import com.maxb.cominsight.models.Gender;
import com.maxb.cominsight.validators.PasswordMatches;
import com.maxb.cominsight.validators.ValidEmail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@PasswordMatches
public class UserDTO {

    private String id;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    private String lastName;


    @NotNull
    @NotEmpty
    private String password;

    private String matchingPassword;

    @NotNull
    @NotEmpty
    private String text;

    private String avatar;
    private Gender gender = Gender.MALE;
    private CompanyDTO company = null;

}
