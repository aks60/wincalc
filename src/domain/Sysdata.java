package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Расчетные данные настройки
 */
public class Sysdata {

    public int sunic;
    public String sname;
    public String stext;
    public float sflot;

    public Sysdata(ResultSet rs) throws SQLException {

        sunic = rs.getInt("SUNIC");
        sname = rs.getString("SNAME");
        stext = rs.getString("STEXT");
        sflot = rs.getFloat("SFLOT");
    }

    public Sysdata() {}

    public static Sysdata get(Constructive constructive, int sunic) {
        return constructive.sysdataMap.get(sunic);
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        if (!constructive.fromPS) {
            Sysdata record = new Sysdata();
            record.sunic = 2101;
            record.sname = "Наценка на изделие с коробками арочной формы";
            record.stext = "~";
            record.sflot = 10;

            constructive.sysdataMap.put(record.sunic, record);
            return;
        }

        String sql = constructive.fromPS ? "select * from SYSDATA" : "select * from PRO4_SYSDATA where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sysdata record = new Sysdata(rs);
                constructive.sysdataMap.put(record.sunic, record);
            }
        }
    }
}
