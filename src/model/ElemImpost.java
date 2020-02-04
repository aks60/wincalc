package model;

import constr.Specification;
import domain.Colslst;
import enums.*;
import domain.Artikls;
import domain.Sysproa;
import javafx.scene.paint.Color;

/**
 * Класс импоста
 */
public class ElemImpost extends ElemBase {

    private LayoutArea side = LayoutArea.NONE;
    protected float truncation = 0; //усечение параметр Артикула1/Артикула2, мм
    protected float anglCut1 = 90; //угол реза импоста
    protected float anglCut2 = 90; //угол реза импоста

    public ElemImpost(AreaSimple root, AreaSimple owner, String id) {

        super(id);
        this.owner = owner;
        setRoot(root);
        this.side = (owner.getLayout() == LayoutArea.HORIZONTAL) ?LayoutArea.VERTICAL :LayoutArea.HORIZONTAL;
        colorBase = getRoot().colorBase;
        colorInternal = getRoot().colorInternal;
        colorExternal = getRoot().colorExternal;
        initСonstructiv();

        //Коррекция положения импоста арки
        if((TypeElem.ARCH == owner.getTypeElem() || TypeElem.TRAPEZE == owner.getTypeElem()) && owner.getChildList().isEmpty()) {

                float dh = articlesRec.aheig / 2;
                owner.addElem(new AreaSimple(owner.getIwin(), root, owner, genId(), LayoutArea.HORIZONTAL, owner.width, dh));
        }
        //if(this != null) System.out.println("ТЕСТОВАЯ ЗАПЛАТКА");
        //Установка координат
        for (int index = owner.getChildList().size() - 1; index >= 0; --index) {
            if (owner.getChildList().get(index) instanceof AreaSimple) {
                ElemBase prevArea = owner.getChildList().get(index); //index указывает на предыдущий элемент
                float dx = articlesRec.asizb;

                if (LayoutArea.VERTICAL.equals(owner.getLayout())) { //сверху вниз
                    setDimension(owner.x1, prevArea.y2 - dx, prevArea.x2, prevArea.y2 + dx);
                    anglHoriz = 0;

                } else if (LayoutArea.HORIZONTAL.equals(owner.getLayout())) { //слева направо
                    setDimension(prevArea.x2 - dx, prevArea.y1, prevArea.x2 + dx, prevArea.y2);
                    anglHoriz = 90;
                }
                break;
            }
        }
    }

    public void initСonstructiv() {
        if (LayoutArea.VERTICAL.equals(owner.getLayout())) { //сверху вниз
            sysproaRec = Sysproa.find(getConst(), owner.iwin.nuni, TypeProfile.IMPOST, ProfileSide.Horiz);
        } else if (LayoutArea.HORIZONTAL.equals(owner.getLayout())) { //слева направо
            sysproaRec = Sysproa.find(getConst(), owner.iwin.nuni, TypeProfile.IMPOST, ProfileSide.Vert);
        }
        articlesRec = Artikls.get(getConst(), sysproaRec.anumb, true);
        specificationRec.setArticlRec(articlesRec);
    }

