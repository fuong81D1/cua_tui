// git
package gui;

import dao.*;
import model.KhoaHoc;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class KhoaHocGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btn_showDS;
	private JButton btn_TimKiem;
	private JButton btn_Xoa;
	private JButton btn_Them;
	private JButton btn_fixInfo;
	private JButton btn_Back;

	/**
	/**
	 * Create the frame.
	 */
	public KhoaHocGUI() {
		setTitle("Khóa học");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1000, 600);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(26, 10, 686, 536);
        contentPane.add(scrollPane);
        
        btn_showDS = new JButton("Hiển thị danh sách khóa học");
        btn_showDS.setBounds(727, 20, 195, 40);
        btn_showDS.addActionListener(e -> hienThi());
        contentPane.add(btn_showDS);
        
        btn_TimKiem = new JButton("Tìm kiếm khóa học");
        btn_TimKiem.setBounds(727, 70, 195, 40);
        btn_TimKiem.addActionListener(e -> timKiem());
        contentPane.add(btn_TimKiem);
        
        btn_Xoa = new JButton("Xóa khóa học");
        btn_Xoa.setBounds(727, 120, 195, 40);
        btn_Xoa.addActionListener(e -> xoaKH());
        contentPane.add(btn_Xoa);
       
        btn_Them = new JButton("Thêm khóa học");
        btn_Them.setBounds(727, 170, 195, 40);
        btn_Them.addActionListener(e -> themKH());
        contentPane.add(btn_Them);
        
        btn_fixInfo = new JButton("Sửa khóa học");
        btn_fixInfo.setBounds(727, 220, 195, 40);
        btn_fixInfo.addActionListener(e -> suaKH());
        contentPane.add(btn_fixInfo);
        
        btn_Back = new JButton("Quay lại");
        btn_Back.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new AdminGUI().setVisible(true);
        		setVisible(false);
        	}
        });
        
        btn_Back.setBounds(727, 491, 195, 40);
        contentPane.add(btn_Back);
        setLocationRelativeTo(null);
        setVisible(true);
        
        
	}
	
	
	private void hienThi() {
		KhoaHocDAO khDAO = new KhoaHocDAO();
		List<KhoaHoc> khoaHocList = khDAO.getAll();

		String[] columnNames = {"Mã KH", "Tên KH", "Số TC", "Học phí", "Giảng viên"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		for (KhoaHoc kh : khoaHocList) {
			model.addRow(new Object[]{
				kh.getIdKhoaHoc(),
				kh.getTenKhoaHoc(),
				kh.getSoTinChi(),
				kh.getHocPhi(),
				kh.getGiangVien().getId()
			});
		}

		table.setModel(model);  //cap nhật bảng
	}
	
	private void timKiem() {
		String keyword = JOptionPane.showInputDialog(this, "Nhập mã khóa học cần tìm:");
		if (keyword == null || keyword.isEmpty()) return;

		KhoaHocDAO khDAO = new KhoaHocDAO();
		KhoaHoc kh = khDAO.timTheoId(keyword);

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		if (kh != null) {
			model.addRow(new Object[]{
				kh.getIdKhoaHoc(), kh.getTenKhoaHoc(), kh.getSoTinChi(), kh.getHocPhi(), kh.getGiangVien().getId()
			});
		} else {
			JOptionPane.showMessageDialog(this, "Không tìm thấy khóa học với mã: " + keyword);
		}
	}
	
	private void xoaKH() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một khóa học để xóa!");
			return;
		}
		String id = table.getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khóa học: " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			KhoaHocDAO dao = new KhoaHocDAO();
			if (dao.xoaKhoaHoc(id)) {
				JOptionPane.showMessageDialog(this, "Xóa thành công!");
				hienThi();
			} else {
				JOptionPane.showMessageDialog(this, "Xóa thất bại!");
			}
		}
	}
	
	private void themKH() {
		String ma = JOptionPane.showInputDialog(this, "Mã khóa học: ");
		String ten = JOptionPane.showInputDialog(this, "Tên khóa học:");
		String tc = JOptionPane.showInputDialog(this, "Số tín chỉ:");
		String hp = JOptionPane.showInputDialog(this, "Học phí:");
		String gv = JOptionPane.showInputDialog(this, "Mã giảng viên:");
		try {
			KhoaHoc kh = new KhoaHoc(ma, ten, Integer.parseInt(tc), Double.parseDouble(hp), new model.GiangVien(gv, "", "", null, 0));
			KhoaHocDAO dao = new KhoaHocDAO();
			if (dao.themKhoaHoc(kh)) {
				JOptionPane.showMessageDialog(this, "Thêm thành công!");
				hienThi();
			} else {
				JOptionPane.showMessageDialog(this, "Thêm thất bại!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
		}
	}
	
	private void suaKH() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Chọn khóa học muốn sửa!");
			return;
		}
		String ma =  table.getValueAt(row, 0).toString();
		String ten = JOptionPane.showInputDialog(this, "Tên khóa học mới:", table.getValueAt(row, 1));
		String tc = JOptionPane.showInputDialog(this, "Tín chỉ mới:", table.getValueAt(row, 2));
		String hp = JOptionPane.showInputDialog(this, "Học phí mới:", table.getValueAt(row, 3));
		String gv = JOptionPane.showInputDialog(this, "GV mới:", table.getValueAt(row, 4));
		try {
			KhoaHoc kh = new KhoaHoc(ma, ten, Integer.parseInt(tc), Double.parseDouble(hp), new model.GiangVien(gv, "", "", null, 0));
			KhoaHocDAO dao = new KhoaHocDAO();
			if (dao.capNhatKhoaHoc(kh)) {
				JOptionPane.showMessageDialog(this, "Sửa thành công!");
				hienThi();
			} else {
				JOptionPane.showMessageDialog(this, "Sửa thất bại!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new KhoaHocGUI());
	}
}

