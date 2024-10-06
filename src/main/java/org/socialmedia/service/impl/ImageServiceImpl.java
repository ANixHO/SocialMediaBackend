package org.socialmedia.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.socialmedia.model.Image;
import org.socialmedia.repository.ImageRepository;
import org.socialmedia.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @PostConstruct
    public void init() {
        File uploadDir = new File(imageUploadDir);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    @Override
    public byte[] getImageDataById(Long id) throws IOException{
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image can not found id: " + id));

        Path path = Paths.get(image.getFilePath());
        return Files.readAllBytes(path);
    }

    @Override
    public List<Long> getImageUrlsByPostId(Long postId) {
        List<Image> images = imageRepository.findByPostId(postId);
        return images.stream()
                .map(Image::getId)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteImageById(Long id){
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can not find image id: " + id));

        try {
            Path filePath = Paths.get(image.getFilePath());
            Files.deleteIfExists(filePath);
        }catch (IOException e){
            throw new RuntimeException("Occurred error while deleting image id: " + id);
        }

        imageRepository.deleteById(id);
    }

    @Transactional
    public void deleteImageByPostId(Long postId){
        List<Image> imageList = imageRepository.findByPostId(postId);

        for (Image image : imageList) {
            this.deleteImageById(image.getId());
        }
    }

    @Override
    @Transactional
    public void saveImage(Long postId, List<MultipartFile> imageFiles){
        if(imageFiles != null) {
            for (MultipartFile file : imageFiles) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(imageUploadDir, fileName);
                try {
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(
                            "Could not store file " + fileName +
                                    " to file path: " + filePath.toString(), e);
                }
                Image image = new Image();
                image.setPostId(postId);
                image.setFilePath(filePath.toString());
                image.setCreatedAt(LocalDateTime.now());
                imageRepository.save(image);
            }
        }
    }
}

