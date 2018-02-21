package com.maxb.cominsight.controllers;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.Following;
import com.maxb.cominsight.models.dto.CompanyDTO;
import com.maxb.cominsight.models.dto.FollowingDTO;
import com.maxb.cominsight.models.essential.Comment;
import com.maxb.cominsight.models.Follower;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.Post;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.services.CommentService;
import com.maxb.cominsight.services.CompanyService;
import com.maxb.cominsight.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/companies/search/{substring}", method = RequestMethod.GET)
    public List<CompanyDTO> getCompanyBySubstring(@PathVariable String substring, Principal principal) {
        return companyService.getCompanies().stream()
                .map(post -> convertToDto(post))
                .collect(Collectors.toList());
    }



    @RequestMapping(value = "/companies/{id}", method = RequestMethod.GET)
    public CompanyDTO getCompanyById(@PathVariable("id") String id, Principal principal) throws EntityNotFoundException{

        String username = principal.getName();
        CompanyDTO companyDTO = convertToDto(companyService.findCompany(id));
        setFollowedProperty(companyDTO, id, username);
        return companyDTO;
    }

    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public CompanyDTO createCompany(@Valid @RequestBody CompanyDTO companyDTO) throws EntityNotFoundException {
        Company company = convertToEntity(companyDTO);
        return convertToDto(companyService.saveCompany(company));
    }



    @RequestMapping(value = "/companies/{id}", method = RequestMethod.PUT)
    public CompanyDTO updateCompany(@PathVariable("id") String id, @Valid @RequestBody CompanyDTO companyDTO) throws EntityNotFoundException {

        Company oldCompany = companyService.findCompany(id);
        if (oldCompany == null) {
            throw new EntityNotFoundException(Company.class);
        }

        oldCompany.setPhone (companyDTO.getPhone());
        oldCompany.setAddress(companyDTO.getAddress());
        oldCompany.setUrl(companyDTO.getUrl());
        oldCompany.setEmail(companyDTO.getEmail());
        oldCompany.setTitle(companyDTO.getTitle());
        oldCompany.setText(companyDTO.getText());
        oldCompany.setCategory(companyDTO.getCategory());
        return convertToDto(companyService.saveCompany(oldCompany));
    }

    @RequestMapping(value = "/companies/update/avatar", method = RequestMethod.PUT)
    public CompanyDTO updateAvatar(@RequestPart("image") MultipartFile multipartFile ,
                                Principal principal) throws EntityNotFoundException, IOException {

        return convertToDto(companyService.updateAvatar(principal.getName(), multipartFile));
    }


    //

    @RequestMapping(value = "/companies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCompany(@PathVariable("id") String id) {
        companyService.deleteCompany(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/companies/{id}/followers", method = RequestMethod.POST)
    public CompanyDTO addFollower(@PathVariable("id") String id, Principal principal) throws EntityNotFoundException{

        String username = principal.getName();
        CompanyDTO companyDTO = convertToDto(companyService.addFollower(id, username));
        setFollowedProperty(companyDTO, id, username);
        return companyDTO;
    }

    private void setFollowedProperty(CompanyDTO companyDTO, String companyId, String username ) throws EntityNotFoundException{

        if(companyService.isFollowed(companyId, username)){
            companyDTO.setFollowed(true);
        }else{
            companyDTO.setFollowed(false);
        }
    }

    @RequestMapping(value = "/companies/{id}/followers", method = RequestMethod.DELETE)
    public CompanyDTO removeFollower(@PathVariable("id") String id, Principal principal) throws EntityNotFoundException{

        String username = principal.getName();
        CompanyDTO companyDTO = convertToDto(companyService.removeFollower(id, username));
        setFollowedProperty(companyDTO, id, username);
        return companyDTO;
    }


//    //user followers these companies
//    @RequestMapping(value = "/companies/user/{userId}", params = { "page", "size" }, method = RequestMethod.GET)
//    public Page<FollowingDTO> followingForMember(@PathVariable("userId") String userId,
//                                                 @RequestParam( "page" ) int page,
//                                                 @RequestParam( "size" ) int size) {
//        return postService.getFollowingForMember(userId, page, size).map(this::convertFollowingToDto);
//    }


    private CompanyDTO convertToDto(Company company) {
        CompanyDTO companyDTO = modelMapper.map(company, CompanyDTO.class);
        return companyDTO;
    }

    private Company convertToEntity(CompanyDTO companyDTO) {
        Company company = modelMapper.map(companyDTO, Company.class);
        return company;
    }

    private FollowingDTO convertFollowingToDto(Following following) {
        FollowingDTO dto = modelMapper.map(following, FollowingDTO.class);
        return dto;
    }
}
