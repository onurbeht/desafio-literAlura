package com.bruno.literAlura.services;

import org.springframework.stereotype.Service;

import com.bruno.literAlura.domain.entities.Author;
import com.bruno.literAlura.domain.repositories.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

}
