package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Список параметров
 */
public class Parlist {

    public short pporn;//номер параметра
    public String pname;//название параметра или его значения
    public int pnumb;//номер параметра
    public int znumb;//номер параметра или значения

    public Parlist() {
    }

    public Parlist(ResultSet rs) throws SQLException {

        pporn = rs.getShort("PPORN");
        pname = rs.getString("PNAME");
        pnumb = rs.getInt("PNUMB");
        znumb = rs.getInt("ZNUMB");
    }

    public static Parlist get(Constructive constructive, long pnumb, long znumb) {
        try {
            return constructive.parlistMap.get(String.valueOf(pnumb) + "_" + String.valueOf(znumb));

        } catch (Exception e) {
            System.out.println("Ошибка Parlist.get  " + pnumb + " " + znumb + e);
            return null;
        }
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from PARLIST" : "select * from PRO4_PARLIST where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Parlist record = new Parlist(rs);
                constructive.parlistMap.put(String.valueOf(record.pnumb) + "_" + String.valueOf(record.znumb), record);
            }
        }
    }
}
