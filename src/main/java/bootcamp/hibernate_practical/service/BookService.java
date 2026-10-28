package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.dto.BookResponse;
import bootcamp.hibernate_practical.dto.CreateBookRequest;
import bootcamp.hibernate_practical.dto.UpdateBookRequest;
import bootcamp.hibernate_practical.entity.Book;
import bootcamp.hibernate_practical.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RestControllerAdvice
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse createBook(CreateBookRequest request) {
        Book book = new Book(
                request.title(),
                request.author(),
                request.genre(),
                request.publicationYear(),
                true
        );
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    public List<BookResponse> getAllBooks() {
        List<Book> booksList = bookRepository.findAll();
        return mapToResponse(booksList);
    }

    public BookResponse getBookById(Long id) {
        Book bookById = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return mapToResponse(bookById);
    }

    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book bookById = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        bookById.setTitle(request.getTitle());
        bookById.setAuthor(request.getAuthor());
        bookById.setGenre(request.getGenre());
        bookById.setPublicationYear(request.getPublicationYear());
        bookById.setAvailable(request.getAvailable());

        bookRepository.save(bookById);
        return mapToResponse(bookById);
    }

    public void deleteBook(Long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bookRepository.deleteById(id);
    }

    public List<BookResponse> findByAuthor(String author) {
        List<Book> booksByAuthor = bookRepository.findByAuthor(author);

        return mapToResponse(booksByAuthor);
    }

    public List<BookResponse> findAvailableBooks() {
        List<Book> booksByAuthor = bookRepository.findByAvailableTrue();

        return mapToResponse(booksByAuthor);
    }

    public List<BookResponse> findPublicatedAfterYear(long year) {
        List<Book> booksPublicatedAfterYear = bookRepository.findPublishedAfterYear(year);

        return mapToResponse(booksPublicatedAfterYear);
    }

    /**
     * mapper to dto for one book
     */
    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublicationYear(),
                book.isAvailable()
        );
    }

    /**
     * mapper to DTO for multiple books
     */
    private List<BookResponse> mapToResponse(List<Book> books) {
        return books.stream().map(this::mapToResponse).toList();
    }
}
