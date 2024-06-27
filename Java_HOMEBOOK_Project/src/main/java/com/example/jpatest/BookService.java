package com.example.jpatest;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class BookService {

	private final BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book getBookById(Long bookId) {
		return bookRepository.findById(bookId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id " + bookId));
	}

	public Book createBook(Book book) {
		return bookRepository.save(book);
	}

	public Book updateBook(Long bookId, Book bookDetails) {
		Book book = bookRepository.findById(bookId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id " + bookId));

		book.setBookname(bookDetails.getBookname());
		book.setPublisher(bookDetails.getPublisher());
		book.setPrice(bookDetails.getPrice());

		return bookRepository.save(book);
	}

	public void deleteBook(Long bookId) {
		Book book = bookRepository.findById(bookId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id " + bookId));

		bookRepository.delete(book);
	}
}