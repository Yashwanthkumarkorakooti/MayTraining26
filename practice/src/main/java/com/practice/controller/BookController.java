//Create a POST API to add Book record in the DB.
//Read authorId as path variable.
//Create a DTO to read title and summary from the API caller.
//validate it using validation annotations.
//Handle the Exception

package com.practice.controller;

import com.practice.dto.addBookReqDto;
import com.practice.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/api/add-book/{authorId}")
    public void addBook(@PathVariable int authorId,
                       @Valid @RequestBody addBookReqDto dto){
        bookService.addBook(authorId,dto);
    }
}
