package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * спецификация составов
 */
public class Vstaspc implements ITSpecific {

    public int vnumb;//ИД состава
    public String anumb;//название компонента
    public int clnum;//текстура
    public int aunic;//ИД компонента
    public short ctype;//тип подбора

    public Vstaspc() {
    }

    public Vstaspc(ResultSet rs) throws SQLException {

        vnumb = rs.getInt("VNUMB");
        anumb = rs.getString("ANUMB");
        clnum = rs.getInt("CLNUM");
        aunic = rs.getInt("AUNIC");
        ctype = rs.getShort("CTYPE");
    }

    public int clnum() {
        return clnum;
    }

    public short ctype() {
        return ctype;
    }

    public static ArrayList<Vstaspc> find(Constructive constructive, int vnumb) {

        ArrayList<Vstaspc> recordList = new ArrayList();
        for (Vstaspc record : constructive.vstaspcList) {
            if (vnumb == record.vnumb) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static Vstaspc find2(Constructive constructive, int psss) {

        for (Vstaspc record : constructive.vstaspcList) {
            if (psss == record.aunic) {
                return record;
            }
        }
        return new Vstaspc();
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from VSTASPC" : "select * from PRO4_VSTASPC where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vstaspc record = new Vstaspc(rs);
                constructive.vstaspcList.add(record);
            }
        }
    }
}
