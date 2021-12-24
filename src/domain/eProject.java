package domain;

import dataset.Field;
import dataset.MetaField;
import dataset.Query;

public enum eProject implements Field {
    up("0", "0", "0", "Список заказов (проектов)", "LISTPRJ"),
    id("4", "10", "0", "Идентификатор", "id"),
    num_ord("12", "32", "1", "Номер заказа", "ZNUMB"),
    num_acc("12", "32", "1", "Номер счета", "INUMB"),
    categ("12", "32", "1", "Категория", "PPREF"),    
    manager("12", "64", "1", "Менеджер", "MNAME"), //это один из user программы
    square("8", "15", "1", "Площадь изделий", "PSQRA"),
    weight("8", "15", "1", "Вес изделий", "EMPTY"),
    exp1c("5", "5", "1", "Экспорт в 1 С", "EXP1C"),    
    type_calc("5", "5", "1", "Тип расчтета", "PTYPE"),
    type_norm("5", "5", "1", "Учет норм отходов", "CTYPE"),
    pric1("8", "15", "1", "Cебестоимость проекта", "PSEBE"),
    pric2("8", "15", "1", "Cтоимость конструкций без скидки", "KPRIC"),
    pric3("8", "15", "1", "Cтоимость комплектации без скидки", "APRIC"),
    pric4("8", "15", "1", "Cтоимость работ без скидок", "WPRIC"),
    pric5("8", "15", "1", "Cтоимость проекта без скидок", "PPRIC"),
    cost1("8", "15", "1", "Cтоимость конструкции со скидками", "KPRCD"),
    cost2("8", "15", "1", "Cтоимость комплектации со скидками", "APRCD"),
    cost3("8", "15", "1", "Cтоимость работ со скидками", "WPRCD"),
    cost4("8", "15", "1", "Cтоимость проекта со скидками", "PPRCD"),
    desc1("8", "15", "1", "Cкидка на конструкции", "KDESC"),
    desc2("8", "15", "1", "Cкидка на комплектацию", "ADESC"),
    desc3("8", "15", "1", "Cкидка на работы", "WDESC"),
    desc4("8", "15", "1", "Скидка на профиль", "DESC1"),
    desc5("8", "15", "1", "Скидка на аксессуары", "DESC2"),
    desc6("8", "15", "1", "Скидка на уплотнения", "DESC3"),
    desc7("8", "15", "1", "Скидка на заполнения", "DESC5"),
    desc8("8", "15", "1", "Cкидка общая", "PDESC"),
    desc9("8", "15", "1", "Доп. скидка", "XDESC"),
    markup("8", "15", "1", "Наценка %", "PMARG"),
    kurs1("8", "15", "1", "Курс основной валюты", "KURSM"),
    kurs2("8", "15", "1", "Курс внутренний валюты", "KURSV"),
    flag1("5", "5", "1", "Флаг пользователя", "PFLAG"),
    flag2("5", "5", "1", "Флаг состояния выгрузки", "PEQUP"),
    flag3("5", "5", "1", "Флаг планирования", "EWORK"),
    flag4("5", "5", "1", "Отправка в производства", "PWORK"),
    date4("93", "19", "1", "Дата регистрации заказа", "PDATE"),
    date5("93", "19", "1", "Дата расчета заказа", "CDATE"),
    date6("93", "19", "1", "Дата отпр. в производство", "WDATE"),
    currenc_id("4", "10", "1", "Валюта", "CNUMB"),
    propart_id("4", "10", "1", "Контрагент", "propart_id");

//    punic("4", "10", "1", "null", "PUNIC"),
//    num_prj("4", "10", "1", "Номер проекта", "PNUMB"),
//    pnum2("5", "5", "1", "суффикс проекта", "PNUM2"),
//    pincn("4", "10", "1", "сквозной номер", "PINCN"),   
//    pprim("-4", "null", "1", "примечание", "PPRIM"),  
//    contractor("12", "64", "1", "Контрагент", "KNAME"),   
//    num_dep("5", "5", "1", "Номер отдела", "NDEPA"),
//    tunic("5", "5", "1", "null", "TUNIC"),
//    prese("5", "5", "1", "резерв", "PRESE"),
//    pstat("12", "64", "1", "статус", "PSTAT"),
//    psend("5", "5", "1", "обмен", "PSEND"),
//    pdocs("5", "5", "1", "null", "PDOCS"),    
//    msumm("8", "15", "1", "оплата", "MSUMM"),
//    obj1("12", "128", "1", "Объект", "POBJA"),
//    obj2("12", "1024", "1", "Описание объекта", "POBJL"),    
//    kprim("-4", "null", "1", "конфиденциально", "KPRIM"),
//    sale_name("12", "64", "1", "Продавец", "SNAME"),
//    constr("12", "64", "1", "Конструктор", "CNAME"),    
//    nunic("4", "10", "1", "null", "NUNIC"),
//    pricl("4", "10", "1", "null", "PRICL"),
//    descr("4", "10", "1", "null", "DESCR"),
//    preme("12", "100", "1", "null", "PREME"),
//    pnump("4", "10", "1", "null", "PNUMP"),       
//    knacd("8", "15", "1", "null", "KNACD"),
//    anacd("8", "15", "1", "null", "ANACD"),
//    wnacd("8", "15", "1", "null", "WNACD"),
//    kdesd("8", "15", "1", "null", "KDESD"),
//    adesd("8", "15", "1", "null", "ADESD"),
//    wdesd("8", "15", "1", "null", "WDESD"),
//    pdesd("8", "15", "1", "null", "PDESD"),
//    xdesd("8", "15", "1", "null", "XDESD"),
//    pricd("8", "15", "1", "null", "PRICD"),
//    wdiff("8", "15", "1", "null", "WDIFF"),
//    adiff("8", "15", "1", "null", "ADIFF"),
//    gdate("93", "19", "1", "null", "GDATE"),
//    dowin("8", "15", "1", "null", "DOWIN"),
//    dsumm("8", "15", "1", "null", "DSUMM"),
//    prcda("8", "15", "1", "null", "PRCDA"),
//    prcdw("8", "15", "1", "null", "PRCDW"),
//    prcdx("8", "15", "1", "null", "PRCDX"),
//    prcw1("8", "15", "1", "null", "PRCW1"),
//    prcw2("8", "15", "1", "null", "PRCW2"),
//    pname("12", "64", "1", "null", "PNAME"),
//    psysp("4", "10", "1", "null", "PSYSP"),
//    clnum("4", "10", "1", "null", "CLNUM"),
//    clnu1("4", "10", "1", "null", "CLNU1"),
//    clnu2("4", "10", "1", "null", "CLNU2"),
//    anumb("12", "32", "1", "null", "ANUMB"),
//    dates("12", "48", "1", "null", "DATES"),
//    date1("93", "19", "1", "настраиваемая дата", "DATE1"),
//    date2("93", "19", "1", "null", "DATE2"),
//    date3("93", "19", "1", "null", "DATE3");
    private MetaField meta = new MetaField(this);
    private static Query query = new Query(values());

    eProject(Object... p) {
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
