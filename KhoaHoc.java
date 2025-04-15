package model;

public class KhoaHoc {
    private String idKhoaHoc;
    private String tenKH;
    private int soTinChi;
    private double hocPhi;
    private GiangVien giangVien;

    public KhoaHoc(String idKhoaHoc, String tenKH, int soTinChi, double hocPhi, GiangVien giangVien) {
        this.idKhoaHoc = idKhoaHoc;
        this.tenKH = tenKH;
        this.soTinChi = soTinChi;
        this.hocPhi = hocPhi;
        this.giangVien = giangVien;
    }

    public String getIdKhoaHoc() { return idKhoaHoc; }
    public String getTenKhoaHoc() { return tenKH; }
    public int getSoTinChi() { return soTinChi; }
    public double getHocPhi() { return hocPhi; }
    public GiangVien getGiangVien() { return giangVien; }

}
