package enums;

import domain.Artikls;

/**
 * Типы мат. ценностей
 */
public enum TypeArtikl {

    KOROBKA(1, 1, "Коробка"),
    STVORKA(1, 2, "Створка"),
    IMPOST(1, 3, "Импост"),
    ARMIROVANIE(1, 7, "Армирование"),
    SHTAPIK(1, 8, "Штапик"),
    FITTING(1, 9, "Фурнитура"),
    MONTPROF(1, 17, "Монтажный профиль"),
    KONZEVPROF(1, 35, "Концевой профиль"),
    FIKSPROF(1, 36, "Фиксирующий профиль"),
    SOEDINITEL(2, 5, "Соединитель"),
    STVORKA_HANDL(2, 11, "Фурнитура.ручка"),
    GLASS(5, 2, "Стеклопакет");

    public int value;
    public int value2;
    public String name;

    TypeArtikl(int value, int value2, String name) {
        this.value = value;
        this.value2 = value2;
        this.name = name;
    }

    public boolean isType(Artikls art) {
        if (value == art.atypm && value2 == art.atypp) return true;
        return false;
    }
}
