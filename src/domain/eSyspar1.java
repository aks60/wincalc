package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;
import java.util.List;
import static java.util.stream.Collectors.toList;

public enum eSyspar1 implements Field {
    up("0", "0", "0", "Парамметры системы профилей", "PARSYSP"),
    id("4", "10", "0", "Идентификатор", "id"),
    text("12", "64", "1", "Значения параметра", "PTEXT"),
    params_id("4", "10", "0", "Ссылка", "PNUMB"),    
    fixed("16", "5", "1", "Закреплено", "PFIXX"), 
    systree_id("4", "10", "0", "Ссылка", "systree_id");
    //npp("5", "5", "1", "Нпп параметра", "PPORN"),
    //PNUMB("4", "10", "0", "Ссылка", "PNUMB"),
    //ZNUMB("4", "10", "1", "Параметр", "ZNUMB"), //пар. вводимые пользователем в системе профилей    

    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eSyspar1(Object... p) {
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

    public static List<Record> find(int _nuni) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(systree_id) == _nuni).collect(toList());
        }
        return new Query(values()).select(up, "where", systree_id, "=", _nuni);
    }

    public String toString() {
        return meta.descr();
    }
}
