package com.bruno.literAlura.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bruno.literAlura.domain.entities.Author;
import com.bruno.literAlura.domain.entities.Book;
import com.bruno.literAlura.domain.repositories.BookRepository;
import com.bruno.literAlura.dtos.AuthorDto;
import com.bruno.literAlura.dtos.BookDto;
import com.bruno.literAlura.dtos.BookResponseDto;
import com.bruno.literAlura.dtos.ClientResponseDto;
import com.bruno.literAlura.infra.client.GutendexClient;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final GutendexClient gutendexClient;
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public Book searchBook(String search) {
        ClientResponseDto response = gutendexClient.findBookByTitle(search);

        if (response.count() >= 1) {
            return saveBook(response);
        }

        return null;
    }

    @Transactional
    private Book saveBook(ClientResponseDto responseDto) {
        // Get the BookDto
        BookDto bookDto = responseDto.results().getFirst();

        var possibleBook = findByTitle(bookDto.title());

        if (possibleBook.isPresent()) {
            return null;
        }

        // Get the author from BookDto
        AuthorDto authorDto = bookDto.authors().getFirst();

        // Set Author
        Author author = Author.builder()
                .name(authorDto.name())
                .birthYear(authorDto.birth_year())
                .deathYear(authorDto.death_year())
                .build();

        // Set Book
        Book newBook = Book.builder()
                .title(bookDto.title())
                .gutendex_id(bookDto.id())
                .languages(bookDto.languages())
                .download_count(bookDto.download_count())
                .author(author)
                .build();

        // Set the book in Author
        author.getBooks().add(newBook);

        // Save author in DB
        authorService.saveAuthor(author);

        // Save book in DB
        return bookRepository.save(newBook);
    }

    public List<BookResponseDto> findAllByTitle(String title) {

        return mapListOfBookToListBookResponseDto(bookRepository.findByTitleIgnoreCaseContaining(title));

    }

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitleIgnoreCase(title);
    }

    public List<BookResponseDto> findAll() {
        return mapListOfBookToListBookResponseDto(bookRepository.findAll());
    }

    public List<BookResponseDto> findByAuthorName(String name) {
        return mapListOfBookToListBookResponseDto(bookRepository.findByAuthor_NameIgnoreCaseContaining(name));
    }

    private List<BookResponseDto> mapListOfBookToListBookResponseDto(List<Book> books) {
        return books.stream().map(book -> new BookResponseDto(book.getTitle(), book.getLanguages(),
                book.getDownload_count(), book.getAuthor().getName(), book.getAuthor().getBirthYear(),
                book.getAuthor().getDeathYear())).toList();
    }

    public List<BookResponseDto> findAllByLanguages(String language) {
        return mapListOfBookToListBookResponseDto(bookRepository.findAllByLanguages(language));
    }

    public List<BookResponseDto> findAllByBirthYear_DeathYear(int birth, int death) {
        return mapListOfBookToListBookResponseDto(
                bookRepository.findAllByAuthor_BirthYearGreaterThanEqualAndAuthor_DeathYearLessThanEqual(birth, death));
    }

}
