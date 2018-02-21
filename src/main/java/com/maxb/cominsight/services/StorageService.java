package com.maxb.cominsight.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public interface StorageService {

    void store(MultipartFile file, String fileName);

    void store(RenderedImage image, String fileName) throws IOException;

    Resource loadFile(String filename);

    void deleteAll();

    void init();
}
