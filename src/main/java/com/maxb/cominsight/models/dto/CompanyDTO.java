package com.maxb.cominsight.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maxb.cominsight.validators.ValidEmail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyDTO {

    private String id;

    @Size(min = 2, max = 50)
    private String title = null;

    @ValidEmail
    private String email = null;

    private String photoUrl = null;
    private String url = null;
    private String address = null;
    private String phone = null;

    private List<FollowerDTO> followers = new ArrayList<>();

    @NotNull
    @NotEmpty
    private String category = null;

    @NotNull
    @NotEmpty
    private String text = null;

    private LocalDateTime registeredDate = LocalDateTime.now();

    private int memberCount = 0;

    private String coverUrl = null;

    private String avatar = null;

    private Boolean followed = null;
}
