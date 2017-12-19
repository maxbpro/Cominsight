package com.maxb.cominsight.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Invite {

    @Id
    private String id;

    private String inviter_id = null;
    private String user_id_id = null;

    private String company_id = null;
    private LocalDateTime created = null;
    private String status;
}
