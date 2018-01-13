package com.maxb.cominsight.services.impl;

import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.Photo;
import com.maxb.cominsight.repositories.CompanyRepository;
import com.maxb.cominsight.repositories.PhotoRepository;
import com.maxb.cominsight.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository repository;

    @Override
    public Page<Photo> getPhotos(int page, int size) {
        return repository.findAll(new PageRequest(page, size));
    }

    @Override
    public Photo savePhoto(Photo photo) {
        return repository.save(photo);
    }

    @Override
    public void deletePhoto(Photo photo) {
        repository.delete(photo);
    }

    @Override
    public void deletePhoto(String id) {
        repository.delete(id);
    }

    @Override
    public Photo findPhoto(String id) {
        return repository.findOne(id);
    }
}
