package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Ограничение сторон фурнитуры
 */
public class Furnlen {
     
    public int funic;
    public int fincr;
    public short fnumb;

    public Furnlen() {
    }

    public Furnlen(ResultSet rs) throws SQLException {
         
        funic = rs.getInt("FUNIC");
        fincr = rs.getInt("FINCR");
        fnumb = rs.getShort("FNUMB");
    }

    public static ArrayList<Furnlen> find(Constructive constructive, int funic) {

        ArrayList<Furnlen> recordList = new ArrayList();
        for (Furnlen record : constructive.furnlenList) {
            if (funic == record.funic) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from FURNLEN" : "select * from PRO4_FURNLEN where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Furnlen record = new Furnlen(rs);
                constructive.furnlenList.add(record);
            }
        }
    }
}
