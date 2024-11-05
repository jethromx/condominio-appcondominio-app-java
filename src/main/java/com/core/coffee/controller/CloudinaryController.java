
package com.core.coffee.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.core.coffee.service.CloudinaryService;
import com.core.coffee.service.RabbitMQSender;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/test")
public class CloudinaryController {

     @Autowired
    private CloudinaryService cloudinaryService;
  

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("file") @NotNull MultipartFile file) {
       // return ResponseEntity.ok("uploadResult");
       try {
        
        Map uploadResult = cloudinaryService.uploadImage(file);
        return ResponseEntity.ok(uploadResult);
    } catch (IOException e) {
        return ResponseEntity.status(500).body(null);
    }
    }

     @Autowired
    private RabbitMQSender rabbitMQSender;

    @PostMapping(value="/send")
    public String sendMessage(@RequestParam(name = "mensaje",required = true ) @NotNull String message) {
        rabbitMQSender.sendMessage(message);
        return "Message sent to RabbitMQ: " + message;
    }

}
