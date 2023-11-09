package com.pblgllgs.mongo.service;

import com.pblgllgs.mongo.collections.Photo;
import com.pblgllgs.mongo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService{

    private final PhotoRepository photoRepository;
    @Override
    public String addPhoto(String originalFilename, MultipartFile image) throws IOException {
        Photo photo = new Photo();
        photo.setTitle(originalFilename);
        photo.setPhoto(new Binary(BsonBinarySubType.BINARY,image.getBytes()));
        return photoRepository.save(photo).getId();
    }

    @Override
    public Photo findById(String id) {
        return photoRepository.findById(id).orElseThrow(()->new RuntimeException("NOT_FOUND"));
    }
}
