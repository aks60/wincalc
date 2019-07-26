package domain;

import enums.ProfileSide;
import enums.TypeProfile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  Профили, системы профилей
 */
public class Sysproa {

    public int nuni;// ID  серии профилей
    public short aprio;//приоритет
    public String anumb;//артикул
    public short asets;//сторона
    public short atype;//тип профиля

    public Sysproa() {
    }

    public Sysproa(ResultSet rs) throws SQLException {

        nuni = rs.getInt("NUNI");
        aprio = rs.getShort("APRIO");
        anumb = rs.getString("ANUMB");
        asets = rs.getShort("ASETS");
        atype = rs.getShort("ATYPE");
    }

    public static ArrayList<Sysproa> find(Constructive constructive, int nuni) {

        ArrayList<Sysproa> sysproaList = new ArrayList();
        //цикл по сист. профилей
        for (Sysproa record : constructive.sysproaList) {
            if (nuni == record.nuni) {
                sysproaList.add(record);
            }
        }
        return sysproaList;
    }

    public static Sysproa find(Constructive constructive, int nuni, TypeProfile atype, ProfileSide asets) {

        HashMap<Short, Sysproa> hmPrio = new HashMap();
        for (Sysproa record : constructive.sysproaList) {
            if (nuni == record.nuni && atype.value == record.atype && (asets.value == record.asets || ProfileSide.Any.value == record.asets)) {
                hmPrio.put(record.aprio, record);
            }
        }
        short minLevel = 32767;
        for (Map.Entry<Short, Sysproa> entry : hmPrio.entrySet()) {

            if (entry.getKey() == 0) {
                return entry.getValue();
            }
            if (minLevel > entry.getKey()) {
                minLevel = entry.getKey();
            }
        }
        if (hmPrio.size() == 0) {
            return new Sysproa();
        }
        return hmPrio.get(minLevel);
    }

    public static Sysproa find(Constructive constructive, int nuni, TypeProfile atype) {

        HashMap<Short, Sysproa> hmPrio = new HashMap();
        for (Sysproa record : constructive.sysproaList) {
            if (nuni == record.nuni && atype.value == record.atype) {
                hmPrio.put(record.aprio, record);
            }
        }
        short minLevel = 32767;
        for (Map.Entry<Short, Sysproa> entry : hmPrio.entrySet()) {

            if (entry.getKey() == 0) {
                return entry.getValue();
            }
            if (minLevel > entry.getKey()) {
                minLevel = entry.getKey();
            }
        }
        if (hmPrio.size() == 0) {
            return new Sysproa();
        }
        return hmPrio.get(minLevel);
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        //String sql = constructive.fromPS ? "select * from SYSPROA" : "select * from PRO4_SYSPROA where REGION_ID=" + constructive.regionId + " ORDER BY ANUMB, APRIO";
        String sql = constructive.fromPS ? "select * from SYSPROA" : "select * from PRO4_SYSPROA where REGION_ID=" + constructive.regionId + " ORDER BY APRIO";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sysproa item = new Sysproa(rs);
                constructive.sysproaList.add(item);
            }
        }
    }
}
