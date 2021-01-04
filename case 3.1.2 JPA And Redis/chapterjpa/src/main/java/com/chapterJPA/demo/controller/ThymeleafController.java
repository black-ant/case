package com.chapterJPA.demo.controller;

import com.chapterJPA.demo.entity.model.ThemModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/learn")
public class ThymeleafController {

        @ModelAttribute("whos")
        public List<String> populateTypes() {
            return Arrays.asList("personZ","personY","personQ");
        }

        @RequestMapping("first")
        public ModelAndView index(){
            List<ThemModel> learnList =new ArrayList<ThemModel>();
            ThemModel bean =new ThemModel("这是一篇thymleaf","尝试做一个thymleaf");
            learnList.add(bean);
            bean =new ThemModel("ant-black","我的小项目");
            learnList.add(bean);
            ModelAndView modelAndView = new ModelAndView("/thymleafTest");
            modelAndView.addObject("learnList", learnList);
            return modelAndView;
        }
}
