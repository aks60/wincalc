package enums;

import static enums.Layout.values;

public enum Form {
    NUM0(0, "Любая"),
    NUM1(1, "Нижняя"),
    NUM2(2, "Правая"),
    NUM3(3, "Верхняя"),
    NUM4(4, "Левая");

    public int id;
    public String name;

    Form(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Form get(int id) {
        for (Form form : values()) {
            if (form.id == id) {
                return form;
            }
        }
        return null;
    }
}
