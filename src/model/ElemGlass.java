package model;

import constr.Specification;
import domain.Artikls;
import domain.Artsvst;
import domain.Sysproa;
import domain.Sysprof;
import enums.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Класс стеклопакета
 */
public class ElemGlass extends ElemBase {

    public static final String RECTANGL = "Прямоугольное";              //
    public static final String RECTANGU_NOT = "Не прямоугольное";       // Параметры
    public static final String ARCHED = "Арочное";                      // формы заполнения
    public static final String ARCHED_NOT = "Не арочное";               //

    private LayoutArea side = LayoutArea.FULL;
    protected float radiusGlass = 0;

    public ElemGlass(AreaSimple root, AreaSimple owner, String id) throws ParseException  {
        this(root, owner, id, null);
    }

    public ElemGlass(AreaSimple root, AreaSimple owner, String id, String paramJson) throws ParseException  {

        super(id);
        this.owner = owner;
        setRoot(root);
        if(paramJson != null) {
            String str = paramJson.replace("'", "\"");
            JSONObject jsonPar = (JSONObject) new JSONParser().parse(str);
            hmParamJson.put(ParamJson.nunic_iwin, jsonPar.get(ParamJson.nunic_iwin.name()));
        }
        initСonstructiv();
        parsingParamJson(root, paramJson);

        if (TypeElem.ARCH == owner.getTypeElem()) {
            setDimension(owner.x1, owner.y1, owner.x2, root.getIwin().getHeightAdd() - owner.y2);
            putHmParam(13015, ARCHED);
        } else {
            setDimension(owner.x1, owner.y1, owner.x2, owner.y2);
            putHmParam(13015, RECTANGL);
        }
    }

    public void initСonstructiv() {

        articlesRec = Artikls.get(getConst(), hmParamJson); //стеклопакет по параметру nunic_iwin
        if(articlesRec == null)   {
            Sysprof sysprofRec = Sysprof.get(getConst(), owner.iwin.nuni); //по умолчанию стеклопакет
            articlesRec = Artikls.get(getConst(), sysprofRec.anumb, false);
        }
        sysproaRec = Sysproa.find(getConst(), owner.iwin.nuni, TypeProfile.FRAME, ProfileSide.Left); //у стеклопакет нет записи в Sysproa пэтому идёт подмена на Frame
        if (articlesRec.asizn == 0) {
            //articlesRec.atech = getRoot().getHmElemFrame().get(LayoutArea.LEFT).getArticlesRec().atech; //а может так!!!
            articlesRec.atech = getRoot().getIwin().getArticlesRec().atech; //TODO наследование дордома Профстроя
        }
        //Цвет стекла
        Artsvst artsvstRec = Artsvst.get2(getConst(), articlesRec.anumb);
        colorBase = artsvstRec.clcod;
        colorInternal = artsvstRec.clcod;
        colorExternal = artsvstRec.clcod;
        specificationRec.setArticlRec(articlesRec);
    }

