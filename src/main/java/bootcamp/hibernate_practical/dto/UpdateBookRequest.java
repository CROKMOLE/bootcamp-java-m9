package bootcamp.hibernate_practical.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class UpdateBookRequest {
    @NotBlank(message = "Title of the book is required")
    private String title;

    @NotBlank(message = "Author of the book is required")
    @Pattern(regexp = "^[A-Za-z0-9._~-]+$",
            message = "author should be one word and can contain only letters, digits, '.', '_', '~' and '-'")
    private String author;

    @NotBlank(message = "Genre should be not null")
    private String genre;

    @Positive(message = "Year should be positive")
    private int publicationYear;

    @NotBlank(message = "Book availability should not be empty")
    public boolean available;
}
