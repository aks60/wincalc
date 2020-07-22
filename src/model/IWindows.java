package model;

import constr.*;
import script.Area;
import domain.*;
import enums.LayoutArea;
import enums.ProfileSide;
import enums.TypeElem;
import enums.TypeProfile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Фабрика (строитель) окна
 */
public class IWindows {

    protected static boolean production = false;
    protected final Constructive constr;
    //protected static final HashMap<Short, Constructive> constrMap = new HashMap<>();
    protected int nuni = 0;
    protected Artikls articlesRec = null;  //главный артикл системы профилей
    protected String prj = "empty";
    protected float percentMarkup = 0;  //процентная надбавка
    protected final int colorNone = 1005;  //без цвета (возвращаемое значение по умолчанию)
    protected float width = 0.f;  //ширина окна
    protected float height = 0.f;  //высота окна
    protected float heightAdd = 0.f; //арка, трапеция, треугольник
    private float scale = .8f; //массштаб рисунка
    protected int colorBase = -1; //базовый цвет
    protected int colorInternal = -1; //внутренний цвет
    protected int colorExternal = -1; //внещний цвет
    private byte[] bufferSmallImg = null; //рисунок без линий
    private byte[] bufferFullImg = null; //полный рисунок
    protected String labelSketch = "empty"; //надпись на эскизе
    private AreaSimple rootArea = null;
    private HashMap<Integer, String> hmPro4Params = new HashMap();
    protected Syssize syssizeRec = null; //константы
    protected BufferedImage img = null;  //образ рисунка
    protected HashMap<Integer, Object[]> hmParamDef = new HashMap(); //параметры по умолчанию
    protected HashMap<String, ElemJoinig> hmJoinElem = new HashMap(); //список соединений рам и створок
    protected HashMap<String, LinkedList<Object[]>> drawMapLineList = new HashMap(); //список линий окон