    public void setSpecifElement(Sysproa sysproaRec) {

        indexUniq(specificationRec);
        specificationRec.element = (LayoutArea.HORIZONTAL == owner.getLayout()) ? LayoutArea.VERTICAL.name : LayoutArea.HORIZONTAL.name;
        specificationRec.setArticlRec(Artikls.get(getConst(), sysproaRec.anumb, false));
        specificationRec.colorBase = colorBase;
        specificationRec.colorInternal = colorInternal;
        specificationRec.colorExternal = colorExternal;
        specificationRec.discount = 0;
        specificationRec.anglHoriz = anglHoriz;

        if (LayoutArea.HORIZONTAL == owner.getLayout()) { //слева направо
            ElemBase elemPrior = prevElem();

            //Элемент слева
            String keyJoinLeft = String.valueOf(elemPrior.x2) + ":" + String.valueOf(elemPrior.y1);
            ElemJoinig joinLeft = getRoot().getHmJoinElem().get(keyJoinLeft);
            ElemBase elemLeft = (joinLeft.joinElement1.equals(this)) ? joinLeft.joinElement2 : joinLeft.joinElement1;
            y1 = elemLeft.y2;
            //элемент справа
            String keyJoinRight = String.valueOf(elemPrior.x2) + ":" + String.valueOf(elemPrior.y2);
            ElemJoinig joinRight = getRoot().getHmJoinElem().get(keyJoinRight);
            ElemBase elemRight = (joinRight.joinElement1.equals(this)) ? joinRight.joinElement2 : joinRight.joinElement1;
            y2 = elemRight.y1;

            specificationRec.width = y2 - y1 + getRoot().iwin.syssizeRec.ssizi * 2 + elemLeft.articlesRec.asizn + elemRight.articlesRec.asizn;;
            specificationRec.height = articlesRec.aheig;

        } else if (LayoutArea.VERTICAL == owner.getLayout()) { //сверху вниз
            ElemBase elemPrior = prevElem();

            //Элемент слева
            String keyJoinLeft = String.valueOf(elemPrior.x1) + ":" + String.valueOf(elemPrior.y2);
            ElemJoinig joinLeft = getRoot().getHmJoinElem().get(keyJoinLeft);
            ElemBase elemLeft = (joinLeft.joinElement1.equals(this)) ? joinLeft.joinElement2 : joinLeft.joinElement1;
            x1 = elemLeft.x2;
            //Элемент справа
            String keyJoinRight = String.valueOf(elemPrior.x2) + ":" + String.valueOf(elemPrior.y2);
            ElemJoinig joinRight = getRoot().getHmJoinElem().get(keyJoinRight);
            ElemBase elemRight = (joinRight.joinElement1.equals(this)) ? joinRight.joinElement2 : joinRight.joinElement1;
            x2 = elemRight.x1;

            specificationRec.width = x2 - x1 + getRoot().iwin.syssizeRec.ssizi * 2 + elemLeft.articlesRec.asizn + elemRight.articlesRec.asizn;
            specificationRec.height = articlesRec.aheig;
        }
        specificationRec.anglCut2 = 90;
        specificationRec.anglCut1 = 90;
    }

    @Override
    public void addSpecifSubelem(Specification specif) {

        indexUniq(specif);
        Artikls specifArtikl = specif.getArticRec();

        //Импост (если элемент включен в список состава)
        if (TypeArtikl.IMPOST.value2 == specifArtikl.atypp && specifArtikl.atypm == 1) {
            specificationRec.setArticlRec(specif.getArticRec()); //= (specif.artikl.equals("-")) ? specif.artikl : "-";
            specificationRec.name = (specif.name.equals("-")) ? specif.name : "-";
            specificationRec.setArticlRec(specif.getArticRec());
            return;

            //Теперь армирование
        } else if (TypeArtikl.ARMIROVANIE.value2 == specifArtikl.atypp && specifArtikl.atypm == 1) {
            specif.element = side.name;

            //if (LayoutArea.HORIZONTAL == side) specif.width = owner.x2 - owner.x1;
            //else if(LayoutArea.VERTICAL == side) specif.width = owner.y2 - owner.y1;

            specif.width = specificationRec.width;
            specif.anglCut2 = 90;
            specif.anglCut1 = 90;

            //Соединитель
        } else if(TypeArtikl.SOEDINITEL.isType(specifArtikl) == true) {

            //specif.colorBase = getRoot().getIwin().getColorNone();
            //specif.colorInternal = getRoot().getIwin().getColorNone();
            //specif.colorExternal = getRoot().getIwin().getColorNone();

            //Остальные
        } else {
            //specif.element = "ЗАП";
        }

        quantityMaterials(specif);
        specificationRec.getSpecificationList().add(specif);
    }

    @Override
    public void setAnglCut(int side, float anglCut) {
        if (side == 1) {
            anglCut1 = anglCut;
        } else {
            anglCut2 = anglCut;
        }
    }

    @Override
    public LayoutArea getLayout() {
        return side;
    }

    @Override
    public void drawElemList() {
        int rgb = Colslst.get2(getRoot().getConst(), colorInternal).cview;

        if (LayoutArea.VERTICAL == owner.getLayout()) {
            strokePolygon(x1, x2, x2, x1, y1, y1, y2, y2, rgb, Color.BLACK, 4);

        } else if (LayoutArea.HORIZONTAL == owner.getLayout()) {
            strokePolygon(x1, x2, x2, x1, y1, y1, y2, y2, rgb, Color.BLACK, 4);
        }
    }

    @Override
    public TypeElem getTypeElem() {
        return TypeElem.IMPOST;
    }

    @Override
    public TypeProfile getTypeProfile() {
        return TypeProfile.IMPOST;
    }

    @Override
    public float getAnglCut(int num) {
        return (num == 1) ? anglCut1 : anglCut2;
    }

    @Override
    public String toString() {
        return super.toString() + ", anglCut=" + anglCut1 + ", anglCut=" + anglCut1;
    }
}
