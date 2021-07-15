package com.search.elastic.repository;

import com.search.elastic.entiry.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/13 17:08
 * @Version 1.0
 **/
@Repository
public interface BooksRepository extends ElasticsearchRepository<Books, Integer> {


//    Page<Books> findByAuthor(String name, Pageable pageable);

    List<Books> findByTitle(String title);

    List<Books> findByTitleAndAuthor(String title, String author);

    List<Books> findByTitleAndDesc(String title, String desc);

    List<Books> findByTitleOrderByPubdate(String title);

    List<Books> findByTitle(String title, Sort sort);
}
