package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Параметры спецификаций составов
 */
public class Parvstm implements ITParam {

    public int psss;//ИД компонента состава
    public short pporn;//номер параметра в таблицах ввода параметров в программу
    public int pnumb;//номер параметра
    public int znumb;
    public int punic;
    public String ptext;//наименование значения параметра

    public Parvstm() {
    }

    public Parvstm(ResultSet rs) throws SQLException {

        psss = rs.getInt("PSSS");
        pporn = rs.getShort("PPORN");
        pnumb = rs.getInt("PNUMB");
        znumb = rs.getInt("ZNUMB");
        punic = rs.getInt("PUNIC");
        ptext = rs.getString("PTEXT");
    }

    public int pnumb() {
        return pnumb;
    }

    public int znumb() {
        return znumb;
    }

    public String ptext() {
        return ptext;
    }

    public static ArrayList<ITParam> find(Constructive constructive, int psss) {

        ArrayList<ITParam> recordList = new ArrayList();
        for (Parvstm record : constructive.parvstmList) {
            if (record.psss == psss) {
                recordList.add(record);
            }
        }
        return recordList;
    }

    //TODO НЕПОНЯТНО!!! При добавлении <" order by pnumb desc"> появлянтся ошибка калькуляции.
    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from PARVSTM" : "select * from PRO4_PARVSTM where REGION_ID=" + constructive.regionId + " order by pnumb desc";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Parvstm record = new Parvstm(rs);
                constructive.parvstmList.add(record);
            }
        }
    }
}
