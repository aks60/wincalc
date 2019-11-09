package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Дерево системы профилей
 */
public class Sysprof {

    public int nuni;//ID ветки дерева
    public String anumb;//заполнение по умолчанию
    public float koef;//коэффициент рентабельности
    public String npref;//замена / код
    public short typew;//1 - окно; 4,5 - двери

    public Sysprof() {
    }

    public Sysprof(ResultSet rs) throws SQLException {

        nuni = rs.getInt("NUNI");
        anumb = rs.getString("ANUMB");
        koef = rs.getFloat("KOEF");
        npref = rs.getString("NPREF");
        typew = rs.getShort("TYPEW");
    }

    public static  Sysprof get(Constructive constructive, int nuni) {
        return constructive.sysprofMap.get(nuni);
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from SYSPROF" : "select * from PRO4_SYSPROF where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sysprof record = new Sysprof(rs);
                constructive.sysprofMap.put(record.nuni, record);
            }
        }
    }
}
