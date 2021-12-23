package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;
import dataset.Record;

public enum ePropart implements Field {
    up("0", "0", "0", "Контрагент", "CLIENTS"),
    id("4", "10", "0", "Идентификатор", "id"),
    partner("12", "64", "1", "Контрагент", "KNAME"),
    manager("12", "32", "1", "Менеджер", "PNAME"),   
    category("12", "32", "1", "Категория", "KPREF"), //Заказчик, поставщик, офис, дилер, специальный   
    note("12", "256", "1", "Примечание", "note"), //Заказчик, поставщик, офис, дилер, специальный   
    flag2("16", "5", "1", "Физ.лицо", "KCHAS"), //0 - частное, 1 - организация
        
    addr_leve1("12", "64", "1", "Адрес 1го уровня", "KTOWN"),
    addr_leve2("12", "192", "1", "Адрес 2го уровня.", "KADRP"),
    addr_phone("12", "32", "1", "Телефон", "KTELE"),
    addr_email("12", "64", "1", "E-mail", "KMAIL"),
  
    org_name("12", "32", "1", "Организация", "KTYPE"),    
    org_leve1("12", "64", "1", "Адрес 1го уровня", "org_leve1"),
    org_leve2("12", "192", "1", "Адрес 2го уровня..", "KADDR"),
    org_phone("12", "32", "1", "Телефон", "KVTEL"),
    org_email("12", "64", "1", "E-mail", "org_email"),
    org_fax("12", "32", "1", "Факс", "KFAXX"),  
    
    bank_name("12", "128", "1", "Банк", "KBANK"),
    bank_inn("12", "64", "1", "ИНН", "KBAN1"),
    bank_rs("12", "64", "1", "Р/С", "KBAN2"),
    bank_bik("12", "64", "1", "БИК", "KBAN3"),
    bank_ks("12", "64", "1", "К/С", "KBAN4"),
    bank_kpp("12", "64", "1", "КПП", "KBAN5"),
    bank_ogrn("12", "32", "1", "ОГРН", "KOGRN"), 
    
    flag1("16", "5", "1", "Скидка менеджера", "KFLAG"), 
    desc1("8", "15", "1", "Скидки на профиль %", "DESC1"),
    desc2("8", "15", "1", "Скидки на аксессуары", "DESC2"),
    desc3("8", "15", "1", "Скидка на уплотнение", "DESC3"),
    desc5("8", "15", "1", "Скидка на заполнение", "DESC5"),
    disc6("8", "15", "1", "Скидки по умолчанию", "CDESC");     
    
//    kunic("4", "10", "1", "null", "KUNIC"),
//    krekl("4", "10", "1", "null", "KREKL"),
//    pricl("4", "10", "1", "null", "PRICL"),
//    knamf("12", "128", "1", "полное название контрагента", "KNAMF"),
//    idnum("12", "32", "1", "null", "IDNUM"),
//    tmark("12", "64", "1", "null", "TMARK"),
//    krnam("12", "48", "1", "null", "KRNAM"),
//    kkdol("12", "48", "1", "должность", "KKDOL"),
//    kbase("12", "48", "1", "null", "KBASE"),
//    kbnam("12", "48", "1", "null", "KBNAM"),
//    kknam("12", "48", "1", "контактное лицо", "KKNAM"),
//    kgrup("12", "96", "1", "null", "KGRUP"),
//    kpasp("12", "32", "1", "паспорт частного лица", "KPASP"),
//    kpasv("12", "128", "1", "паспорт выдан", "KPASV"),
//    kdepa("5", "5", "1", "номер отдела", "KDEPA"),
//    kprim("-4", "null", "1", "примечание", "KPRIM"),
//    jcntr("5", "5", "1", "заметки", "JCNTR"),
//    saldo("8", "15", "1", "сальдо", "SALDO"),
//    kcred("8", "15", "1", "null", "KCRED"),
//    kdate("93", "19", "1", "дата заведения", "KDATE"),
//    dnumb("12", "32", "1", "null", "DNUMB"),
//    face1("12", "48", "1", "null", "FACE1"),
//    post1("12", "48", "1", "null", "POST1"),
//    mail1("12", "48", "1", "null", "MAIL1"),
//    tele1("12", "48", "1", "null", "TELE1"),
//    face2("12", "48", "1", "null", "FACE2"),
//    post2("12", "48", "1", "null", "POST2"),
//    mail2("12", "48", "1", "null", "MAIL2"),
//    tele2("12", "48", "1", "null", "TELE2"),
//    face3("12", "48", "1", "null", "FACE3"),
//    post3("12", "48", "1", "null", "POST3"),
//    mail3("12", "48", "1", "null", "MAIL3"),
//    tele3("12", "48", "1", "null", "TELE3"),
//    gnumb("5", "5", "1", "null", "GNUMB"),
//    cuser("4", "10", "1", "null", "CUSER");    
    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    ePropart(Object... p) {
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
