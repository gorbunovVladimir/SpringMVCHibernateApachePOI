package com.gorbunov.spring.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Vl on 06.11.2016.
 */
public class FileUpload {
    MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
