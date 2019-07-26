package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Комплекты
 */
public class Komplst {

    public String kname;//название комплекта (для пользователя)
    public int kunic;//ID комплекта
    public short ktype;
    public String anumb;//артикул, получаемый в случае использования "скатки" или ламинации
    public int clnum;//текстура скатанного или ламинированного артикула
    public short khide;//флаг "Скрыт". Устанавливается для запрета использования комплекта
    public String kpref;//категория
    /*public float aqtyk;
    public String kpict;
    public String kgrup;
    public short xdepa;
    public float kdiff;
    public short pnump;
    public short gnumb;
    public String ksize;*/

    public Komplst() {
    }


    public Komplst(ResultSet rs) throws SQLException {


        kname = rs.getString("KNAME");
        kunic = rs.getInt("KUNIC");
        ktype = rs.getShort("KTYPE");
        anumb = rs.getString("ANUMB");
        clnum = rs.getInt("CLNUM");
        khide = rs.getShort("KHIDE");
        kpref = rs.getString("KPREF");
        //aqtyk = rs.getFloat("AQTYK");
        //kpict = rs.getString("KPICT");
        //kgrup = rs.getString("KGRUP");
        //xdepa = rs.getShort("XDEPA");
        //kdiff = rs.getFloat("KDIFF");
        //pnump = rs.getShort("PNUMP");
        //gnumb = rs.getShort("GNUMB");
        //ksize = rs.getString("KSIZE");
    }

    public static ArrayList<Komplst> find(Constructive constructive, String anumb) {

        ArrayList<Komplst> recordList = new ArrayList();
        for (Komplst record : constructive.komplstList) {
            if (anumb.equals(record.anumb)) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from KOMPLST" : "select * from PRO4_KOMPLST where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Komplst record = new Komplst(rs);
                constructive.komplstList.add(record);
            }
        }
    }
}
