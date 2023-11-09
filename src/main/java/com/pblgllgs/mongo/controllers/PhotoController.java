package com.pblgllgs.mongo.controllers;

import com.pblgllgs.mongo.collections.Photo;
import com.pblgllgs.mongo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

@RestController
@RequestMapping("/api/v1/photos")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;
    
    @PostMapping
    public String save(@RequestParam("image") MultipartFile image) throws IOException {
        return photoService.addPhoto(image.getOriginalFilename(),image);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable("id") String id){
        Photo photo =  photoService.findById(id);
        Resource resource = new ByteArrayResource(photo.getPhoto().getData());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ photo.getTitle() +"\"" )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
