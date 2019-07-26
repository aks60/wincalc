package model;

import constr.Specification;
import domain.Artikls;
import domain.Sysproa;
import enums.*;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;

/**
 * Створка окна
 */
public class AreaStvorka extends AreaSimple {

    public String handleHeight = ""; //высота ручки
    protected TypeOpen typeOpen = TypeOpen.OM_INVALID; //тип открывания

    public AreaStvorka(IWindows iwin, AreaSimple owner, String id, String paramJson) {

        super(id);
        this.iwin = iwin;
        this.owner = owner;
        this.width = owner.width;
        this.height = owner.height;
        setDimension(owner.x1, owner.y1, owner.x2, owner.y2);
        this.colorBase = iwin.colorBase;
        this.colorInternal = iwin.colorInternal;
        this.colorExternal = iwin.colorExternal;
        try {
            String str = paramJson.replace("'", "\"");
            JSONObject jsoPar = (JSONObject) new JSONParser().parse(str);
            hmParamJson.put(ParamJson.typeOpen, jsoPar.get(ParamJson.typeOpen.name()));
            hmParamJson.put(ParamJson.funic, jsoPar.get(ParamJson.funic.name()));

        } catch (ParseException e) {
            System.err.println("Ошибка AreaStvorka() " + e);
        }
        if (hmParamJson.get(ParamJson.typeOpen) != null) {
            int key = Integer.valueOf(hmParamJson.get(ParamJson.typeOpen).toString());
            for (TypeOpen typeOpen : TypeOpen.values()) {
                if (typeOpen.value == key) {
                    this.typeOpen = typeOpen;
                }
            }
        }
        setRoot(this);
        initСonstructiv();
        parsingParamJson(getRoot(), paramJson);
    }

    public void initСonstructiv() {

        Sysproa sysproaRec = Sysproa.find(getConst(), iwin.nuni, TypeProfile.STVORKA);
        articlesRec = Artikls.get(getConst(), sysproaRec.anumb, true);
        if (articlesRec.asizn == 0) {
            articlesRec.asizn = iwin.articlesRec.asizn; //TODO наследование дордома Профстроя
        }
        specificationRec.setArticlRec(articlesRec);
    }

    public void setCorrection() {

        //Коррекция створки с учётом нахлёста
        ElemJoinig ownerLeftTop = getHmJoinElem().get(x1 + ":" + y1);
        ElemJoinig ownerRightBott = getHmJoinElem().get(x2 + ":" + y2);
        ElemBase elemLeft = null, elemTop = null, elemBott = null, elemRight = null;
        //По умолчанию угловое на ус
        elemLeft = ownerLeftTop.joinElement1;
        elemTop = ownerLeftTop.joinElement2;
        elemBott = ownerRightBott.joinElement2;
        elemRight = ownerRightBott.joinElement1;

        if (ownerLeftTop.varJoin == VariantJoin.VAR4) {
            elemLeft = (ownerLeftTop.elemJoinTop == ownerLeftTop.elemJoinBottom) ? ownerLeftTop.joinElement2 : ownerLeftTop.joinElement1;
            elemTop = (ownerLeftTop.elemJoinTop == ownerLeftTop.elemJoinBottom) ? ownerLeftTop.joinElement1 : ownerLeftTop.joinElement2;
        }
        if (ownerRightBott.varJoin == VariantJoin.VAR4) {
            if (ownerRightBott.elemJoinTop == ownerRightBott.elemJoinBottom && ownerRightBott.elemJoinLeft != null && ownerRightBott.elemJoinRight == null) {
                elemBott = (ownerRightBott.elemJoinLeft == ownerRightBott.elemJoinLeft) ? ownerRightBott.joinElement1 : ownerRightBott.joinElement2;
                elemRight = (ownerRightBott.elemJoinLeft == ownerRightBott.elemJoinLeft) ? ownerRightBott.joinElement2 : ownerRightBott.joinElement1;
            } else {
                elemBott = (ownerRightBott.elemJoinLeft == ownerRightBott.elemJoinLeft) ? ownerRightBott.joinElement2 : ownerRightBott.joinElement1;
                elemRight = (ownerRightBott.elemJoinLeft == ownerRightBott.elemJoinLeft) ? ownerRightBott.joinElement1 : ownerRightBott.joinElement2;
            }
        }
        x1 = elemLeft.x2 - elemLeft.articlesRec.asizn - getIwin().syssizeRec.ssizf;
        y1 = elemTop.y2 - elemLeft.articlesRec.asizn - getIwin().syssizeRec.ssizf;
        x2 = elemRight.x1 + elemLeft.articlesRec.asizn + getIwin().syssizeRec.ssizf;
        y2 = elemBott.y1 + elemLeft.articlesRec.asizn + getIwin().syssizeRec.ssizf;
        width = x2 - x1;
        height = y2 - y1;
        specificationRec.width = width;
        specificationRec.height = height;
        //Коррекция стеклопакета с учётом нахлёста створки
        ElemGlass elemGlass = null;
        for (ElemBase elemBase : getChildList()) {
            if (TypeElem.GLASS == elemBase.getTypeElem()) {
                elemGlass = (ElemGlass) elemBase;
            }
        }
        elemGlass.x1 = x1;
        elemGlass.x2 = x2;
        elemGlass.y1 = y1;
        elemGlass.y2 = y2;
        elemGlass.width = width;
        elemGlass.height = height;
        elemGlass.specificationRec.width = width;
        elemGlass.specificationRec.height = height;

        //Добавим рамы створки
        addRama(new ElemFrame(this, "11", LayoutArea.LEFT));

        addRama(new ElemFrame(this, "12", LayoutArea.RIGHT));

        addRama(new ElemFrame(this, "13", LayoutArea.TOP));

        addRama(new ElemFrame(this, "14", LayoutArea.BOTTOM));
        for (Map.Entry<LayoutArea, ElemFrame> elem : hmElemFrame.entrySet()) {
            elem.getValue().anglCut1 = 45;
            elem.getValue().anglCut2 = 45;
        }

    }

