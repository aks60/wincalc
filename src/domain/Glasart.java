package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Спецификация групп заполнения
 */
public class Glasart implements ITSpecific {

    public int gnumb;//ID группы заполнения
    public String anumb;//артикул элемента
    public int clnum;//Текстура
    public short ctype;
    public int gunic;
    public double afric;

    public Glasart() {
    }


    public Glasart(ResultSet rs) throws SQLException {

        gnumb = rs.getInt("GNUMB");
        anumb = rs.getString("ANUMB");
        clnum = rs.getInt("CLNUM");
        ctype = rs.getShort("CTYPE");
        gunic = rs.getInt("GUNIC");
        afric = rs.getFloat("AFRIC");
    }

    public static ArrayList<Glasart> find(Constructive constructive, int gnumb, float afric) {

        ArrayList<Glasart> recordList = new ArrayList();
        for (Glasart record : constructive.glasartList) {
            if (gnumb == record.gnumb && record.afric == afric) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from GLASART" : "select * from PRO4_GLASART where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Glasart record = new Glasart(rs);
                constructive.glasartList.add(record);
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
