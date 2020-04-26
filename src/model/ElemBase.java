package model;

import constr.Specification;
import domain.Artikls;
import domain.Constructive;
import domain.Parlist;
import domain.Sysproa;
import enums.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.util.*;

//if(vstalstRec.vnumb == "15606071601")) System.out.println("ТЕСТОВАЯ ЗАПЛАТКА");

/**
 * Базовый класс всех элементов конструкции окна
 */
public abstract class ElemBase {

    public static final int SIDE_START = 1; //левая сторона
    public static final int SIDE_END = 2; //правая сторона
    protected static float moveXY = 40; //смещение рисунка
    protected AreaSimple owner = null; //владелец
    protected String id = "0"; //идентификатор элемента
    private AreaSimple root = null; //главное окно
    protected HashMap<ParamJson, Object> hmParamJson = new HashMap(); //параметры элемента
    protected HashMap<String, String> hmFieldVal = new HashMap(); //свойства элемента <имя поля => значение>

    protected float width = 0; //ширина
    protected float height = 0; //высота
    protected float anglHoriz = -1; //угол к горизонту

    protected float x1 = 0;
    protected float y1 = 0;
    protected float x2 = 0;
    protected float y2 = 0;

    protected int colorBase = -1;
    protected int colorInternal = -1;
    protected int colorExternal = -1;

    protected Sysproa sysproaRec = null; //профиль в системе
    protected Artikls articlesRec = null; //мат. средства, основной профиль
    protected Specification specificationRec = null; //спецификация элемента

    /**
     * Конструктор элемента
     */
    public ElemBase(String id) {
        this.id = id;
        specificationRec = new Specification(id, this);
    }

    //TODO Нужна синхронизаци при генерации нового кдюча

    /**
     * Генерация нового ключа
     */
    public String genId() {
        int maxId = 0;
        for (Specification s : specificList())
            if (Integer.valueOf(s.id) > maxId) maxId = Integer.valueOf(s.id);
        //System.out.println(maxId);
        return String.valueOf(++maxId);
    }

    /**
     * Получить id элемента
     */
    public String getId() {
        return id;
    }

