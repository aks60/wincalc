package model;

import constr.Specification;
import enums.VariantJoin;
import domain.Connlst;
import domain.Connvar;
import domain.Constructive;

import java.util.ArrayList;

/**
 * Соединения элементов конструкции окна
 */
public class ElemJoinig {

    public static final int FIRST_SIDE = 1; //первая сторона
    public static final int SECOND_SIDE = 2; //вторая сторона

    public String id = "0"; //идентификатор соединения
    private final Constructive constructive;

    protected ElemBase elemJoinTop = null;      //
    protected ElemBase elemJoinBottom = null;   // Элементы соединения, временно для
    protected ElemBase elemJoinLeft = null;     // схлопывания повторяющихся обходов
    protected ElemBase elemJoinRight = null;    //

    protected VariantJoin varJoin = VariantJoin.EMPTY;    // Вариант соединения
    protected ElemBase joinElement1 = null;  // Элемент соединения 1
    protected ElemBase joinElement2 = null;  // Элемент соединения 2

    protected float cutAngl1 = 90;    //Угол реза1
    protected float cutAngl2 = 90;    //Угол реза2
    protected float anglProf = 0;     //Угол между профилями
    public String costsJoin = "";     //Трудозатраты, ч/ч.

    protected Connlst connlstRec = null;
    protected Connvar connvarRec = null;
    protected ArrayList<Specification> specificationList = new ArrayList();

    public ElemJoinig(Constructive constructive) {
        this.constructive = constructive;
    }

    /**
     * Инициализация вариантов соединения и первичная углов реза
     */
    public void initJoin() {

        if (elemJoinLeft == null && elemJoinRight != null && elemJoinBottom != null && elemJoinTop == null) { //угловое соединение левое верхнее
            joinElement1 = elemJoinBottom;
            joinElement2 = elemJoinRight;
            varJoin = VariantJoin.VAR2;
            elemJoinBottom.setAnglCut(ElemBase.SIDE_START, cutAngl1);
            elemJoinRight.setAnglCut(ElemBase.SIDE_END, cutAngl2);

        } else if (elemJoinLeft == null && elemJoinRight != null && elemJoinBottom == null && elemJoinTop != null) { //угловое соединение левое нижнее
            joinElement1 = elemJoinTop;
            joinElement2 = elemJoinRight;
            varJoin = VariantJoin.VAR2;
            elemJoinTop.setAnglCut(ElemBase.SIDE_END, cutAngl1);
            elemJoinRight.setAnglCut(ElemBase.SIDE_START, cutAngl2);

        } else if (elemJoinLeft != null && elemJoinRight == null && elemJoinBottom != null && elemJoinTop == null) { //угловое соединение правое верхнее
            joinElement2 = elemJoinLeft;
            joinElement1 = elemJoinBottom;
            varJoin = VariantJoin.VAR2;
            elemJoinLeft.setAnglCut(ElemBase.SIDE_START, cutAngl2);
            elemJoinBottom.setAnglCut(ElemBase.SIDE_END, cutAngl1);

        } else if (elemJoinLeft != null && elemJoinRight == null && elemJoinTop != null && elemJoinBottom == null) { //угловое соединение правое нижнее
            joinElement2 = elemJoinLeft;
            joinElement1 = elemJoinTop;
            varJoin = VariantJoin.VAR2;
            elemJoinLeft.setAnglCut(ElemBase.SIDE_END, cutAngl2);
            elemJoinTop.setAnglCut(ElemBase.SIDE_START, cutAngl1);

        } else if ((elemJoinLeft != null && elemJoinBottom != null && elemJoinRight != null && elemJoinTop == null) && elemJoinLeft.equals(elemJoinRight)) { //T - соединение верхнее
            joinElement2 = elemJoinLeft;
            joinElement1 = elemJoinBottom;
            varJoin = VariantJoin.VAR4;
            elemJoinBottom.setAnglCut(ElemBase.SIDE_START, cutAngl1);

        } else if (elemJoinLeft != null && elemJoinTop != null && elemJoinRight != null && elemJoinBottom == null && elemJoinLeft.equals(elemJoinRight)) { //T - соединение нижнее
            joinElement1 = elemJoinTop;
            joinElement2 = elemJoinLeft;
            varJoin = VariantJoin.VAR4;
            elemJoinTop.setAnglCut(ElemBase.SIDE_END, cutAngl1);

        } else if (elemJoinLeft == null && elemJoinTop != null && elemJoinRight != null && elemJoinBottom != null && elemJoinTop.equals(elemJoinBottom)) { //T - соединение левое
            joinElement2 = elemJoinTop;
            joinElement1 = elemJoinRight;
            varJoin = VariantJoin.VAR4;
            elemJoinRight.setAnglCut(ElemBase.SIDE_START, cutAngl2);

        } else if (elemJoinLeft != null && elemJoinTop != null && elemJoinRight == null && elemJoinBottom != null && elemJoinTop.equals(elemJoinBottom)) { //T - соединение правое
            joinElement2 = elemJoinTop;
            joinElement1 = elemJoinLeft;
            varJoin = VariantJoin.VAR4;
            elemJoinLeft.setAnglCut(ElemBase.SIDE_END, cutAngl2);

        } else {
            System.out.println("Инициализация соединения не выполнена  - LEFT.id=" + elemJoinLeft.id + " - RIGHT.id=" + elemJoinRight.id + " - BOTT.id=" + elemJoinBottom.id + " - TOP.id=" + elemJoinTop.id);
        }
    }

    public float getAnglJoin(int side) {
        return (side == 1) ? cutAngl1 : cutAngl2;
    }

    public VariantJoin getVarJoin() {
        return varJoin;
    }

    public ElemBase getJoinElement(int i) {
        return (i == 1) ? joinElement1 : joinElement2;
    }
}
