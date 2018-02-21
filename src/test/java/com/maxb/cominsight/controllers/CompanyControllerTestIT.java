package com.maxb.cominsight.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxb.cominsight.CominsightApplication;
import com.maxb.cominsight.config.response.ResponseEnvelope;
import com.maxb.cominsight.models.essential.Comment;
import com.maxb.cominsight.models.Follower;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.Post;
import com.maxb.cominsight.models.essential.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.maxb.cominsight.controllers.UserControllerTestIT.userJsonExample;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = CominsightApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerTestIT {

    @LocalServerPort
    private int port;

    @Autowired
    private MongoTemplate mongoTemplate;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    public static String companyJsonExample = "{\"address\": \"address 1\",\n" +
            "\t\"email\": \"test@gmail.com\",\n" +
            "\t\"phone\": \"8929 - 32- 32\",\n" +
            "\t\"title\": \"Rutorika\",\n" +
            "\t\"url\": \"rut.com\"\n" +
            "}";

    public static String companyWrongJsonExample = "{\"address\": \"address 1\",\n" +
            "\t\"email\": \"test@gmail.com\",\n" +
            "\t\"phone\": \"8929 - 32- 32\",\n" +
            "\t\"url\": \"rut.com\"\n" +
            "}";

    public static  String followerJsonExample = "{\n" +
            "\t\"userId\": \"?\",\n" +
            "\t\"userName\": \"Max\"\n" +
            "}";



    private Company companyObj = null;
    private Company companyObjWithoutTitle = null;
    private User userObj = null;
    private Follower followerObj = null;

    @Before
    public void init() throws IOException {
        companyObj = new ObjectMapper().readValue(companyJsonExample, Company.class);
        companyObjWithoutTitle = new ObjectMapper().readValue(companyWrongJsonExample, Company.class);

        userObj = new ObjectMapper().readValue(userJsonExample, User.class);
        followerObj = new ObjectMapper().readValue(followerJsonExample, Follower.class);
    }

    @After
    public void destroy(){
        mongoTemplate.dropCollection(Company.class);
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.dropCollection(Comment.class);
        mongoTemplate.dropCollection(Post.class);
    }

    @Test
    public void saveCompanySuccess() {

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies"), companyObj, ResponseEnvelope.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, String> result = (HashMap<String, String> )envelope.getResult();
        assertEquals(companyObj.getTitle(), result.get("title"));
        assertEquals(companyObj.getEmail(), result.get("email"));
        assertEquals(companyObj.getAddress(), result.get("address"));
        assertEquals(companyObj.getUrl(), result.get("url"));
        assertEquals(companyObj.getPhone(), result.get("phone"));


    }

    @Test
    public void saveCompanyTitleValidationFailed() {

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies"), companyObjWithoutTitle, ResponseEnvelope.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, Object> result = (HashMap<String, Object> )envelope.getResult();

        assertEquals("Validation error", result.get("message"));

        List<HashMap<String, String>> subErrors = (List<HashMap<String, String>>)result.get("subErrors");

        assertEquals(1, subErrors.size());
        assertEquals("title", subErrors.get(0).get("field"));
        assertEquals("may not be null", subErrors.get(0).get("message"));
    }

    @Test
    public void saveCompanyAlreadyExists() {

        ResponseEntity responseEntity2  = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies"), companyObj, ResponseEnvelope.class);

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies"), companyObj, ResponseEnvelope.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, String> result = (HashMap<String, String> )envelope.getResult();

        assertEquals("Duplicate error", result.get("message"));

    }


    @Test
    public void allCompanies() throws Exception {

        restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies"), companyObj, ResponseEnvelope.class);

        ResponseEntity responseEntity = restTemplate.getForEntity(
                createURLWithPort("/api/v1/companies"), ResponseEnvelope.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        List<HashMap<String, String>> result = (List<HashMap<String, String>>)envelope.getResult();

        assertEquals(companyObj.getTitle(), result.get(0).get("title"));
        assertEquals(companyObj.getEmail(), result.get(0).get("email"));
        assertEquals(companyObj.getAddress(), result.get(0).get("address"));
        assertEquals(companyObj.getUrl(), result.get(0).get("url"));
        assertEquals(companyObj.getPhone(), result.get(0).get("phone"));

    }

    //update

    //delete



    @Test
    public void addNewFollowerSuccess() throws IOException{

        followerObj.setUserId(getUser().getId());

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies/" + getCompany().getId() + "/followers"),
                followerObj, ResponseEnvelope.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, Object> result = (HashMap<String, Object> )envelope.getResult();
        assertEquals(companyObj.getTitle(), result.get("title"));

        assertNull(envelope.getErrors());

        List<HashMap<String, String>> followers = (List<HashMap<String, String>>)result.get("followers");

        assertEquals(1, followers.size());
        assertEquals(followerObj.getUserName(), followers.get(0).get("userName"));
        assertEquals(followerObj.getUserId(), followers.get(0).get("userId"));

    }

    @Test
    public void addNewFollowerAlreadyExists() throws IOException{

        followerObj.setUserId(getUser().getId());

        Company company = getCompany();

        restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies/" + company.getId() + "/followers"),
                followerObj, ResponseEnvelope.class);

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies/" + company.getId() + "/followers"),
                followerObj, ResponseEnvelope.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, Object> result = (HashMap<String, Object> )envelope.getResult();
        assertEquals(companyObj.getTitle(), result.get("title"));

        assertNull(envelope.getErrors());

        List<HashMap<String, String>> followers = (List<HashMap<String, String>>)result.get("followers");

        assertEquals(1, followers.size());
        assertEquals(followerObj.getUserName(), followers.get(0).get("userName"));
        assertEquals(followerObj.getUserId(), followers.get(0).get("userId"));

    }

    @Test
    public void removeFollowerSuccess() throws IOException{

        User follower = getUser();
        Company company = getCompany();

        followerObj.setUserId(follower.getId());

        restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies/" + company.getId() + "/followers"),
                followerObj, ResponseEnvelope.class);


        ResponseEntity<ResponseEnvelope> response = restTemplate.exchange(
                createURLWithPort("/api/v1/companies/" + company.getId() + "/followers/" + follower.getId()),
                HttpMethod.DELETE, null, ResponseEnvelope.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseEnvelope envelope = response.getBody();
        HashMap<String, Object> result = (HashMap<String, Object> )envelope.getResult();

        assertNull(envelope.getErrors());

        List<HashMap<String, String>> followers = (List<HashMap<String, String>>)result.get("followers");

        assertEquals(0, followers.size());
    }



    private Company getCompany() throws IOException{

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies"), companyObj, ResponseEnvelope.class);

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, String> result = (HashMap<String, String> )envelope.getResult();


        Company company = new Company();
        company.setId(result.get("id"));
        company.setTitle(result.get("title"));
        company.setEmail(result.get("email"));
        company.setAddress(result.get("address"));
        company.setUrl(result.get("url"));
        company.setPhone(result.get("phone"));

        return company;
    }

    private User getUser(){

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/users"), userObj, ResponseEnvelope.class);

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, String> result = (HashMap<String, String> )envelope.getResult();

        User user = new User();
        user.setId(result.get("id"));
        user.setEmail(result.get("email"));
        user.setUsername(result.get("username"));
        user.setFirstName(result.get("firstName"));
        user.setLastName(result.get("lastName"));

        return user;
    }



    @Test
    public void addPhoto() throws IOException{

        Company company = getCompany();
        User user =  getUser();
        Post post = getPhotoObj(company, user);

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies/" + company.getId() + "/photos"),
                post, ResponseEnvelope.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, Object> result = (HashMap<String, Object> )envelope.getResult();
        assertEquals(companyObj.getTitle(), result.get("title"));

        assertNull(envelope.getErrors());

        List<HashMap<String, Object>> photos = (List<HashMap<String, Object>>)result.get("photos");

        assertEquals(1, photos.size());
        assertNotNull(photos.get(0).get("id"));

        HashMap<String, String> userRes = (HashMap<String, String>)photos.get(0).get("user");
        HashMap<String, String> companyRes = (HashMap<String, String>)photos.get(0).get("company");

        assertEquals(user.getId(), userRes.get("id"));
        assertEquals(user.getUsername(), userRes.get("username"));

        assertEquals(company.getId(), companyRes.get("id"));
        assertEquals(company.getTitle(), companyRes.get("title"));
    }



    @Test
    public void deletePhoto() throws IOException{

        Company company = getCompany();
        Post post = getPhotoObj(company, getUser());

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies/" + company.getId() + "/photos"),
                post, ResponseEnvelope.class);

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, Object> result = (HashMap<String, Object> )envelope.getResult();

        String photoId = result.get("id").toString();

        ResponseEntity<ResponseEnvelope> response = restTemplate.exchange(
                createURLWithPort("/api/v1/companies/" + company.getId() + "/photos/" + photoId),
                HttpMethod.DELETE, null, ResponseEnvelope.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private Post getPhotoObj(Company company, User user) {

        Post post = new Post();
        post.setCompany(company);
        post.setUser(user);
        post.setPhotoUrl("test url");
        return post;
    }

    @Test
    public void addComment() throws IOException{

        Company company = getCompany();
        User user =  getUser();
        Post post = getPhoto(company, user);

        Comment comment = getCommentObj(user, post);

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies/" + company.getId() +"/photos/"+ post.getId() + "/comments"),
                comment, ResponseEnvelope.class);

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, Object> result = (HashMap<String, Object> )envelope.getResult();

        assertNotNull(result.get("id"));
        assertNotNull(result.get("user"));
        assertNotNull(result.get("company"));


        assertNull(envelope.getErrors());

        List<HashMap<String, Object>> comments = (List<HashMap<String, Object>>)result.get("comments");

        assertEquals(1, comments.size());
        assertNotNull(comments.get(0).get("id"));

    }

    @Test
    public void removeComment() throws IOException{

        Company company = getCompany();
        User user =  getUser();
        Post post = getPhoto(company, user);

        Comment comment = getCommentObj(user, post);

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies/" + company.getId() +"/photos/"+ post.getId() + "/comments"),
                comment, ResponseEnvelope.class);

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, Object> result = (HashMap<String, Object> )envelope.getResult();
        List<HashMap<String, Object>> comments = (List<HashMap<String, Object>>)result.get("comments");

        String commentId = comments.get(0).get("id").toString();

        ResponseEntity<ResponseEnvelope> response = restTemplate.exchange(
                createURLWithPort("/api/v1/companies/" + company.getId() +"/photos/"+ post.getId() + "/comments/" + commentId),
                HttpMethod.DELETE, null, ResponseEnvelope.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    private Comment getCommentObj(User user, Post post){
        Comment comment = new Comment();
        comment.setText("test text");
        comment.setUser(user);
        comment.setPhoto(post);
        return comment;
    }

    private Post getPhoto(Company company, User user){

        Post postObj = getPhotoObj(company, user);

        ResponseEntity responseEntity =  restTemplate.postForEntity(
                createURLWithPort("/api/v1/companies/" + company.getId() + "/photos"),
                postObj, ResponseEnvelope.class);

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, Object> result = (HashMap<String, Object> )envelope.getResult();

        Post post = new Post();
        post.setUser (user);
        post.setCompany(company);

        List<HashMap<String, Object>> photos = (List<HashMap<String, Object>>)result.get("photos");
        post.setId(photos.get(0).get("id").toString());

        return post;
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}