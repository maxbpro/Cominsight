package com.maxb.cominsight.services;

import com.maxb.cominsight.models.Comment;
import com.maxb.cominsight.models.essential.Photo;
import org.springframework.data.domain.Page;

public interface PhotoService {


    Page<Photo> getPhotos(int page, int size);

    Photo savePhoto(Photo photo);

    void deletePhoto(Photo photo);

    void deletePhoto(String id);

    Photo findPhoto(String id);
}
