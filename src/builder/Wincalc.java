package builder;

import builder.model.ElemGlass;
import builder.model.AreaStvorka;
import builder.model.AreaArch;
import builder.model.AreaTriangl;
import builder.model.ElemJoining;
import builder.model.AreaSimple;
import builder.model.AreaRectangl;
import builder.model.AreaTrapeze;
import builder.model.ElemCross;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataset.Record;
import domain.eArtikl;
import domain.eSyssize;
import domain.eSyspar1;
import domain.eSysprof;
import enums.UseArtiklTo;
import builder.making.Cal5e;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import builder.making.Specific;
import builder.making.Tariffic;
import builder.making.Joining;
import builder.making.Elements;
import builder.making.Filling;
import builder.making.Furniture;
import builder.model.AreaDoor;
import builder.model.ElemFrame;
import builder.model.ElemSimple;
import builder.script.GsonRoot;
import builder.script.GsonElem;
import common.ArrayList2;
import common.LinkedList2;
import enums.Form;
import enums.Layout;
import enums.Type;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import javax.swing.ImageIcon;

public class Wincalc {

    public Connection conn;
    public Integer nuni = 0;
    public Record artiklRec = null; //главный артикл системы профилей   
    public Record syssizeRec = null; //константы    
    public float genId = 0; //генерация ключа в спецификации

    public float width = 0.f; //ширина окна
    public float height = 0.f; //высота окна
    public float heightAdd = 0.f; //дополнительная высота
    public int colorID1 = -1;  //базовый цвет
    public int colorID2 = -1;  //внутренний цвет
    public int colorID3 = -1;  //внещний цвет

    public BufferedImage bufferImg = null;  //образ рисунка
    public ImageIcon imageIcon = null; //рисунок конструкции
    public Graphics2D gc2d = null; //графический котекст рисунка  
    public double scale = 1; //коэффициент сжатия

    public AreaSimple rootArea = null; //главное окно кострукции
    public GsonRoot rootGson = null; //главное окно кострукции в формате gson
    public Form form = Form.NUM0; //форма контура 

    public HashMap<Integer, Record> mapPardef = new HashMap(); //пар. по умолчанию + наложенные пар. клиента
    public LinkedList2<AreaSimple> listSortAr = new LinkedList2(); //список ElemSimple
    public LinkedList2<ElemSimple> listSortEl = new LinkedList2(); //список ElemSimple
    public LinkedList2<ElemSimple> listTreeEl = new LinkedList2(); //список ElemSimple
    public HashMap<String, ElemJoining> mapJoin = new HashMap(); //список соединений рам и створок 
    public ArrayList2<Specific> listSpec = new ArrayList2(); //спецификация
    public Cal5e calcJoining, calcElements, calcFilling, calcFurniture, calTariffication; //объекты калькуляции конструктива

    public Wincalc() {
    }

    public Wincalc(String productJson) {
        build(productJson);
    }

    public AreaSimple build(String productJson) {

        genId = 0;
        form = Form.NUM0;
        heightAdd = 0.f;
        Arrays.asList((List) listSortAr, (List) listSortEl, (List) listSpec, (List) listTreeEl).forEach(el -> el.clear());
        Arrays.asList(mapPardef, mapJoin).forEach(el -> el.clear());

        //Парсинг входного скрипта
        parsing(productJson);

        rootArea.joining(); //соединения ареа
        listSortAr.stream().filter(el -> el.type == Type.STVORKA).collect(toList()).forEach(el -> el.joining()); //соединения створок
        listSortEl.forEach(it -> it.setSpecific()); //спецификация профилей

        return rootArea;
    }

