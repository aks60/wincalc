package enums;

/**
 * Тип профиля (SYSPROA.ATYPE) в системе конструкций
 */
public enum TypeProfile {
    UNKNOWN(0, "любой тип"),
    FRAME(1, "рама"),
    STVORKA(2, "створка"),
    IMPOST(3, "импост"),
    STOIKA(5, "стойка"),
    POPERECHINA(6, "поперечина МС"),
    RASKLADKA(7, "раскладка"),
    UNKNOWN_8(8, "!!!drok"),
    SHTULP(9, "штульп"),
    ERKER(10, "эркер");

    public int value;
    public String name;

    TypeProfile(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
