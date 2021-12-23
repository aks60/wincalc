package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

public enum eArtdet implements Field {
    up("0", "0", "0", "Тариф.мат.цености", "ARTSVST"),
    id("4", "10", "0", "Идентификатор", "id"),
    price_coeff("8", "15", "1", "Коэф.накладных расходов", "KNAKL"),
    cost_unit("8", "15", "1", "Тариф единицы измерения", "CLPRV"),
    cost_c1("8", "15", "1", "Тариф основной текстуры", "CLPRC"),
    cost_c2("8", "15", "1", "Тариф внутренний текстуры", "CLPR1"),
    cost_c3("8", "15", "1", "Тариф внешний текстуры", "CLPR2"),
    cost_c4("8", "15", "1", "Тариф двухсторонний текстуры", "CLPRA"),
    mark_c1("16", "5", "1", "Галочка основной текстуры", "mark_c1"),
    mark_c2("16", "5", "1", "Галочка внутренний текстуры", "mark_c2"),
    mark_c3("16", "5", "1", "Галочка внешний текстуры", "mark_c3"),
    cost_min("8", "15", "1", "Минимальный тариф", "CMINP"),
    artikl1("12", "32", "1", "Артикул склада", "ASKL1"),
    artikl2("12", "32", "1", "Артикул 1С", "ASKL2"),
    color_fk("4", "10", "1", "Ссылка на id_Группы или id_Текстуры", "color_fk"),
    artikl_id("4", "10", "0", "Артикул", "artikl_id");
    //anumb("12", "32", "1", "артикул", "ANUMB"),
    //clcod("4", "10", "1", "id техстуры", "CLCOD"),
    //clnum("4", "10", "1", "id группы текстуры", "CLNUM"),
    //cluni("4", "10", "1", "null", "CLUNI"),    
    //prefe("5", "5", "1", "Галочки по приоритетности ", "CWAYS"), //текстур (основной, внутренней, внешней):     
    //000-0 - нет галочек (по всем текстурам этот цвет не основной для материала), 
    //001-1 - галочка на внутренней текстуре, 
    //010-2 - галочка на внешней текстуре, 
    //011-3 - галочки на внутренней и внешней текстурах, 
    //100-4 - галочка на основной текстуре, 
    //101-5 - галочки на основной и внутреней текстурах, 
    //110-6 - галочки на основной и внешней текстурах, 
    //111-7 - галочки на всех текстурах (по всем текстурам основной цвет).       

    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eArtdet(Object... p) {
        meta.init(p);
    }

    public static Query query() {
        if (query.size() == 0) {
            query.select(up, "order by", id);
            Query.listOpenTable.add(query);
        }
        return query;
    }

    public MetaField meta() {
        return meta;
    }

    public Field[] fields() {
        return values();
    }

    public static List<Record> find(int _artikl_id) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(artikl_id) == _artikl_id).collect(toList());
        }
        return new Query(values()).select(up, "where", artikl_id, "=", _artikl_id, "order by", id);
    }

    public static Record find2(int _artikl_id) {
        if (_artikl_id == -3) {
            return record();
        }
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> rec.getInt(artikl_id) == _artikl_id).findFirst().orElse(record());
        }
        List<Record> record = new Query(values()).select("select first 1 * from " + up.tname() + " where " + artikl_id.name() + " = " + _artikl_id);
        return (record.size() == 0) ? record() : record.get(0);
    }

    public static Record record() {
        Record record = up.newRecord();
        record.setNo(id, -3);
        record.setNo(artikl_id, -3);
        record.setNo(color_fk, -3);
        return record;
    }

    public String toString() {
        return meta.descr();
    }
}
