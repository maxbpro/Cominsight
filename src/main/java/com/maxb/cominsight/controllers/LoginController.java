package com.maxb.cominsight.controllers;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.Following;
import com.maxb.cominsight.models.Gender;
import com.maxb.cominsight.models.dto.UserDTO;
import com.maxb.cominsight.models.essential.*;
import com.maxb.cominsight.repositories.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentReplayRepository replayRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @PostConstruct
    private void init(){

        //companies

        Company company = new Company();
        company.setEmail("evdakov@gmail.com");
        company.setTitle("Rutorika");
        company.setPhone("123456");
        company.setAddress("Zipovsk 5");
        company.setUrl("http://rutorika.ru");
        company.setMemberCount(0);
        company.setText("Lorem Ipsum is simply dummy text of the printing & typesetting.");
        company.setPhotoUrl("img/posts-img/post-item-02.jpg");
        company.setCategory("Public group");
        companyRepository.save(company);


        Company company2 = new Company();
        company2.setEmail("popin@gmail.com");
        company2.setTitle("Poloniumarts");
        company2.setPhone("123456");
        company2.setAddress("NoName street");
        company2.setUrl("http://poloniumarts.ru");
        company2.setMemberCount(0);
        company2.setText("Lorem Ipsum is simply dummy text of the printing & typesetting.");
        company2.setPhotoUrl("img/posts-img/post-item-02.jpg");
        company2.setCategory("Public group");
        companyRepository.save(company2);

        //user

        User user = new User();
        user.setUsername("maxb2010");
        user.setEmail("maxbpro2010@gmail.com");
        user.setFirstName("Max");
        user.setAvatar("img/cover-header-img/avatar-01.jpg");
        user.setLastName("Biuanow");
        user.setPassword(encoder.encode("1234"));
        user.setCompany(company);
        user.setGender(Gender.MALE);
        user.setEnabled(true);
        user.setText("Lorem Ipsum is simply dummy text of the printing & typesetting.");

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        usersRepository.save(user);

        //followings
        Following following1 = new Following();
        following1.setCompanyId(company.getId());
        following1.setCompanyPhotoUrl(company.getPhotoUrl());
        following1.setCompanyTitle(company.getTitle());

        user.getFollowing().add(following1);

        Following following2 = new Following();
        following2.setCompanyId(company2.getId());
        following2.setCompanyPhotoUrl(company2.getPhotoUrl());
        following2.setCompanyTitle(company2.getTitle());

        user.getFollowing().add(following2);

        usersRepository.save(user);

        //photos

        for(int i = 0; i < 3; i++){
            Post post = new Post();
            post.setMember(user);
            post.setCompany(company);
            post.setPhotoUrl("maxb20101518205947735.7lg");
            post.setText("Your guide to perfect rebranding Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley.");
            post.setCategory("Creative");
            post.setTitle("Title");
            post.setTags(new String[]{"tag1", "tag2"});
            post.setCommentsCount(0);
            postRepository.save(post);

            //comments and replays

            Comment comment1 = new Comment();
            comment1.setText("Hello");
            comment1.setMember(user);
            comment1.setPost(post);
            commentRepository.save(comment1);

            CommentReplay replay = new CommentReplay();
            replay.setComment(comment1);
            replay.setMember(user);
            replay.setText("Replay text");
            replayRepository.save(replay);

        }

        for(int i = 0; i < 30; i++){
            Post post = new Post();
            post.setMember(user);
            post.setCompany(company2);
            post.setPhotoUrl("maxb20091518205947735.7lg");
            post.setText("Your guide to perfect rebranding Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley.");
            post.setCategory("Creative");
            post.setTitle("Title");
            post.setTags(new String[]{"tag1", "tag2"});
            post.setCommentsCount(0);
            postRepository.save(post);

            //comments and replays

            Comment comment1 = new Comment();
            comment1.setText("Hello");
            comment1.setMember(user);
            comment1.setPost(post);
            commentRepository.save(comment1);

            CommentReplay replay = new CommentReplay();
            replay.setComment(comment1);
            replay.setMember(user);
            replay.setText("Replay text");
            replayRepository.save(replay);
        }

        for(int i = 0; i < 30; i++){

            Blog blog = new Blog();
            blog.setCompany(company);
            blog.setMember(user);
            blog.setTitle("Blog title");
            blog.setText("Your guide to perfect rebranding Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley.");
            blog.setTags(new String[]{"tag1", "tag2"});

            blogRepository.save(blog);
        }

    }



    /**
     * This method will return the logged user.
     *
     * @param principal
     * @return Principal java security principal object
     */
    @RequestMapping("/user")
    public User user(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        return usersRepository.findByUsername(loggedUsername);
    }


}
