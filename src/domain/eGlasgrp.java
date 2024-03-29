package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;
import java.util.ArrayList;

public enum eGlasgrp implements Field {
    up("0", "0", "0", "Группы заполнения", "GLASGRP"),
    id("4", "10", "0", "Идентификатор", "id"),
    name("12", "64", "1", "Название группы", "GNAME"),
    gap("8", "15", "1", "Зазор", "GZAZO"),
    depth("12", "128", "1", "Доступные толщины", "BFRIC");
    //artikl_id("4", "10", "1", "Ссылка", "artikl_id");
    //gnumb("4", "10", "1", "id группы", "GNUMB"),    
    //gdiff("8", "15", "1", "null", "GDIFF"),
    //pnump("5", "5", "1", "null", "PNUMP");
    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eGlasgrp(Object... p) {
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

    public static ArrayList<Record>  findAll() {
        if (Query.conf.equals("calc")) {
            return query();
        }
        return new Query(values()).select(up);
    }
    
    public static Record find(int glasgrpId) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(id) == glasgrpId).findFirst().orElse(up.newRecord());
        }
        Query recordList = new Query(values()).select(up, "where", id, "=", glasgrpId);
        return (recordList.isEmpty() == true) ? up.newRecord() : recordList.get(0);
    }
    
    public String toString() {
        return meta.descr();
    }
}
