package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Параметры вариантов соединений
 */
public class Parconv  implements ITParam {

    public int psss;
    public short pporn;
    public int pnumb;//номер параметра
    public int znumb;
    public int punic;
    public String ptext;//наименование значения параметра

    public Parconv() {
    }

    public int pnumb() {
        return pnumb;
    }

    public int znumb() {
        return znumb;
    }

    public String ptext() {
        return ptext;
    }

    public Parconv(ResultSet rs) throws SQLException {

        psss = rs.getInt("PSSS");
        pporn = rs.getShort("PPORN");
        pnumb = rs.getInt("PNUMB");
        znumb = rs.getInt("ZNUMB");
        punic = rs.getInt("PUNIC");
        ptext = rs.getString("PTEXT");
    }

    public static  ArrayList<ITParam> find(Constructive constructive, int psss) {

        ArrayList<ITParam> recordList = new ArrayList();
        for (Parconv record : constructive.parconvList) {
            if (psss == record.psss) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from PARCONV" : "select * from PRO4_PARCONV where REGION_ID=" + constructive.regionId + " order by pnumb desc";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Parconv item = new Parconv(rs);
                constructive.parconvList.add(item);
            }
        }
    }
}
