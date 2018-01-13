package com.maxb.cominsight.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document
@Data
@EqualsAndHashCode(of = "userId")
public class Follower {


    @NotNull
    private String userId = null;

    @NotNull
    private String userName = null;

    private LocalDateTime created = LocalDateTime.now();

}
