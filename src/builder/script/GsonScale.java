package builder.script;

import builder.Wincalc;
import enums.Layout;
import java.awt.Color;
import java.util.LinkedList;

public class GsonScale {

    public static Color BLACK = Color.BLACK;
    public static Color GRAY = Color.GRAY;
    public static Color CHANGE = Color.BLUE;
    public static Color ADJUST = Color.MAGENTA;

    public Color color = Color.black;  //цвет выделения линии 
    public float id = -1;
    private Wincalc iwin = null;

    public GsonScale(Wincalc iwin, float id) {
        this.iwin = iwin;
        this.id = id;
    }

    public GsonElem elem() {
        return iwin.rootGson.find(id);
    }

    public LinkedList<GsonElem> childs() {
        return iwin.rootGson.find(id).childs;
    }

    public Layout layout() {
        return iwin.rootGson.find(id).layout();
    }

    public float width() {
        return iwin.rootGson.find(id).width();
    }

    public float height() {
        return iwin.rootGson.find(id).height();
    }

}
