package model;

import domain.Sysdata;
import domain.Sysproa;
import enums.LayoutArea;
import enums.TypeElem;

import javax.annotation.Nullable;

/**
 * Является родителем для всех элементов образуюших арку.
 * Контролирует и управляет поведение элементов арки.
 */
public class AreaArch extends AreaSimple {

    protected double radiusArch = 0; //радиус арки

    public AreaArch(IWindows iwin, String id, LayoutArea layout, float width, float height, int colorBase, int colorInterna, int colorExternal, String paramJson) throws Exception {
        super(null, id, layout, width, height, colorBase, colorInterna, colorExternal);
        this.iwin = iwin;
        owner = this;
        iwin.percentMarkup = Sysdata.get(iwin.constr, 2101).sflot;
        setRoot(this);
        parsingParamJson(this, paramJson);
        if(width < iwin.heightAdd - height) {
            throw new Exception("ОШИБКА! Радиус арки не может быть меньще её высоты.");
        }
    }

    public void setSpecifElement(ElemFrame elemFrame, Sysproa sysproaRec) {

        if (LayoutArea.ARCH == elemFrame.side) {
            float ssizp = iwin.syssizeRec.ssizp;
            double angl = Math.toDegrees(Math.asin(width / (getRadiusArch() * 2)));
            elemFrame.length = (float) (Math.PI * getRadiusArch() * angl * 2) / 180;
            elemFrame.specificationRec.width = elemFrame.length + ssizp; // ssizp * 2; //TODO ВАЖНО !!! расчет требует корректировки
            elemFrame.specificationRec.height = elemFrame.articlesRec.aheig;
        }
    }

    /**
     * Получить радиус арки
     */
    public double getRadiusArch() {
        return radiusArch;
    }

    @Override
    public void passJoinRama() {

        String key1 = String.valueOf(x1) + ":" + String.valueOf(y1);
        String key2 = String.valueOf(x2) + ":" + String.valueOf(y1);
        String key3 = String.valueOf(x2) + ":" + String.valueOf(y2);
        String key4 = String.valueOf(x1) + ":" + String.valueOf(y2);

        //Угловое соединение левое верхнее
        ElemJoinig elemJoin1 = new ElemJoinig(getConst());
        elemJoin1.elemJoinRight = hmElemFrame.get(LayoutArea.ARCH);
        elemJoin1.elemJoinBottom = hmElemFrame.get(LayoutArea.LEFT);
        getHmJoinElem().put(key1, elemJoin1);
        float dz = elemJoin1.elemJoinRight.articlesRec.aheig;
        float h = iwin.getHeightAdd() - height;
        float w = width;
        double r = (Math.pow(w / 2, 2) + Math.pow(h, 2)) / (2 * h);  //R = (L2 + H2) / 2H - радиус арки
        radiusArch = r; //запишем радиус дуги в AreaArch
        double angl = 90 - Math.toDegrees(Math.asin(w / (r * 2))); // Math.toDegrees() — преобразование радианов в градусы ... Math.asin() — арксинус
        double ang2 = 90 - Math.toDegrees(Math.asin((w - 2 * dz) / ((r - dz) * 2)));
        double a1 = r * Math.sin(Math.toRadians(angl));
        double a2 = (r - dz) * Math.sin(Math.toRadians(ang2));
        double ang3 = 90 - Math.toDegrees(Math.atan((a1 - a2) / dz)); //угол реза рамы
        double a3 = Math.sqrt(Math.pow(r, 2) + Math.pow(r - dz, 2) - 2 * r * (r - dz) * Math.cos(Math.toRadians(ang2 - angl)));
        double ang4 = 90 - Math.toDegrees((Math.acos((Math.pow(a3, 2) + Math.pow(r, 2) - Math.pow(r - dz, 2)) / (2 * r * a3))));
        elemJoin1.cutAngl1 = (float) ang3; //угол реза 1
        elemJoin1.cutAngl2 = (float) ang4; //угол реза 2
        elemJoin1.anglProf = (float) ang4;
        elemJoin1.elemJoinRight.setAnglCut(ElemBase.SIDE_END, elemJoin1.cutAngl2);
        elemJoin1.elemJoinBottom.setAnglCut(ElemBase.SIDE_START, elemJoin1.cutAngl1);

        //Угловое соединение правое верхнее
        ElemJoinig elemJoin2 = new ElemJoinig(getConst());
        elemJoin2.elemJoinLeft = hmElemFrame.get(LayoutArea.ARCH);
        elemJoin2.elemJoinBottom = hmElemFrame.get(LayoutArea.RIGHT);
        getHmJoinElem().put(key2, elemJoin2);
        elemJoin2.cutAngl1 = (float) ang3;
        elemJoin2.cutAngl2 = (float) ang4;
        elemJoin2.anglProf = (float) ang4;
        elemJoin2.elemJoinLeft.setAnglCut(ElemBase.SIDE_START, elemJoin2.cutAngl2);
        elemJoin2.elemJoinBottom.setAnglCut(ElemBase.SIDE_END, elemJoin2.cutAngl1);

        //Угловое соединение правое нижнее
        ElemJoinig elemJoin3 = new ElemJoinig(getConst());
        elemJoin3.elemJoinTop = hmElemFrame.get(LayoutArea.RIGHT);
        elemJoin3.elemJoinLeft = hmElemFrame.get(LayoutArea.BOTTOM);
        getHmJoinElem().put(key3, elemJoin3);
        elemJoin3.cutAngl1 = 45;
        elemJoin3.cutAngl2 = 45;
        elemJoin3.anglProf = 90;
        elemJoin3.elemJoinTop.setAnglCut(ElemBase.SIDE_START, elemJoin3.cutAngl1);
        elemJoin3.elemJoinLeft.setAnglCut(ElemBase.SIDE_END, elemJoin3.cutAngl2);

        //Угловое соединение левое нижнее
        ElemJoinig elemJoin4 = new ElemJoinig(getConst());
        elemJoin4.elemJoinRight = hmElemFrame.get(LayoutArea.BOTTOM);
        elemJoin4.elemJoinTop = hmElemFrame.get(LayoutArea.LEFT);
        getHmJoinElem().put(key4, elemJoin4);
        elemJoin4.cutAngl1 = 45;
        elemJoin4.cutAngl2 = 45;
        elemJoin4.anglProf = 90;
        elemJoin4.elemJoinRight.setAnglCut(ElemBase.SIDE_START, elemJoin4.cutAngl2);
        elemJoin4.elemJoinTop.setAnglCut(ElemBase.SIDE_END, elemJoin4.cutAngl1);
    }

    @Override
    public TypeElem getTypeElem() {
        return TypeElem.ARCH;
    }
}
