package enums;

/**
 * Заполнение стеклопакета
 * Пока не использую т.к. параметр текстовый
 */
public enum FillingGlass {

    
    RECTANGL(1, "Прямоугольное"),
    NOT_RECTANGU(2, "Не прямоугольное"),
    ARCHED(3, "Арочное"),
    NOT_ARCHED(4, "Не арочное");

    public int value = 0;
    public String name = "";

    FillingGlass(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
