package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Тариф. мат. цености
 */
public class Artsvst {

    public String anumb;//артикул
    public int clcod;//код_текстуры
    public int clnum;//??? Текстура
    public int cluni;
    public float clprc;//тариф основной текстуры
    public float knakl;//коэффициент накладных расходов
    public float clpr1;//тариф внутренний текстуры
    public float clpr2;//тариф внешний текстуры
    public float clpra;//тариф двухсторонний текстуры
    public float cminp;//минимальный тариф
    public short cways; //>>>"галочки по приоритетности текстур (основной, внутренней, внешней):
    //0 - нет галочек (по всем текстурам этот цвет не основной для материала),
    //1 - галочка на внутренней текстуре,
    //2 - галочка на внешней текстуре,
    //3 - галочки на внутренней и внешней текстурах,
    //4 - галочка на основной текстуре,
    //5 - галочки на основной и внутреней текстурах,
    //6 - галочки на основной и внешней текстурах,
    //7 - галочки на всех текстурах (по всем текстурам основной цвет)."

    public Artsvst() {
    }

    public Artsvst(ResultSet rs) throws SQLException {

        anumb = rs.getString("ANUMB");
        clcod = rs.getInt("CLCOD");
        clnum = rs.getInt("CLNUM");
        cluni = rs.getInt("CLUNI");
        clprc = rs.getFloat("CLPRC");
        knakl = rs.getFloat("KNAKL");
        clpr1 = rs.getFloat("CLPR1");
        clpr2 = rs.getFloat("CLPR2");
        clpra = rs.getFloat("CLPRA");
        cminp = rs.getFloat("CMINP");
        cways = rs.getShort("CWAYS");
    }

    public static ArrayList<Artsvst> find(Constructive constructive, String anumb) {

        ArrayList<Artsvst> recordList = new ArrayList();
        for (Artsvst record : constructive.artsvstList) {
            if (anumb.equals(record.anumb)) {
                if (record.clcod > 0) recordList.add(record);
            }
        }
        return recordList;
    }

    public static ArrayList<Artsvst> get(Constructive constructive, String anumb) {
        return constructive.artsvstMap.get(anumb);
    }

    public static Artsvst get2(Constructive constructive, String anumb) {
        return constructive.artsvstMap2.get(anumb);
    }

    public static void add(Constructive constructive, Artsvst record) {
        ArrayList<Artsvst> recordList = constructive.artsvstMap.get(record.anumb);
        if (recordList == null) {
            recordList = new ArrayList();
            constructive.artsvstMap.put(record.anumb, recordList);
        }
        recordList.add(record);
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from ARTSVST" : "select * from PRO4_ARTSVST where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Artsvst record = new Artsvst(rs);
                constructive.artsvstList.add(record);
                if (record.clcod > 0) constructive.artsvstMap2.put(record.anumb, record);
                add(constructive, record);
            }
        }
    }
}
