package com.maxb.cominsight.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogDTO {

    private String id;
    private MemberDTO member = null;
    private CompanyDTO company = null;

    private String title;
    private String text;
    private String[] tags;

    private int commentsCount = 0;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime created = null;
}
