package com.gang.study.spring.fileupload.demo.logic;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname UploadLogic
 * @Description TODO
 * @Date 2020/5/11 23:01
 * @Created by zengzg
 */
@RequestMapping("/file")
@RestController
public class UploadLogic {

    @PostMapping("")
    public String upload() {
        return "";
    }
}
