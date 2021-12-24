package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;
import java.util.List;
import static java.util.stream.Collectors.toList;

public enum eElemdet implements Field {
    up("0", "0", "0", "Спецификация составов", "VSTASPC"),
    id("4", "10", "0", "Идентификатор", "id"),
    types("5", "5", "1", "Подбор текстуры", "CTYPE"), // 0 - указана вручную 11 - профиль 31 - основная
    color_fk("4", "10", "1", "Ссылка", "CLNUM"),
    artikl_id("4", "10", "1", "Ссылка", "artikl_id"),
    element_id("4", "10", "0", "Ссылка", "element_id");
    //clnum("4", "10", "1", "текстура 0-Авто_подб 100000-Точн.подбор 1-. -ХХХ-ручн.парам.", "CLNUM"); //CLNUM=>COLOT.CNUMB
    //anumb("12", "32", "1", "Артикул", "ANUMB"). // ANUMB->ARTIKL.ANUMB  
    //vnumb("4", "10", "1", "ELEMENT_ID", "VNUMB"),
    //aunic("4", "10", "1", "ID_ELEMDET", "AUNIC"),   
    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eElemdet(Object... p) {
        meta.init(p);
    }

    public MetaField meta() {
        return meta;
    }

    public Field[] fields() {
        return values();
    }

    public static List<Record> find(int _id) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(element_id) == _id).collect(toList());
        }
        return new Query(values()).select(up, "where", element_id.name(), "=", _id);
    }

    public static Query query() {
        if (query.size() == 0) {
            query.select(up, "order by", id);
            Query.listOpenTable.add(query);
        }
        return query;
    }

    public String toString() {
        return meta.descr();
    }
}
