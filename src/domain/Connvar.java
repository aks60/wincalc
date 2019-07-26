package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Варианты соединений
 */
public class Connvar {

    public int cconn;
    public int cunic;//ID соединения
    public short cprio;//приоритет
    public short ctype;//тип варианта
    public short cnext;//
    public short cmirr;//1 - использовать зеркально, 0 - нельзя использовать зеркально
    public String cname;//название варианта

    public Connvar() {
    }

    public Connvar(ResultSet rs) throws SQLException {

        cconn = rs.getInt("CCONN");
        cunic = rs.getInt("CUNIC");
        cprio = rs.getShort("CPRIO");
        ctype = rs.getShort("CTYPE");
        cnext = rs.getShort("CNEXT");
        cmirr = rs.getShort("CMIRR");
        cname = rs.getString("CNAME");
    }

    public static ArrayList<Connvar> find(Constructive constructive, int cconn) {

        ArrayList<Connvar> recordList = new ArrayList();
        for (Connvar record : constructive.connvarList) {
            if (cconn == record.cconn) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from CONNVAR" : "select * from PRO4_CONNVAR where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Connvar item = new Connvar(rs);
                constructive.connvarList.add(item);
            }
        }
    }
}
