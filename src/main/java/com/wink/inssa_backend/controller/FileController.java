package com.wink.inssa_backend.controller;

import com.wink.inssa_backend.domain.ImageFile;
import com.wink.inssa_backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/file")
    public String getFileForm() {
        return "file";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            fileService.saveFile(file);
            model.addAttribute("message", "File uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload file!");
        }
        return "file";
    }

    @GetMapping("/download/{id}")
    public String downloadFile(@PathVariable Long id, Model model) {
        ImageFile imageFile = fileService.getFile(id);
        model.addAttribute("file", imageFile);
        return "fileDownload";
    }
}