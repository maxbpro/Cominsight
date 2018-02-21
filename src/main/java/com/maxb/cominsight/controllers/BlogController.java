package com.maxb.cominsight.controllers;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.dto.BlogDTO;
import com.maxb.cominsight.models.essential.Blog;
import com.maxb.cominsight.services.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
public class BlogController {


    @Autowired
    private BlogService blogService;


    @Autowired
    private ModelMapper modelMapper;



    @RequestMapping(value = "/blogs", params = { "page", "size" }, method = RequestMethod.GET)
    public Page<BlogDTO> allBlogs(@RequestParam( "page" ) int page, @RequestParam( "size" ) int size) {
        return blogService.getAll(page, size).map(this::convertToDto);
    }

    @RequestMapping(value = "/blogs/company/{companyId}", params = { "page", "size" }, method = RequestMethod.GET)
    public Page<BlogDTO> blogsForCompany(@PathVariable("companyId") String companyId,
                                  @RequestParam( "page" ) int page,
                                  @RequestParam( "size" ) int size) {
        return blogService.getForCompany(companyId, page, size).map(this::convertToDto);
    }

    @RequestMapping(value = "/blogs/user/{userId}", params = { "page", "size" }, method = RequestMethod.GET)
    public Page<BlogDTO> blogsForMember(@PathVariable("userId") String userId,
                                  @RequestParam( "page" ) int page,
                                  @RequestParam( "size" ) int size) {
        return blogService.getForMember(userId, page, size).map(this::convertToDto);
    }

    @RequestMapping(value = "/blogs/{id}", method = RequestMethod.GET)
    public BlogDTO getBlogById(@PathVariable("id") String id) {
        return convertToDto(blogService.find(id));
    }


    @RequestMapping(value = "/blogs/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBlog(@PathVariable("id") String id) {
        blogService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "/blogs", method = RequestMethod.POST)
    public BlogDTO newBlog(@Valid @RequestBody BlogDTO blogDTO,
                           Principal principal) throws EntityNotFoundException {

        Blog blog = convertToEntity(blogDTO);
        return convertToDto(blogService.save(principal.getName(), blog, blogDTO.getCompany().getId()));
    }

//    @RequestMapping(value = "/blogs/{id}", method = RequestMethod.PUT)
//    public PostDTO updateBlog(@PathVariable("id") String id, @RequestBody Company company) throws EntityNotFoundException {
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







    private BlogDTO convertToDto(Blog blog) {
        BlogDTO blogDTO = modelMapper.map(blog, BlogDTO.class);
        return blogDTO;
    }

    private Blog convertToEntity(BlogDTO blogDTO) {
        Blog blog = modelMapper.map(blogDTO, Blog.class);
        return blog;
    }
}
