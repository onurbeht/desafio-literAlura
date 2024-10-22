package com.bruno.literAlura.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruno.literAlura.domain.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, String> {

}
