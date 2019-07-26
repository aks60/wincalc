package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Параметры групп заполнения
 */
public class Pargrup implements ITParam {

    public int psss;
    public short pporn;
    public int pnumb;//номер параметра
    public int znumb;
    public int punic;
    public String ptext;//наименование значения параметра

    public Pargrup() {
    }

    public Pargrup(ResultSet rs) throws SQLException {

        psss = rs.getInt("PSSS");
        pporn = rs.getShort("PPORN");
        pnumb = rs.getInt("PNUMB");
        znumb = rs.getInt("ZNUMB");
        punic = rs.getInt("PUNIC");
        ptext = rs.getString("PTEXT");
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

    public static ArrayList<ITParam> find(Constructive constructive, int gnumb) {

        ArrayList<ITParam> recordList = new ArrayList();
        for (Pargrup record : constructive.pargrupList) {
            if (gnumb == record.psss) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from PARGRUP" : "select * from PRO4_PARGRUP where REGION_ID=" + constructive.regionId + " order by pnumb desc";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pargrup record = new Pargrup(rs);
                constructive.pargrupList.add(record);
            }
        }
    }
}
