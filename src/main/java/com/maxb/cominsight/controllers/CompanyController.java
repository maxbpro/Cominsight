package com.maxb.cominsight.controllers;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.Comment;
import com.maxb.cominsight.models.Follower;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.Photo;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.services.CommentService;
import com.maxb.cominsight.services.CompanyService;
import com.maxb.cominsight.services.PhotoService;
import com.maxb.cominsight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CommentService commentService;


    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public List<Company> allCompanies() {
        return companyService.getCompanies();
    }

    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public Company createCompany(@Valid @RequestBody Company company) throws EntityNotFoundException {
        return companyService.saveCompany(company);
    }

    @RequestMapping(value = "/companies/{id}", method = RequestMethod.PUT)
    public Company updateCompany(@PathVariable("id") String id, @RequestBody Company company) throws EntityNotFoundException {

        Company oldCompany = companyService.findCompany(id);
        if (oldCompany == null) {
            throw new EntityNotFoundException(Company.class);
        }
        oldCompany.setPhone (company.getPhone());
        oldCompany.setAddress(company.getAddress());
        oldCompany.setUrl(company.getUrl());
        oldCompany.setEmail(company.getEmail());
        oldCompany.setTitle(company.getTitle());
        return companyService.saveCompany(oldCompany);
    }

    @RequestMapping(value = "/companies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCompany(@PathVariable("id") String id) {
        companyService.deleteCompany(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/companies/{id}/followers", method = RequestMethod.POST)
    public Company addFollower(@PathVariable("id") String id, @Valid @RequestBody Follower follower) throws EntityNotFoundException{

        Company company = companyService.findCompany(id);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        if(!company.getFollowers().contains(follower)){
            company.getFollowers().add(follower);
        }

        return companyService.saveCompany(company);
    }

    @RequestMapping(value = "/companies/{id}/followers/{userId}", method = RequestMethod.DELETE)
    public Company removeFollower(@PathVariable("id") String id,
                                  @PathVariable("userId") String userId) throws EntityNotFoundException{

        Company company = companyService.findCompany(id);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        company.getFollowers().removeIf(follower -> {
            if(follower.getUserId().equals(userId))
                return true;
            return false;
        });

        return company;
    }

    @RequestMapping(value = "/companies/{id}/photos", method = RequestMethod.POST)
    public Company addPhoto(@PathVariable("id") String companyId,
                            @Valid @RequestBody Photo photo) throws EntityNotFoundException{

        Company company = companyService.findCompany(companyId);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        photoService.savePhoto(photo);

        company.getPhotos().add(photo);
        companyService.saveCompany(company);

        return company;
    }

    @RequestMapping(value = "/companies/{companyId}/photos/{photoId}", method = RequestMethod.DELETE)
    public Company removePhoto(@PathVariable("companyId") String companyId,
                               @PathVariable("photoId") String photoId) throws EntityNotFoundException{

        Company company = companyService.findCompany(companyId);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        company.getPhotos().removeIf(photo -> {
            if(photo.getId().equals(photoId))
                return true;
            return false;
        });

        photoService.deletePhoto(photoId);

        return company;
    }

    @RequestMapping(value = "/companies/{companyId}/photos/{photoId}/comments", method = RequestMethod.POST)
    public Photo addComment(@PathVariable("companyId") String companyId,
                              @PathVariable("photoId") String photoId,
                              @Valid @RequestBody Comment comment) throws EntityNotFoundException{

        Company company = companyService.findCompany(companyId);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        Photo photo = photoService.findPhoto(photoId);

        if(photo == null){
            throw new EntityNotFoundException(Photo.class);
        }

        commentService.saveComment(comment);

        photo.getComments().add(comment);
        photoService.savePhoto(photo);

        return photo;
    }

    @RequestMapping(value = "/companies/{companyId}/photos/{photoId}/comments/{commentId}", method = RequestMethod.DELETE)
    public Photo removeComment(@PathVariable("companyId") String companyId,
                                 @PathVariable("photoId") String photoId,
                                 @PathVariable("commentId") String commentId) throws EntityNotFoundException{

        Company company = companyService.findCompany(companyId);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        Photo photo = photoService.findPhoto(photoId);

        if(photo == null){
            throw new EntityNotFoundException(Photo.class);
        }

        photo.getComments().removeIf(comment -> {
            if(comment.getId().equals(commentId))
                return true;
            return false;
        });

        commentService.deleteComment(commentId);

        return photo;
    }
}
