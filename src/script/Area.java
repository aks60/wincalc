package script;

import com.google.gson.Gson;
import enums.LayoutArea;
import enums.TypeElem;
import java.util.LinkedList;

/**
 * Контернер передачи данных.
 * В контейнере могут находиться другие контейнеры и элементы.
 */
public class Area extends Element {

    // Эти поля нужны для всех арий:
    protected LayoutArea layoutArea = null; //ориентация при размещении area
    protected float width = 0;              //ширина area, мм
    protected float height = 0;             //высота area, мм
    private LinkedList<Element> elements = new LinkedList(); //список элементов в area

    // Это поле нужно для расчета размеров Арии на этапе конструирования, из Сервера не передается.
    private Float lengthSide = null;         //ширина или высота добавляемой area, зависит от layoutArea (см. функцию add())

    // Эти поля нужны только для корневой арии:
    private Integer nuni = null;             //nuni профиля (PRO4_SYSPROF.NUNI)
    protected Float heightLow = null;        //меньшая высота, мм (по аналогии с ПС-4). Для прямоугольного изделия = height.
    protected Integer colorBase = null;      //основная текстура (PRO4_COLSLST.CCODE)
    protected Integer colorInternal = null;  //внутренняя текстура (PRO4_COLSLST.CCODE)
    protected Integer colorExternal = null;  //внешняя текстура (PRO4_COLSLST.CCODE)

    // Это поле нужно только для тестов, из Сервера не передается.
    private String prj = null; //номер тестируемого проекта

    /**
     * Конструктор  створки
     * @param id          id элемента
     * @param layoutArea  расположения
     * @param elemType    тип элемента
     * @param paramJson   параметры элемента
     */
    public Area(String id, LayoutArea layoutArea, TypeElem elemType, String paramJson) {
        super.id = id;
        this.layoutArea = layoutArea;
        this.elemType = elemType;
        this.paramJson = paramJson;
    }

    /**
     * Конструктор вложенной Area
     * @param id           id элемента
     * @param layoutArea   расположения
     * @param elemType     тип элемента
     * @param lengthSide   длина стороны, сторона зависит от направлени расположения area
     */
    public Area(String id, LayoutArea layoutArea, TypeElem elemType, float lengthSide) {
        super.id = id;
        this.layoutArea = layoutArea;
        this.elemType = elemType;
        this.lengthSide = lengthSide;
    }

    /**
     * Конструктор прямоугольного окна
     * @param id             id элемента
     * @param layoutArea     расположения
     * @param elemType       тип элемента
     * @param width          ширина элемента
     * @param height         высота удуьента
     * @param colorBase      внутренняя текстура
     * @param colorInternal  внешняя текстура
     * @param colorExternal  внешняя текстура
     */
    public Area(String id, LayoutArea layoutArea, TypeElem elemType, float width, float height, int colorBase, int colorInternal, int colorExternal, String paramJson) {
        this(id, layoutArea, elemType, width, height, height, colorBase, colorInternal, colorExternal, paramJson);
    }

    /**
     * Конструктор непрямоугольного окна
     * @param id             id элемента
     * @param layoutArea     расположения
     * @param elemType       тип элемента
     * @param width          ширина элемента
     * @param height         высота удуьента
     * @param heightLow      меньшая высота
     * @param colorBase      внутренняя текстура
     * @param colorInternal  внешняя текстура
     * @param colorExternal  внешняя текстура
     */
    public Area(String id, LayoutArea layoutArea, TypeElem elemType, float width, float height, float heightLow, int colorBase, int colorInternal, int colorExternal, String paramJson) {
        init(id, layoutArea, elemType, width, height, heightLow, colorBase, colorInternal, colorExternal, paramJson);
    }

    /**
     * Инициализация
     * @param id             id элемента
     * @param layoutArea     расположения
     * @param elemType       тип элемента
     * @param width          ширина элемента
     * @param height         высота удуьента
     * @param heightLow      меньшая высота
     * @param colorBase      внутренняя текстура
     * @param colorInternal  внешняя текстура
     * @param colorExternal  внешняя текстура
     */
    private void init(String id, LayoutArea layoutArea, TypeElem elemType, float width, float height, float heightLow, int colorBase, int colorInternal, int colorExternal, String paramJson) {
        super.id = id;
        this.layoutArea = layoutArea;
        this.elemType = elemType;
        this.width = width;
        this.height = height;
        this.heightLow = heightLow;
        this.colorBase = colorBase;
        this.colorInternal = colorInternal;
        this.colorExternal = colorExternal;
        this.paramJson = paramJson;
    }

