package constr;

import domain.*;
import enums.LayoutArea;
import enums.MeasUnit;
import enums.TypeElem;
import model.AreaSimple;
import model.ElemBase;

import java.util.LinkedList;

/**
 * Расчёт стоимости элементов окна
 * <p>
 * Запускаю цикл по элементам конструкции окна.
 * Произвожу расчёт  собес-сти за ед. изм. , калькуляцию
 * количества без отхода и количества с отходом.
 * <p>
 * Запускаю цикл по правилам расчёта и пытаюсь попасть в правила расчёта.
 * Если удаётся попасть увеличиваю себестоимости в rkoef раз и на rpric величину надбавки.
 * После завершения цикла правил расчёта произвожу расчёт собес-сти с отходом.
 * <p>
 * Запускаю цикл по списку спецификаций элемента конструкции. Произвожу расчёт
 * собес-сти за ед. изм. , калькуляцию количества без отхода и количества с отходом.
 * <p>
 * Снова цикл по правилам расчёта. Если удаётся попасть в правила расчёта увеличиваю
 * себестоимости в rkoef раз и на rpric величину надбавки. После завершения цикла правил
 * расчёта произвожу расчёт собес-сти с отходом При завершении итерации перехожу к новому
 * элементу конструкции и т.д.
 */
public class CalcTariffication extends CalcBase {

    private Sysprof sysprofRec = null;

    public CalcTariffication(AreaSimple root) {
        super(root);
        sysprofRec = Sysprof.get(constr, root.getIwin().getNuni());
    }

    /**
     * @param elemList - список элементов окна рамы, импосты, стеклопакеты...
     */
    public void calculate(LinkedList<ElemBase> elemList) {

        //Расчёт  собес-сть за ед. изм. по таблице мат. ценностей
        for (ElemBase elemBase : elemList) {

            Specification specifElemRec = elemBase.getSpecificationRec();

            //if(specifElemRec.artikl.equals("4x9x4x9x4")) System.out.println("ТЕСТОВАЯ ЗАПЛАТКА");

            calcCostPrice(specifElemRec);
            for (Specification specifSubelemRec : specifElemRec.getSpecificationList()) {

                calcCostPrice(specifSubelemRec);
            }
        }
        //Увеличение собестоимости в rkoef раз и на rpric величину надбавки
        for (ElemBase elemBase : elemList) {

            Specification specifElemRec = elemBase.getSpecificationRec();

            //if(specifElemRec.artikl.equals("4x9x4x9x4")) System.out.println("ТЕСТОВАЯ ЗАПЛАТКА");

            TypeElem type = elemBase.getOwner().getTypeElem();
            //Цикл по правилам расчёта
            for (Ruleclk ruleclkRec : constr.ruleclkList) {

                //Только эти параметры используются в БиМакс
                //фильтр по полю riskl, rused и colorXXX таблицы rulecls
                if (TypeElem.GLASS == elemBase.getTypeElem()) {//фильтр для стеклопакета

                    if (ruleclkRec.riskl == PAR0) {//не проверять форму
                        checkRuleColor(ruleclkRec, specifElemRec);

                    } else if (ruleclkRec.riskl == PAR10 && TypeElem.TRAPEZE == type) { //не прямоугольное, не арочное заполнение
                        checkRuleColor(ruleclkRec, specifElemRec);

                    } else if (ruleclkRec.riskl == PAR12 && TypeElem.ARCH == type) {//не прямоугольное заполнение с арками
                        checkRuleColor(ruleclkRec, specifElemRec);
                    }
                } else if (ruleclkRec.riskl == PAR4 && TypeElem.FRAME_BOX == type && LayoutArea.ARCH == elemBase.getLayout()) { //фильтр для арки профиля AYPC.W62.0101
                    checkRuleColor(ruleclkRec, specifElemRec); //профиль с радиусом

                } else {
                    if (ruleclkRec.riskl == PAR0) {  //не проверять форму
                        checkRuleColor(ruleclkRec, specifElemRec); //всё остальное не проверять форму
                    }
                }
            }

            specifElemRec.outPrice = specifElemRec.inPrice * specifElemRec.quantity2;            //собестоимости с отходом
            Grupart grupartRec = Grupart.find(constr, elemBase.getArticlesRec().munic).get(0);
            specifElemRec.inCost = specifElemRec.outPrice * grupartRec.mkoef * sysprofRec.koef; //стоимость без скидки
            specifElemRec.inCost = specifElemRec.inCost + (specifElemRec.inCost / 100) * root.getIwin().getPercentMarkup();
            specifElemRec.outCost = specifElemRec.inCost;                                        //стоимость со скидкой

            for (Specification specifSubelemRec : specifElemRec.getSpecificationList()) {
                for (Ruleclk ruleclkRec : constr.ruleclkList) { //цикл по правилам расчёта

                    if (ruleclkRec.riskl == PAR0) {  //не проверять форму
                        checkRuleColor(ruleclkRec, specifSubelemRec);
                    }
                }
                specifSubelemRec.outPrice = specifSubelemRec.inPrice * specifSubelemRec.quantity2; //расчёт собестоимости с отходом
                Grupart grupartRec2 = Grupart.find(constr, specifSubelemRec.getArticRec().munic).get(0);
                specifSubelemRec.inCost = specifSubelemRec.outPrice * grupartRec2.mkoef * sysprofRec.koef;
                specifSubelemRec.inCost = specifSubelemRec.inCost + (specifSubelemRec.inCost / 100) * root.getIwin().getPercentMarkup();
                specifSubelemRec.outCost = specifSubelemRec.inCost;
            }
        }

        //Расчёт веса элемента конструкции
        for (ElemBase el : elemList) {
            for (Specification spec : el.getSpecificationList()) {
                spec.weight = spec.quantity * spec.getArticRec().amass;
            }
        }
    }

