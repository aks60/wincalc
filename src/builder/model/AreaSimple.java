package builder.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dataset.Record;
import domain.eParams;
import domain.eSysprof;
import enums.Layout;
import enums.LayoutJoin;
import enums.TypeJoin;
import java.io.File;
import java.util.EnumMap;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
//import startup.Main;
import builder.Wincalc;
import common.UCom;
import common.eProfile;
import enums.PKjson;
import enums.Type;

public class AreaSimple extends Com5t {

    public EnumMap<Layout, ElemFrame> mapFrame = new EnumMap<>(Layout.class); //список рам в окне 
    public LinkedList<Com5t> listChild = new LinkedList(); //дети

    public AreaSimple(Wincalc iwin, AreaSimple owner, float id, Type type, Layout layout, float width, float height, int color1, int color2, int color3, String param) {
        super(id, iwin, owner);
        this.type = type;
        this.layout = layout;
        this.colorID1 = color1;
        this.colorID2 = color2;
        this.colorID3 = color3;
        if (owner != null && (owner.type == Type.ARCH || owner.type == Type.TRAPEZE)) {
            if (owner.listChild.isEmpty()) { //примитивно, пока всё нестандартное сверху
                this.type = owner.type;
            }
        }
        initСonstructiv(param);
        setLocation(width, height);
        initParametr(param);
    }

    public void initСonstructiv(String param) {
        if (param(param, PKjson.colorID1) != -1) {
            this.colorID1 = param(param, PKjson.colorID1);
        }
        if (param(param, PKjson.sysprofID) != -1) { //профили через параметр
            sysprofRec = eSysprof.find3(param(param, PKjson.sysprofID));
        }
        iwin.listSortAr.add(this);
    }

    protected void setLocation(float width, float height) {
        //Происходит при подкдадке дополнительной ареа в арке
        //или сужении area створки при нахлёсте профилей
        if (owner != null) {
            if (owner.listChild.isEmpty() == true) {

                if (Layout.VERT.equals(owner.layout)) { //сверху вниз
                    float Y2 = (owner.y1 + height > owner.y2) ? owner.y2 : owner.y1 + height;
                    setDimension(owner.x1, owner.y1, owner.x2, Y2);

                } else if (Layout.HORIZ.equals(owner.layout)) { //слева направо
                    float X2 = (owner.x1 + width > owner.x2) ? owner.x2 : owner.x1 + width;
                    setDimension(owner.x1, owner.y1, X2, owner.y2);
                }

            } else {
                for (int index = owner.listChild.size() - 1; index >= 0; --index) { //т.к. this area ёщё не создана начнём с конца
                    if (owner.listChild.get(index) instanceof AreaSimple) {
                        AreaSimple prevArea = (AreaSimple) owner.listChild.get(index);

                        if (Layout.VERT.equals(owner.layout)) { //сверху вниз                            
                            float Y2 = (prevArea.y2 + height > owner.y2) ? owner.y2 : prevArea.y2 + height;
                            setDimension(owner.x1, prevArea.y2, owner.x2, Y2);

                        } else if (Layout.HORIZ.equals(owner.layout)) { //слева направо
                            float X2 = (prevArea.x2 + width > owner.x2) ? owner.x2 : prevArea.x2 + width;
                            setDimension(prevArea.x2, owner.y1, X2, owner.y2);
                        }
                        break;
                    }
                }
            }
        }
    }

    protected void setLocation2(float width, float height) {

        if (owner != null) {
            //Первая area добавляемая в area владельца
            if (owner.listChild.isEmpty() == true) {
                if (Layout.VERT.equals(owner.layout)) { //сверху вниз
                    setDimension(owner.x1, owner.y1, owner.x2, owner.y1 + height);
                } else if (Layout.HORIZ.equals(owner.layout)) { //слева направо
                    setDimension(owner.x1, owner.y1, owner.x1 + width, owner.y2);
                }

                //Aреа перед текущей, т.к. this area ёщё не создана начнём с конца
            } else {
                for (int index = owner.listChild.size() - 1; index >= 0; --index) {
                    if (owner.listChild.get(index) instanceof AreaSimple) {
                        AreaSimple prevArea = (AreaSimple) owner.listChild.get(index);

                        //Если последняя доб. area выходит за коорд. owner area. 
                        //Происходит при подкдадке дополнительной ареа над импостом и
                        //сужении створки при нахлёсте профиля
                        if (Layout.VERT.equals(owner.layout)) { //сверху вниз                            
                            float Y2 = (prevArea.y2 + height > owner.y2) ? owner.y2 : prevArea.y2 + height;
                            setDimension(owner.x1, prevArea.y2, owner.x2, Y2);

                        } else if (Layout.HORIZ.equals(owner.layout)) { //слева направо
                            float X2 = (prevArea.x2 + width > owner.x2) ? owner.x2 : prevArea.x2 + width;
                            setDimension(prevArea.x2, owner.y1, X2, owner.y2);
                        }
                        break;
                    }
                }
            }
        }
    }

