package enums;


// Стороны для профилей (SYSPROA.ASETS)
// select distinct ASETS from PRO4_SYSPROA where region_id = 177 order by ASETS
public enum ProfileSide {
    Horiz(-2, "Горизонтальная"),
    Vert(-3, "Вертикальная"),
    Any(-1, "Любая"),
    Manual(0, "Вручную"),
    Bottom(1, "Низ"),
    Right(2, "Правая"),
    Top(3, "Верх"),
    Left(4, "Левая");

    public int value;
    public String name;

    ProfileSide(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
