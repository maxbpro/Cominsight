package com.maxb.cominsight.repositories;

import com.maxb.cominsight.models.essential.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {

    List<Company> findByIdIn(List<String> ids, Pageable pageable);


}
