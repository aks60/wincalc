package enums;


/**
 * Типы элементов приходящие на вход сервиса (json to model)
 * Тут объединены окно и все его элементы
 */
public enum TypeElem {

    MOSKITKA_SET(-13, "Москитка"),
    GLASS(1, "Стеклопакет"),
    FRAME(2, "Рама"),
    IMPOST(3, "Импост"),
    STVORKA(4, "Створка"),
    SHTULP(10, "Штульп"),
    MOSKITKA(13, "Москитка"),
    RASKLADKA(14, "Раскладка"),
    WOI_STVORKA(23, "Створка"), //без импоста
    SAND(100, "Сэндвич"),
    JALOUSIE(101, "Жалюзи"),
    AREA(1000, "Контейнер компонентов"),
    SQUARE(1001, "Окно четырёхугольное в сборе"),
    TRAPEZE(1002, "Окно трапеция в сборе"),
    TRIANGL(1003, "Треугольное окно в сборе"),
    ARCH(1004, "Арочное окно в сборе"),
    FULLSTVORKA(1005, "Створка в сборе"),
    NONE(0, "Не определено");

    public int value;
    public String name;

    TypeElem(int value, String name) {
        this.value = value;
        this.name = name;
    }

    //public int value2() { return _value; }

/*    public static ElemType FromInt(int value2) {

        for (ElemType item : ElemType.values()) {
            if (item.value2() == value2) {
                return item;
            }
        }
        return null;
    }*/

    //public Boolean isFilling() { return (this == GLASS || this == SAND); }

    //private final int _value;
}
