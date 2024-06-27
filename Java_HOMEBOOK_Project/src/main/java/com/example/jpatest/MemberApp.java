package com.example.jpatest;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@ComponentScan(basePackages = "com.example.jpatest")
public class MemberApp extends JFrame {
    private final MemberService memberService;

    private JTable table;
    private JTextField memberIdField;
    private JTextField joinDateField;
    private JTextField passwordField;
    private JTextField phoneField;
    private JTextField isWithdrawField;

    @Autowired
    public MemberApp(MemberService memberService) {
        this.memberService = memberService;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1050, 400);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

		JPanel buttonPanel = new JPanel();
		JButton addButton = new JButton("Add");
		JButton readButton = new JButton("Read");
		JButton updateButton = new JButton("Update");
		JButton deleteButton = new JButton("Delete");

		buttonPanel.add(addButton);
		buttonPanel.add(readButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);

		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addMember();
			}
		});

		// 이하 코드는 이전 코드와 동일합니다.
		// ...

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(inputPanel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		inputPanel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel label = new JLabel("Member ID:");
		panel.add(label);
		JLabel label_1 = new JLabel("Join Date:");
		panel.add(label_1);
		JLabel label_2 = new JLabel("Password:");
		panel.add(label_2);
		JLabel label_3 = new JLabel("Phone:");
		panel.add(label_3);
		JLabel label_4 = new JLabel("Is Withdraw:");
		panel.add(label_4);

		JPanel panel_1 = new JPanel();
		inputPanel.add(panel_1);
		memberIdField = new JTextField(10);
		panel_1.add(memberIdField);
		joinDateField = new JTextField(10);
		panel_1.add(joinDateField);
		passwordField = new JTextField(10);
		panel_1.add(passwordField);
		phoneField = new JTextField(10);
		panel_1.add(phoneField);
		isWithdrawField = new JTextField(10);
		panel_1.add(isWithdrawField);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		getContentPane().add(mainPanel);
		setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addMember();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MemberApp.this, "Invalid input format.");
                }
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMembers();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMember();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMember();
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // 선택한 행의 데이터를 입력 필드에 채웁니다.
                    String memberId = table.getValueAt(selectedRow, 0).toString();
                    String joinDate = table.getValueAt(selectedRow, 1).toString();
                    String password = table.getValueAt(selectedRow, 2).toString();
                    String phone = table.getValueAt(selectedRow, 3).toString();
                    String isWithdraw = table.getValueAt(selectedRow, 4).toString();

                    memberIdField.setText(memberId);
                    joinDateField.setText(joinDate);
                    passwordField.setText(password);
                    phoneField.setText(phone);
                    isWithdrawField.setText(isWithdraw);
                }
            }
        });
    }

    private void addMember() {
        String memberId = memberIdField.getText();
        String joinDateText = joinDateField.getText();
        String password = passwordField.getText();
        String phone = phoneField.getText();
        String isWithdraw = isWithdrawField.getText();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate joinDate = LocalDate.parse(joinDateText, formatter);
            Member member = new Member(memberId, joinDate, password, phone, isWithdraw);
            memberService.addMember(member);
            clearInputFields();
            loadMembers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MemberApp.this, "Invalid date format.");
        }
    }

    private void updateMember() {
        String memberId = memberIdField.getText();
        Member foundMember = memberService.getMemberById(memberId);
        if (foundMember != null) {
            Member member = foundMember;
            member.setPassword(passwordField.getText());
            member.setPhone(phoneField.getText());
            member.setIsWithdraw(isWithdrawField.getText());

            memberService.updateMember(member);

            JOptionPane.showMessageDialog(MemberApp.this, "Member updated successfully!");
        } else {
            JOptionPane.showMessageDialog(MemberApp.this, "Member not found!");
        }

        loadMembers();
    }

    private void deleteMember() {
        String memberId = memberIdField.getText();
        Member foundMember = memberService.getMemberById(memberId);

        if (foundMember != null) {
            memberService.deleteMember(memberId);

            JOptionPane.showMessageDialog(MemberApp.this, "Member deleted successfully!");

            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(MemberApp.this, "Member not found!");
        }

        loadMembers();
    }

    private void clearInputFields() {
        memberIdField.setText("");
        joinDateField.setText("");
        passwordField.setText("");
        phoneField.setText("");
        isWithdrawField.setText("");
    }

    private void loadMembers() {
        List<Member> members = memberService.getAllMembers();

        // JTable에 데이터를 표시하기 위한 모델 생성
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Member ID", "Join Date", "Password", "Phone", "Is Withdraw"});

        // 회원 목록을 모델에 추가
        for (Member member : members) {
            model.addRow(new Object[]{member.getMemberId(), member.getJoinDate(), member.getPassword(),
                    member.getPhone(), member.getIsWithdraw()});
        }

        // JTable에 모델 설정
        table.setModel(model);
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

        ApplicationContext context = SpringApplication.run(MemberApp.class, args);

        SwingUtilities.invokeLater(() -> {
            MemberApp app = context.getBean(MemberApp.class);
        });
    }
}