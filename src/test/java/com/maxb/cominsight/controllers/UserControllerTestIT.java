package com.maxb.cominsight.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxb.cominsight.CominsightApplication;
import com.maxb.cominsight.config.response.ResponseEnvelope;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = CominsightApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTestIT {

    @LocalServerPort
    private int port;

    @Autowired
    private MongoTemplate mongoTemplate;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    public static String userJsonExample = "{" +
            "\t\"firstName\": \"Maxim\",\n" +
            "\t\"lastName\": \"Buanow\",\n" +
            "\t\"username\": \"maxb2009\",\n" +
            "\t\"password\": \"12345\",\n" +
            "\t\"gender\": \"MALE\",\n" +
            "\t\"email\": \"maxbpro2009@gmail.com\"\n" +
            "}";

    public static String userJsonWithoutUsername = "{" +
            "\t\"firstName\": \"Maxim\",\n" +
            "\t\"lastName\": \"Buanow\",\n" +
            "\t\"password\": \"12345\",\n" +
            "\t\"gender\": \"MALE\",\n" +
            "\t\"email\": \"maxbpro2009@gmail.com\"\n" +
            "}";

    private User userObj = null;
    private User userObjWithoutUsername = null;


    @Before
    public void init() throws IOException {
        userObj = new ObjectMapper().readValue(userJsonExample, User.class);
        userObjWithoutUsername = new ObjectMapper().readValue(userJsonWithoutUsername, User.class);
    }

    @After
    public void destroy(){
        mongoTemplate.dropCollection(User.class);
    }

    @Test
    public void saveUserSuccess() {

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/users"), userObj, ResponseEnvelope.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, String> result = (HashMap<String, String> )envelope.getResult();
        assertEquals(userObj.getFirstName(), result.get("firstName"));
        assertEquals(userObj.getEmail(), result.get("email"));
        assertEquals(userObj.getLastName(), result.get("lastName"));
        assertEquals(userObj.getUsername(), result.get("username"));
        assertEquals(userObj.getGender().toString(), result.get("gender"));


    }

    @Test
    public void saveUserUsernameValidationFailed() {

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/users"), userObjWithoutUsername, ResponseEnvelope.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, Object> result = (HashMap<String, Object> )envelope.getResult();

        assertEquals("Validation error", result.get("message"));

        List<HashMap<String, String>> subErrors = (List<HashMap<String, String>>)result.get("subErrors");

        assertEquals(1, subErrors.size());
        assertEquals("username", subErrors.get(0).get("field"));
        assertEquals("may not be null", subErrors.get(0).get("message"));
    }

    @Test
    public void saveUserAlreadyExists() {

        restTemplate.postForEntity(
                createURLWithPort("/api/v1/users"), userObj, ResponseEnvelope.class);

        ResponseEntity responseEntity = restTemplate.postForEntity(
                createURLWithPort("/api/v1/users"), userObj, ResponseEnvelope.class);


        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        HashMap<String, String> result = (HashMap<String, String> )envelope.getResult();

        assertEquals("Duplicate error", result.get("message"));
    }


    @Test
    public void allUsers() throws Exception {

       restTemplate.postForEntity(
                createURLWithPort("/api/v1/users"), userObj, ResponseEnvelope.class);

        ResponseEntity responseEntity = restTemplate.getForEntity(
                createURLWithPort("/api/v1/users"), ResponseEnvelope.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResponseEnvelope envelope = (ResponseEnvelope)responseEntity.getBody();
        List<HashMap<String, String>> result = (List<HashMap<String, String>>)envelope.getResult();

        assertEquals(userObj.getFirstName(), result.get(0).get("firstName"));
        assertEquals(userObj.getEmail(), result.get(0).get("email"));
        assertEquals(userObj.getLastName(), result.get(0).get("lastName"));
        assertEquals(userObj.getUsername(), result.get(0).get("username"));
        assertEquals(userObj.getGender().toString(), result.get(0).get("gender"));

    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}