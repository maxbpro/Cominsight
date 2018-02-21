package com.maxb.cominsight.models.essential;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxb.cominsight.models.*;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "user")
public class User implements UserDetails {

    @Id
    private String id;

    @NotNull
    @Indexed
    private String username;

    @NotNull
    @Indexed
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;


    private String password;

    private String avatar;

    private String text;

    private LocalDateTime registeredDate = LocalDateTime.now();

    private Gender gender = null;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean enabled = false;
    private boolean credentialsNonExpired = true;
    private List<String> roles = new ArrayList<>();


    //following companies
    private List<Following> following = new ArrayList<>();

    //in the future maybe
    //private List<Friend> friends = new ArrayList<>();

    //own company
    private Company company = null;

    //news
    private List<Like> newLikes = new ArrayList<>();
    private List<Follower> newFollowers = new ArrayList<>();
    private List<Invite> invites = new ArrayList<>();

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
