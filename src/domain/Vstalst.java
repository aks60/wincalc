package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Cоставы
 */
public class Vstalst {

    public String anumb;//артикул
    public String vname;//наименование состава
    public int vnumb;//ИД состава
    public short atypm;//тип артикула
    public short vtype;//тип состава (1 - внутренний, 5 - состав_С/П)
    public short vsets;//установка обязательности
    public String vlets;//для серии (из ARTIKLS.ASERI)

    private static int indexField = 0;
    private boolean ps3 = true;

    public Vstalst() {
    }

    public Vstalst(ResultSet rs) throws SQLException {

        anumb = rs.getString("ANUMB");
        vname = rs.getString("VNAME");
        vnumb = rs.getInt("VNUMB");
        atypm = rs.getShort("ATYPM");
        if (Constructive.fromPS3 == false) {
            vtype = rs.getShort("VTYPE");
        } else {
            vtype = 1;
        }
        vsets = rs.getShort("VSETS");
        vlets = rs.getString("VLETS");
    }

    public static ArrayList<Vstalst> find(Constructive constructive, String anumb, String vlets) {

        ArrayList<Vstalst> recordList = new ArrayList();
        for (Vstalst record : constructive.vstalstList) {
            if (anumb.equals(record.anumb) && (vlets.equals(record.vlets) || record.vlets.equals("-"))) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static ArrayList<Vstalst> find(Constructive constructive, String anumb) {

        ArrayList<Vstalst> recordList = new ArrayList();
        for (Vstalst record : constructive.vstalstList) {
            if (anumb.equals(record.anumb) && record.vsets > 0) {  //&& record.anumb.charAt(0) != '@') {
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static ArrayList<Vstalst> find2(Constructive constructive, String aseri) {

        ArrayList<Vstalst> recordList = new ArrayList();
        for (Vstalst record : constructive.vstalstList) {
            if (aseri.equals(record.vlets) && record.vsets > 0) { // && record.anumb.charAt(0) != '@') {
                recordList.add(record);
            }
        }
        return recordList;
    }

    /*public static Vstalst find(Constructive constructive, String anumb, int atypm, int vtype) {

        for (Vstalst record : constructive.vstalstList) {
            if (anumb.equals(record.anumb) && atypm == record.atypm && record.vtype == vtype) {
                return record;
            }
        }
        return null;
    }*/
    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from VSTALST" : "select * from PRO4_VSTALST where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vstalst record = new Vstalst(rs);
                constructive.vstalstList.add(record);
            }
        }
    }
}
