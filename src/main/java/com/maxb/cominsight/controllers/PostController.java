package com.maxb.cominsight.controllers;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.Follower;
import com.maxb.cominsight.models.dto.CompanyDTO;
import com.maxb.cominsight.models.dto.PostDTO;
import com.maxb.cominsight.models.essential.Comment;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.Post;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.services.*;
import org.imgscalr.Scalr;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PostController {


    @Autowired
    private PostService postService;



    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/posts", params = { "page", "size" }, method = RequestMethod.GET)
    public Page<PostDTO> allPosts(@RequestParam( "page" ) int page, @RequestParam( "size" ) int size) {
        return postService.getAll(page, size).map(this::convertToDto);
    }

    @RequestMapping(value = "/posts/company/{companyId}", params = { "page", "size" }, method = RequestMethod.GET)
    public Page<PostDTO> postsForCompany(@PathVariable("companyId") String companyId,
                                         @RequestParam( "page" ) int page,
                                         @RequestParam( "size" ) int size) {
        return postService.getForCompany(companyId, page, size).map(this::convertToDto);
    }

    @RequestMapping(value = "/posts/user/{userId}", params = { "page", "size" }, method = RequestMethod.GET)
    public Page<PostDTO> postsForMember(@PathVariable("userId") String userId,
                                        @RequestParam( "page" ) int page,
                                        @RequestParam( "size" ) int size) {
        return postService.getForMember(userId, page, size).map(this::convertToDto);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public PostDTO getPostById(@PathVariable("id") String id) {
        return convertToDto(postService.find(id));
    }


    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePost(@PathVariable("id") String id) {
        postService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public PostDTO newPost(@RequestPart("data") @Valid PostDTO postDTO, @RequestPart("image") MultipartFile multipartFile ,
                           Principal principal) throws EntityNotFoundException, IOException {

        Post post = convertToEntity(postDTO);
        return convertToDto(postService.save(principal.getName(), multipartFile, post, postDTO.getCompany().getId()));
    }

//    @RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
//    public PostDTO updatePost(@PathVariable("id") String id, @RequestBody Company company) throws EntityNotFoundException {
//
//        Company oldCompany = companyService.findCompany(id);
//        if (oldCompany == null) {
//            throw new EntityNotFoundException(Company.class);
//        }
//        oldCompany.setPhone (company.getPhone());
//        oldCompany.setAddress(company.getAddress());
//        oldCompany.setUrl(company.getUrl());
//        oldCompany.setEmail(company.getEmail());
//        oldCompany.setTitle(company.getTitle());
//        return convertToDto(companyService.saveCompany(oldCompany));
//    }
//



    @RequestMapping(value = "/timeline", method = RequestMethod.GET)
    public Page<PostDTO> timeline(@RequestParam( "page" ) int page,
                                  @RequestParam( "size" ) int size,
                                  Principal principal) {

        String username = principal.getName();
        return postService.getForUser(username, page, size).map(this::convertToDto);
    }




    private PostDTO convertToDto(Post post) {
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        return postDTO;
    }

    private Post convertToEntity(PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        return post;
    }
}