    /**
     * Инициализация pro4Params
     */
    protected void parsingParamJson(AreaSimple root, String paramJson) {
        try {
            if (paramJson != null && paramJson.isEmpty() == false) {
                String str = paramJson.replace("'", "\"");
                JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);
                ArrayList<ArrayList<Long>> jsonArr = (JSONArray) jsonObj.get(ParamJson.pro4Params.name());
                if (jsonArr instanceof ArrayList && jsonArr.isEmpty() == false) {

                    hmParamJson.put(ParamJson.pro4Params, jsonObj.get(ParamJson.pro4Params.name())); //первый вариант

                    HashMap<Integer, Object[]> hmValue = new HashMap();
                    for (ArrayList<Long> jsonRec : jsonArr) {
                        int pnumb = Integer.valueOf(String.valueOf(jsonRec.get(0)));
                        Parlist rec = Parlist.get(root.getConst(), jsonRec.get(0), jsonRec.get(1));
                        if (pnumb < 0 && rec != null)
                            hmValue.put(pnumb, new Object[]{rec.pname, rec.znumb, 0});
                    }
                    hmParamJson.put(ParamJson.pro4Params2, hmValue); //второй вариант
                }
            }
        } catch (ParseException e) {
            System.err.println("Ошибка ElemBase.parsingParamJson() " + e);
        }
    }

    protected void resposeParamJson() {
        LinkedList<ElemBase> elList = root.getElemList(TypeElem.GLASS, TypeElem.FULLSTVORKA);
        elList.add(root);
        for (ElemBase el : elList) {
            HashMap<Integer, Object[]> map = (HashMap) el.getHmParamJson().get(ParamJson.pro4Params2);
            el.setHmParamJson(null);
            if (map != null) {
                JSONObject ojs = new JSONObject();
                ArrayList arr = new ArrayList();
                for (Map.Entry<Integer, Object[]> e : map.entrySet()) {
                    Object[] o = e.getValue();
                    ArrayList a = new ArrayList();
                    a.add(e.getKey());
                    a.add(o[1]);
                    a.add(o[2]);
                    arr.add(a);
                }
                ojs.put("id", el.id);
                ojs.put("elemType", el.getTypeElem());
                ojs.put("pro4Params", arr);
                el.setHmParamJson(ojs);
                //System.out.println(ojs.toJSONString());
            }
        }
    }

    /**
     * Идентификатор элмента
     */
    public void indexUniq(Specification sp) {

        AreaSimple area = (getOwner() instanceof AreaSimple) ? getOwner() : getOwner().getOwner();
        sp.areaId = area.getId();
        sp.elemId = this.id;
        sp.elemType = String.valueOf(this.getTypeElem().value);
    }

    /**
     * Получить порядок расположения элементов в контейнере
     */
    public abstract LayoutArea getLayout();

    /**
     * Заполнить главную спецификацию элемента
     */
    public void setDimension(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Добавить спецификацию в состав элемента
     */
    public abstract void addSpecifSubelem(Specification specification);

    /**
     * Типы элементов
     */
    public abstract TypeElem getTypeElem();

    /**
     * Типы профилей
     */
    public abstract TypeProfile getTypeProfile();

    /**
     * Контейнер владелец элемента
     */
    public AreaSimple getOwner() {
        return owner;
    }

    /**
     * Спецификация элемента и его сост...
     */
    public ArrayList<Specification> getSpecificationList() {
        ArrayList<Specification> specList = new ArrayList();
        specList.addAll(specificationRec.getSpecificationList());
        specList.add(specificationRec);
        return specList;
    }

    /**
     * Список элементов в контейнере родителя
     */
    public LinkedList<ElemBase> getChildList() {
        return new LinkedList();
    }

    /**
     * Угол реза
     *
     * @param side сторона элемента
     */
    public void setAnglCut(int side, float anglCut) {
    }

    /**
     * Угол реза
     *
     * @param side сторона элемента
     */
    public float getAnglCut(int side) {
        return -1;
    }

    /**
     * Map имя поля => значение
     *
     * @return
     */
    public HashMap<String, String> getHmFieldVal() {
        return hmFieldVal;
    }

    /**
     * Мап параметров элемента
     */
    public String getHmParam(Object def, int... p) {
        return specificationRec.getHmParam(def, p);
    }

    public void putHmParam(Integer key, String val) {
        specificationRec.putHmParam(key, val);
    }

    /**
     * Установить главное окно
     */
    public void setRoot(AreaSimple root) {
        this.root = root;
    }

    /**
     * Получить главное окно
     */
    public AreaSimple getRoot() {
        return root;
    }

    /**
     * Получить соединение с БД
     */
    public Constructive getConst() {
        return getRoot().getIwin().constr;
    }

    /**
     * Получить спецификацию элемента ElemBase
     */
    public Specification getSpecificationRec() {
        return specificationRec;
    }

    /**
     * Расчёт материала в зависимости от ед. измерения
     */
    public void quantityMaterials(Specification specif) {

        if (MeasUnit.PIE.value == specif.getArticRec().atypi) { //шт
            specif.count = Integer.valueOf(specif.getHmParam(specif.count, 11030, 33030, 14030));

            if (specif.getHmParam(0, 33050).equals("0") == false) {
                float widthBegin = Float.valueOf(specif.getHmParam(0, 33040));
                int countStep = Integer.valueOf(specif.getHmParam(1, 33050, 33060));
                float count = (specificationRec.width - widthBegin) / Integer.valueOf(specif.getHmParam(1, 33050, 33060));

                if ((specificationRec.width - widthBegin) % Integer.valueOf(specif.getHmParam(1, 33050, 33060)) == 0)
                    specif.count = (int) count;
                else specif.count = (int) count + 1;

                if (widthBegin != 0) ++specif.count;
            }
        } else if (MeasUnit.METR.value == specif.getArticRec().atypi) { //метры
            if (specif.width == 0)
                specif.width = specificationRec.width; //TODO вообще это неправильно, надо проанализировать. Без этой записи специф. считается неправильно.
            specif.width = Float.valueOf(specif.getHmParam(specif.width, 34070)); //Длина, мм (должна быть первой)
            specif.width = specif.width + Float.valueOf(specif.getHmParam(0, 34051)); //Поправка, мм
        }
    }

    public Sysproa getSysproaRec() {
        return sysproaRec;
    }

    public Artikls getArticlesRec() {
        return articlesRec;
    }

    /**
     * Поучить следующий элемент в контейнере
     */
    public ElemBase nextElem() {

        for (ListIterator<ElemBase> iter = getAreaElemList().listIterator(); iter.hasNext(); ) {
            if (iter.next().id == id) {
                if (iter.hasNext()) {
                    return iter.next();
                }
                return iter.previous();
            }
        }
        return owner;
    }

    /**
     * Получить предыдущий элемент в контейнере
     */
    public ElemBase prevElem() {

        for (ListIterator<ElemBase> iter = getAreaElemList().listIterator(); iter.hasNext(); ) {
            if (iter.next().id == id) { //находим элемент в списке и от него движемся вверх
                iter.previous();
                if (iter.hasPrevious()) {
                    return iter.previous();
                }
                return iter.next();
            }
        }
        return owner;
    }

    /**
     * Ширина
     */
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Высота
     */
    public float getHeight() {
        return height;
    }

    /**
     * Ограничение угла к горизонту
     */
    public float getAnglHoriz() {
        return anglHoriz;
    }

    /**
     * Список area и impost
     */
    public LinkedList<ElemBase> getAreaElemList() {

        LinkedList<ElemBase> elemList = new LinkedList();
        for (ElemBase elemBase : owner.getChildList()) {
            if (TypeElem.AREA == elemBase.getTypeElem() || TypeElem.IMPOST == elemBase.getTypeElem()) {
                elemList.add(elemBase);
            }
        }
        return elemList;
    }

    /**
     * Тип открывания
     */
    public TypeOpen getTypeOpen() {
        return TypeOpen.OM_INVALID;
    }

    /**
     * Цвет профиля
     */
    public int getColor(int index) {
        if (index == 1) return colorBase;
        else if (index == 2) return colorInternal;
        else if (index == 3) return colorExternal;
        else return -1;
    }

    /**
     * Координаты элемента
     */
    public float getXY(int index) {
        float xy[] = {x1, y1, x2, y2};
        return xy[index - 1];
    }

    /**
     * Прорисовка элемента на холсте
     */
    public void drawElemList() {
    }

    /**
     * Получение спецификации окна
     */
    public ArrayList<Specification> specificList() {

        LinkedList<ElemBase> elemList = getRoot().getElemList(null);
        ArrayList<Specification> specList = new ArrayList();
        for (ElemBase elemBase : elemList) {
            specList.addAll(elemBase.getSpecificationList());
        }
        Collections.sort(specList, Collections.reverseOrder((a, b) -> a.name.compareTo(b.name)));
        return specList;
    }

    public void setHmParamJson(HashMap<ParamJson, Object> hmParamJson) {
        this.hmParamJson = hmParamJson;
    }

    public HashMap<ParamJson, Object> getHmParamJson() {
        return hmParamJson;
    }

    /**
     * Прорисовка замкнутой линии
     */

    protected void strokeLine(float x1, float y1, float x2, float y2, Color rdbStroke, int lineWidth) {

        float scale = getRoot().getIwin().getScale();
        Graphics2D gc = getRoot().getIwin().getImg().createGraphics();
        gc.setStroke(new BasicStroke((float) lineWidth)); //толщина линии
        gc.setColor(java.awt.Color.BLACK);
        float h = getRoot().getIwin().getHeightAdd() - getRoot().getIwin().getHeight();
        gc.drawLine((int) ((x1 + moveXY) * scale), (int) ((y1 + moveXY + h) * scale), (int) ((x2 + moveXY) * scale), (int) ((y2 + moveXY + h) * scale));
    }

    protected void strokePolygon(float x1, float x2, float x3, float x4, float y1,
                                 float y2, float y3, float y4, int rgbFill, Color rdbStroke, double lineWidth) {

        float scale = getRoot().getIwin().getScale();
        Graphics2D gc = getRoot().getIwin().getImg().createGraphics();
        gc.setStroke(new BasicStroke((float) lineWidth)); //толщина линии
        gc.setColor(java.awt.Color.BLACK);
        float h = getRoot().getIwin().getHeightAdd() - getRoot().getIwin().getHeight();
        gc.drawPolygon(new int[]{(int) ((x1 + moveXY) * scale), (int) ((x2 + moveXY) * scale), (int) ((x3 + moveXY) * scale), (int) ((x4 + moveXY) * scale)},
                new int[]{(int) ((y1 + moveXY + h) * scale), (int) ((y2 + moveXY + h) * scale), (int) ((y3 + moveXY + h) * scale), (int) ((y4 + moveXY + h) * scale)}, 4);
        gc.setColor(new java.awt.Color(rgbFill & 0x000000FF, (rgbFill & 0x0000FF00) >> 8, (rgbFill & 0x00FF0000) >> 16));
        gc.fillPolygon(new int[]{(int) ((x1 + moveXY) * scale), (int) ((x2 + moveXY) * scale), (int) ((x3 + moveXY) * scale), (int) ((x4 + moveXY) * scale)},
                new int[]{(int) ((y1 + moveXY + h) * scale), (int) ((y2 + moveXY + h) * scale), (int) ((y3 + moveXY + h) * scale), (int) ((y4 + moveXY + h) * scale)}, 4);
    }

    protected void strokeArc(double x, double y, double w, double h, double startAngle,
                             double arcExtent, int rdbStroke, double lineWidth) {

        //System.out.println("x= " + x + " y = " + y + " w= " + w + " h= " + h + " startAngle=" + startAngle 
                //+ " arcExtent=" + arcExtent + " rdbStroke=" + rdbStroke + " lineWidth=" + lineWidth);
        float scale = getRoot().getIwin().getScale();
        Graphics2D gc = getRoot().getIwin().getImg().createGraphics();
        gc.setStroke(new BasicStroke((float) lineWidth * scale)); //толщина линии
        gc.setColor(new java.awt.Color(rdbStroke & 0x000000FF, (rdbStroke & 0x0000FF00) >> 8, (rdbStroke & 0x00FF0000) >> 16));
        gc.drawArc((int) ((x + moveXY) * scale), (int) ((y + moveXY) * scale), (int) (w * scale), (int) (h * scale), (int) startAngle, (int) arcExtent);
    }

    protected void fillArc(double x, double y, double w, double h, double startAngle, double arcExtent) {

        float scale = getRoot().getIwin().getScale();
        Graphics2D gc = getRoot().getIwin().getImg().createGraphics();
        gc.setColor(new java.awt.Color(226, 255, 250));
        gc.fillArc((int) ((x + moveXY) * scale), (int) ((y + moveXY) * scale), (int) (w * scale), (int) (h * scale), (int) startAngle, (int) arcExtent);
    }

    protected void fillPoligon(float x1, float x2, float x3, float x4, float y1, float y2, float y3, float y4) {

        float scale = getRoot().getIwin().getScale();
        Graphics2D gc = getRoot().getIwin().getImg().createGraphics();
        gc.setColor(new java.awt.Color(226, 255, 250));
        float h = getRoot().getIwin().getHeightAdd() - getRoot().getIwin().getHeight();
        gc.fillPolygon(new int[]{(int) ((x1 + moveXY) * scale), (int) ((x2 + moveXY) * scale), (int) ((x3 + moveXY) * scale), (int) ((x4 + moveXY) * scale)},
                new int[]{(int) ((y1 + moveXY + h) * scale), (int) ((y2 + moveXY + h) * scale), (int) ((y3 + moveXY + h) * scale), (int) ((y4 + moveXY + h) * scale)}, 4);
    }

    /**
     * Сравнение элементов по id
     */
    public boolean equals(Object obj) {
        return id == ((ElemBase) obj).id;
    }
    
    public String toString() {           
        return "ELEM: owner=" + owner.id + ", id=" + id + ", x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2;
    }    
}