    public IWindows(Constructive constr) {
        this.constr = constr;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String productParamsJson = Area.test(604005); // тут удобно редактировать тестовый проект //////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    /**
     * Калькуляция объектной модели окна, вывод графического интерфейса
     */
    public AreaSimple create(String productJson) {

        hmParamDef.clear();
        hmJoinElem.clear();
        drawMapLineList.clear();

        //Парсинг входного скрипта
        AreaSimple mainArea = parsingScript(productJson);
        img = new BufferedImage((int) (width + 260),
                (int) (heightAdd + 260), BufferedImage.TYPE_INT_RGB); //инит. буфера рисунка

        //Загрузим параметры по умолчанию
        ArrayList<Parsysp> parsyspList = Parsysp.find(constr, nuni);
        parsyspList.stream().forEach(record -> hmParamDef.put(record.pnumb, new Object[]{record.ptext, record.znumb, 0}));

        //Создание root окна
        if (mainArea instanceof AreaSquare) rootArea = (AreaSquare) mainArea; //калькуляция простого окна
        else if (mainArea instanceof AreaArch) rootArea = (AreaArch) mainArea; //калькуляция арки
        else if (mainArea instanceof AreaTrapeze) rootArea = (AreaTrapeze) mainArea; //калькуляция трапеции

        //Инициализация объектов калькуляции
        LinkedList<AreaSimple> areaList = rootArea.getElemList(TypeElem.AREA); //список контейнеров
        LinkedList<AreaStvorka> stvorkaList = rootArea.getElemList(TypeElem.FULLSTVORKA); //список створок
        EnumMap<LayoutArea, ElemFrame> hmElemRama = rootArea.hmElemFrame; //список рам
        
        CalcConstructiv constructiv = new CalcConstructiv(mainArea); //конструктив
        CalcTariffication tariffic = new CalcTariffication(mainArea); //класс тарификации

        //Соединения рамы
        rootArea.passJoinRama();  //обход соединений и кальк. углов рамы
        areaList.stream().forEach(area -> area.passJoinArea(hmJoinElem)); //обход(схлопывание) соединений рамы
        hmJoinElem.entrySet().stream().forEach(elemJoin -> elemJoin.getValue().initJoin()); //инит. варианта соединения

        //Соединения створок
        stvorkaList.stream().forEach(stvorka -> stvorka.setCorrection()); //коррекция размера створки с учётом нахлёста и построение рамы створки
        stvorkaList.stream().forEach(stvorka -> stvorka.passJoinRama()); //обход соединений и кальк. углов створок

        //Список набора элементов
        LinkedList<ElemBase> elemList = rootArea.getElemList(null);  //(важно! получаем после построения створки)

        //Калькуляция
        try {            
            constructiv.compositionFirst();                //составы
            constructiv.joiningFirst();                    //соединения
            constructiv.fillingFirst();                    //заполнения
            constructiv.fittingFirst();                    //фурнитура
            constructiv.kitsFirst();                       //комплекты
            tariffic.calculate(elemList);                  //тарификация
            rootArea.drawWin(1f, bufferFullImg, true);     //full рис.
            //rootArea.drawWin(.3f, bufferSmallImg, false);  //small рис.
            //rootArea.resposeParamJson();                   //выходные пар.
        } catch (Exception e) {
            System.out.println("Ошибка калькуляции конструктива IWin.create() " + e);
        }

        //Тестирование
        if (production == false) {
            System.out.println("okno=" + prj);
            //Specification.write_txt(constr, rootArea.specificList()); //вывод на тестирование в DLL
            Specification.write_txt2(constr, rootArea.specificList()); //вывод на тестирование в DLL
            //model.Main.compareIWin(rootArea.specificList(), prj, true); //сравнение спецификации с профстроем 
            //System.out.println(productJson); //вывод на консоль json
            //CalcBase.test_param(ParamSpecific.paramSum); //тестирование парам. спецификации
            //Main.print_joining(hmJoinElem); //соединения на консоль
            //hmJoinElem.entrySet().forEach(it -> System.out.println("id=" + it.getValue().id + "  JOIN=" + it.getValue().varJoin   + "  POINT:" + it.getKey() + " (" + it.getValue().joinElement1.specificationRec.artikl + ":" + it.getValue().joinElement2.specificationRec.artikl + ")")); 
            
            
            //elemList.stream().forEach(el -> System.out.println(el));
        }
        return rootArea;
    }

    /**
     * Парсим входное json окно и строим объектную модель окна
     */
    private AreaSimple parsingScript(String json) {
        String layoutObj = null;
        AreaSimple rootArea = null;
        try {
            JSONParser parser = new JSONParser();
            //java.io.Reader json2 = new  java.io.FileReader("src\\resource\\script.json");
            //java.io.Reader json2 = new  java.io.FileReader("X:\\_aks\\Razio58 арка 1ств (пустая areaId).json");
            JSONObject mainObj = (JSONObject) parser.parse(json);
            String id = mainObj.get("id").toString();
            String paramJson = mainObj.get("paramJson").toString();
            nuni = Integer.parseInt(mainObj.get("nuni").toString());
            if (mainObj.get("prj") != null) prj = mainObj.get("prj").toString();
            width = Float.parseFloat(mainObj.get("width").toString());
            height = Float.parseFloat(mainObj.get("heightLow").toString());
            heightAdd = Float.parseFloat(mainObj.get("height").toString());

            Sysproa sysproaRec = Sysproa.find(constr, nuni, TypeProfile.FRAME, ProfileSide.Left);
            articlesRec = Artikls.get(constr, sysproaRec.anumb, true); //главный артикл системы профилей
            syssizeRec = Syssize.find(constr, articlesRec.sunic); //системные константы

            //Цвета
            colorBase = Integer.parseInt(mainObj.get("colorBase").toString());
            colorInternal = Integer.parseInt(mainObj.get("colorInternal").toString());
            colorExternal = Integer.parseInt(mainObj.get("colorExternal").toString());

            //Определим напрвление построения окна
            layoutObj = mainObj.get("layoutArea").toString();
            LayoutArea layoutRoot = ("VERTICAL".equals(layoutObj)) ? LayoutArea.VERTICAL : LayoutArea.HORIZONTAL;

            if ("SQUARE".equals(mainObj.get("elemType").toString())) {
                rootArea = new AreaSquare(this, id, layoutRoot, width, height, colorBase, colorInternal, colorExternal, paramJson); //простое

            } else if ("TRAPEZE".equals(mainObj.get("elemType").toString())) {
                rootArea = new AreaTrapeze(this, id, layoutRoot, width, height, colorBase, colorInternal, colorExternal, paramJson); //трапеция

            } else if ("TRIANGL".equals(mainObj.get("elemType").toString())) {
                rootArea = new AreaTriangl(this, id, layoutRoot, width, height, colorBase, colorInternal, colorExternal, paramJson); //треугольник

            } else if ("ARCH".equals(mainObj.get("elemType").toString())) {
                rootArea = new AreaArch(this, id, layoutRoot, width, height, colorBase, colorInternal, colorExternal, paramJson); //арка

            }

            //Добавим рамы
            for (Object elementRama : (JSONArray) mainObj.get("elements")) {
                JSONObject elemRama = (JSONObject) elementRama;

                if (TypeElem.FRAME_BOX.name().equals(elemRama.get("elemType"))) {

                    if (LayoutArea.LEFT.name().equals(elemRama.get("layoutRama"))) {
                        ElemFrame ramaLeft = rootArea.addRama(new ElemFrame(rootArea, elemRama.get("id").toString(), LayoutArea.LEFT));

                    } else if (LayoutArea.RIGHT.name().equals(elemRama.get("layoutRama"))) {
                        ElemFrame ramaRight = rootArea.addRama(new ElemFrame(rootArea, elemRama.get("id").toString(), LayoutArea.RIGHT));

                    } else if (LayoutArea.TOP.name().equals(elemRama.get("layoutRama"))) {
                        ElemFrame ramaTop = rootArea.addRama(new ElemFrame(rootArea, elemRama.get("id").toString(), LayoutArea.TOP));

                    } else if (LayoutArea.BOTTOM.name().equals(elemRama.get("layoutRama"))) {
                        ElemFrame ramaBottom = rootArea.addRama(new ElemFrame(rootArea, elemRama.get("id").toString(), LayoutArea.BOTTOM));

                    } else if (LayoutArea.ARCH.name().equals(elemRama.get("layoutRama"))) {
                        ElemFrame ramaArch = rootArea.addRama(new ElemFrame(rootArea, elemRama.get("id").toString(), LayoutArea.ARCH));
                    }
                }
            }

            //Элементы окна
            for (Object objL1 : (JSONArray) mainObj.get("elements")) { //первый уровень
                JSONObject elemL1 = (JSONObject) objL1;
                if (TypeElem.AREA.name().equals(elemL1.get("elemType"))) {
                    AreaSimple areaSimple1 = parsingAddArea(rootArea, rootArea, elemL1);

                    for (Object objL2 : (JSONArray) elemL1.get("elements")) { //второй уровень
                        JSONObject elemL2 = (JSONObject) objL2;
                        if (TypeElem.AREA.name().equals(elemL2.get("elemType"))) {
                            AreaSimple areaSimple2 = parsingAddArea(rootArea, areaSimple1, elemL2);

                            for (Object objL3 : (JSONArray) elemL2.get("elements")) {  //третий уровень
                                JSONObject elemL3 = (JSONObject) objL3;
                                if (TypeElem.AREA.name().equals(elemL3.get("elemType"))) {
                                    AreaSimple areaSimple3 = parsingAddArea(rootArea, areaSimple2, elemL3);

                                    for (Object objL4 : (JSONArray) elemL3.get("elements")) {  //четвёртый уровень
                                        JSONObject elemL4 = (JSONObject) objL4;
                                        if (TypeElem.AREA.name().equals(elemL4.get("elemType"))) {
                                            AreaSimple areaSinple4 = parsingAddArea(rootArea, areaSimple3, elemL4);
                                        } else parsingAddElem(rootArea, areaSimple3, elemL4);
                                    }

                                } else parsingAddElem(rootArea, areaSimple2, elemL3);
                            }
                        } else parsingAddElem(rootArea, areaSimple1, elemL2);
                    }
                } else parsingAddElem(rootArea, rootArea, elemL1);
            }
        } catch (ParseException e) {
            System.out.println("Ошибка Iwindows.parsingScript() " + e);
        } catch (Exception e2) {
            System.out.println("Ошибка Iwindows.parsingScript() " + e2);
        }
        return rootArea;
    }

    private AreaSimple parsingAddArea(AreaSimple rootArea, AreaSimple ownerArea, JSONObject objArea) {

        float width = (ownerArea.getLayout() == LayoutArea.VERTICAL) ? ownerArea.width : Float.valueOf(objArea.get("width").toString());
        float height = (ownerArea.getLayout() == LayoutArea.VERTICAL) ? Float.valueOf(objArea.get("height").toString()) : ownerArea.height;

        String layoutObj = objArea.get("layoutArea").toString();
        LayoutArea layoutArea = ("VERTICAL".equals(layoutObj)) ? LayoutArea.VERTICAL : LayoutArea.HORIZONTAL;
        String id = objArea.get("id").toString();
        AreaSimple elemArea = new AreaSimple(this, rootArea, ownerArea, id, layoutArea, width, height);
        ownerArea.addElem(elemArea);
        return elemArea;
    }

    private void parsingAddElem(AreaSimple root, AreaSimple owner, JSONObject elem) throws ParseException {

        if (TypeElem.IMPOST.name().equals(elem.get("elemType"))) {
            owner.addElem(new ElemImpost(root, owner, elem.get("id").toString()));

        } else if (TypeElem.GLASS.name().equals(elem.get("elemType"))) {
            if (elem.get("paramJson") != null) {
                owner.addElem(new ElemGlass(root, owner, elem.get("id").toString(), elem.get("paramJson").toString()));
            } else {
                owner.addElem(new ElemGlass(root, owner, elem.get("id").toString()));
            }

        } else if (TypeElem.FULLSTVORKA.name().equals(elem.get("elemType"))) {

            AreaStvorka elemStvorka = new AreaStvorka(this, owner, elem.get("id").toString(), elem.get("paramJson").toString());
            owner.addElem(elemStvorka);
            //Уровень ниже
            for (Object obj : (JSONArray) elem.get("elements")) { //т.к. может быть и глухарь
                JSONObject elem2 = (JSONObject) obj;
                if (TypeElem.GLASS.name().equals(elem2.get("elemType"))) {
                    if (elem2.get("paramJson") != null) {
                        elemStvorka.addElem(new ElemGlass(root, elemStvorka, elem2.get("id").toString(), elem2.get("paramJson").toString()));
                    } else {
                        elemStvorka.addElem(new ElemGlass(root, elemStvorka, elem2.get("id").toString()));
                    }
                }
            }
        }
    }

    public void putDrawLineList(String key, Object... list) {
        LinkedList<Object[]> list2 = drawMapLineList.get(key);
        if (list2 == null) list2 = new LinkedList();
        list2.add(list);
    }

    public LinkedList<Object[]> getDrawLineList(String key) {
        return drawMapLineList.get(key);
    }

    public Artikls getArticlesRec() {
        return articlesRec;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getHeightAdd() {
        return heightAdd;
    }

    public int getColorNone() {
        return colorNone;
    }

    public int getColorProfile(int index) {
        int color[] = {colorBase, colorInternal, colorExternal};
        return color[index - 1];
    }

    public HashMap<Integer, Object[]> getHmParamDef() {
        return hmParamDef;
    }

    public HashMap<String, ElemJoinig> getHmJoinElem() {
        return hmJoinElem;
    }

    public void setLabelSketch(String labelSketch) {
        this.labelSketch = labelSketch;
    }

    public String getLabelSketch() {
        return labelSketch;
    }

    public int getNuni() {
        return nuni;
    }

    public String getPrj() {
        return prj;
    }

    public BufferedImage getImg() {
        return img;
    }

    public float getPercentMarkup() {
        return percentMarkup;
    }

    public Constructive getConstr() {
        return constr;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public byte[] getBufferFullImg() {
        return bufferFullImg;
    }

    public void setBufferFullImg(byte[] bufferFullImg) {
        this.bufferFullImg = bufferFullImg;
    }

    public byte[] getBufferSmallImg() {
        return bufferSmallImg;
    }

    public void setBufferSmallImg(byte[] bufferSmallImg) {
        this.bufferSmallImg = bufferSmallImg;
    }
}
