package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;
import enums.UseSide;
import enums.UseArtiklTo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum eSysprof implements Field {
    up("0", "0", "0", "Профили системы", "SYSPROA"),
    id("4", "10", "0", "Идентификатор", "id"),
    prio("4", "10", "1", "Приоритет", "APRIO"), //0 - 1 -  2 -  3 -  4 -  5 -  6 - 10 -  1000 - "
    use_type("5", "5", "1", "Тип использования", "ATYPE"),
    use_side("5", "5", "1", "Сторона использования", "ASETS"),
    artikl_id("4", "10", "0", "Артикул", "artikl_id"),
    systree_id("4", "10", "0", "Ссылка", "systree_id");
    //aunic("4", "10", "1", "ИД компонента", "AUNIC"),
    //nuni("4", "10", "1", "ID  серии профилей", "NUNI"),    
    //anumb("12", "32", "1", "артикул", "ANUMB"),
    //cflag("5", "5", "1", "Свои текстуры", "CFLAG");
    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eSysprof(Object... p) {
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
            query.select(up, "order by", prio);
            Query.listOpenTable.add(query);
        }
        return query;
    }

    public static ArrayList<Record> find(int _nuni) {
        if (Query.conf.equals("calc")) {
            ArrayList<Record> sysproaList = new ArrayList();
            query().stream().filter(rec -> _nuni == rec.getInt(systree_id)).forEach(rec -> sysproaList.add(rec));
            return sysproaList;
        }
        return new Query(values()).select(up, "where", systree_id, "=", _nuni, "order by", prio);
    }

    public static Record find2(int _nuni, UseArtiklTo _type) {
        if (_nuni == -3) {
            return record(_type.id);
        }
        if (Query.conf.equals("calc")) {
            HashMap<Integer, Record> mapPrio = new HashMap();
            query().stream().filter(rec -> rec.getInt(systree_id) == _nuni && rec.getInt(use_type) == _type.id)
                    .forEach(rec -> mapPrio.put(rec.getInt(prio), rec));
            int minLevel = 32767;
            for (Map.Entry<Integer, Record> entry : mapPrio.entrySet()) {

                if (entry.getKey() == 0) { //если нулевой приоритет
                    return entry.getValue();
                }
                if (minLevel > entry.getKey()) { //поднимаемся вверх по приоритету
                    minLevel = entry.getKey();
                }
            }
            if (mapPrio.size() == 0) {
                return null;
            }
            return mapPrio.get(minLevel);
        }
        Query recordList = new Query(values()).select("select first 1 * from " + up.tname() + " where "
                + systree_id.name() + " = " + _nuni + " and " + use_type.name() + " = " + _type.id + " order by " + prio.name());
        return (recordList.isEmpty() == true) ? null : recordList.get(0);
    }

    public static Record find3(int _id) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(id) == _id).findFirst().orElse(up.newRecord());
        }
        Query recordList = new Query(values()).select(up, "where", id, "=", _id);
        return (recordList.isEmpty() == true) ? up.newRecord() : recordList.get(0);
    }  

    public static Record find4(int nuni, int id, UseSide us1) {
        if (nuni == -3) {
            return record(id);
        }
        Record record = query().stream().filter(rec -> rec.getInt(systree_id) == nuni && rec.getInt(use_type) == id
                && UseSide.MANUAL.id != rec.getInt(use_side) && us1.id == rec.getInt(use_side)).findFirst().orElse(null);
        if (record != null) {
            return record;
        }
        record = query().stream().filter(rec -> rec.getInt(systree_id) == nuni && rec.getInt(use_type) == id
                && UseSide.MANUAL.id != rec.getInt(use_side) && UseSide.ANY.id == rec.getInt(use_side)).findFirst().orElse(up.newRecord());
        
        return record;
    }

    public static Record find5(int nuni, int id, UseSide us1, UseSide us2) {
        if (nuni == -3) {
            return record(id);
        }
        Record record = query().stream().filter(rec -> rec.getInt(systree_id) == nuni && rec.getInt(use_type) == id
                && UseSide.MANUAL.id != rec.getInt(use_side) && us1.id == rec.getInt(use_side)).findFirst().orElse(null);
        if (record != null) {
            return record;
        }
        record = query().stream().filter(rec -> rec.getInt(systree_id) == nuni && rec.getInt(use_type) == id
                && UseSide.MANUAL.id != rec.getInt(use_side) && us2.id == rec.getInt(use_side)).findFirst().orElse(null);
        if (record != null) {
            return record;
        }
        record = query().stream().filter(rec -> rec.getInt(systree_id) == nuni && rec.getInt(use_type) == id
                && UseSide.MANUAL.id != rec.getInt(use_side) && UseSide.ANY.id == rec.getInt(use_side)).findFirst().orElse(up.record(id));
        
        return record;
    }


    public static Record record(int typeId) {

        Record record = up.newRecord();
        record.setNo(id, -3);
        record.setNo(use_type, typeId);
        record.setNo(use_side, UseSide.ANY.id);
        record.setNo(systree_id, -3);
        record.setNo(artikl_id, -3);
        return record;
    }

    public String toString() {
        return meta.descr();
    }
}