    /**
     * Фильтр по полю riskl, colorXXX таблицы rulecls
     */
    private void checkRuleColor(Ruleclk ruleclkRec, Specification specifRec) {

        Integer[] arr1 = parserInt(ruleclkRec.rcodm);
        Integer[] arr2 = parserInt(ruleclkRec.rcod1);
        Integer[] arr3 = parserInt(ruleclkRec.rcod2);
        if (specifRec.artikl == ruleclkRec.anumb || ((specifRec.getArticRec().atypm * 100 + specifRec.getArticRec().atypp) == ruleclkRec.rused)) { //артикл ИЛИ тип ИЛИ подтип совпали
            if (compareColor(arr1, specifRec.colorBase) == true && compareColor(arr2, specifRec.colorInternal) == true && compareColor(arr3, specifRec.colorExternal) == true) {
                if (ruleclkRec.rallp == 0) {

                    boolean ret = compareFloat(ruleclkRec.rleng, specifRec.quantity2);
                    if (ret == true) {
                        specifRec.inPrice = specifRec.inPrice * ruleclkRec.rkoef + ruleclkRec.rpric;  //увеличение собестоимости в rkoef раз и на rpric величину надбавки
                    }

                } else if (ruleclkRec.rallp == 1) { //по использованию c расчётом общего количества по артикулу, подтипу, типу

                    LinkedList<ElemBase> elemList = root.getElemList(null);
                    float quantity3 = 0;
                    if (ruleclkRec.rused < 0) { //по артикулу
                        for (ElemBase elemBase : elemList) {
                            if (elemBase.getSpecificationRec().artikl.equals(specifRec.artikl)) {

                                quantity3 = quantity3 + elemBase.getSpecificationRec().quantity;
                            }
                            for (Specification specifRec2 : elemBase.getSpecificationRec().getSpecificationList()) {
                                if (specifRec2.artikl.equals(specifRec.artikl)) {

                                    quantity3 = quantity3 + specifRec2.quantity;
                                }
                            }
                        }
                    } else { //по подтипу, типу

                        for (ElemBase elemBase : elemList) {
                            Specification specifRec2 = elemBase.getSpecificationRec();
                            if (specifRec2.getArticRec().atypm * 100 + specifRec2.getArticRec().atypp == ruleclkRec.rused) {

                                quantity3 = quantity3 + elemBase.getSpecificationRec().quantity;
                            }
                            for (Specification specifRec3 : specifRec2.getSpecificationList()) {
                                if (specifRec3.getArticRec().atypm * 100 + specifRec3.getArticRec().atypp == ruleclkRec.rused) {

                                    quantity3 = quantity3 + specifRec3.quantity;
                                }
                            }
                        }
                    }
                    boolean ret = compareFloat(ruleclkRec.rleng, quantity3);
                    if (ret == true) {
                        specifRec.inPrice = specifRec.inPrice * ruleclkRec.rkoef + ruleclkRec.rpric;  //увеличение собестоимости в rkoef раз и на rpric величину надбавки
                    }
                }
            }
        }
    }

