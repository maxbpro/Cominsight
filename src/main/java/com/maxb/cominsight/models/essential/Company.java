package com.maxb.cominsight.models.essential;

import com.maxb.cominsight.models.Follower;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "company")
@EqualsAndHashCode(of = "id")
public class Company {

    @Id
    private String id;

    @NotNull
    @Indexed
    private String title = null;

    @NotNull
    @Indexed
    private String email = null;

    @NotNull
    private String url = null;

    @NotNull
    private String address = null;

    @NotNull
    private String phone = null;

    private List<Follower> followers = new ArrayList<>();

    @DBRef
    private List<Post> posts = new ArrayList<>();

    private String category = null;
    private String text = null;

    private LocalDateTime registeredDate = LocalDateTime.now();

    private int memberCount = 0;

    private String coverUrl = null;

    private String photoUrl = null;

    public void init(){
        registeredDate = LocalDateTime.now();
    }
}
