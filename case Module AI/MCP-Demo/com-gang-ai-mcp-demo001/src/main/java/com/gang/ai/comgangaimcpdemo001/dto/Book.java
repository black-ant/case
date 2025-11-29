//package com.gang.ai.comgangaimcpdemo001.dto;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "books")
//@Data
//public class Book {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String title;
//
//    @Column(nullable = false)
//    private String category;
//
//    @Column(nullable = false)
//    private String author;
//
//    @Column(nullable = false)
//    private LocalDate publicationDate;
//
//    @Column(nullable = false, unique = true)
//    private String isbn;
//}
//
