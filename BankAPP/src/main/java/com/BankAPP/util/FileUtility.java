package com.BankAPP.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUtility {

    public static  void validateFile(MultipartFile file){
        if(file.isEmpty()){
            throw new RuntimeException("Please select file to upload");
        }

        List<String> allowedExts = List.of("png","jpg","jpeg","pdf");

        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") +1).toLowerCase();

        if(!allowedExts.contains(ext)){
            throw new RuntimeException(ext + " not allowed");
        }
        if(file.getSize() > 5*1025*1024){
            throw new RuntimeException("File size cannot exceed 5 MB");
        }
    }
}
