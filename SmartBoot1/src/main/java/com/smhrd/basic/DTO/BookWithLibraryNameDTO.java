package com.smhrd.basic.DTO;

import com.smhrd.basic.entity.BookEntity;

public class BookWithLibraryNameDTO {
    private String title;
    private String author;
    private String publisher;
    private String bookImg;  // 표지 이미지
    private String libraryName; // 도서관 이름

    public BookWithLibraryNameDTO(BookEntity book, String libraryName) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.bookImg = book.getBookImg(); // 책 이미지 URL
        this.libraryName = libraryName;  // 도서관 이름
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }
}


