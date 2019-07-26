package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Парамметры системы профилей
 */
public class Parsysp {

    public int psss;//ИД серии профилей
    public short pporn;//номер параметра в таблицах ввода параметров в программу
    public int pnumb;//номер параметра
    public int znumb;//значение параметра
    public int punic;
    public String ptext;//наименование значения параметра

    public Parsysp() {
    }

    public Parsysp(ResultSet rs) throws SQLException {


        psss = rs.getInt("PSSS");
        pporn = rs.getShort("PPORN");
        pnumb = rs.getInt("PNUMB");
        znumb = rs.getInt("ZNUMB");
        punic = rs.getInt("PUNIC");
        ptext = rs.getString("PTEXT");
    }

    public static ArrayList<Parsysp> find(Constructive constructive, int nunic) {

        ArrayList<Parsysp> recordList = new ArrayList();
        for (Parsysp record : constructive.parsyspList) {
            if (nunic == record.psss) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from PARSYSP" : "select * from PRO4_PARSYSP where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                constructive.parsyspList.add(new Parsysp(rs));
            }
        }
    }
}
