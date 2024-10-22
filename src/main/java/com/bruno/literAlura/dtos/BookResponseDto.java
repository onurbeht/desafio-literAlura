package com.bruno.literAlura.dtos;

import java.util.List;

public record BookResponseDto(
                String title,
                List<String> languages,
                Integer download_count,
                String author_name,
                Integer author_birth_year,
                Integer author_death_year) {

}
