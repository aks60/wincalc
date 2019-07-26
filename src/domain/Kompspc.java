package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Спецификация комплектов
 */
public class Kompspc {

    public int kunic;//ID комплекта
    public int kincr;//ID набора параметров комплекта для артикула
    public int clnum;//основная текстура
    public String anumb;//артикул, входящий в состав комплекта
    public int clnu1;//внутренняя текстура
    public int clnu2;//внешняя текстура
    public short kmain;//флаг основного элемента комплекта

    public Kompspc() {
    }


    public Kompspc(ResultSet rs) throws SQLException {

        kunic = rs.getInt("KUNIC");
        kincr = rs.getInt("KINCR");
        clnum = rs.getInt("CLNUM");
        anumb = rs.getString("ANUMB");
        clnu1 = rs.getInt("CLNU1");
        clnu2 = rs.getInt("CLNU2");
        kmain = rs.getShort("KMAIN");
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
