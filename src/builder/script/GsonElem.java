package builder.script;

import enums.Layout;
import enums.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

public class GsonElem {

    protected static transient float genId = -1;  //идентификатор    
    protected float id = -1;  //идентификатор
    protected transient GsonElem owner = null;  //владелец     
    protected LinkedList<GsonElem> childs = new LinkedList();  //список детей
    protected Layout layout = null; //сторона расположения эл. рамы
    protected Type type = null; //тип элемента
    protected String param = null; //параметры элемента
    protected Float length = null; //ширина или высота добавляемой area (зависит от напрвления расположения) 

    public transient float point = 0;  //точка scale 

    public GsonElem() {
    }

    //Конструктор Elem
    public GsonElem(Type type) {
        this.id = ++genId;
        this.type = type;
    }

    //Конструктор Elem
    public GsonElem(Type type, String paramJson) {
        this.id = ++genId;
        this.type = type;
        this.param = paramJson;
    }

    //Конструктор Elem
    public GsonElem(Type type, Layout layoutRama) {
        this.id = ++genId;
        this.type = type;
        this.layout = layoutRama;
    }
    //Конструктор Elem
    public GsonElem(Type type, Layout layoutRama, String paramJson) {
        this.id = ++genId;
        this.type = type;
        this.layout = layoutRama;
        this.param = paramJson;
    }

    //Конструктор Area
    public GsonElem(Layout layout, Type type, float length) {
        this.id = ++genId;
        this.layout = layout;
        this.type = type;
        this.length = length; //длина стороны, сторона зависит от направления расположения area
    }

    //Конструктор створки
    public GsonElem(Layout layout, Type type) {
        this.id = ++genId;
        this.layout = layout;
        this.type = type;
    }

    //Конструктор створки
    public GsonElem(Layout layout, Type type, String paramJson) {
        this.id = ++genId;
        this.layout = layout;
        this.type = type;
        this.param = paramJson; //параметры элемента
    }

    public GsonElem addArea(GsonElem area) {
        childs = (childs == null) ? new LinkedList() : childs;
        if (area.type == Type.STVORKA) {
            area.length = this.length;
        }
        this.childs.add(area);
        return area;
    }

    public GsonElem addElem(GsonElem element) {
        childs = (childs == null) ? new LinkedList() : childs;
        this.childs.add(element);
        return this;
    }

    public float id() {
        return id;
    }

    public Type type() {
        return type;
    }

    public Layout layout() {
        return layout;
    }

    public String param() {
        return (param == null || param.isEmpty() == true) ? "{}" : param;
    }

    public void param(String param) {
        this.param = param;
    }

    public float height() {
        return (owner.layout == Layout.VERT) ? length : owner.height();
    }

    public float width() {
        return (owner.layout == Layout.HORIZ) ? length : owner.width();
    }

    public void resizUp(List<GsonScale> list, Layout layout) {
        float dx = 1;
        GsonElem e2 = null;
        for (GsonScale gs : list) {
            GsonElem el = find(gs.id);
            if (el.length != null) {
                if (gs.color == GsonScale.ADJUST) {
                    e2 = el;
                }
                float x = el.length - Math.round(el.length);
                if (x != 0 && x < 0) {
                    el.length -= x;
                    dx += x;
                } else {
                    el.length -= x;
                    dx += x;
                }
                el.owner.resizAll(layout);
            }
        }
        if (e2 != null && e2.length != null) {
            e2.length += dx - 1;
            e2.owner.resizAll(layout);
        }
    }

    public void resizElem(float _diff, List<GsonScale> _list, Layout _layout) {

        GsonRoot root = (GsonRoot) this;
        float changeSum = 0;
        for (GsonScale o : _list) {
            float v = (_layout == Layout.HORIZ) ? o.width() : o.height();
            changeSum += v;
        }
        //    Горизонтальное перераспределение
        if (_layout == Layout.HORIZ) {
            for (GsonScale gsonScale : _list) {
                GsonElem elem = gsonScale.elem();
                if (elem.length != null) {
                    float k = elem.length / changeSum;
                    elem.length = elem.length + _diff * k;
                    elem.owner.resizAll(_layout);
                } else {
                    root.resizAll(_layout);
                }
            }
            root.width = root.width + _diff;

            //Вертикальное перераспределение
        } else if (_layout == Layout.VERT) {
            for (GsonScale gsonScale : _list) {
                GsonElem elem = gsonScale.elem();
                if (elem.length != null) {
                    float k = elem.length / changeSum;
                    elem.length = elem.length + _diff * k;
                    elem.owner.resizAll(_layout);
                } else {
                    root.resizAll(_layout);
                }
            }
            root.height = root.height + _diff;
            if (root.type == Type.ARCH) {
                root.heightAdd = root.height - root.childs.get(4).height();
            } else if (root.type == Type.TRAPEZE) {
                root.heightAdd = root.height - root.childs.get(4).height();
            } else {
                root.heightAdd = root.height;
            }
        }
    }

    public void resizAll(Layout _layout) {
        if (this.owner != null) {
            float sum = 0;
            if (_layout == this.layout()) {
                for (GsonElem elem : this.childs) {
                    if (elem.type == Type.AREA) {
                        sum += elem.length;
                    }
                }
            }
            if (sum != 0) {
                if (this.owner.layout != _layout) {
                    this.owner.length = sum;
                } else {
                    this.length = sum;
                }
            }
            this.owner.resizAll(_layout);
        }
    }

    public GsonElem owner() {
        return owner;
    }

    public LinkedList<GsonElem> childs() {
        return childs;
    }

    public LinkedList<GsonElem> areas() {
        LinkedList<GsonElem> list = new LinkedList();
        childs.forEach(el -> {
            if (el.type() == Type.STVORKA || el.type() == Type.AREA) {
                list.add(el);
            }
        });
        return list;
    }

    public LinkedList<GsonElem> elems() {
        LinkedList<GsonElem> list = new LinkedList();
        childs.forEach(el -> {
            if (el.type() != Type.STVORKA || el.type() != Type.AREA) {
                list.add(el);
            }
        });
        return list;
    }

    public GsonElem find(float id) {
        if (this.id == id) {
            return this;
        }
        for (GsonElem el : elems()) {
            if (el.id == id) {
                return el;
            }
        }
        for (GsonElem area2 : areas()) { //уровень 2
            if (area2.id == id) {
                return area2;
            }
            for (GsonElem el2 : area2.elems()) {
                if (el2.id == id) {
                    return el2;
                }
            }
            for (GsonElem area3 : area2.areas()) { //уровень 3
                if (area3.id == id) {
                    return area3;
                }
                for (GsonElem el3 : area3.elems()) {
                    if (el3.id == id) {
                        return el3;
                    }
                }
                for (GsonElem area4 : area3.areas()) { //уровень 4
                    if (area4.id == id) {
                        return area4;
                    }
                    for (GsonElem el4 : area4.elems()) {
                        if (el4.id == id) {
                            return el4;
                        }
                    }
                    for (GsonElem area5 : area4.areas()) { //уровень 5
                        if (area5.id == id) {
                            return area5;
                        }
                        for (GsonElem el5 : area5.elems()) {
                            if (el5.id == id) {
                                return el5;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public void setParent(GsonElem node) {
        node.elems().forEach(elem -> elem.owner = node);
        for (GsonElem area : node.areas()) {
            area.owner = node;
            area.elems().forEach(elem -> elem.owner = node);
            setParent(area); //реккурсия
        }
    }
}
