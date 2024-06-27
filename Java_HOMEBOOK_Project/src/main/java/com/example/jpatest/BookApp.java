package com.example.jpatest;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//@SpringBootApplication // 이부분의 주석을 해제하면 gui프로그램을 구동시킬 수 있다.
@ComponentScan(basePackages = "com.example.jpatest") // 패키지 스캔 경로 추가
public class BookApp extends JFrame {

	private final BookService bookService;

	// UI Components
	private JButton loadButton;
	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JTable table;
	private JTextField bookIdField;
	private JTextField bookNameField;
	private JTextField publisherField;
	private JTextField priceField;

	@Autowired
	public BookApp(BookService bookService) {

		this.bookService = bookService;

		setTitle("Book Database CRUD Tester");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create UI components
		loadButton = new JButton("Load Data");
		addButton = new JButton("Add Book");
		updateButton = new JButton("Update Book");
		deleteButton = new JButton("Delete Book");

		bookIdField = new JTextField(10);
		bookNameField = new JTextField(10);
		publisherField = new JTextField(10);
		priceField = new JTextField(10);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectionRow();// JTABLE VIEW 이벤트 처리 루틴 

		// Add action listeners to buttons
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadBooks();
			}
		});

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBook();
			}
		});

		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateBook();
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteBook();
			}
		});

		// Create and set layout for the frame
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4, 2));
		inputPanel.add(new JLabel("Book ID:"));
		inputPanel.add(bookIdField);
		inputPanel.add(new JLabel("Book Name:"));
		inputPanel.add(bookNameField);
		inputPanel.add(new JLabel("Publisher:"));
		inputPanel.add(publisherField);
		inputPanel.add(new JLabel("Price:"));
		inputPanel.add(priceField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loadButton);
		buttonPanel.add(addButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(inputPanel, BorderLayout.NORTH);
		mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		getContentPane().add(mainPanel);

		setVisible(true);
	}
	private void selectionRow() {
		// table 객체에 이벤트 처리를 위한 과정
		ListSelectionModel selectionModel = table.getSelectionModel();
		// selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 단일
		// 선택 모드 설정
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					bookIdField.setText("" + table.getValueAt(selectedRow, 0));
					bookNameField.setText("" + table.getValueAt(selectedRow, 1));
					publisherField.setText("" + table.getValueAt(selectedRow, 2));
					priceField.setText("" + table.getValueAt(selectedRow, 3));
				}
			}
		});
	}
	
	

	private void loadBooks() {
		List<Book> books = bookService.getAllBooks();

		// 모델을 새로 만들고 열 이름을 설정합니다.
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] { "Book ID", "Book Name", "Publisher", "Price" });

		// 가져온 책 목록을 모델에 추가합니다.
		for (Book book : books) {
			model.addRow(new Object[] { book.getBookid(), book.getBookname(), book.getPublisher(), book.getPrice() });
		}

		// JTable에 모델을 설정합니다.
		table.setModel(model);
	}

	private void addBook() {
		long bookid = Integer.parseInt(bookIdField.getText());
		String bookName = bookNameField.getText();
		String publisher = publisherField.getText();
		long price = Long.parseLong(priceField.getText());

		Book book = new Book();
		book.setBookid(bookid);
		book.setBookname(bookName);
		book.setPublisher(publisher);
		book.setPrice(price);

		bookService.createBook(book);

		loadBooks();
		clearInputFields();
	}

	private void updateBook() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			showMessage("Select a book to update.");
			return;
		}

		long bookid = Integer.parseInt(bookIdField.getText());
		String bookName = bookNameField.getText();
		String publisher = publisherField.getText();
		long price = Long.parseLong(priceField.getText());

		Book book = new Book();
		book.setBookid(bookid);
		book.setBookname(bookName);
		book.setPublisher(publisher);
		book.setPrice(price);

		bookService.updateBook(bookid, book);

		loadBooks();
		clearInputFields();
	}

	private void deleteBook() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			showMessage("Select a book to delete.");
			return;
		}

		Long bookId = (Long) table.getValueAt(selectedRow, 0);
		bookService.deleteBook(bookId);

		loadBooks();
		clearInputFields();
	}

	private void clearInputFields() {
		bookIdField.setText("");
		bookNameField.setText("");
		publisherField.setText("");
		priceField.setText("");
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public static void main(String[] args) {

		try {
	        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    ApplicationContext context = SpringApplication.run(BookApp.class, args);

	    SwingUtilities.invokeLater(() -> {
	        BookApp app = context.getBean(BookApp.class);
	    });
	}
}
