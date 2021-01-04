package com.search.elastic.controller;

import com.alibaba.fastjson.JSONObject;
import com.search.elastic.entiry.Books;
import com.search.elastic.repository.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/13 17:15
 * @Version 1.0
 **/
@RestController
@RequestMapping("books")
public class BooksContoller extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BooksRepository booksRepository;

    @PostMapping("getBooks")
    public String getBooks() {
        return apiResponse(booksRepository.findAll());
    }

    @PostMapping("getBooksfilter")
    public String getBooksByFilter(@RequestParam("type") String type, @RequestParam("value") String value, @RequestParam("other") String other) {
        List<Books> list;
        logger.info("type is :{} ,value is:{} ,other is :{}", type, value, other);
        switch (type) {
            //查询标题
            case "T":
                list = booksRepository.findByTitle(value);
                break;
            //查询标题和作者
            case "TA":
                list = booksRepository.findByTitleAndAuthor(value, other);
                break;
            //查询标题和描述
            case "TD":
                list = booksRepository.findByTitleAndDesc(value, other);
                break;
            //查询标题和发布时间
            case "TOD":
                list = booksRepository.findByTitleOrderByPubdate(value);
                break;
            //标题和发布时间Sort 对象版
            case "TSD":
                Sort sort = new Sort(Sort.Direction.DESC, "pubdate");
                list = booksRepository.findByTitle(value, sort);
                break;
            default:
                list = new LinkedList<>();
        }
        return apiResponse(list);
    }

    @PostMapping("savebooks")
    public String saveBooks(@RequestParam("title") String title, @RequestParam("desc") String desc, @RequestParam("price") BigDecimal price, @RequestParam("author") String author) {
        Books books = new Books(new Random().nextInt(111), title, desc, price, author, new Date());
        return apiResponse(booksRepository.save(books));
    }
}
