package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Группа текстур
 */
public class Grupcol {

    public int gnumb;//номер группы
    public String gname;//название группы
    public int gunic;
    public float gkoef;//ценовой коэффицент
    public float gprc1;//ценовой коэффицент
    public float gprc2;//ценовой коэффицент

    public Grupcol() {
    }

    public Grupcol(ResultSet rs, boolean fromPS) throws SQLException {

        gnumb = rs.getInt("GNUMB");
        gname = rs.getString("GNAME");
        gunic = rs.getInt("GUNIC");
        gkoef = rs.getFloat("GKOEF");
        gprc1 = rs.getFloat("GPRC1");
        gprc2 = rs.getFloat("GPRC2");
    }

    public static Grupcol get(Constructive constructive, int gnumb) {
        return constructive.grupcolMap.get(gnumb);
    }

    public  static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from GRUPCOL" : "select * from PRO4_GRUPCOL where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Grupcol record = new Grupcol(rs, constructive.fromPS);
                constructive.grupcolMap.put(record.gnumb, record);
            }
        }
    }
}
