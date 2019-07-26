package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Валюта
 */
public class Correnc {

    public String cname;//название
    public String cnumb;//ID валюты
    public float ckurs;//кросс курс

    public Correnc() {
    }

    public Correnc(ResultSet rs) throws SQLException {


        cname = rs.getString("CNAME");
        cnumb = rs.getString("CNUMB");
        ckurs = rs.getFloat("CKURS");
    }

    public static Correnc find(Constructive constructive, String cnumb) {
        for (Correnc currenc : constructive.correncList) {
            if (currenc.cnumb.equals(cnumb))
                return currenc;
        }
        return null;
    }

    public static Correnc get(Constructive constructive, int key) {
        return constructive.correncMap.get(String.valueOf(key));
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from CORRENC" : "select * from PRO4_CORRENC where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Correnc record = new Correnc(rs);
                constructive.correncList.add(record);
                constructive.correncMap.put(record.cnumb, record);
            }
        }
    }
}
