package com.maxb.cominsight.services.impl;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.Follower;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.repositories.CompanyRepository;
import com.maxb.cominsight.repositories.UserRepository;
import com.maxb.cominsight.services.CompanyService;
import com.maxb.cominsight.services.StorageService;
import com.maxb.cominsight.services.UserService;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Company company) {
        companyRepository.delete(company);
    }

    @Override
    public void deleteCompany(String id)  {
        companyRepository.delete(id);
    }

    @Override
    public Company addFollower(String companyId,  String username) throws EntityNotFoundException{

        User user = userRepository.findByUsername(username);

        Follower follower = new Follower();
        follower.setUserId(user.getId());
        follower.setUserName(user.getUsername());

        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        if(!company.getFollowers().contains(follower)){
            company.getFollowers().add(follower);
        }

        return companyRepository.save(company);
    }

    @Override
    public Company removeFollower(String companyId,  String username) throws EntityNotFoundException{

        User user = userRepository.findByUsername(username);

        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        company.getFollowers().removeIf(follower -> {
            if(follower.getUserId().equals(user.getId()))
                return true;
            return false;
        });

        return companyRepository.save(company);
    }

    @Override
    public boolean isFollowed(String companyId,  String username) throws EntityNotFoundException{

        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        boolean followed = company.getFollowers().stream()
                .anyMatch(follower -> follower.getUserName().equals(username));

        return followed;
    }

    @Override
    public Company findCompany(String id) {
        return companyRepository.findOne(id);
    }

    @Override
    public Company updateAvatar(String username, MultipartFile multipartFile) throws EntityNotFoundException, IOException {

        User user = userRepository.findByUsername(username);

        Company company = companyRepository.findOne(user.getCompany().getId());
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        BufferedImage image = ImageIO.read(convFile);
        BufferedImage photoLg = Scalr.resize(image, 800);
        BufferedImage photoMd = Scalr.resize(image, 150);
        BufferedImage photoSm = Scalr.resize(image, 80);
        BufferedImage photoXs = Scalr.resize(image, 50);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String postfix = company.getRegisteredDate().format(formatter);

        String avatarImageLg = username + postfix + "-avatar-lg";
        String avatarImageMd = username + postfix + "-avatar-md";
        String avatarImageSm = username + postfix + "-avatar-sm";
        String avatarImageXs = username + postfix + "-avatar-xs";

        storageService.store(photoLg, avatarImageLg);
        storageService.store(photoMd, avatarImageMd);
        storageService.store(photoSm, avatarImageSm);
        storageService.store(photoXs, avatarImageXs);

        company.setPhotoUrl(avatarImageLg);

        return companyRepository.save(company);
    }
}
