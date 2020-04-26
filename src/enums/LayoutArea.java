package enums;

public enum LayoutArea {
    NONE("Любая"),
    FULL("Везде"),
    HORIZONTAL("Горизонтальное"),
    VERTICAL("Вертикальное"),
    LEFT("Левая"),
    RIGHT("Правая"),
    TOP("Верхняя"),
    BOTTOM("Нижняя"),
    LSKEW("Левый угол"),
    RSKEW("Правый угол"),
    ARCH("Арка");

    public String name = "";

    LayoutArea(String name) {
        this.name = name;
    }
}
