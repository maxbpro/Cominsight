package com.maxb.cominsight.services.impl;

import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.repositories.CompanyRepository;
import com.maxb.cominsight.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

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
    public void deleteCompany(String id) {
        companyRepository.delete(id);
    }

    @Override
    public Company findCompany(String id) {
        return companyRepository.findOne(id);
    }
}
