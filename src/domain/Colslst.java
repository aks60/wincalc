package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Описание текстур
 */
public class Colslst {

    public int ccode;//код текстуры
    public String cname;//название текстуры
    public int cnumb;//цвет отображения
    public int cview;//цвет отображения
    public float ckoef;//ценовой коэффицент основной
    public float koef1;//ценовой коэффицент внутренний
    public float koef2;//ценовой коэффицент внешний
    public short cgrup;//группа

    public Colslst() {
    }

    public Colslst(ResultSet rs) throws SQLException {
         
        ccode = rs.getInt("CCODE");
        cname = rs.getString("CNAME");
        cnumb = rs.getInt("CNUMB");
        cview = rs.getInt("CVIEW");
        ckoef = rs.getFloat("CKOEF");
        koef1 = rs.getFloat("KOEF1");
        koef2 = rs.getFloat("KOEF2");
        cgrup = rs.getShort("CGRUP");
    }

    public static Colslst get(Constructive constructive, int cnumb) {
        return constructive.colslstMap.get(cnumb);
    }

    public static Colslst get2(Constructive constructive, int ccode) {
        return constructive.colslstMap2.get(ccode);
    }

    public static Colslst get3(Constructive constructive, String name) {
        return constructive.colslstMap3.get(name);
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from COLSLST" : "select * from PRO4_COLSLST where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                //constructive.colslstList.add(new Colslst(rs));
                Colslst obj =  new Colslst(rs);
                constructive.colslstMap.put(rs.getInt("CNUMB"), obj);
                constructive.colslstMap2.put(rs.getInt("CCODE"), obj);
                constructive.colslstMap3.put(rs.getString("CNAME"), obj);
            }
        }
    }
}
