package com.blogapp.impl;

import com.blogapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {  //todo : this file service is not working

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
//        get Filename
        String fileName = file.getOriginalFilename();

//        get random name for file
        String randomId = UUID.randomUUID().toString();
//        attatch to filename
        String uniqueFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
//        full path
        String filePath = path + "\\"+ uniqueFileName;

//        create folder if not created
        File file_Dir = new File(filePath);
        if (!file_Dir.exists()){
            if (file_Dir.mkdir()){
                System.out.println("folder created");
            }else{
                System.out.println("folder not created");
            }
        }

//        copy file
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return uniqueFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+File.separator+fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
