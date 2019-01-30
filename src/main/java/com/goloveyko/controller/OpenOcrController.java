package com.goloveyko.controller;

import com.goloveyko.constants.Constants;
import com.goloveyko.service.OpenOcrService;
import com.goloveyko.utility.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class OpenOcrController {
    private static final Logger logger = LoggerFactory.getLogger(OpenOcrController.class);

    private OpenOcrService openOcrService;

    OpenOcrController(OpenOcrService openOcrService) {
        this.openOcrService = openOcrService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/imageForm")
    public String imageForm() {
        return "imageForm";
    }

    @GetMapping("/fileForm")
    public String fileForm() {
        return "fileForm";
    }

    @PostMapping("/image")
    public String processImage(@RequestParam("imageUrl") String url,
                               @RequestParam("language") String language, Model model) {
        String response = openOcrService.processImageFromUrl(url, language);
        model.addAttribute("response", response);
        return "result";
    }

    @PostMapping("/file")
    public String processFile(@RequestParam("fileUpload") MultipartFile file,
                                    @RequestParam("language") String language, Model model) {
        String fileName = file.getOriginalFilename();

        if(!file.isEmpty()){
            FileUpload.save(file);
            logger.info("File successfully saved: " + fileName);
        } else {
            logger.info("File was not saved: " + fileName);
        }

        String response = openOcrService.translateFileToText(Constants.PATH_TO_FILE + fileName, language);
        model.addAttribute("response", response);
        return "result";
    }
}
