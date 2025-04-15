package interface_;

import model.KhoaHoc;
import java.util.List;

public interface IKhoaHocDAO {
    List<KhoaHoc> getAll();
    KhoaHoc timTheoId(String id);
    boolean themKhoaHoc(KhoaHoc kh);
    boolean capNhatKhoaHoc(KhoaHoc kh);
    boolean xoaKhoaHoc(String id);
}
