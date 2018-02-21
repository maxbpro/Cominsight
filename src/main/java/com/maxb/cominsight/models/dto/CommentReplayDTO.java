package com.maxb.cominsight.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentReplayDTO {

    private String id;
    private CommentDTO comment;
    private MemberDTO member = null;
    private String text = null;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime created = null;
}
