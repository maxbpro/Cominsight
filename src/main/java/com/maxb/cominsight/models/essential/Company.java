package com.maxb.cominsight.models.essential;

import com.maxb.cominsight.models.Follower;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "company")
public class Company {

    @Id
    private String id;

    @NotNull
    @Size(min = 2, max = 50)
    @Indexed(unique = true)
    private String title = null;

    @NotNull
    @Email
    @Indexed(unique = true)
    private String email = null;

    private String photoUrl = null;

    @NotNull
    private String url = null;

    @NotNull
    private String address = null;

    @NotNull
    private String phone = null;

    private List<Follower> followers = new ArrayList<>();

    @DBRef
    private List<Photo> photos = new ArrayList<>();

}
