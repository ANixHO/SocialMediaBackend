package org.socialmedia.controller;

import org.socialmedia.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id){
        try {
            byte[] imageData = imageService.getImageDataById(id);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImageById(@PathVariable Long  id){
        try {
            imageService.deleteImageById(id);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
