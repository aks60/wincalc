package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;
import static domain.eArtdet.artikl_id;
import static domain.eArtdet.color_fk;
import static domain.eArtdet.id;
import static domain.eArtikl.code;
import static domain.eArtikl.id;
import static domain.eArtikl.up;
import static domain.eArtikl.values;
import static domain.eArtikl.virtualRec;
import java.util.HashMap;
import java.util.List;
import static java.util.stream.Collectors.toList;

public enum eColor implements Field {
    up("0", "0", "0", "Описание текстур", "COLSLST"),
    id("4", "10", "0", "Идентификатор", "CCODE"),
    name("12", "32", "1", "Название", "CNAME"),
    name2("12", "32", "1", "Название у поставщика", "CNAMP"),
    rgb("4", "10", "1", "Цвет отображения", "CVIEW"),
    coef1("8", "15", "1", "Ценовой коэф.основной", "CKOEF"),
    coef2("8", "15", "1", "Ценовой коэф.внутренний", "KOEF1"),
    coef3("8", "15", "1", "Ценовой коэф.внешний", "KOEF2"),
    suffix1("12", "8", "1", "Суффикс основной тестуры", "CMAIN"),
    suffix2("12", "8", "1", "Суффикс внутренний тестуры", "CINTO"),
    suffix3("12", "8", "1", "Суффикс внешний текстуры", "COUTS"),
    is_prod("16", "5", "1", "Для изделий", "CORDS"),
    orient("5", "5", "1", "Ориентация", "CORIE"),
    pain("5", "5", "1", "Покраска", "CTYPE"),
    colgrp_id("5", "5", "1", "Группа", "colgrp_id");
    //numb("4", "10", "1", "id", "CNUMB"),
    //cgrup("5", "5", "1", "группа", "CGRUP");    
    //cpict("-4", "80", "1", "null", "CPICT"),
    //xdepa("5", "5", "1", "null", "XDEPA"),
    //nouse("5", "5", "1", "null", "NOUSE"),
    //cprc1("8", "15", "1", "null", "CPRC1"),
    //cprc2("8", "15", "1", "null", "CPRC2");
    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());
    private static HashMap<Integer, Record> map = new HashMap();

    eColor(Object... p) {
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
            map.clear();
            query.stream().forEach(rec -> map.put(rec.getInt(id), rec));            
        }
        return query;
    }

    public static Record get(int id) {
        if (id == -3) {
            return virtualRec();
        }
        query();
        Record rec = map.get(id);
        return (rec == null) ? virtualRec() : rec;
    }
    
    public static Record find(int _id) {
        if (_id == -3) {
            return virtualRec();
        }
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(id) == _id).findFirst().orElse(up.newRecord());
        }
        Query recordList = new Query(values()).select(up, "where", id, "=", _id);
        return (recordList.isEmpty() == true) ? up.newRecord() : recordList.get(0);
    }

    public static List<Record> find2(int _colgrp_id) {

        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(colgrp_id) == _colgrp_id).collect(toList());
        }
        return new Query(values()).select(up, "where", colgrp_id, "=", _colgrp_id);
    }

    public static Record find3(int _color_fk) {
        if (_color_fk == -3) {
            return virtualRec();
        }
        if (Query.conf.equals("calc")) {
            if (_color_fk < 0) {
                return query().stream().filter(rec -> rec.getInt(colgrp_id) == _color_fk * -1).findFirst().orElse(up.newRecord());
            } else {
                return query().stream().filter(rec -> rec.getInt(id) == _color_fk).findFirst().orElse(up.newRecord());
            }
        }
        if (_color_fk < 0) {
            return new Query(values()).select("select first 1 * from " + up.tname() + " where " + colgrp_id.name() + " = " + (_color_fk * -1)).get(0);
        } else {
            return new Query(values()).select(up, "where", id, "=", _color_fk).get(0);
        }
    }

    public static Record virtualRec() {
        Record record = up.newRecord();
        record.setNo(id, -3);
        record.setNo(code, 33240);
        record.setNo(name, "Virtual");
        record.setNo(rgb, 15790320);
        return record;
    }

    public String toString() {
        return meta.descr();
    }
}
