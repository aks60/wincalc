package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Группа материальных ценостей
 */
public class Grupart {
     
    public String mpref;//категория группы МЦ
    public String mname;//название группы МЦ
    public int munic;//ID группы МЦ
    public float mkoef;//Ценовой коэффицент

    public Grupart() {
    }

    public Grupart(ResultSet rs) throws SQLException {
         
        mpref = rs.getString("MPREF");
        mname = rs.getString("MNAME");
        munic = rs.getInt("MUNIC");
        mkoef = rs.getFloat("MKOEF");
    }

    public static ArrayList<Grupart> find(Constructive constructive, int munic) {

        ArrayList<Grupart> recordList = new ArrayList();
        for (Grupart record : constructive.grupartList) {
            if (munic == record.munic) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from GRUPART" : "select * from PRO4_GRUPART where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Grupart record = new Grupart(rs);
                constructive.grupartList.add(record);
            }
        }
    }
}
