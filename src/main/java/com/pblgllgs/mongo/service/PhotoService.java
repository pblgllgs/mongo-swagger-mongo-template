package com.pblgllgs.mongo.service;

import com.pblgllgs.mongo.collections.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    String addPhoto(String originalFilename, MultipartFile image) throws IOException;

    Photo findById(String id);
}
