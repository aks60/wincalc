package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Спецификация комплектов
 */
public class Kompspc {

    public int clnum;//основная текстура
    public String anumb;//артикул, входящий в состав комплекта

    public Kompspc() {
    }


    public Kompspc(ResultSet rs) throws SQLException {

        clnum = rs.getInt("CLNUM");
        anumb = rs.getString("ANUMB");
    }

    public static ArrayList<Kompspc> find(Constructive constructive, String anumb) {

        ArrayList<Kompspc> recordList = new ArrayList();
        for (Kompspc record : constructive.kompspcList) {
            if (anumb.equals(record.anumb)) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from KOMPSPC" : "select * from PRO4_KOMPSPC where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Kompspc record = new Kompspc(rs);
                constructive.kompspcList.add(record);
            }
        }
    }
}
