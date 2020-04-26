package enums;

/**
 * Типы открывания створок
 */
public enum TypeOpen {

    OM_INVALID(-1, "empty", "Ошибка"),
    OM_FIXED(0, "empty", "Глухая створка (не открывается)"),
    OM_LEFT(1, "левое", "Левая поворотная (открывается справа-налево, ручка справа)"),
    OM_RIGHT(2, "правое", "Правая поворотная (открывается слева-направо, ручка слева)"),
    OM_LEFTUPPER(3, "левое", "Левая поворотно-откидная"),
    OM_RIGHTUPPER(4, "правое", "Правая поворотно-откидная"),
    OM_UPPER(5, "", "откидная (открывается сверху)"),
    OM_LEFTSHIFT(11, "левое", "Раздвижная влево (открывается справа-налево, защелка справа"),
    OM_RIGHTSHIFT(12, "правое", "Раздвижная вправо (открывается слева-направо, защелка слева");

    public int value;
    public String side;
    public String name;

    TypeOpen(int value, String side, String name) {
        this.value = value;
        this.side = side;
        this.name = name;
    }
}