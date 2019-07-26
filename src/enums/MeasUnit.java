package enums;

public enum MeasUnit {
    METR(1, "пог.м."),
    METR2(2, "кв.м."),
    PIE(3, "шт."),
    ML(4, "мл."),
    SET(5, "комп."),
    KG(7, "кг"),
    LT(8, "литры"),
    PAIR(11, "пара");

    public int value = 0;
    public String name = "";

    MeasUnit(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getName(int index) {
        for (MeasUnit unit: values()) {
            if (unit.value == index) {
                return unit.name;
            }
        }
        return "";
    }
}
