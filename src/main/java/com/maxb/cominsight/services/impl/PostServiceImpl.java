package com.maxb.cominsight.services.impl;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.Following;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.Post;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.repositories.CompanyRepository;
import com.maxb.cominsight.repositories.PostRepository;
import com.maxb.cominsight.repositories.UserRepository;
import com.maxb.cominsight.services.CompanyService;
import com.maxb.cominsight.services.PostService;
import com.maxb.cominsight.services.StorageService;
import org.bson.types.ObjectId;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public Page<Post> getAll(int page, int size) {
        return postRepository.findAll(new PageRequest(page, size));
    }

    @Override
    public Page<Post> getForUser(String username, int page, int size) {

        User user = userRepository.findByUsername(username);
        List<Following> followingList = user.getFollowing();

        List<String> titles = followingList.stream().map(following ->
                following.getCompanyTitle()).collect(Collectors.toList());

        Page<Post> items = postRepository.findAllByCompanyTitleInOrderByCreatedAsc(titles,
                new PageRequest(page, size));

        return items;

    }

    @Override
    public Page<Post> getForMember(String userId, int page, int size) {
        return postRepository.findAllByMemberId(new ObjectId(userId), new PageRequest(page, size));
    }

    @Override
    public Page<Following> getFollowingForMember(String userId, int page, int size) {
        return null;
    }

    @Override
    public Page<Post> getForCompany(String companyId, int page, int size) {
        return postRepository.findAllByCompanyId(new ObjectId(companyId), new PageRequest(page, size));
    }

    @Override
    public Post save(String username, MultipartFile multipartFile, Post post, String companyId) throws EntityNotFoundException, IOException{

        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        BufferedImage image = ImageIO.read(convFile);
        BufferedImage photoLg = Scalr.resize(image, 800);
        BufferedImage photoMd = Scalr.resize(image, 500);
        BufferedImage photoSm = Scalr.resize(image, 200);
        BufferedImage photoXs = Scalr.resize(image, 100);

        String postfix = String.format("%s.%s", String.valueOf(new Date().getTime()), String.valueOf(new Random().nextInt(9)));

        String postImageLg = username + postfix +"lg";
        String postImageMd = username + postfix +"md";
        String postImageSm = username + postfix +"sm";
        String postImageXs = username + postfix +"xs";

        storageService.store(photoLg, postImageLg);
        storageService.store(photoMd, postImageMd);
        storageService.store(photoSm, postImageSm);
        storageService.store(photoXs, postImageXs);


        post.setPhotoUrl(postImageLg);

        //

        User user = userRepository.findByUsername(username);
        post.setMember(user);

        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        post.setCompany(company);

        return postRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public void delete(String id) {
        postRepository.delete(id);
    }

    @Override
    public Post find(String id) {
        return postRepository.findOne(id);
    }
}
