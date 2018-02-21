package com.maxb.cominsight.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDTO {

    private String id;
    private PostDTO post;
    private MemberDTO member = null;
    private String text = null;

    private LocalDateTime created = null;

    private List<CommentReplayDTO> replays = null;
}
