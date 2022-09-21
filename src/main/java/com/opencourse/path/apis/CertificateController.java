package com.opencourse.path.apis;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.opencourse.path.services.CertificationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cetififcate")
public class CertificateController {

    private final CertificationService service;

    //authentic users
    @GetMapping
    public ResponseEntity<byte[]> getCertificate(@RequestParam(required = true) String pathId) throws WriterException, IOException{
        Long userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_PDF)
        .body(service.getCertificate(pathId, userId));
    }

}
