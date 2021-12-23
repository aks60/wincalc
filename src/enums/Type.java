package enums;

//Типы элементов
public enum Type implements Enam {

    NONE(0, 0, "Не определено"),
    //== Type ==
    FRAME_SIDE(1, 1, "Сторона коробки"),
    STVORKA_SIDE(2, 2, "Сторона створки"),
    IMPOST(3, 3, "Импост"),
    RIGEL_IMPOST(4, "Ригель/импост"),
    STOIKA(5, 5, "Стойка"),
    STOIKA_FRAME(6, "Стойка/коробка"),
    ERKER(7, 7, "Эркер"),
    EDGE(8, 8, "Грань"),
    SHTULP(9, 9, "Штульп"),
    GLASS(10, "Заполнение (Стеклопакет, стекло"),
    MOSKITKA(13, "Москитка"),
    RASKLADKA(14, 7, "Раскладка"),
    SAND(15, "Сэндвич"),
    JALOUSIE(15, "Жалюзи"),
    //SUPPORT(XX, "Подкладка"),
    JOINING(98, "Соединения"),
    PARAM(99, "Параметры конструкции"),
    //== TypeArea ==
    AREA(1000, "Контейнер"),
    RECTANGL(1001, 1, "Окно четырёхугольное"),
    TRAPEZE(1002, 1, "Окно трапеция"),
    TRIANGL(1003, 1, "Треугольное окно"),
    ARCH(1004, 1, "Арочное окно"),
    STVORKA(1005, 2, "Створка"),
    FRAME(1006, 3, "Коробка"),
    DOOR(1007, 3, "Дверь");

    public int id;
    public int id2 = 0; //это UseArtiklTo
    public String name;

    Type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    Type(int id, int id2, String name) {
        this.id = id;
        this.id2 = id2;
        this.name = name;
    }

    public int numb() {
        return id;
    }
}
