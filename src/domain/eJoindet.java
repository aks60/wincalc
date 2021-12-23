package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

public enum eJoindet implements Field {
    up("0", "0", "0", "Спецификация вар.соединения", "CONNSPC"),
    id("4", "10", "0", "Идентификатор", "id"),
    types("5", "5", "1", "Подбор текстуры", "CTYPE"),
    color_fk("4", "10", "1", "Ссылка", "CLNUM"),
    artikl_id("4", "10", "1", "Ссылка", "artikl_id"),
    joinvar_id("4", "10", "0", "Ссылка", "joinvar_id");
    //clnum("4", "10", "1", "текстура 0-Авто_подб 100000-Точн.подбор 1-. -ХХХ-ручн.парам.", "CLNUM"), //CLNUM=>COLOT.CNUMB
    //anumb("12", "32", "1", "Артикул", "ANUMB"). // ANUMB->ARTIKL.ANUMB  
    //cunic("4", "10", "1", "10_JOINING", "CUNIC"),
    //aunic("4", "10", "1", "ID_JOINDET", "AUNIC"),    
    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eJoindet(Object... p) {
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

    public static List<Record> find(int _id) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(joinvar_id) == _id).collect(toList());
        }
        return new Query(values()).select(up, "where", joinvar_id, "=", _id, "order by", id);
    }

    public String toString() {
        return meta.descr();
    }
}
