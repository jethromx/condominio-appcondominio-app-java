package com.core.coffee.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    //@Autowired
   // private Cloudinary cloudinary;
    @Value("${cloudinary.url}")
    private String cloudinaryUrl;

    @Async
    public Map uploadImage(MultipartFile file) throws IOException {
       
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    }
}