    /**
     * Добавление спецификации в технологический контейнер
     *
     * @param specif - спецификация с установленными значениями параметров
     */
    @Override
    public void addSpecifSubelem(Specification specif) {

        indexUniq(specif);
        /*Artikls cpecifArtikls = specif.getArticRec();
        if (TypeArtikl.STVORKA.isType(cpecifArtikls)) {

        } */
        ElemFrame elem = getHmElemFrame().get(LayoutArea.RIGHT);
        //specif.count = Integer.valueOf(specif.getHmParam(specif.count, 24030));
        elem.specificationRec.getSpecificationList().add(specif);
    }

    @Override
    public void passJoinRama() {

        //Угловое соединение левое верхнее
        ElemJoinig elemJoin1 = new ElemJoinig(getConst());
        elemJoin1.elemJoinRight = hmElemFrame.get(LayoutArea.TOP);
        elemJoin1.elemJoinBottom = hmElemFrame.get(LayoutArea.LEFT);
        elemJoin1.joinElement1 = hmElemFrame.get(LayoutArea.LEFT);
        elemJoin1.joinElement2 = hmElemFrame.get(LayoutArea.TOP);
        elemJoin1.cutAngl1 = 45;
        elemJoin1.cutAngl2 = 45;
        elemJoin1.varJoin = VariantJoin.VAR2;
        getHmJoinElem().put(String.valueOf(x1) + ":" + String.valueOf(y1), elemJoin1);

        //Угловое соединение правое верхнее
        ElemJoinig elemJoin2 = new ElemJoinig(getConst());
        elemJoin2.elemJoinLeft = hmElemFrame.get(LayoutArea.TOP);
        elemJoin2.elemJoinBottom = hmElemFrame.get(LayoutArea.RIGHT);
        elemJoin2.joinElement1 = hmElemFrame.get(LayoutArea.RIGHT);
        elemJoin2.joinElement2 = hmElemFrame.get(LayoutArea.TOP);
        elemJoin2.cutAngl1 = 45;
        elemJoin2.cutAngl2 = 45;
        elemJoin2.varJoin = VariantJoin.VAR2;
        getHmJoinElem().put(String.valueOf(x2) + ":" + String.valueOf(y1), elemJoin2);

        //Угловое соединение правое нижнее
        ElemJoinig elemJoin3 = new ElemJoinig(getConst());
        elemJoin3.elemJoinTop = hmElemFrame.get(LayoutArea.RIGHT);
        elemJoin3.elemJoinLeft = hmElemFrame.get(LayoutArea.BOTTOM);
        elemJoin3.joinElement1 = hmElemFrame.get(LayoutArea.RIGHT);
        elemJoin3.joinElement2 = hmElemFrame.get(LayoutArea.BOTTOM);
        elemJoin3.cutAngl1 = 45;
        elemJoin3.cutAngl2 = 45;
        elemJoin3.varJoin = VariantJoin.VAR2;
        getHmJoinElem().put(String.valueOf(x2) + ":" + String.valueOf(y2), elemJoin3);

        //Угловое соединение левое нижнее
        ElemJoinig elemJoin4 = new ElemJoinig(getConst());
        elemJoin4.elemJoinRight = hmElemFrame.get(LayoutArea.BOTTOM);
        elemJoin4.elemJoinTop = hmElemFrame.get(LayoutArea.LEFT);
        elemJoin4.joinElement1 = hmElemFrame.get(LayoutArea.LEFT);
        elemJoin4.joinElement2 = hmElemFrame.get(LayoutArea.BOTTOM);
        elemJoin4.cutAngl1 = 45;
        elemJoin4.cutAngl2 = 45;
        elemJoin4.varJoin = VariantJoin.VAR2;
        getHmJoinElem().put(String.valueOf(x1) + ":" + String.valueOf(y2), elemJoin4);

        //Прилигающее верхнее
        ElemJoinig elemJoin_top = new ElemJoinig(getConst());
        elemJoin_top.id = genId();
        elemJoin_top.elemJoinTop = hmElemFrame.get(LayoutArea.TOP);
        elemJoin_top.elemJoinBottom = hmElemFrame.get(LayoutArea.TOP);
        elemJoin_top.joinElement1 = hmElemFrame.get(LayoutArea.TOP);
        elemJoin_top.joinElement2 = (owner == owner.getRoot()) ? owner.hmElemFrame.get(LayoutArea.TOP) : owner.getAdjoinedElem(LayoutArea.TOP);
        elemJoin_top.cutAngl1 = 0;
        elemJoin_top.cutAngl2 = 0;
        elemJoin_top.varJoin = VariantJoin.VAR1;
        iwin.getHmJoinElem().put(String.valueOf(x1 + width / 2) + ":" + String.valueOf(y1 + 1), elemJoin_top);

        //Прилигающее нижнее
        ElemJoinig elemJoin_bottom = new ElemJoinig(getConst());
        elemJoin_bottom.id = genId();
        elemJoin_bottom.elemJoinTop = hmElemFrame.get(LayoutArea.BOTTOM);
        elemJoin_bottom.elemJoinBottom = hmElemFrame.get(LayoutArea.BOTTOM);
        elemJoin_bottom.joinElement1 = hmElemFrame.get(LayoutArea.BOTTOM);
        elemJoin_bottom.joinElement2 = (owner == owner.getRoot()) ? owner.hmElemFrame.get(LayoutArea.BOTTOM) : owner.getAdjoinedElem(LayoutArea.BOTTOM);
        elemJoin_bottom.cutAngl1 = 0;
        elemJoin_bottom.cutAngl2 = 0;
        elemJoin_bottom.varJoin = VariantJoin.VAR1;
        getHmJoinElem().put(String.valueOf(x1 + width / 2) + ":" + String.valueOf(y2 - 1), elemJoin_bottom);

        //Прилигающее левое
        ElemJoinig elemJoin_left = new ElemJoinig(getConst());
        elemJoin_left.id = genId();
        elemJoin_left.elemJoinLeft = hmElemFrame.get(LayoutArea.LEFT);
        elemJoin_left.elemJoinRight = hmElemFrame.get(LayoutArea.LEFT);
        elemJoin_left.joinElement1 = hmElemFrame.get(LayoutArea.LEFT);
        elemJoin_left.joinElement2 = (owner == owner.getRoot()) ? owner.hmElemFrame.get(LayoutArea.LEFT) : owner.getAdjoinedElem(LayoutArea.LEFT);
        elemJoin_left.cutAngl1 = 0;
        elemJoin_left.cutAngl2 = 0;
        elemJoin_left.varJoin = VariantJoin.VAR1;
        getHmJoinElem().put(String.valueOf(x1 + 1) + ":" + String.valueOf(y1 + height / 2), elemJoin_left);

        //Прилигающее правое
        ElemJoinig elemJoin_right = new ElemJoinig(getConst());
        elemJoin_right.id = genId();
        elemJoin_right.elemJoinLeft = hmElemFrame.get(LayoutArea.RIGHT);
        elemJoin_right.elemJoinRight = hmElemFrame.get(LayoutArea.RIGHT);
        elemJoin_right.joinElement1 = hmElemFrame.get(LayoutArea.RIGHT);
        elemJoin_right.joinElement2 = (owner == owner.getRoot()) ? owner.hmElemFrame.get(LayoutArea.RIGHT) : owner.getAdjoinedElem(LayoutArea.RIGHT);
        elemJoin_right.cutAngl1 = 0;
        elemJoin_right.cutAngl2 = 0;
        elemJoin_right.varJoin = VariantJoin.VAR1;
        getHmJoinElem().put(String.valueOf(x2 - 1) + ":" + String.valueOf(y1 + height / 2), elemJoin_right);
    }

