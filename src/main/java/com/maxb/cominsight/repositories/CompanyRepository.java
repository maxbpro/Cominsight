package com.maxb.cominsight.repositories;

import com.maxb.cominsight.models.essential.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
}
