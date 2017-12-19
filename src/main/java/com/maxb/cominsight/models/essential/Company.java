package com.maxb.cominsight.models.essential;

import com.maxb.cominsight.models.Follower;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "company")
public class Company {

    @Id
    private String id;

    private String title = null;
    private String photoUrl = null;
    private String url = null;
    private String address = null;
    private String phone = null;


    private List<Photo> photos = new ArrayList<>();
    
    private List<Follower> followers = new ArrayList<>();
}
