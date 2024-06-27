package com.example.jpatest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
   
    @GetMapping("/")
    public String index(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "index_book";
    }

   
    @GetMapping("/{bookid}")
    public String viewBook(@PathVariable("bookid") Long bookid, Model model) {
        Book book = bookService.getBookById(bookid);
        if (book == null) {
            throw new BookNotFoundException(bookid);
        }
        model.addAttribute("book", book);
        return "detail_book";
    }
    
    

    @PostMapping("/{bookid}")
    public ResponseEntity<Book> updateBook(@PathVariable("bookid") Long bookId, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(bookId, book);
        return ResponseEntity.ok().body(updatedBook);
    }
    
    // edit 페이지에서 폼 제출 시 처리
    @PostMapping("/edit/{bookid}")
    public String editBookSubmit(@PathVariable("bookid") Long bookid, @ModelAttribute("book") Book book) {
        // bookId에 해당하는 Book 객체의 정보를 업데이트 한다.
    	Book updatedBook = bookService.updateBook(bookid, book);
        return "redirect:/book/";
    }
    

    @PostMapping("/delete/{bookid}")
    public String deleteBook(@PathVariable Long bookid) {
        bookService.deleteBook(bookid);
        //return ResponseEntity.noContent().build();
        return "redirect:/book/";
    }
      

    // HTTP GET 요청을 처리하며, URL은 "/book/new"입니다.
    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "new_book";
    }
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, Model model) {
        bookService.createBook(book);
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "index_book";
    }
    
    @GetMapping("/edit/{bookid}")
    public String showEditForm(@PathVariable("bookid") Long bookid, Model model) {
        Book book = bookService.getBookById(bookid);
        model.addAttribute("book", book);
        return "edit_book";
    }

}