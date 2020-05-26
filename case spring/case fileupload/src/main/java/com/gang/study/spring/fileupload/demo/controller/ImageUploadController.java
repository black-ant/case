package com.gang.study.spring.fileupload.demo.controller;

import com.gang.study.spring.fileupload.demo.model.File;
import com.gang.study.spring.fileupload.demo.service.FileService;
import com.gang.study.spring.fileupload.demo.utils.SaveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

import static com.gang.study.spring.fileupload.demo.utils.CreateMd5.createMd5;
import static com.gang.study.spring.fileupload.demo.utils.DeepCopy.deepClone;
import static com.gang.study.spring.fileupload.demo.utils.IsImag.isImage;
import static com.gang.study.spring.fileupload.demo.utils.SaveFile.getRealPath;


@Controller
@RequestMapping("/ImageUpload")
public class ImageUploadController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/Index", method = RequestMethod.GET)
    public String Upload() {
        return "ImageUpload/Upload";
    }

    @ResponseBody
    @RequestMapping(value = "/ImageUp", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("id") String id,
                             @RequestParam("name") String name,
                             @RequestParam("type") String type,
                             @RequestParam("lastModifiedDate") String lastModifiedDate,
                             @RequestParam("size") int size,
                             @RequestParam("file") MultipartFile file) {
        String fileName = "";

        MultipartFile saveFile = null;

        try {
            saveFile = (MultipartFile) deepClone(file);
            java.io.File tempFile = new java.io.File(UUID.randomUUID().toString());
            file.transferTo(tempFile);
            if (!isImage(tempFile))
                return "{\"error\":true}";

            String realpath = getRealPath();
            String ext = name.substring(name.lastIndexOf("."));
            fileName = UUID.randomUUID().toString() + ext;
            SaveFile.saveFile(realpath, fileName, saveFile);

            fileService.save(new File(fileName, createMd5(file).toString(), new Date()));

        } catch (Exception ex) {
            return "{\"error\":true}";
        }

        return "{jsonrpc = \"2.0\",id = id,filePath = \"/Upload/\" + fileFullName}";
    }
}

