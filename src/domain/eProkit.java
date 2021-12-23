package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import static domain.eKitdet.values;

public enum eProkit implements Field {
    up("0", "0", "0", "Комплекты изделия", "EMPTY"),
    id("4", "10", "0", "Идентификатор", "id"),    
    numb("8", "15", "1", "Количество", "numb"),
    width("8", "15", "1", "Длина", "width"),
    height("8", "15", "1", "Ширина", "height"),
    color1_id("4", "10", "1", "Ссылка", "color1_id"),
    color2_id("4", "10", "1", "Ссылка", "color2_id"),
    color3_id("4", "10", "1", "Ссылка", "color3_id"),
    angl1("8", "15", "1", "Угол", "angl1"),
    angl2("8", "15", "1", "Угол", "angl2"),
    flag("5", "5", "1", "Флаг", "flag"), //Основного элемента комплекта
    artikl_id("4", "10", "0", "Артикл", "artikl_id"),
    proprod_id("4", "10", "0", "Изделие", "proprod_id");
    
    //name("12", "64", "1", "Название комплекта", "name"),
    //type("5", "5", "1", "Флаг", "type"),
    //categ("5", "5", "1", "Флаг", "categ"),
   
    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eProkit(Object... p) {
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

    public String toString() {
        return meta.descr();
    }    
}