    @Override
    public void drawElemList() {

        hmElemFrame.get(LayoutArea.TOP).drawElemList();
        hmElemFrame.get(LayoutArea.BOTTOM).drawElemList();
        hmElemFrame.get(LayoutArea.LEFT).drawElemList();
        hmElemFrame.get(LayoutArea.RIGHT).drawElemList();

        if (hmParamJson.get(ParamJson.typeOpen) != null) {
            float dx = 20, dy = 60, X1 = 0, Y1 = 0;
            String value = hmParamJson.get(ParamJson.typeOpen).toString();
            ElemBase elemL = hmElemFrame.get(LayoutArea.LEFT);
            ElemBase elemR = hmElemFrame.get(LayoutArea.RIGHT);
            ElemBase elemT = hmElemFrame.get(LayoutArea.TOP);
            ElemBase elemB = hmElemFrame.get(LayoutArea.BOTTOM);
            if (value.equals("1") || value.equals("3")) {
                X1 = elemR.x1 + (elemR.x2 - elemR.x1) / 2;
                Y1 = elemR.y1 + (elemR.y2 - elemR.y1) / 2;
                strokeLine(elemL.x1, elemL.y1, elemR.x2, elemR.y1 + (elemR.y2 - elemR.y1) / 2, Color.BLACK, 1);
                strokeLine(elemL.x1, elemL.y2, elemR.x2, elemR.y1 + (elemR.y2 - elemR.y1) / 2, Color.BLACK, 1);

            } else if (value.equals("2") || value.equals("4")) {
                X1 = elemL.x1 + (elemL.x2 - elemL.x1) / 2;
                Y1 = elemL.y1 + (elemL.y2 - elemL.y1) / 2;
                strokeLine(elemR.x2, elemR.y1, elemL.x1, elemL.y1 + (elemL.y2 - elemL.y1) / 2, Color.BLACK, 1);
                strokeLine(elemR.x2, elemR.y2, elemL.x1, elemL.y1 + (elemL.y2 - elemL.y1) / 2, Color.BLACK, 1);
            }
            if (value.equals("3") || value.equals("4")) {
                strokeLine(elemB.x1, elemB.y2, elemT.x1 + (elemT.x2 - elemT.x1) / 2, elemT.y1, Color.BLACK, 1);
                strokeLine(elemB.x2, elemB.y2, elemT.x1 + (elemT.x2 - elemT.x1) / 2, elemT.y1, Color.BLACK, 1);
            }
            strokePolygon(X1 - dx, X1 + dx, X1 + dx, X1 - dx, Y1 - dy, Y1 - dy, Y1 + dy, Y1 + dy, 0xFFFFFFFF, Color.BLACK, 2);
            dx = dx - 12;
            Y1 = Y1 + 20;
            strokePolygon(X1 - dx, X1 + dx, X1 + dx, X1 - dx, Y1 - dy, Y1 - dy, Y1 + dy, Y1 + dy, 0xFFFFFFFF, Color.BLACK, 2);
        }
    }

    @Override
    public TypeElem getTypeElem() {
        return TypeElem.FULLSTVORKA;
    }

    @Override
    public TypeOpen getTypeOpen() {
        return typeOpen;
    }
}
