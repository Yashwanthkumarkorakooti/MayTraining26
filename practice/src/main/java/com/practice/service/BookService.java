package com.practice.service;

import com.practice.dto.addBookReqDto;
import com.practice.exception.ResourceNotFoundException;
import com.practice.mapper.BookMapper;
import com.practice.model.Author;
import com.practice.model.Book;
import com.practice.repository.AuthorRepository;
import com.practice.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public void addBook(int authorId, addBookReqDto dto) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author Not Found"));

        Book book = new Book();
        book.setTitle(dto.title());
        book.setSummary(dto.summary());

        book.setAuthor(author);

//        //or  mapper class
//        Book book = bookMapper.convertToEntity(dto,authorId);

        bookRepository.save(book);

    }
}
