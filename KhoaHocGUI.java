package gui;

import model.*;
import dao.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class KhoaHocGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable courseTable;
    private DefaultTableModel courseTableModel;
    private KhoaHocDAO khoaHocDAO;
    private JFrame parentFrame;

    public KhoaHocGUI() {
        this.khoaHocDAO = new KhoaHocDAO();
        setTitle("Quản lý khóa học");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        getContentPane().setLayout(new BorderLayout(10, 10));

        // Title and Logout
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(10, 20, 5, 20));
        JLabel titleLabel = new JLabel("Quản lý khóa học", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setMargin(new Insets(5, 14, 2, 14));
        btnLogout.setMinimumSize(new Dimension(80, 23));

        JPanel logoutPanel = new JPanel(new BorderLayout());
        logoutPanel.setMinimumSize(new Dimension(80, 0));
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        logoutPanel.add(btnLogout, BorderLayout.SOUTH);

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(logoutPanel, BorderLayout.EAST);
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Center Content
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel courseListLabel = new JLabel("Danh sách khóa học", SwingConstants.LEFT);
        courseListLabel.setFont(new Font("Arial", Font.BOLD, 16));
        courseListLabel.setBorder(new EmptyBorder(0, 0, 8, 0));

        String[] columnNames = {"STT", "Mã KH", "Tên KH", "Giảng viên", "Số TC", "Học phí"};
        courseTableModel = new DefaultTableModel(columnNames, 0);
        courseTable = new JTable(courseTableModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setPreferredSize(new Dimension(420, 220));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(courseListLabel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 0, 20));
        buttonPanel.setPreferredSize(new Dimension(200, 300));
        buttonPanel.setBorder(new EmptyBorder(30, 10, 30, 10));

        JButton btnShow = new JButton("Hiển thị DS");
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnBack = new JButton("Quay lại");

        buttonPanel.add(btnShow);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.add(Box.createHorizontalStrut(10));
        contentPanel.add(tablePanel);
        contentPanel.add(Box.createHorizontalStrut(50));
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createHorizontalStrut(10));

        centerPanel.add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        // Button actions
        btnLogout.addActionListener(e -> System.exit(0));
        btnShow.addActionListener(e -> loadCourses());
        btnAdd.addActionListener(e -> JOptionPane.showMessageDialog(this, "Thêm khóa học"));
        btnEdit.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sửa khóa học"));
        btnDelete.addActionListener(e -> JOptionPane.showMessageDialog(this, "Xóa khóa học"));
        btnSearch.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tìm kiếm khóa học"));

        btnBack.addActionListener((ActionEvent e) -> {
            this.dispose();
            if (parentFrame != null) parentFrame.setVisible(true);
        });

        loadCourses();
    }

    private void loadCourses() {
        courseTableModel.setRowCount(0);
        List<KhoaHoc> khoaHoc = khoaHocDAO.getAllKhoaHoc();
        int stt = 1;
        for (KhoaHoc c : khoaHoc) {
            courseTableModel.addRow(new Object[]{
                    stt++, c.getMaKhoaHoc(), c.getTenKhoaHoc(),
                    c.getGiangVien(), c.getSoTinChi(), c.getHocPhi()
            });
        }
    }

    public static void main(String[] args) {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KhoaHocGUI frame = new KhoaHocGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
