package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;
import java.util.List;
import static java.util.stream.Collectors.toList;

public enum eParams implements Field {
    up("0", "0", "0", "Список параметров", "PARLIST"),
    id("4", "10", "0", "Идентификатор", "id"),
    text("12", "64", "1", "Название или значения параметра", "PNAME"),
    joint("16", "5", "1", "Параметры соединений", "PCONN"),
    elem("16", "5", "1", "Парметры составов", "PVSTA"),
    glas("16", "5", "1", "Параметры стеклопакетов", "PGLAS"),
    furn("16", "5", "1", "Параметры фурнитуры", "PFURN"),
    otkos("16", "5", "1", "Параметры откосов", "POTKO"),
    kits("16", "5", "1", "Парметры комплектов", "PKOMP"),
    color("16", "5", "1", "Парметры текстур", "PCOLL"),
    label("12", "32", "1", "Надпись на эскизе", "PMACR"),
    prog("16", "5", "1", "Системные (вшитые в систему)", "VPROG"),
    params_id("4", "10", "1", "Владелец", "params_id");
    //grup("4", "10", "1", "Группа", "PNUMB"), 
    //numb("4", "10", "1", "Нпп в группе", "ZNUMB"),    
    //[INS, 603, -603, null, null, 0, 0, 0, 0, 0, 0, null, null, null]
    //npp("5", "5", "1", "Номер параметра", "PPORN"),
    //ptype("16", "5", "1", "Количество тысяч par1", "PTYPF"),
    //pmacr("12", "32", "1", "null", "PMACR"),
    //xdepa("5", "5", "1", "null", "XDEPA"),
    //psize("12", "96", "1", "null", "PSIZE");

    //ПОЯСНЕНИЯ !!!
    //npp -  Присваивается автоматически, возможно поменять вручную.  В рамках одного группы одноименных номеров быть не может.  Возможно менять вручную если такой номер не занят
    //par1 - par1 < 0 - параметры вводимые пользователем, par1 > 0 - параметры, зашитые в программу
    //par2 - Номер параметра или значения: -1 - параметр не имеет определенного в таблицах настройки значения,  0 - наименование параметра, > 0  - значение параметра 
    //types - Тип параметра, с znum = -1 : -1 -  значение для с znum > 0  0 -   значение для с znum = 0  1 - параметры спецификаций соединений 2 - параметры спецификаций соединений 3 - параметры спецификаций соединений 4 - параметры спецификаций соединений 7 - параметры комплектов 8 - параметры комплектов 9 - параметры комплектов 11 - параметры соединений 12 - параметры соединений 13 - параметры групп заполнения 14 - параметры заполнения 15 - параметры заполнения 21 - параметры фурнитуры 24 -параметры спецификаций фурнитуры 25 - 31 - параметры составов профилей 33 - параметры  спецификаций составов профилей 34 - параметры спецификаций составов профилей 37 - параметры составовстеклопакетов 38 - параметры спецификаций составов стеклопакетов 39 - параметры спецификаций составов стеклопакетов 40 - "
    //
    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eParams(Object... p) {
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
            query.select(up, "order by", params_id, ",", text);
            Query.listOpenTable.add(query);
        }
        return query;
    }

    public static Record find(int _id) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> _id == rec.getInt(id)).findFirst().orElse(up.newRecord());
        }
        Query recordList = new Query(values()).select(up, "where", id, "=", _id);
        return (recordList.isEmpty() == true) ? up.newRecord() : recordList.get(0);
    }

    public static List<Record> find2(int _params_id) {
        if (Query.conf.equals("calc")) {
            return query().stream().filter(rec -> _params_id == rec.getInt(params_id)).collect(toList());
        }
        return new Query(values()).select(up, "where", params_id, "=", _params_id);
    }

    public static Record newRecord2() {
        Record record = up.newRecord();
        record.set(text, "");
        return record;
    }

    public String toString() {
        return meta.descr();
    }
}
