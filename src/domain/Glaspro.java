package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Профили в группе заполнения
 */
public class Glaspro {

    public String anumb;//артикул
    public int zunic;
    public int gnumb;//ID группы
    public short gtype;//Системные константы (7 - привязка установлена, 3 - привязка отсутствует)

    public Glaspro() {
    }

    public Glaspro(ResultSet rs) throws SQLException {

        anumb = rs.getString("ANUMB");
        zunic = rs.getInt("ZUNIC");
        gnumb = rs.getInt("GNUMB");
        gtype = rs.getShort("GTYPE");
    }

    public static ArrayList<Glaspro> find(Constructive constructive, int gnumb) {

        ArrayList<Glaspro> recordList = new ArrayList<>();
        for (Glaspro record : constructive.glasproList) {
            if (gnumb == record.gnumb) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from GLASPRO" : "select * from PRO4_GLASPRO where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                constructive.glasproList.add(new Glaspro(rs));
            }
        }
    }
}
