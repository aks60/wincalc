package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Ограничения сторон для спецификации фурнитуры
 */
public class Furnles {
     
    public int fincs;
    public int funic;//ID фурнитурного набора
    public short lnumb;//номер стороны (1 - нижняя, 3 - верхняя, 2 - правая, 4 - левая, 5 - нижняя(для изделия формы трапеции)
    public float lminl;//мин. длина, мм
    public float lmaxl;//макс. длина, мм

    public Furnles() {
    }

    public Furnles(ResultSet rs) throws SQLException {
         
        fincs = rs.getInt("FINCS");
        funic = rs.getInt("FUNIC");
        lnumb = rs.getShort("LNUMB");
        lminl = rs.getFloat("LMINL");
        lmaxl = rs.getFloat("LMAXL");
    }

    public static ArrayList<Furnles> find(Constructive constructive, int fincs) {

        ArrayList<Furnles> recordList = new ArrayList();
        for (Furnles record : constructive.furnlesList) {
            if (fincs == record.fincs) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from FURNLES" : "select * from PRO4_FURNLES where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Furnles record = new Furnles(rs);
                constructive.furnlesList.add(record);
            }
        }
    }
}