    public void setSpecifElement() {

        indexUniq(specificationRec);

        float gzazo = Float.valueOf(hmFieldVal.get("GZAZO"));
        if (owner instanceof AreaArch) { //если арка

            ElemFrame elemArch = getRoot().hmElemFrame.get(LayoutArea.ARCH);
            ElemImpost elemImpost = null;  //первый импост в стеклопакете снизу;
            for (ElemBase elemBase : getRoot().getChildList()) {
                if (TypeElem.IMPOST == elemBase.getTypeElem()) {
                    elemImpost = (ElemImpost) elemBase;
                    break;
                }
            }
            y1 = y1 + elemArch.articlesRec.aheig - elemArch.articlesRec.asizn + gzazo;
            y2 = y2 + elemImpost.articlesRec.asizn - gzazo;
            height = y2 - y1;
            specificationRec.height = height;
            double r = ((AreaArch) getRoot()).radiusArch - elemArch.articlesRec.aheig + elemArch.articlesRec.asizn - gzazo;
            double l = Math.sqrt(2 * height * r - height * height);
            x1 = (owner.width / 2) - (float) l;
            x2 = owner.width - x1;
            radiusGlass = (float) r;
            width = x2 - x1;
            
            specificationRec.width = width;

            specificationRec.id = id;
            specificationRec.element = "Арочное";
            specificationRec.setArticlRec(articlesRec);
            specificationRec.colorBase = colorBase;
            specificationRec.colorInternal = colorInternal;
            specificationRec.colorExternal = colorExternal;

        } else {

            ElemBase elemTop = owner.iwin.getHmJoinElem().get(owner.x1 + ":" + owner.y1).elemJoinRight;
            y1 = elemTop.y2 - elemTop.articlesRec.asizn + gzazo;

            ElemBase elemBottom = owner.iwin.getHmJoinElem().get(owner.x1 + ":" + owner.y2).elemJoinRight;
            y2 = elemBottom.y1 + elemBottom.articlesRec.asizn - gzazo;

            ElemBase elemLeft = owner.iwin.getHmJoinElem().get(owner.x1 + ":" + owner.y1).elemJoinBottom;
            x1 = elemLeft.x2 - elemLeft.articlesRec.asizn + gzazo;

            ElemBase elemRight = owner.iwin.getHmJoinElem().get(owner.x2 + ":" + owner.y1).elemJoinBottom;
            x2 = elemRight.x1 + elemRight.articlesRec.asizn - gzazo;

            width = x2 - x1;
            height = y2 - y1;
            specificationRec.width = width;
            specificationRec.height = height;
            specificationRec.id = id;
            specificationRec.element = "Прямоугольное";
            specificationRec.setArticlRec(articlesRec);
            specificationRec.colorBase = colorBase;
            specificationRec.colorInternal = colorInternal;
            specificationRec.colorExternal = colorExternal;
        }
    }

    /**
     * Получить радиус стеклопакета
     */
    public float getRadiusGlass() {
        return radiusGlass;
    }

    @Override
    public void addSpecifSubelem(Specification specif) {

        indexUniq(specif);
        specif.element = "ЗАП";
        if (TypeArtikl.GLASS.value2 == specif.getArticRec().atypp && specif.getArticRec().atypm == 5) { //стеклопакет
            return;

        } else if (TypeArtikl.SHTAPIK.value2 == specif.getArticRec().atypp && specif.getArticRec().atypm == 1) { //штапик
            specif.id = getId();

        } else if (TypeArtikl.KONZEVPROF.value2 == specif.getArticRec().atypp && specif.getArticRec().atypm == 3) { //уплотнитель
            specif.id = getId();

        } else {
            specif.id = getId();
        }
        quantityMaterials(specif);
        specificationRec.getSpecificationList().add(specif);
    }

    @Override
    public LayoutArea getLayout() {
        return side;
    }

    @Override
    public void drawElemList() {
        if (owner instanceof AreaArch) {
            ElemFrame ef = getRoot().hmElemFrame.get(LayoutArea.ARCH);
            float dz = ef.articlesRec.aheig;
            double r = ((AreaArch) owner.getRoot()).radiusArch;
            double ang1 = 90 - Math.toDegrees(Math.asin(getRoot().width / (r * 2)));
            double ang2 = 90 - Math.toDegrees(Math.asin((getRoot().width - 2 * dz) / ((r - dz) * 2)));
            fillArc(getRoot().width / 2 - r + dz, dz, (r - dz) * 2, (r - dz) * 2, ang2, (90 - ang2) * 2); //прорисовка на сцену
        } else {
            fillPoligon(x1, x2, x2, x1, y1, y1, y2, y2);
        }
    }

    @Override
    public TypeElem getTypeElem() {
        return TypeElem.GLASS;
    }

    @Override
    public TypeProfile getTypeProfile() {
        return TypeProfile.UNKNOWN;
    }

    @Override
    public String toString() {
        return super.toString() + ", radiusGlass=" + radiusGlass;
    }
}
