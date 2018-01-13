package com.maxb.cominsight.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Invite {

    @Id
    private String id;

    private String inviterId = null;
    private String userId = null;
    private String userName = null;

    private String companyId = null;
    private LocalDateTime created = null;
    private String status;
}
