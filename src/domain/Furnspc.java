package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Спецификация фурнитуры
 */
public class Furnspc  implements ITSpecific {
     
    public int fincs;//ID зависимого фурнитурного набора из  FINCB  по данной фурнитуре
    public int funic;//ID фурнитурного набора (из FURNLST.FUNIC )
    public String anumb;//артикул материала или слово "НАБОР".
    public int fincb;//ссылка на параметры и на зависимую/вложенную спецификацию фурнитуры
    public int clnum;//текстура (COLSLST.CNUMB). Но если это НАБОР, то тут FURNSPC.FUNIC набора.
    public short ctype;//вариант подбора основной текстуры
    public short fleve;//тип спецификации (1 - основная, 2 - зависимая, 3 - вложенная)

    public Furnspc() {
    }

    public Furnspc(ResultSet rs) throws SQLException {
         
        fincs = rs.getInt("FINCS");
        funic = rs.getInt("FUNIC");
        anumb = rs.getString("ANUMB");
        fincb = rs.getInt("FINCB");
        clnum = rs.getInt("CLNUM");
        ctype = rs.getShort("CTYPE");
        fleve = rs.getShort("FLEVE");
    }

    public int clnum() {
        return clnum;
    }

    public short ctype() {
        return ctype;
    }

    public static ArrayList<Furnspc> find(Constructive constructive, int funic) {

        ArrayList<Furnspc> recordList = new ArrayList();
        for (Furnspc record : constructive.furnspcList) {
            if (funic == record.funic) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static ArrayList<Furnspc> find2(Constructive constructive, int fincb) {

        ArrayList<Furnspc> recordList = new ArrayList();
        for (Furnspc record : constructive.furnspcList) {
            if (fincb == record.fincs) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static ArrayList<Furnspc> find(Constructive constructive, String fincb) {

        ArrayList<Furnspc> recordList = new ArrayList();
        for (Furnspc record : constructive.furnspcList) {
            if (fincb.equals(record.funic)) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from FURNSPC" : "select * from PRO4_FURNSPC where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Furnspc record = new Furnspc(rs);
                constructive.furnspcList.add(record);
            }
        }
    }
}
