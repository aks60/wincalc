package model;

import enums.TypeElem;
import enums.LayoutArea;


/**
 * Является родителем для всех элементов образуюших треугольное окно
 * Контролирует и управляет поведение элементов треугольного окна
 */
public class AreaTriangl extends AreaSimple {

    public AreaTriangl(IWindows iwin, String id, LayoutArea layout, float width, float height, int colorBase, int colorInterna, int colorExternal, String paramJson) {
        super(null, id, layout, width, height, colorBase, colorInterna, colorExternal);
        this.iwin = iwin;
        setRoot(this);
        parsingParamJson(this, paramJson);
    }

    @Override
    public TypeElem getTypeElem() {
        return TypeElem.TRIANGL;
    }
}
