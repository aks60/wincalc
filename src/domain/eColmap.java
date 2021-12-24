package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;
import static domain.eColor.colgrp_id;
import static domain.eColor.id;
import static domain.eColor.up;
import static domain.eColor.values;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static domain.eColor.virtualRec;

public enum eColmap implements Field {
    up("0", "0", "0", "Парметры текстур", "PARCOLS"),
    id("4", "10", "0", "Идентификатор", "id"),    
    colgrp_id("4", "10", "1", "Группа соответствия цветов", "PNUMB"),
    joint("16", "5", "1", "Параметр соединений", "joint"),
    elem("16", "5", "1", "Параметр составов", "elem"),
    glas("16", "5", "1", "Параметр стеклопакетов", "glas"),
    furn("16", "5", "1", "Параметр фурнитуры", "furn"),
    otkos("16", "5", "1", "Параметр откосов", "otkos"),
    komp("16", "5", "1", "Параметр комплектов", "PKOMP"),
    color_id1("4", "10", "1", "Текстура профиля", "color_id1"),
    color_id2("4", "10", "1", "Цвет элемента", "color_id2");
    //text("12", "64", "1", "Значения параметра", "PTEXT"),
    //npp("5", "5", "1", "Нпп параметра", "PPORN"),
    //numb("4", "10", "1", "Параметр", "ZNUMB"), //пар. вводимые пользователем в системе профилей

    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eColmap(Object... p) {
        meta.init(p);
    }

    public MetaField meta() {
        return meta;
    }

    public Field[] fields() {
        return values();
    }

    public static Query query() {
        if (query.size() == 0) {
            query.select(up, "order by", id);
            Query.listOpenTable.add(query);
        }
        return query;
    }

    public static List<Record> find(int colorID) {

        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(color_id1) == colorID).collect(toList());
        }
        return new Query(values()).select(up, "where", color_id1, "=", colorID);
    }
    
    public static List<Record> find2(int colgrpID) {

        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(colgrp_id) == colgrpID).collect(toList());
        }
        return new Query(values()).select(up, "where", colgrp_id, "=", colgrpID);
    }
    
    public static List<Record> find3(int colorID, int colgrpID) {

        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(color_id1) == colorID && rec.getInt(colgrp_id) == Math.abs(colgrpID)).collect(toList());
        }
        return new Query(values()).select(up, "where", color_id1, "=", colorID, "and", colgrp_id, "=", Math.abs(colgrpID));
    }

    public String toString() {
        return meta.descr();
    }
}
