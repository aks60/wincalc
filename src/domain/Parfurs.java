package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * параметры спецификаций фурнитуры
 */
public class Parfurs implements ITParam {

    public int psss;//номер строки из спецификации фурнитуры FURNSPC.FINCB, FURNSPC.CLNUM
    public short pporn;//номер значения виден в ПС PARLIST.PPORN
    public int pnumb;//номер параметра
    public int znumb;//значение параметра
    public int punic;//номер строки (id)
    public String ptext;//наименование значения параметра

    public Parfurs() {
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

    public static ArrayList<ITParam> find(Constructive constructive, int fincb) {

        ArrayList<ITParam> recordList = new ArrayList();
        for (Parfurs record : constructive.parfursList) {
            if (fincb == record.psss) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from PARFURS" : "select * from PRO4_PARFURS where REGION_ID=" + constructive.regionId + " order by pnumb desc";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Parfurs record = new Parfurs(rs);
                constructive.parfursList.add(record);
            }
        }
    }

    public Parfurs(ResultSet rs) throws SQLException {
         
        psss = rs.getInt("PSSS");
        pporn = rs.getShort("PPORN");
        pnumb = rs.getInt("PNUMB");
        znumb = rs.getInt("ZNUMB");
        punic = rs.getInt("PUNIC");
        ptext = rs.getString("PTEXT");
    }
}
