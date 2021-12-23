package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;
import java.util.List;
import java.util.stream.Collectors;

public enum eElement implements Field {
    up("0", "0", "0", "Вставки", "VSTALST"),
    id("4", "10", "0", "Идентификатор", "id"),
    name("12", "64", "1", "Наименование", "VNAME"),
    typset("4", "10", "1", "Тип состава", "typset"),
    signset("12", "32", "1", "Признак состава", "VSIGN"),
    markup("8", "15", "1", "Наценка %", "VPERC"),
    todef("16", "5", "1", "Ставить по умолчанию", "todef"),
    toset("16", "5", "1", "Установка обязательности", "toset"),
    series_id("4", "10", "1", "Ссылка(серия)", "series_id"),
    artikl_id("4", "10", "1", "Ссылка", "artikl_id"),
    elemgrp_id("4", "10", "0", "Ссылка", "elemgrp_id");
    //series("12", "32", "1", "Для серии", "VLETS"),
    //vsets("5", "5", "1", "Установка обязательности", "VSETS"), //0 -умолчание нет, обязательно нет 1 -умолчание да, обязательно да, 2 -умолчание да, обязательно нет"
    //vtype("12", "16", "1", "Тип состава (1 - внутренний, 5 - состав_С/П)", "VTYPE"),
    //anumb("12", "32", "1", "артикул", "ANUMB"),    
    //atypm("5", "5", "1", "тип артикула  1 - профили  5 - заполнение", "ATYPM"),    
    //vnumb("4", "10", "0", "ID", "VNUMB"),
    //vpict("12", "64", "1", "чертеж состава", "VPICT"),
    //vgrup("12", "32", "1", "группа", "VGRUP")    
    //xdepa("5", "5", "1", "null", "XDEPA"),
    //vdiff("8", "15", "1", "null", "VDIFF"),
    //pnump("5", "5", "1", "null", "PNUMP"),
    //vcomp("5", "5", "1", "null", "VCOMP");
    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eElement(Object... p) {
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

    public static List<Record> find(int series2_id) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> series2_id == rec.getInt(series_id) && rec.getInt(todef) > 0).collect(Collectors.toList());
        }
        return new Query(values()).select(up, "where", series_id, "=", series2_id, "and", todef, "> 0");
    }

    public static List<Record> find2(int artikl2_id) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> artikl2_id == rec.getInt(artikl_id) && rec.getInt(todef) > 0).collect(Collectors.toList());
        }
        return new Query(values()).select(up, "where", artikl_id, "=", artikl2_id, "and", todef, "> 0");        
    }
    
    public static List<Record> find3(int artikl2_id, int series2_id) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> (artikl2_id == rec.getInt(artikl_id)
                    || series2_id == rec.getInt(series_id)) && rec.getInt(todef) > 0).collect(Collectors.toList());
        }
        return new Query(values()).select(up, "where (", artikl_id, "=", artikl2_id, "or", series_id, "=", series2_id, ") and", todef, "> 0");
    }
    
    public String toString() {
        return meta.descr();
    }
}
