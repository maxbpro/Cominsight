package com.maxb.cominsight.services;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyService {

    List<Company> getCompanies();

    Company saveCompany(Company company);

    void deleteCompany(Company company);

    void deleteCompany(String id);

    Company findCompany(String id);

    Company addFollower(String companyId,  String username) throws EntityNotFoundException;

    Company removeFollower(String companyId,  String username) throws EntityNotFoundException;

    boolean isFollowed(String companyId,  String username) throws EntityNotFoundException;

    Company updateAvatar(String username, MultipartFile multipartFile) throws EntityNotFoundException, IOException;
}
