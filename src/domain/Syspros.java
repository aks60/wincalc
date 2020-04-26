package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Фурнитуры системы профилей
 */
public class Syspros {

    public int nuni;//ИД серии профилей
    public int funic;//ИД фурнитурного набора
    public short fporn;
    public String nruch;//расположение ручки по умолчанию

    public Syspros() {
    }

    public Syspros(ResultSet rs) throws SQLException {

        nuni = rs.getInt("NUNI");
        funic = rs.getInt("FUNIC");
        fporn = rs.getShort("FPORN");
        nruch = rs.getString("NRUCH");
    }

    public static ArrayList<Syspros> find(Constructive constructive, int nuni) {

        ArrayList<Syspros> recordList = new ArrayList();
        for (Syspros record : constructive.sysprosList) {
            if (nuni == record.nuni) {
                recordList.add(record);
            }
        }
        Collections.sort(recordList, new Comparator<Syspros>() {
            @Override
            public int compare(Syspros p1, Syspros p2) {
                return p1.fporn > p2.fporn ? 1 : (p1.fporn < p2.fporn) ? -1 : 0;
            }
        });

        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from SYSPROS" : "select * from PRO4_SYSPROS where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Syspros record = new Syspros(rs);
                constructive.sysprosList.add(record);
            }
        }
    }
}
