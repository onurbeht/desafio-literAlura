package com.bruno.literAlura.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bruno.literAlura.domain.entities.Book;

public interface BookRepository extends JpaRepository<Book, String> {

    Optional<Book> findByTitleIgnoreCase(String title);

    List<Book> findByTitleIgnoreCaseContaining(String title);

    List<Book> findByAuthor_NameIgnoreCaseContaining(String name);

    List<Book> findAllByAuthor_BirthYearGreaterThanEqualAndAuthor_DeathYearLessThanEqual(Integer birth_year,
            Integer death_year);

    @Query("SELECT b FROM Book b where :language MEMBER OF b.languages")
    List<Book> findAllByLanguages(@Param("language") String language);

}
