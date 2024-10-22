package com.bruno.literAlura.dtos;

import java.util.List;

public record BookDto(
        Long id,
        String title,
        List<AuthorDto> authors,
        List<String> languages,
        Integer download_count) {
}
// "title": <string>,
// "subjects": <array of strings>,
// "authors": <array of Persons>,
// "translators": <array of Persons>,
// "bookshelves": <array of strings>,
// "languages": <array of strings>,
// "copyright": <boolean or null>,
// "media_type": <string>,
// "formats": <Format>,
// "download_count": <number>