    protected void initParametr(String param) {
        try {
            if (param != null && param.isEmpty() == false && param.equals("null") == false) {
                JsonObject jsonObj = new Gson().fromJson(param, JsonObject.class);
                JsonArray jsonArr = jsonObj.getAsJsonArray(PKjson.ioknaParam);
                if (jsonArr != null && !jsonArr.isJsonNull() && jsonArr.isJsonArray()) {
                    jsonArr.forEach(it -> {
                        Record paramRec = eParams.find(it.getAsInt());
                        iwin.mapPardef.put(paramRec.getInt(eParams.params_id), paramRec);
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка:Com5t.parsingParam() " + e);
        }
    }

    public void joining() {

        LinkedList<ElemSimple> impList = UCom.listSortObj(iwin.listSortEl, Type.IMPOST, Type.SHTULP, Type.STOIKA);
        LinkedList<ElemSimple> elemList = UCom.listSortObj(iwin.listSortEl, Type.FRAME_SIDE, Type.STVORKA_SIDE, Type.IMPOST, Type.SHTULP, Type.STOIKA);

        //T - соединения
        for (ElemSimple elemImp : impList) {
            //Цикл по сторонам рамы и импостам (т.к. в створке Т-обр. соединений нет)
            for (ElemSimple elem5e : elemList) {
                if ((elem5e.owner.type == Type.ARCH && elem5e.layout == Layout.TOP) == false) { //для арки inside() не работает
                    elemImp.anglCut[0] = 90;
                    elemImp.anglCut[1] = 90;

                    if (elemImp.owner.layout == Layout.HORIZ) { //Импосты(штульпы...)  расположены по горизонтали слева на право                     
                        if (elem5e.inside(elemImp.x2, elemImp.y2) == true && elem5e != elemImp) { //T - соединение нижнее                              
                            ElemJoining.create(elemImp.joinPoint(0), iwin, TypeJoin.VAR40, LayoutJoin.TBOT, elemImp, elem5e, 90);
                        } else if (elem5e.inside(elemImp.x1, elemImp.y1) == true && elem5e != elemImp) { //T - соединение верхнее                            
                            ElemJoining.create(elemImp.joinPoint(1), iwin, TypeJoin.VAR40, LayoutJoin.TTOP, elemImp, elem5e, 90);
                        }

                    } else {//Импосты(штульпы...) расположены по вертикали снизу вверх
                        if (elem5e.inside(elemImp.x1, elemImp.y1) == true && elem5e != elemImp) { //T - соединение левое                             
                            ElemJoining.create(elemImp.joinPoint(0), iwin, TypeJoin.VAR40, LayoutJoin.TLEFT, elemImp, elem5e, 90);
                        } else if (elem5e.inside(elemImp.x2, elemImp.y2) == true && elem5e != elemImp) { //T - соединение правое                              
                            ElemJoining.create(elemImp.joinPoint(1), iwin, TypeJoin.VAR40, LayoutJoin.TRIGH, elemImp, elem5e, 90);
                        }
                    }
                }
            }
        }
    }

    //Рисуем конструкцию
    public void draw() {
        try {
            //Прорисовка стеклопакетов
            LinkedList<ElemGlass> elemGlassList = UCom.listSortObj(iwin.listSortEl, Type.GLASS);
            elemGlassList.stream().forEach(el -> el.paint());

            //Прорисовка импостов
            LinkedList<ElemCross> elemImpostList = UCom.listSortObj(iwin.listSortEl, Type.IMPOST);
            elemImpostList.stream().forEach(el -> el.paint());

            //Прорисовка штульпов
            LinkedList<ElemCross> elemShtulpList = UCom.listSortObj(iwin.listSortEl, Type.SHTULP);
            elemShtulpList.stream().forEach(el -> el.paint());

            //Прорисовка стоек
            LinkedList<ElemCross> elemStoikaList = UCom.listSortObj(iwin.listSortEl, Type.STOIKA);
            elemStoikaList.stream().forEach(el -> el.paint());

            //Прорисовка рам
            mapFrame.get(Layout.TOP).paint();
            mapFrame.get(Layout.BOTT).paint();
            mapFrame.get(Layout.LEFT).paint();
            mapFrame.get(Layout.RIGHT).paint();

            //Прорисовка створок
            LinkedList<AreaStvorka> elemStvorkaList = UCom.listSortObj(iwin.listSortAr, Type.STVORKA);
            elemStvorkaList.stream().forEach(el -> el.paint());

            //Рисунок в память
            if (iwin.bufferImg != null) {
                ByteArrayOutputStream byteArrOutStream = new ByteArrayOutputStream();
                ImageIO.write(iwin.bufferImg, "png", byteArrOutStream);
                if (eProfile.dev == true) {
                    File outputfile = new File("CanvasImage.png");
                    ImageIO.write(iwin.bufferImg, "png", outputfile);
                }
            }
        } catch (Exception s) {
            System.err.println("Ошибка:AreaSimple.drawWin() " + s);
        }
    }
}
