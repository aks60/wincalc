package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Правила расчета
 */
public class Ruleclk {

    public String rname;//название правила
    public String anumb;//артикул
    public short rused;//тип материальных ценностей
    public float rkoef;//коэффициент
    public String rleng;//количество
    public String rcodm;//коды текстур позиции (основная)
    public String rcod1;//коды текстур позиции (внутренняя)
    public String rcod2;//коды текстур позиции (внешняя)
    public short rallp;//общее
    public short riskl;//для формы позиций
    public float rpric;//надбавка

    public Ruleclk() {
    }

    public Ruleclk(ResultSet rs) throws SQLException {

        rname = rs.getString("RNAME");
        anumb = rs.getString("ANUMB");
        rused = rs.getShort("RUSED");
        rkoef = rs.getFloat("RKOEF");
        rleng = rs.getString("RLENG");
        rcodm = rs.getString("RCODM");
        rcod1 = rs.getString("RCOD1");
        rcod2 = rs.getString("RCOD2");
        rallp = rs.getShort("RALLP");
        riskl = rs.getShort("RISKL");
        rpric = rs.getFloat("RPRIC");
    }


    public  static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from RULECLK" : "select * from PRO4_RULECLK where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ruleclk record = new Ruleclk(rs);
                constructive.ruleclkList.add(record);
            }
        }
    }
}
