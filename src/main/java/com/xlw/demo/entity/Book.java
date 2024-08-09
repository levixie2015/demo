package com.xlw.demo.entity;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer bookId;
    private String bookTitle;
    private String bookAuthor;
    private Double bookPrice;
}