package com.maxb.cominsight.services;

import com.maxb.cominsight.models.essential.Company;

import java.util.List;

public interface CompanyService {

    List<Company> getCompanies();

    Company saveCompany(Company company);

    void deleteCompany(Company company);

    void deleteCompany(String id);

    Company findCompany(String id);
}
