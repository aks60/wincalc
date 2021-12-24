package builder.script;

import builder.Wincalc;
import common.eProfile;
import enums.Layout;
import enums.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
//import startup.Main;

public class GsonRoot extends GsonElem {

    public String name = "Конструкция";
    public int prj = 1; //PNUMB - номер тестируемого проекта, поле пока нужно только для тестов 
    public int ord = 1; //ONUMB - номер тестируемого заказа, поле пока нужно только для тестов 
    private Integer nuni = -3;  //nuni профиля (PRO4_SYSPROF.NUNI)
    protected Float width = null; //ширина area, мм
    protected Float height = null; //высота area, мм    
    public Float heightAdd = 0f;  //дополнительная высота, мм.
    public int form = 0;
    public Integer color1 = -3;  //основная текстура
    public Integer color2 = -3;  //внутренняя текстура
    public Integer color3 = -3;  //внешняя текстура    

    public GsonRoot(int prj, int ord, int nuni, String name, Layout layout, Type type, float width, float height, int color1, int color2, int color3) {
        init(prj, ord, nuni, name, layout, type, width, height, height, color1, color2, color3, null);
    }

    public GsonRoot(int prj, int ord, int nuni, String name, Layout layout, Type type, float width, float height, int color1, int color2, int color3, String paramJson) {
        init(prj, ord, nuni, name, layout, type, width, height, height, color1, color2, color3, paramJson);
    }

    public GsonRoot(int prj, int ord, int nuni, String name, Layout layout, Type type, float width, float height1, float height2, int color1, int color2, int color3) {

        if (type == Type.TRAPEZE) {
            if (height1 > height2) {
                init(prj, ord, nuni, name, layout, type, width, height1, height2, color1, color2, color3, null);
                form = 2;
            } else {
                init(prj, ord, nuni, name, layout, type, width, height2, height1, color1, color2, color3, null);
                form = 4;
            }
        } else {
            if (type == Type.ARCH) {
                form = 3;
            }
            init(prj, ord, nuni, name, layout, type, width, height1, height2, color1, color2, color3, null);
        }
    }

    public GsonRoot(int prj, int ord, int nuni, String name, Layout layout, Type type, float width, float height1, float height2, int color1, int color2, int color3, String paramJson) {
        init(prj, ord, nuni, name, layout, type, width, height1, height2, color1, color2, color3, param);
    }

    public void init(int prj, int ord, int nuni, String name, Layout layout, Type type, float width, float height1, float height2, int color1, int color2, int color3, String paramJson) {
        super.genId = 0;
        super.id = 0;
        this.prj = prj;
        this.ord = ord;
        this.nuni = nuni;
        this.name = name;
        this.layout = layout;
        this.type = type;
        this.width = width;
        this.height = height1;
        this.heightAdd = height2;
        this.length = null;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.param = paramJson;
    }

    public void propery(String prj, int nuni, String name) {
        this.nuni = nuni;
        this.prj = Integer.valueOf(prj);
        this.name = (name == null) ? this.name : name;
        if (nuni == -3 && eProfile.dev == false) {
            this.color1 = -3;
            this.color2 = -3;
            this.color3 = -3;
        }
    }

    public float height() {
        return height;
    }

    public float width() {
        return width;
    }

    public int nuni() {
        return nuni;
    }

    public List<GsonScale> lineArea(Wincalc iwin, Layout layout) {
        Set<GsonElem> list1 = new LinkedHashSet(), list2 = new LinkedHashSet();;
        Set<GsonScale> listOut = new LinkedHashSet();
        lineArea(list1, this, layout);
        for (GsonElem elem : list1) {
            list2.clear();
            lineArea(list2, elem, layout);
            if (list2.isEmpty()) {
                listOut.add(new GsonScale(iwin, elem.id));
            }
        }
        if (listOut.isEmpty()) {
            listOut.add(new GsonScale(iwin, this.id));
        }
        return new ArrayList(listOut);
    }

    public void lineArea(Set<GsonElem> list, GsonElem elem, Layout layout) {
        for (int i = 0; i < elem.childs.size(); ++i) {
            GsonElem elem2 = elem.childs.get(i);
            if (elem2.owner.layout == layout && (elem2.type == Type.IMPOST || elem2.type == Type.SHTULP || elem2.type == Type.STOIKA)) {
                list.add(elem.childs.get(i - 1));
                list.add(elem.childs.get(i + 1));
            }
            lineArea(list, elem2, layout);
        }
    }
    
}
