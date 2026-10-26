package bootcamp.hibernate_practical.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;


public record CreateBookRequest(
    @NotBlank(message = "Title of the book is required")
    String title,

    @NotBlank(message = "Author of the book is required")
    @Pattern(regexp = "^[A-Za-z0-9._~-]+$",
            message = "author should be one word and can contain only letters, digits, '.', '_', '~' and '-'")
    String author,

    @NotBlank(message = "Genre should be not null")
    String genre,

    @Positive(message = "Year should be positive")
    int publicationYear
) {}
