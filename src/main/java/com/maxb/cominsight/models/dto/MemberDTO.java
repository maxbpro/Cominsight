package com.maxb.cominsight.models.dto;

import com.maxb.cominsight.models.Gender;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String text;
    private String avatar;
    private String email;
    private Gender gender = null;
    private CompanyDTO company = null;
    private List<FollowingDTO> following = new ArrayList<>();
}
