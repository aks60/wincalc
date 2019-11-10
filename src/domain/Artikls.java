package domain;

import enums.ParamJson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Материальные цености
 */
public class Artikls {

    public String anumb;//артикул
    public short atypm;//главный тип
    public short atypp;//подтип артикула
    public int munic;//группа материальных ценостей
    public int cnumb;//ID валюты
    public int nunicIwin;
    public String amain;//артикул аналога
    public String aname;//название
    public String atech;//технологический код контейнера
    public String aseri;//серия
    public float asizn;//размер технологический
    public short atypi;//ед.измерения
    public float aheig;//ширина
    public float afric;//толщина
    public float asizb;//размер В мм
    public float amass;//удельный вес
    public String agrup;//группа печати
    public float akoef;//ценовой коэффицент
    public float aouts;//% отхода
    public int sunic;//ID системы (см. табл. SYSSIZE)
    public int cnumt;//кросс-курс валюты для неосновных текстур (внутренняя, внешняя, двухсторонняя)

    public Artikls() {
    }

    public Artikls(ResultSet rs, boolean fromPS) throws SQLException {

        anumb = rs.getString("ANUMB");
        atypm = rs.getShort("ATYPM");
        atypp = rs.getShort("ATYPP");
        munic = rs.getInt("MUNIC");
        cnumb = rs.getInt("CNUMB");
        if (fromPS == false) nunicIwin = rs.getInt("NUNIC_IWIN");
        amain = rs.getString("AMAIN");
        aname = rs.getString("ANAME");
        atech = rs.getString("ATECH");
        aseri = rs.getString("ASERI");
        asizn = rs.getFloat("ASIZN");
        atypi = rs.getShort("ATYPI");
        aheig = rs.getFloat("AHEIG");
        afric = rs.getFloat("AFRIC");
        asizb = rs.getFloat("ASIZB");
        amass = rs.getFloat("AMASS");
        agrup = rs.getString("AGRUP");
        akoef = rs.getFloat("AKOEF");
        aouts = rs.getFloat("AOUTS");
        sunic = rs.getInt("SUNIC");
        cnumt = rs.getInt("CNUMT");
    }

    public static Artikls get(Constructive constructive, String anumb, boolean analog) {

        Artikls art = constructive.artiklsMap.get(anumb);
        if (analog == true && art.amain != null && art.amain.isEmpty() == false) {
            Artikls art2 = constructive.artiklsMap.get(art.amain);
            return art2;
        }
        return art;
    }

    public static Artikls get(Constructive constructive, HashMap<ParamJson, Object> hmParam) {

        Object key = hmParam.get(ParamJson.nunic_iwin);
        if (key == null) return null;
        if (constructive.fromPS == true) {
            return get(constructive, key.toString(), false);
        }
        return constructive.artiklsMap2.get(Integer.valueOf(key.toString()));
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {

        String sql = constructive.fromPS ? "select * from ARTIKLS" : "select * from PRO4_ARTIKLS where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Artikls record = new Artikls(rs, constructive.fromPS);
                constructive.artiklsList.add(record);
                constructive.artiklsMap.put(record.anumb, record);
                constructive.artiklsMap2.put(record.nunicIwin, record);
            }
        }
    }
}
