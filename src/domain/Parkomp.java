package domain;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Параметры комплектов
 */
public class Parkomp {

    public int psss;//ИД компонента комплекта
    public short pporn;
    public int pnumb;//номер параметра
    public int znumb;
    public int punic;
    public String ptext;//наименование значения параметра

    public Parkomp() {
    }

    public Parkomp(ResultSet rs) throws SQLException {
         
        psss = rs.getInt("PSSS");
        pporn = rs.getShort("PPORN");
        pnumb = rs.getInt("PNUMB");
        znumb = rs.getInt("ZNUMB");
        punic = rs.getInt("PUNIC");
        ptext = rs.getString("PTEXT");
    }
}
