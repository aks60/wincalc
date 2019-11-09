package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * Системные константы
 */
public class Syssize {

    public int sunic;//ID системы
    public String sname;//Система артикулов
    public float ssizp;//Припуск на сварку
    public float ssizf;//Нахлест створки
    public float ssizi;//Заход импоста

    public Syssize() {
    }

    public Syssize(ResultSet rs) throws SQLException {

        sunic = rs.getInt("SUNIC");
        sname = rs.getString("SNAME");
        ssizp = rs.getFloat("SSIZP");
        ssizf = rs.getFloat("SSIZF");
        ssizi = rs.getFloat("SSIZI");
    }

    public static Syssize find(Constructive constructive, int sunic) {
        if (constructive.syssizeMap.isEmpty()) return new Syssize();

        Syssize found = constructive.syssizeMap.get(sunic);
        if(found == null) {
            for (Map.Entry<Integer, Syssize>  elem : constructive.syssizeMap.entrySet()) {
                if(elem.getValue().sname.equals("@")) found = elem.getValue();
            }
        }
        return found;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from SYSSIZE" : "select * from PRO4_SYSSIZE where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Syssize item = new Syssize(rs);
                constructive.syssizeMap.put(item.sunic, item);
            }
        }
    }
}
