package dao;

import interface_.IKhoaHocDAO;
import model.GiangVien;
import model.KhoaHoc;
import java.sql.*;
import java.util.*;

public class KhoaHocDAO implements IKhoaHocDAO {
    private Connection conn = DBConnection.getConnection();

    @Override
    public List<KhoaHoc> getAll() {
        List<KhoaHoc> list = new ArrayList<>();
        String sql = "SELECT * FROM KHOAHOC";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KhoaHoc kh = new KhoaHoc(
                    rs.getString("MAKHOAHOC"),
                    rs.getString("TENKHOAHOC"),
                    rs.getInt("SOTINCHI"),
                    rs.getDouble("HOCPHI"),
                    new GiangVien(rs.getString("MAGIANGVIEN"), "", "", null, 0)
                );
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public KhoaHoc timTheoId(String id) {
        String sql = "SELECT * FROM KHOAHOC WHERE MAKHOAHOC = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new KhoaHoc(
                    rs.getString("MAKHOAHOC"),
                    rs.getString("TENKHOAHOC"),
                    rs.getInt("SOTINCHI"),
                    rs.getDouble("HOCPHI"),
                    new GiangVien(rs.getString("MAGIANGVIEN"), "", "", null, 0)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean themKhoaHoc(KhoaHoc kh) {
        String sql = "INSERT INTO KHOAHOC (MAKHOAHOC, TENKHOAHOC, SOTINCHI, HOCPHI, MAGIANGVIEN) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getIdKhoaHoc());
            ps.setString(2, kh.getTenKhoaHoc());
            ps.setInt(3, kh.getSoTinChi());
            ps.setDouble(4, kh.getHocPhi());
            ps.setString(5, kh.getGiangVien().getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean capNhatKhoaHoc(KhoaHoc kh) {
        String sql = "UPDATE KHOAHOC SET TENKHOAHOC = ?, SOTINCHI = ?, HOCPHI = ?, MAGIANGVIEN = ? WHERE MAKHOAHOC = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getTenKhoaHoc());
            ps.setInt(2, kh.getSoTinChi());
            ps.setDouble(3, kh.getHocPhi());
            ps.setString(4, kh.getGiangVien().getId());
            ps.setString(5, kh.getIdKhoaHoc());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean xoaKhoaHoc(String id) {
        String sql = "DELETE FROM KHOAHOC WHERE MAKHOAHOC = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
