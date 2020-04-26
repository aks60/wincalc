package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  Группы заполнения
 */
public class Glasgrp {

    public String gname;//название группы
    public int gnumb;//ID группы заполнения
    public double gzazo;//зазор

    public Glasgrp() {
    }


    public Glasgrp(ResultSet rs) throws SQLException {

        gname = rs.getString("GNAME");
        gnumb = rs.getInt("GNUMB");
        gzazo = rs.getFloat("GZAZO");
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from GLASGRP" : "select * from PRO4_GLASGRP where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                constructive.glasgrpList.add(new Glasgrp(rs));
            }
        }
    }
}
