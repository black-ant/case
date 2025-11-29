//package com.gang.ai.comgangaimcpdemo001.service;
//
//import org.springframework.ai.tool.annotation.Tool;
//import org.springframework.ai.tool.annotation.ToolParam;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class BookServiceImpl implements BookService {
//    private final BookRepository bookRepository;
//
//    public BookServiceImpl(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
//
//    @Override
//    @Tool(name="findBooksByAuthor", description="根据作者查询图书")
//    public List<Book> findBooksByAuthor(@ToolParam("author") String author) {
//        return bookRepository.findByAuthor(author);
//    }
//
//    @Override
//    @Tool(name="findBooksByCategory", description="根据分类查询图书")
//    public List<Book> findBooksByCategory(@ToolParam("category") String category) {
//        return bookRepository.findByCategory(category);
//    }
//}
//
