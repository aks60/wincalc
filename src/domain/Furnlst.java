package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Список фурнитуры
 */
public class Furnlst {

    public String fname;//название
    public int funic;//ID фурнитуры / фурнитурного набора

    public Furnlst() {
    }

    public Furnlst(ResultSet rs) throws SQLException {
         
        fname = rs.getString("FNAME");
        funic = rs.getInt("FUNIC");
    }


    public static ArrayList<Furnlst> find(Constructive constructive, int funic) {

        ArrayList<Furnlst> recordList = new ArrayList();
        for (Furnlst record : constructive.furnlstList) {
            if (funic == record.funic) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static Furnlst find2(Constructive constructive, int clnum) {

        for (Furnlst record : constructive.furnlstList) {
            if (clnum == record.funic) {
                return record;
            }
        }
        return null;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from FURNLST" : "select * from PRO4_FURNLST where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Furnlst record = new Furnlst(rs);
                constructive.furnlstList.add(record);
            }
        }
    }
}
