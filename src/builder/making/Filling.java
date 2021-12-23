package builder.making;

import dataset.Record;
import domain.eArtikl;
import domain.eGlasdet;
import domain.eGlasgrp;
import domain.eGlasprof;
import domain.eSysprof;
import domain.eSystree;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import builder.Wincalc;
import builder.param.ElementDet;
import builder.param.FillingDet;
import builder.param.FillingVar;
import builder.model.ElemGlass;
import builder.model.ElemSimple;
import common.UCom;
import dataset.Query;
import static domain.eArtikl.depth;
import enums.Layout;
import enums.Type;
import enums.UseArtiklTo;
import java.util.Arrays;

/**
 * Заполнения
 */
public class Filling extends Cal5e {

    private FillingVar fillingVar = null;
    private FillingDet fillingDet = null;
    private ElementDet elementDet = null;

    public Filling(Wincalc iwin) {
        super(iwin);
        fillingVar = new FillingVar(iwin);
        fillingDet = new FillingDet(iwin);
        elementDet = new ElementDet(iwin);
    }

    public Filling(Wincalc iwin, boolean shortPass) {
        super(iwin);
        fillingVar = new FillingVar(iwin);
        fillingDet = new FillingDet(iwin);
        elementDet = new ElementDet(iwin);
        this.shortPass = shortPass;
    }

    @Override
    public void calc() {
        LinkedList<ElemGlass> elemGlassList = UCom.listSortObj(iwin.listSortEl, Type.GLASS);
        for (ElemGlass elemGlass : elemGlassList) {
            calc2(elemGlass); //цикл по стеклопакетам 
        }
    }

    public void calc2(ElemGlass elemGlass) {
        super.calc();
        try {
            Float depth = elemGlass.artiklRec.getFloat(eArtikl.depth); //толщина стекда

            List<ElemSimple> elemFrameList = null;
            if (elemGlass.owner.type == Type.ARCH) {
                elemFrameList = Arrays.asList(rootArea().mapFrame.get(Layout.BOTT), rootArea().mapFrame.get(Layout.RIGHT), rootArea().mapFrame.get(Layout.TOP), rootArea().mapFrame.get(Layout.LEFT));
            } else {
                elemFrameList = Arrays.asList(elemGlass.joinFlat(Layout.BOTT), elemGlass.joinFlat(Layout.RIGHT), elemGlass.joinFlat(Layout.TOP), elemGlass.joinFlat(Layout.LEFT));
            }

            //Цикл по сторонам стеклопакета
            for (int side = 0; side < 4; ++side) {
                ElemSimple elemFrame = elemFrameList.get(side);
                elemGlass.anglHoriz = elemGlass.sideHoriz[side]; //проверяемая сторона стеклопакета в цикле

                //Цикл по группам заполнений
                for (Record glasgrpRec : eGlasgrp.findAll()) {
                    if (UCom.containsNumbJust(glasgrpRec.getStr(eGlasgrp.depth), depth) == true) { //доступные толщины 

                        List<Record> glasdetList = eGlasdet.find(glasgrpRec.getInt(eGlasgrp.id), elemGlass.artiklRec.getFloat(eArtikl.depth));
                        List<Record> glasprofList = eGlasprof.find(glasgrpRec.getInt(eGlasgrp.id));

                        //Цикл по профилям в группах заполнений
                        for (Record glasprofRec : glasprofList) {
                            if (elemFrame.artiklRecAn.getInt(eArtikl.id) == glasprofRec.getInt(eGlasprof.artikl_id)) { //если артикулы совпали
                                if (Arrays.asList(1, 2, 3, 4).contains(glasprofRec.getInt(eGlasprof.inside))) {  //внутреннее заполнение

                                    //ФИЛЬТР вариантов, параметры накапливаются в спецификации элемента
                                    if (fillingVar.filter(elemGlass, glasgrpRec) == true) {
                                        
                                        elemGlass.gzazo = glasgrpRec.getFloat(eGlasgrp.gap);
                                        elemGlass.gsize[side] = glasprofRec.getFloat(eGlasprof.gsize);

                                        if (shortPass == false) {
                                            detail(elemGlass, glasgrpRec, glasdetList);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка:Filling.calc() " + e);
        } finally {
            Query.conf = conf;
        }
    }

    protected void detail(ElemGlass elemGlass, Record glasgrpRec, List<Record> glasdetList) {
        try {
            //Цикл по списку детализации
            for (Record glasdetRec : glasdetList) {

                HashMap<Integer, String> mapParam = new HashMap(); //тут накапливаются параметры element и specific                        

                //ФИЛЬТР детализации, параметры накапливаются в mapParam
                if (fillingDet.filter(mapParam, elemGlass, glasdetRec) == true) {
                    setVariant.add(glasgrpRec.getInt(eGlasgrp.id)); //сделано для запуска формы Filling на ветке Systree
                    Record artiklRec = eArtikl.find(glasdetRec.getInt(eGlasdet.artikl_id), false);
                    Specific spcAdd = new Specific(glasdetRec, artiklRec, elemGlass, mapParam);

                    if (UColor.colorFromProduct(spcAdd, 1)
                            && UColor.colorFromProduct(spcAdd, 2)
                            && UColor.colorFromProduct(spcAdd, 3)) {

                        spcAdd.place = "ЗАП";
                        elemGlass.addSpecific(spcAdd);
                    }
                }
            }
            //}
        } catch (Exception e) {
            System.err.println("Ошибка:Filling.detail() " + e);
        }
    }
}
