package bootcamp.hibernate_practical.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class UpdateBookRequest {
    @NotBlank(message = "Title of the book is required")
    private String title;

    @NotBlank(message = "Author of the book is required")
    @Pattern(regexp = "^[\\p{L} .'-]+$",
            message = "author should be one word and can contain only letters, digits, '.', '_', '~' and '-'")
    private String author;

    @NotBlank(message = "Genre should be not null")
    private String genre;

    @Positive(message = "Year should be positive")
    private Integer publicationYear;

    @NotNull(message = "Book availability should not be empty")
    private Boolean available;
}
