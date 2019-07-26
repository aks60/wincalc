package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Дерево системы профилей
 */
public class Sysprof {

    public int nuni;//ID ветки дерева
    public short nlev;//номер ступеньки в дереве
    public int npar;//ID материнского профиля (SYSPROF.NUNI)
    public String anumb;//заполнение по умолчанию
    public String text;//наименование ветки дерева
    public float koef;//коэффициент рентабельности
    public String zsize;//доступные толщины стеклопакетов
    public String ccode;//доступные основные текстуры
    public String ccod1;//доступные внешние текстуры
    public String ccod2;//доступные внешние текстуры
    public short tcalk;//спецификация профилей и пакетов для сборки
    public short tview;//примечание
    public short tdrit;//режим проволоки в системе
    public String ngrup;//система доступная для групп пользователя
    public String npref;//замена / код
    public short pnump;
    public short typew;//1 - окно; 4,5 - двери
    public short pnumn;

    public Sysprof() {
    }

    public Sysprof(ResultSet rs) throws SQLException {

        nuni = rs.getInt("NUNI");
        nlev = rs.getShort("NLEV");
        npar = rs.getInt("NPAR");
        anumb = rs.getString("ANUMB");
        text = rs.getString("TEXT");
        koef = rs.getFloat("KOEF");
        zsize = rs.getString("ZSIZE");
        ccode = rs.getString("CCODE");
        ccod1 = rs.getString("CCOD1");
        ccod2 = rs.getString("CCOD2");
        tcalk = rs.getShort("TCALK");
        tview = rs.getShort("TVIEW");
        tdrit = rs.getShort("TDRIT");
        ngrup = rs.getString("NGRUP");
        npref = rs.getString("NPREF");
        pnump = rs.getShort("PNUMP");
        typew = rs.getShort("TYPEW");
        pnumn = rs.getShort("PNUMN");
    }

    public static  Sysprof get(Constructive constructive, int nuni) {
        return constructive.sysprofMap.get(nuni);
    }

    public static void load(Constructive constructive, Statement stmt) throws SQLException {
        String sql = constructive.fromPS ? "select * from SYSPROF" : "select * from PRO4_SYSPROF where REGION_ID=" + constructive.regionId;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sysprof record = new Sysprof(rs);
                constructive.sysprofMap.put(record.nuni, record);
            }
        }
    }
}
