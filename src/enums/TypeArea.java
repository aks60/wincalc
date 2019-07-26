package enums;

public enum TypeArea {
    AREA(1000, "Контейнер компонентов"),
    SQUARE(1001, "Четырёхугольник"),
    TRAPEZE(1002, "Трапеция"),
    TRIANGL(1003, "Треугольник"),
    ARCH(1004, "Арка"),
    NONE(1005, "Не определено");

    public int value;
    public String name;

    TypeArea(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
/*
enum class ProductArea : __int32
{
	Accessory = 0,		// Аксессуар (подоконник, отлив, откос и т.п.)
	Window = 1,			// Окно
	Door = 2,			// Дверь из оконного профиля (например балконная дверь)
	BalconyJoin = 3,	// Вставка-соединитель между окном и балконной дверью
	RoomDoor = 4,		// Дверь из дверного профиля (межкомнатная или входная дверь)
	Jalousie = 5,		// Жалюзи (заявка [107305])
};
 */
