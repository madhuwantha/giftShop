package com.mycompany.myapp.web.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author madhuwantha
 * created on 4/17/2021
 */
@RestController
@RequestMapping("/public")
public class ALl {

    private static final String UPLOAD_DIR = "D://upload//";

    @RequestMapping(value = "/image/{galleryId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFile(@PathVariable("galleryId") String galleryId) throws IOException {
        byte[] bFile = Files.readAllBytes(new File(UPLOAD_DIR + galleryId).toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(bFile.length);

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(bFile, headers, HttpStatus.OK);
        return responseEntity;
    }
}
