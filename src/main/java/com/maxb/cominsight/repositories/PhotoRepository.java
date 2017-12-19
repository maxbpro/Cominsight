package com.maxb.cominsight.repositories;

import com.maxb.cominsight.models.essential.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {
}
