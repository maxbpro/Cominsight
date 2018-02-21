package com.maxb.cominsight.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class PostDTO {

    private String id;

    @NotNull
    private MemberDTO member = null;

    @NotNull
    private CompanyDTO company = null;
    private String photoUrl = null;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime created = null;


    private String category = null;
    private String title = null;
    private String text = null;
    private int commentsCount = 0;

    private String[] tags = null;

}
