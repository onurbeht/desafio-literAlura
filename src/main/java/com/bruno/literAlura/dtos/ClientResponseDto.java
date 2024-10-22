package com.bruno.literAlura.dtos;

import java.util.List;

public record ClientResponseDto(
                Integer count,
                String next,
                String previous,
                List<BookDto> results) {

}
