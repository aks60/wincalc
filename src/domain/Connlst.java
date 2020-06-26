package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Соединения
 */
public class Connlst {

    public String cpref;//категория
    public String anum1;//артикул 1
    public String anum2;//артикул 2
    public int cconn;//ID соединения
    public String cname;//название соединения
    public short cvarf;//битовая маска: 0x100=256 - установлен флаг "Основное соединение". Смысл других бит пока неизвестен.
    public String cequv;//аналоги

    public Connlst() {
    }

    public Connlst(ResultSet rs) throws SQLException {

        cpref = rs.getString("CPREF");
        anum1 = rs.getString("ANUM1");
        anum2 = rs.getString("ANUM2");
        cconn = rs.getInt("CCONN");
        cname = rs.getString("CNAME");
        cvarf = rs.getShort("CVARF");
        cequv = rs.getString("CEQUV");
    }

    public static Connlst find(Constructive constructive, String anum1, String anum2) {

        for (Connlst record : constructive.connlstList) {
            if ((anum1.equals(record.anum1) && anum2.equals(record.anum2))) {
                return record;
            }
        }
        return new Connlst();
    }

    public static Connlst find(Constructive constructive, String cequv) {

        for (Connlst record : constructive.connlstList) {
            if (cequv.equals(record.cequv) && (record.cvarf & 0x100) != 0) {
                return record;
            }
        }
        return new Connlst();
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from CONNLST" : "select * from PRO4_CONNLST where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Connlst item = new Connlst(rs);
                constructive.connlstList.add(item);
            }
        }
    }
}
