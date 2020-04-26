package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Cпецификация вариантов соединения
 */
public class Connspc implements ITSpecific{

    public int cunic;
    public int aunic;
    public String anumb;//артикул
    public int clnum;
    public short ctype;

    public Connspc() {
    }

    public  Connspc(ResultSet rs) throws SQLException {

        cunic = rs.getInt("CUNIC");
        aunic = rs.getInt("AUNIC");
        anumb = rs.getString("ANUMB");
        clnum = rs.getInt("CLNUM");
        ctype = rs.getShort("CTYPE");
    }

    public static ArrayList<Connspc> find(Constructive constructive, int cunic) {

        ArrayList<Connspc> recordList = new ArrayList();
        for (Connspc record : constructive.connspcList) {
            if (cunic == record.cunic) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from CONNSPC" : "select * from PRO4_CONNSPC where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Connspc item = new Connspc(rs);
                constructive.connspcList.add(item);
            }
        }
    }

    public int clnum() {
        return clnum;
    }

    public short ctype() {
        return ctype;
    }
}
