package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Наименование значения параметра
 */
public class Parcols {
     
    public int psss;
    public short pporn;
    public int pnumb;//номер параметра
    public int znumb;
    public int punic;
    public String ptext;//наименование значения параметра

    public Parcols() {
    }

    public Parcols(ResultSet rs) throws SQLException {
         
        psss = rs.getInt("PSSS");
        pporn = rs.getShort("PPORN");
        pnumb = rs.getInt("PNUMB");
        znumb = rs.getInt("ZNUMB");
        punic = rs.getInt("PUNIC");
        ptext = rs.getString("PTEXT");
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from PARCOLS" : "select * from PRO4_PARCOLS where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Parcols record = new Parcols(rs);
                constructive.parcolsList.add(record);
            }
        }
    }
}
