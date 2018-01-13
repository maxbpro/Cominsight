package com.maxb.cominsight.controllers;

import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.services.CompanyService;
import com.maxb.cominsight.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.bar;
import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CompanyController.class, secure = false)
public class CompanyControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    String exampleCompanyJson = "{\"address\": \"address 1\",\n" +
            "\t\"email\": \"test@gmail.com\",\n" +
            "\t\"phone\": \"8929 - 32- 32\",\n" +
            "\t\"title\": \"Rutorika\",\n" +
            "\t\"url\": \"rut.com\"\n" +
            "}";

    String exampleFollowerJson = "{\n" +
            "\t\"userId\": \"1234567\",\n" +
            "\t\"userName\": \"Max\"\n" +
            "}";


    @Test
    public void allCompanies() throws Exception {

        Company company = new Company();
        company.setTitle("Rutorika");

        List<Company> companies = singletonList(company);

        doReturn(companies).when(companyService).getCompanies();

        List<Company> companyServiceCompanies = companyService.getCompanies();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/companies");

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(1)))
                .andExpect(jsonPath("$.result[0].title", is("Rutorika")))
                .andReturn();

    }

    @Test
    public void createCompany() throws Exception {

        Company mockCompany = new Company();
        mockCompany.setTitle("Rutorika");

//        Mockito.when(companyService.saveCompany(Mockito.any(Company.class)))
//                .thenReturn(mockCompany);

        doReturn(mockCompany).when(companyService).saveCompany(Mockito.any(Company.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/companies")
                .accept(MediaType.APPLICATION_JSON).content(exampleCompanyJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(jsonPath("$.result.title", is("Rutorika")))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }

}