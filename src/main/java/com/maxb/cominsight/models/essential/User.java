package com.maxb.cominsight.models.essential;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxb.cominsight.models.Follower;
import com.maxb.cominsight.models.Following;
import com.maxb.cominsight.models.Invite;
import com.maxb.cominsight.models.Like;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "user")
public class User implements UserDetails {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String name;
    private boolean anonymity = true;
    private String gender = null;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean enabled = true;
    private boolean credentialsNonExpired = true;
    private List<String> roles = new ArrayList<>();


    private List<Following> following = new ArrayList<>();
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