    /**
     * Добавление элемента в дерево
     * @param element   добавляемый элемент в дерево
     * @return          добавляемый элемент в дерево
     */
    public Element add(Element element) {
        if (element instanceof Area) {

            Area area = (Area) element;
            if (TypeElem.FULLSTVORKA == element.elemType) {

                area.width = this.width;
                area.height = this.height;
            } else {

                if (LayoutArea.VERTICAL == layoutArea) {
                    area.height = area.lengthSide;
                    area.width = width;
                } else {
                    area.height = height;
                    area.width = area.lengthSide;
                }
            }
        }
        elements.add(element);
        return element;
    }

    /**
     *
     * @param nuni профиля
     * @param prj  проект
     */
    public void setParam(int nuni, String prj) {
        this.nuni = nuni;
        this.prj = prj;
    }

    public float getHeight() {
        return height;
    }

    public float getHeightLow() {
        return heightLow;
    }

    public float getWidth() {
        return width;
    }

    public int getNuni() {
        return nuni;
    }

    public LayoutArea getLayoutArea() {
        return layoutArea;
    }

    public LinkedList<Element> getElements() {
        return elements;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// ТЕСТОВЫЕ ФУНКЦИИ  ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String test(Integer id) {
        if (id == 601001) {

            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.SQUARE, 900, 1300, 1009, 10009, 1009, "");
            rootArea.setParam(8, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));
            Area area2 = (Area) rootArea.add(new Area("6", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':1, 'funic':23}"));
            area2.add(new Element("7", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 601002) {
            Area rootArea = new Area("1", LayoutArea.HORIZONTAL, TypeElem.SQUARE, 1300, 1400, 1009, 10009, 1009, "");
            rootArea.setParam(29, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));
            Area area2 = (Area) rootArea.add(new Area("6", LayoutArea.HORIZONTAL, TypeElem.AREA, 1300 / 2));
            rootArea.add(new Element("7", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("8", LayoutArea.HORIZONTAL, TypeElem.AREA, 1300 / 2));
            Area area4 = (Area) area2.add(new Area("9", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':3, 'funic':20}"));
            Area area5 = (Area) area3.add(new Area("10", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':3, 'funic':20}"));

            area4.add(new Element("11", TypeElem.GLASS));
            area5.add(new Element("12", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 601003) {
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.SQUARE, 1440, 1700, 1009, 1009, 1009, "");
            rootArea.setParam(81, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            Area area2 = (Area) rootArea.add(new Area("6", LayoutArea.HORIZONTAL, TypeElem.AREA, 400));
            rootArea.add(new Element("7", TypeElem.IMPOST));
            Area area4 = (Area) rootArea.add(new Area("8", LayoutArea.HORIZONTAL, TypeElem.AREA, 1300));
            Area area5 = (Area) area4.add(new Area("9", LayoutArea.VERTICAL, TypeElem.AREA, 720));
            area4.add(new Element("10", TypeElem.IMPOST));
            Area area6 = (Area) area4.add(new Area("11", LayoutArea.VERTICAL, TypeElem.AREA, 720));
            Area area8 = (Area) area5.add(new Area("12", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':1, 'funic':337}"));
            Area area9 = (Area) area6.add(new Area("13", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':3, 'funic':336}"));

            area2.add(new Element("14", TypeElem.GLASS));
            area8.add(new Element("15", TypeElem.GLASS));
            area9.add(new Element("16", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 601004) {
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.SQUARE, 1440, 1700, 1009, 1009, 1009, "");
            rootArea.setParam(8, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            Area area2 = (Area) rootArea.add(new Area("6", LayoutArea.HORIZONTAL, TypeElem.AREA, 400));
            rootArea.add(new Element("7", TypeElem.IMPOST));
            Area area4 = (Area) rootArea.add(new Area("8", LayoutArea.HORIZONTAL, TypeElem.AREA, 1300));
            Area area5 = (Area) area4.add(new Area("9", LayoutArea.VERTICAL, TypeElem.AREA, 720));
            area4.add(new Element("10", TypeElem.IMPOST));
            Area area6 = (Area) area4.add(new Area("11", LayoutArea.VERTICAL, TypeElem.AREA, 720));
            Area area8 = (Area) area5.add(new Area("12", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':1, 'funic':23}"));
            Area area9 = (Area) area6.add(new Area("13", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':4, 'funic':20}"));

            area2.add(new Element("14", TypeElem.GLASS));
            area8.add(new Element("15", TypeElem.GLASS));
            area9.add(new Element("16", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 601005) {
            Area rootArea = new Area("1", LayoutArea.HORIZONTAL, TypeElem.SQUARE, 1600, 1700, 1009, 1009, 1009, "");
            rootArea.setParam(8, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            Area area2 = (Area) rootArea.add(new Area("6", LayoutArea.VERTICAL, TypeElem.AREA, 800));
            rootArea.add(new Element("7", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("8", LayoutArea.VERTICAL, TypeElem.AREA, 800));
            Area area4 = (Area) area2.add(new Area("9", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':1, 'funic':23}"));
            Area area5 = (Area) area3.add(new Area("10", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':4, 'funic':20}"));

            area4.add(new Element("11", TypeElem.GLASS));
            area5.add(new Element("12", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 601006) {
            Area rootArea = new Area("1", LayoutArea.HORIZONTAL, TypeElem.SQUARE, 900, 1400, 1009, 1009, 1009, "");
            rootArea.setParam(110, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.GLASS, "{'nunic_iwin':'615496322'}")); //или 'R4x10x4x10x4'
            return new Gson().toJson(rootArea);

        } else if (id == 601007) {
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.SQUARE, 1100, 1400, 1009, 10018, 10018, "");
            rootArea.setParam(87, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            Area area2 = (Area) rootArea.add(new Area("6", LayoutArea.HORIZONTAL, TypeElem.AREA, 300));
            rootArea.add(new Element("7", TypeElem.IMPOST));
            Area area4 = (Area) rootArea.add(new Area("8", LayoutArea.HORIZONTAL, TypeElem.AREA, 1100));
            Area area5 = (Area) area4.add(new Area("9", LayoutArea.VERTICAL, TypeElem.AREA, 550));
            area4.add(new Element("10", TypeElem.IMPOST));
            Area area6 = (Area) area4.add(new Area("11", LayoutArea.VERTICAL, TypeElem.AREA, 550));
            Area area8 = (Area) area5.add(new Area("12", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':1, 'funic':93}"));
            Area area9 = (Area) area6.add(new Area("13", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':4, 'funic':92}"));

            area2.add(new Element("14", TypeElem.GLASS));
            area8.add(new Element("15", TypeElem.GLASS));
            area9.add(new Element("16", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 601008) {
            Area rootArea = new Area("1", LayoutArea.HORIZONTAL, TypeElem.SQUARE, 1200, 1700, 1009, 28014, 21057, "");
            rootArea.setParam(99, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            Area area2 = (Area) rootArea.add(new Area("6", LayoutArea.HORIZONTAL, TypeElem.AREA, 600));
            rootArea.add(new Element("7", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("8", LayoutArea.VERTICAL, TypeElem.AREA, 600));
            Area area4 = (Area) area3.add(new Area("9", LayoutArea.VERTICAL, TypeElem.AREA, 550));
            area3.add(new Element("10", TypeElem.IMPOST));
            Area area5 = (Area) area3.add(new Area("11", LayoutArea.VERTICAL, TypeElem.AREA, 1150));
            Area area6 = (Area) area4.add(new Area("12", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':4, 'funic':20}"));

            area2.add(new Element("13", TypeElem.GLASS));
            area6.add(new Element("14", TypeElem.GLASS));
            area5.add(new Element("15", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 601009) {
            Area rootArea = new Area("1", LayoutArea.HORIZONTAL, TypeElem.SQUARE, 700, 1400, 1009, 1009, 1009, "");
            rootArea.setParam(54, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.GLASS, "{'nunic_iwin':'1685457539'}")); //или '4x12x4x12x4' для nuni = 54
            return new Gson().toJson(rootArea);

        } else if (id == 601010) {
            Area rootArea = new Area("1", LayoutArea.HORIZONTAL, TypeElem.SQUARE, 1300, 1400, 1009, 1009, 1009, "{'pro4Params':[[-862071,295],[-862065,314],[-862062,325],[-862131,17],[-862097,195],[-862060,335]]}");
            rootArea.setParam(54, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            Area area2 = (Area) rootArea.add(new Area("6", LayoutArea.VERTICAL, TypeElem.AREA, 650));
            rootArea.add(new Element("7", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("8", LayoutArea.VERTICAL, TypeElem.AREA, 650));

            Area area4 = (Area) area2.add(new Area("9", LayoutArea.FULL, TypeElem.FULLSTVORKA,  "{'typeOpen':1,'funic':23, 'pro4Params': [[-862107,826],[-862106,830]]}"));
            Area area5 = (Area) area3.add(new Area("10", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':4,'funic':20, 'pro4Params': [[-862107,184],[-862106,186]]}"));

            area4.add(new Element("11", TypeElem.GLASS, "{'nunic_iwin':'1685457539'}"));
            area5.add(new Element("12", TypeElem.GLASS, "{'nunic_iwin':'1685457539'}"));
            return new Gson().toJson(rootArea);

        } else if (id == 604004) {
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.ARCH, 1300, 1700, 1050, 1009, 1009, 1009, "");
            rootArea.setParam(37, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.ARCH));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("7", LayoutArea.HORIZONTAL, TypeElem.AREA, 1050));

            Area area4 = (Area) area3.add(new Area("8", LayoutArea.VERTICAL, TypeElem.AREA, 650));
            area3.add(new Element("9", TypeElem.IMPOST));
            Area area5 = (Area) area3.add(new Area("10", LayoutArea.VERTICAL, TypeElem.AREA, 650));

            Area area6 = (Area) area5.add(new Area("11", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':4, 'funic':20}"));

            rootArea.add(new Element("12", TypeElem.GLASS));
            area4.add(new Element("13", TypeElem.GLASS));
            area6.add(new Element("14", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 604005) {
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.ARCH, 1300, 1500, 1200, 1009, 10009, 1009, "");
            rootArea.setParam(135, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.ARCH));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("7", LayoutArea.HORIZONTAL, TypeElem.AREA, 1200));
            Area area4 = (Area) area3.add(new Area("8", LayoutArea.VERTICAL, TypeElem.AREA, 650));
            area3.add(new Element("9", TypeElem.IMPOST));
            Area area5 = (Area) area3.add(new Area("10", LayoutArea.VERTICAL, TypeElem.AREA, 650));
            Area area6 = (Area) area4.add(new Area("11", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':1, 'funic':23}"));
            Area area7 = (Area) area5.add(new Area("12", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':4, 'funic':20}"));

            rootArea.add(new Element("13", TypeElem.GLASS));
            area6.add(new Element("14", TypeElem.GLASS));
            area7.add(new Element("15", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 604006) {
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.ARCH, 1100, 1600, 1220, 1009, 1009, 10012, "");
            rootArea.setParam(135, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.ARCH));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("7", LayoutArea.HORIZONTAL, TypeElem.AREA, 1220));
            Area area4 = (Area) area3.add(new Area("8", LayoutArea.VERTICAL, TypeElem.AREA, 550));
            area3.add(new Element("9", TypeElem.IMPOST));
            Area area5 = (Area) area3.add(new Area("10", LayoutArea.VERTICAL, TypeElem.AREA, 550));
            Area area6 = (Area) area4.add(new Area("11", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':1, 'funic':23}"));
            Area area7 = (Area) area5.add(new Area("12", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':4, 'funic':20}"));

            rootArea.add(new Element("13", TypeElem.GLASS));
            area6.add(new Element("14", TypeElem.GLASS));
            area7.add(new Element("15", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 604007) {
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.ARCH, 1400, 1700, 1300, 1009, 1009, 10001, "");
            rootArea.setParam(99, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.ARCH));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("7", LayoutArea.HORIZONTAL, TypeElem.AREA, 1300));
            Area area4 = (Area) area3.add(new Area("8", LayoutArea.VERTICAL, TypeElem.AREA, 700));
            area3.add(new Element("9", TypeElem.IMPOST));
            Area area5 = (Area) area3.add(new Area("10", LayoutArea.VERTICAL, TypeElem.AREA, 700));
            Area area6 = (Area) area4.add(new Area("11", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':1, 'funic':23}"));
            Area area7 = (Area) area5.add(new Area("12", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':3, 'funic':20}"));

            rootArea.add(new Element("13", TypeElem.GLASS));
            area6.add(new Element("14", TypeElem.GLASS));
            area7.add(new Element("15", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 604008) {
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.ARCH, 1300, 1500, 1200, 1009, 10009, 1009, "");
            rootArea.setParam(8, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.ARCH));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("7", LayoutArea.HORIZONTAL, TypeElem.AREA, 1200));
            Area area4 = (Area) area3.add(new Area("8", LayoutArea.VERTICAL, TypeElem.AREA, 650));
            area3.add(new Element("9", TypeElem.IMPOST));
            Area area5 = (Area) area3.add(new Area("10", LayoutArea.VERTICAL, TypeElem.AREA, 650));
            Area area6 = (Area) area4.add(new Area("11", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':3, 'funic':-1}"));
            Area area7 = (Area) area5.add(new Area("12", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':3, 'funic':-1}"));

            rootArea.add(new Element("13", TypeElem.GLASS));
            area6.add(new Element("14", TypeElem.GLASS));
            area7.add(new Element("15", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 604009) {
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.ARCH, 1300, 1500, 1200, 1009, 10009, 1009, "");
            rootArea.setParam(8, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.ARCH));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("7", LayoutArea.HORIZONTAL, TypeElem.AREA, 1200));

            rootArea.add(new Element("8", TypeElem.GLASS));
            area3.add(new Element("9", TypeElem.GLASS));
            return new Gson().toJson(rootArea);


        } else if (id == 604010) {
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.ARCH, 1300, 1700, 1400, 1009, 10009, 1009, "");
            rootArea.setParam(29, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.ARCH));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("7", LayoutArea.HORIZONTAL, TypeElem.AREA, 1400));
            Area area4 = (Area) area3.add(new Area("8", LayoutArea.VERTICAL, TypeElem.AREA, 650));
            area3.add(new Element("9", TypeElem.IMPOST));
            Area area5 = (Area) area3.add(new Area("10", LayoutArea.VERTICAL, TypeElem.AREA, 650));
            Area area6 = (Area) area5.add(new Area("11", LayoutArea.VERTICAL, TypeElem.AREA, 550));
            area5.add(new Element("12", TypeElem.IMPOST));
            Area area7 = (Area) area5.add(new Area("13", LayoutArea.VERTICAL, TypeElem.AREA, 850));

            Area area8 = (Area) area6.add(new Area("14", LayoutArea.FULL, TypeElem.FULLSTVORKA, "{'typeOpen':4, 'funic':20}"));
            rootArea.add(new Element("15", TypeElem.GLASS));
            area4.add(new Element("16", TypeElem.GLASS));
            area7.add(new Element("17", TypeElem.GLASS));
            area8.add(new Element("18", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

        } else if (id == 605001) {

            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.TRAPEZE, 1300, 1500, 1200, 1009, 10009, 1009, "");
            rootArea.setParam(8, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME_BOX, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME_BOX, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME_BOX, LayoutArea.ARCH));
            rootArea.add(new Element("5", TypeElem.FRAME_BOX, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.IMPOST));
            Area area3 = (Area) rootArea.add(new Area("7", LayoutArea.HORIZONTAL, TypeElem.AREA, 1200));

            rootArea.add(new Element("8", TypeElem.GLASS));
            area3.add(new Element("9", TypeElem.GLASS));
            return new Gson().toJson(rootArea);

            /*
            Area rootArea = new Area("1", LayoutArea.VERTICAL, TypeElem.TRAPEZE, 1300, 1500, 1200, 1009, 10009, 1009);
            rootArea.setParam(8, id.toString());
            rootArea.add(new Element("2", TypeElem.FRAME, LayoutArea.LEFT));
            rootArea.add(new Element("3", TypeElem.FRAME, LayoutArea.RIGHT));
            rootArea.add(new Element("4", TypeElem.FRAME, LayoutArea.TOP));
            rootArea.add(new Element("5", TypeElem.FRAME, LayoutArea.BOTTOM));

            rootArea.add(new Element("6", TypeElem.IMPOST));
            Area area4 = (Area) rootArea.add(new Area("7", LayoutArea.HORIZONTAL, TypeElem.AREA, 1200));

            area4.add(new Element("8", TypeElem.GLASS));
            return new Gson().toJson(rootArea);*/
        }
        return null;
    }
}