    /**
     * Считает тариф для заданного артикула заданных цветов по
     * таблице PRO4_ARTSVST (Материальные ценности -> нижняя таблица)
     */
    public void calcCostPrice(Specification specificRec) {

        Colslst baseColorRec = Colslst.get2(constr, specificRec.colorBase);        //
        Colslst insideColorRec = Colslst.get2(constr, specificRec.colorInternal);  //описание текстур
        Colslst outsideColorRec = Colslst.get2(constr, specificRec.colorExternal); //

        Correnc kursBaseRec = Correnc.get(constr, specificRec.getArticRec().cnumb);    // кросс-курс валюты для основной текстуры
        Correnc kursNoBaseRec = Correnc.get(constr, specificRec.getArticRec().cnumt);  // кросс-курс валюты для неосновных текстур (внутренняя, внешняя, двухсторонняя)

        //Цикл по тарификационной таблице мат ценностей
        for (Artsvst artsvst : Artsvst.get(constr, specificRec.getArticRec().anumb)) {

            float artsvstRowTariff = 0;
            boolean artsvstRowUsed = false;

            if (artsvst.clpra != 0 && insideColorRec.cnumb == outsideColorRec.cnumb
                    && CanBeUsedAsInsideColor(artsvst) && CanBeUsedAsOutsideColor(artsvst)
                    && IsArtTariffAppliesForColor(artsvst, insideColorRec)) { //если двухсторонняя текстура

                artsvstRowTariff += (artsvst.clpra * Math.max(insideColorRec.koef1, insideColorRec.koef2) / kursNoBaseRec.ckurs);
                artsvstRowUsed = true;

            } else {

                //Object ooo = CanBeUsedAsBaseColor(artsvst);
                //Object ooo2 = IsArtTariffAppliesForColor(artsvst, baseColorRec);

                if (CanBeUsedAsBaseColor(artsvst) && IsArtTariffAppliesForColor(artsvst, baseColorRec)) { //подбираем тариф основной текстуры
                    Grupcol grupcolRec = Grupcol.get(constr, baseColorRec.cgrup);
                    artsvstRowTariff += (artsvst.clprc * baseColorRec.ckoef * grupcolRec.gkoef) / kursBaseRec.ckurs;
                    artsvstRowUsed = true;
                }
                if (CanBeUsedAsInsideColor(artsvst) && IsArtTariffAppliesForColor(artsvst, insideColorRec)) { //подбираем тариф внутренней текстуры
                    Grupcol grupcolRec = Grupcol.get(constr, insideColorRec.cgrup);
                    artsvstRowTariff += (artsvst.clpr1 * insideColorRec.koef1 * grupcolRec.gkoef) / kursNoBaseRec.ckurs;
                    artsvstRowUsed = true;
                }
                if (CanBeUsedAsOutsideColor(artsvst) && IsArtTariffAppliesForColor(artsvst, outsideColorRec)) { //подбираем тариф внешней текстуры
                    Grupcol grupcolRec = Grupcol.get(constr, outsideColorRec.cgrup);
                    artsvstRowTariff += (artsvst.clpr2 * outsideColorRec.koef2 * grupcolRec.gkoef) / kursNoBaseRec.ckurs;
                    artsvstRowUsed = true;
                }
            }
            if (artsvstRowUsed && artsvst.cminp != 0 && specificRec.quantity != 0 && artsvstRowTariff * specificRec.quantity < artsvst.cminp) {
                artsvstRowTariff = artsvst.cminp / specificRec.quantity;    //используем минимальный тариф (CMINP)
            }
            if (artsvstRowUsed) {
                specificRec.inPrice = specificRec.inPrice + (artsvstRowTariff * artsvst.knakl);
            }
        }
        //TODO Нужна доработка для расчёта по минимальному тарифу. См. dll VirtualPro4::CalcArtTariff
        if (MeasUnit.METR.value == specificRec.getArticRec().atypi) { //метры
            specificRec.quantity = specificRec.width / 1000;

        } else if (MeasUnit.METR2.value == specificRec.getArticRec().atypi) { //кв. метры
            specificRec.quantity = specificRec.width * specificRec.height / 1000000;

        } else if (MeasUnit.PIE.value == specificRec.getArticRec().atypi) { //шт.
            specificRec.quantity = specificRec.count;
        }
        specificRec.quantity2 = specificRec.quantity + (specificRec.quantity * specificRec.getArticRec().aouts / 100);
    }
}
