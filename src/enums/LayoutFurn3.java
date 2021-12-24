/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

import static enums.UseSide.values;
import static enums.TypeOpen1.values;

//Стороны фурнитуры
public enum LayoutFurn3 implements Enam {
    P1(1, "Нижняя"),
    P2(2, "Правая"),
    P3(3, "Верхняя"),
    P4(4, "Левая"),
    P5(5, "? Нижняя(для трапеции)"),
    P6(6, "? Нижняя(для арки)");

    public int id;
    public String name;

    LayoutFurn3(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int numb() {
        return id;
    }

    public String text() {
        return name;
    }
    
    public Enam[] fields() {
        return values();
    }
}