    // Парсим входное json окно и строим объектную модель окна
    private void parsing(String json) {
        try {
            //System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(new com.google.gson.JsonParser().parse(json))); //для тестирования
            //System.out.println(new GsonBuilder().create().toJson(new com.google.gson.JsonParser().parse(json))); //для тестирования
            Gson gson = new GsonBuilder().create();
            rootGson = gson.fromJson(json, GsonRoot.class);
            rootGson.setParent(rootGson);

            //Инит конструктива
            this.nuni = rootGson.nuni();
            this.form = Form.NUM0.get(rootGson.form);
            this.width = rootGson.width();
            this.height = rootGson.height();
            this.heightAdd = rootGson.heightAdd;
            this.colorID1 = rootGson.color1;
            this.colorID2 = rootGson.color2;
            this.colorID3 = rootGson.color3;
            this.artiklRec = eArtikl.find(eSysprof.find2(nuni, UseArtiklTo.FRAME).getInt(eSysprof.artikl_id), true);
            this.syssizeRec = eSyssize.find(artiklRec);
            eSyspar1.find(nuni).stream().forEach(rec -> mapPardef.put(rec.getInt(eSyspar1.params_id), rec)); //загрузим параметры по умолчанию

            //Главное окно
            if (Type.RECTANGL == rootGson.type()) {
                rootArea = new AreaRectangl(this, rootGson, colorID1, colorID2, colorID3); //простое
            } else if (Type.DOOR == rootGson.type()) {
                rootArea = new AreaDoor(this, rootGson, colorID1, colorID2, colorID3); //дверь                
            } else if (Type.TRAPEZE == rootGson.type()) {
                rootArea = new AreaTrapeze(this, rootGson, colorID1, colorID2, colorID3); //трапеция
            } else if (Type.TRIANGL == rootGson.type()) {
                rootArea = new AreaTriangl(this, rootGson, colorID1, colorID2, colorID3); //треугольник
            } else if (Type.ARCH == rootGson.type()) {
                rootArea = new AreaArch(this, rootGson, colorID1, colorID2, colorID3); //арка
            }

            //Добавим рамы
            for (GsonElem gsonElem : rootGson.childs()) {
                if (Type.FRAME_SIDE == gsonElem.type()) {
                    rootArea.mapFrame.put(gsonElem.layout(), new ElemFrame(rootArea, gsonElem.id(), gsonElem.layout(), gsonElem.param()));
                }
            }

            //Всё остальное
            recursion(rootArea, rootGson);

        } catch (Exception e) {
            System.err.println("Ошибка:Wincalc.parsing() " + e);
        }
    }

    private void recursion(AreaSimple owner, GsonElem gsonElem) {
        try {
            LinkedHashMap<AreaSimple, GsonElem> hm = new LinkedHashMap();
            for (GsonElem el : gsonElem.childs()) {

                //Добавим Area
                if (Type.STVORKA == el.type()) {
                    AreaSimple area5e = new AreaStvorka(Wincalc.this, owner, el.id(), el.param());
                    owner.listChild.add(area5e);
                    hm.put(area5e, el);
                } else if (Type.AREA == el.type()) {
                    AreaSimple area5e = new AreaSimple(Wincalc.this, owner, el.id(), el.type(), el.layout(), el.width(), el.height(), -1, -1, -1, null);
                    owner.listChild.add(area5e);
                    hm.put(area5e, el);

                    //Добавим Elements
                } else if (Type.IMPOST == el.type() || Type.SHTULP == el.type() || Type.STOIKA == el.type()) {
                    owner.listChild.add(new ElemCross(owner, el.type(), el.id(), el.param()));

                } else if (Type.GLASS == el.type()) {
                    owner.listChild.add(new ElemGlass(owner, el.id(), el.param()));
                }
            }

            //Теперь вложенные элементы
            for (Map.Entry<AreaSimple, GsonElem> entry : hm.entrySet()) {
                recursion(entry.getKey(), entry.getValue());
            }

        } catch (Exception e) {
            System.err.println("Ошибка:Wincalc.recursion() " + e);
        }
    }

    //Конструктив и тарификация 
    public void constructiv(boolean norm_otx) {
        try {
            calcJoining = new Joining(this); //соединения
            calcJoining.calc();
            calcElements = new Elements(this); //составы
            calcElements.calc();
            calcFilling = new Filling(this); //заполнения
            calcFilling.calc();
            calcFurniture = new Furniture(this); //фурнитура 
            calcFurniture.calc();
            calTariffication = new Tariffic(this, norm_otx); //тарификация
            calTariffication.calc();

            for (ElemSimple elemRec : listSortEl) {
                if (elemRec.spcRec.artikl.trim().charAt(0) != '@') {
                    listSpec.add(elemRec.spcRec);
                }
                for (Specific specific : elemRec.spcRec.spcList) {
                    if (specific.artikl.trim().charAt(0) != '@') {
                        listSpec.add(specific);
                    }
                }
            }
            Collections.sort(listSpec, (o1, o2) -> (o1.place.subSequence(0, 3) + o1.name + o1.width).compareTo(o2.place.subSequence(0, 3) + o2.name + o2.width));

        } catch (Exception e) {
            System.err.println("Ошибка:Wincalc.constructiv(" + e);
        }
    }
}
