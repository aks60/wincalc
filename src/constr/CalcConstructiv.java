package constr;

import domain.*;
import enums.*;
import model.*;

import java.util.*;

/**
 * Расчёт конструктива окна.
 */
public class CalcConstructiv extends CalcBase {

    private ParamVariant paramVariant = null;
    private ParamSpecific paramSpecific = null;

    public String sideCheck = ""; //TODO Эту переменную надо вынести в map параметров!!!

    public CalcConstructiv(AreaSimple root) {

        super(root);
        paramVariant = new ParamVariant(root, this);
        paramSpecific = new ParamSpecific(root, this);
    }

    /**
     * Фурнитура
     */
    public void fittingFirst() {
        for (paramSpecific.pass = 1; paramSpecific.pass < 4; paramSpecific.pass++) {

            LinkedList<AreaStvorka> elemStvorkaList = root.getElemList(TypeElem.FULLSTVORKA);
            //цикл по створкам
            for (AreaStvorka fullStvorka : elemStvorkaList) {

                //Подбор фурнитуры
                ArrayList<Syspros> sysprosList = Syspros.find(constr, nuni);
                Syspros sysprosRec = sysprosList.get(0);
                int funic = Integer.valueOf(fullStvorka.getHmParamJson().get(ParamJson.funic).toString());
                if (funic != -1) {
                    for (Syspros sysproaRec2 : sysprosList) {
                        if (sysproaRec2.funic == funic) {
                            sysprosRec = sysproaRec2; //теперь sysprosRec моответствует параметру полученному из i-okna
                            break;
                        }
                    }
                }
                //Подбор текстуры ручки створки
                Object colorHandl = fullStvorka.getHmParamJson().get(ParamJson.colorHandl);
                if (colorHandl == null) { //если цвет не установлен подбираю по основной текстуре
                    fullStvorka.getHmParamJson().put(ParamJson.colorHandl, root.getIwin().getColorProfile(1));
                }

                if (sysprosRec.nruch.equalsIgnoreCase("по середине"))
                    fullStvorka.handleHeight = "по середине";
                else if (sysprosRec.nruch.equalsIgnoreCase("константная"))
                    fullStvorka.handleHeight = "константная";
                else if (sysprosRec.nruch.equalsIgnoreCase("вариационная"))
                    fullStvorka.handleHeight = "установлена";

                Furnlst furnlstRec = Furnlst.find2(constr, sysprosRec.funic); //первая запись в списке конструктива
                ArrayList<Furnlen> furnlenList = Furnlen.find(constr, furnlstRec.funic);
                boolean out = true;
                //Цикл по описанию сторон фурнитуры
                for (Furnlen furnlenRec : furnlenList) {

                    ArrayList<ITParam> parfurlList = Parfurl.find(constr, furnlenRec.fincr);
                    out = paramVariant.checkParfurl(fullStvorka, parfurlList); //параметры вариантов
                    if (out == false) break;
                }
                if (out == false) continue;

                fittingMidle(fullStvorka, furnlstRec, 1);
            }
        }
    }

    /**
     * Фурнитура
     */
    protected void fittingMidle(AreaStvorka fullStvorka, Furnlst furnlstRec, int count) {

        ArrayList<Furnspc> furnspcList = Furnspc.find(constr, furnlstRec.funic);
        for (Furnspc furnspcRec : furnspcList) {

            if (furnspcRec.fleve == 1) {
                boolean ret1 = fittingSecond(fullStvorka, furnspcRec, count);
                if (ret1 == true) {

                    for (Furnspc furnspcRec2 : furnspcList) {
                        if (furnspcRec2.fleve == 2 && furnspcRec.fincb == furnspcRec2.fincs) {
                            boolean ret2 = fittingSecond(fullStvorka, furnspcRec2, count);
                            if (ret2 == true) {

                                for (Furnspc furnspcRec3 : furnspcList) {
                                    if (furnspcRec3.fleve == 3 && furnspcRec2.fincb == furnspcRec3.fincs) {
                                        boolean ret3 = fittingSecond(fullStvorka, furnspcRec3, count);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean fittingSecond(AreaStvorka elStvorka, Furnspc furnspcRec, int count) {

        HashMap<Integer, String> hmParam = new HashMap(); //тут накапливаются параметры element и specific
        //подбор текстуры ручки
        if (furnspcRec.anumb.equals("НАБОР") == false) {
            Artikls artiklRec = Artikls.get(constr, furnspcRec.anumb, false);
            if (artiklRec != null && TypeArtikl.STVORKA_HANDL.isType(artiklRec)) {
                int colorHandl = Integer.valueOf(elStvorka.getHmParamJson().get(ParamJson.colorHandl).toString());
                if (furnspcRec.clnum > 0) {
                    boolean empty = true;
                    ArrayList<Artsvst> artsvstList = Artsvst.get(constr, furnspcRec.anumb);
                    for (Artsvst artsvst : artsvstList) {
                        if (artsvst.clcod == colorHandl) {
                            empty = false;
                        }
                    }
                    if (empty == true) return false;
                }
            }
        }
        ArrayList<Furnles> furnlesList = Furnles.find(constr, furnspcRec.fincb);
        //Цикл по ограничению сторон фурнитуры
        for (Furnles furnlesRec : furnlesList) {

            ElemFrame el = null;
            int side = furnlesRec.lnumb;
            if (furnlesRec.lnumb < 0) {
                String[] par = sideCheck.split("/");
                if (furnlesRec.lnumb == -1) {
                    side = (par[0].equals("*") == true) ? 99 : Integer.valueOf(par[0]);
                } else if (furnlesRec.lnumb == -2) {
                    side = (par[1].equals("*") == true) ? 99 : Integer.valueOf(par[1]);
                }
            }
            if (side == 1) el = elStvorka.getRoot().getHmElemFrame().get(LayoutArea.BOTTOM);
            else if (side == 2) el = elStvorka.getRoot().getHmElemFrame().get(LayoutArea.RIGHT);
            else if (side == 3) el = elStvorka.getRoot().getHmElemFrame().get(LayoutArea.TOP);
            else if (side == 4) el = elStvorka.getRoot().getHmElemFrame().get(LayoutArea.LEFT);

            float width = el.getSpecificationRec().width - 2 * el.getArticlesRec().asizn;
            if (furnlesRec.lmaxl < width || (furnlesRec.lminl > width)) return false;

        }
        //Фильтр параметров
        ArrayList<ITParam> parfursList = Parfurs.find(constr, furnspcRec.fincb);
        if (paramSpecific.checkParfurs(hmParam, elStvorka, parfursList) == false) return false; //параметры спецификаций

        //Наборы
        if ("НАБОР".equals(furnspcRec.anumb)) {
            int count2 = (hmParam.get(24030) == null) ? 1 : Integer.valueOf((hmParam.get(24030)));
            Furnlst furnlstRec2 = Furnlst.find2(constr, furnspcRec.clnum);
            try {
                fittingMidle(elStvorka, furnlstRec2, count2); //рекурсия обработки наборов
            } catch (Exception e) {
                System.out.println("Ошибка CalcConstructiv.fittingMidle() " + e);
            }

        } else if (paramSpecific.pass == 2) {
            //Спецификация
            Artikls artikl = Artikls.get(constr, furnspcRec.anumb, false);
            if (artikl != null && furnspcRec.anumb.charAt(0) != '@') {

                Specification specif = new Specification(artikl, elStvorka, hmParam);
                specif.count = Integer.valueOf(specif.getHmParam(specif.count, 24030));
                specif.count = specif.count * count;
                specif.setColor(this, elStvorka, furnspcRec);
                specif.element = "FURN";
                elStvorka.addSpecifSubelem(specif); //добавим спецификацию в элемент
            }
            return true;
        }
        return true;
    }

    /**
     * Список допустимых параметров
     */
   /* protected void fittingAvailable(ElemBase el) {

        HashMap<Integer, Object[]> pJson = (HashMap) el.getHmParamJson().get(ParamJson.pro4Params2);
        if (pJson != null && pJson.isEmpty() == false) {  // если параметры от i-okna есть
            if (paramSpecific.pass == 0) {

            }
        }
    }*/
    public void joiningFirst() {

        HashMap<String, ElemJoinig> hmJoinElem = root.getHmJoinElem(); //список соединений
        //Цикл по списку соединений
        for (Map.Entry<String, ElemJoinig> hmElemJoin : hmJoinElem.entrySet()) {

            ElemJoinig elemJoin = hmElemJoin.getValue();
            ElemBase joinElement1 = elemJoin.getJoinElement(1);
            ElemBase joinElement2 = elemJoin.getJoinElement(2);

            Connlst connlstRec = Connlst.find(constr, joinElement1.getArticlesRec().anumb, joinElement2.getArticlesRec().anumb);
            ArrayList<Connvar> connvarList = Connvar.find(constr, connlstRec.cconn);

            if (connvarList.isEmpty() && connlstRec.cequv.isEmpty() == false) {  //если неудача, ищем в аналоге
                connlstRec = Connlst.find(constr, connlstRec.cequv);
                connvarList = Connvar.find(constr, connlstRec.cconn);
            }
            Collections.sort(connvarList, (connvar1, connvar2) -> connvar1.cprio - connvar2.cprio);
            //Цикл по вариантам соединения
            for (Connvar connvar : connvarList) {

                if (connvar.ctype != elemJoin.getVarJoin().value) continue; //если варианты соединения не совпали
                ArrayList<ITParam> parconvList = Parconv.find(constr, connvar.cunic);
                boolean out = paramVariant.checkParconv(elemJoin, parconvList); //ФИЛЬТР вариантов
                if (out == false) continue;
                ArrayList<Connspc> connspcList = Connspc.find(constr, connvar.cunic);
                //Цикл по спецификации соединений
                for (Connspc connspc : connspcList) {

                    HashMap<Integer, String> hmParam2 = new HashMap(); //тут накапливаются параметры
                    ArrayList<ITParam> parconsList = Parcons.find(constr, connspc.aunic);
                    out = paramSpecific.checkSpecific(hmParam2, joinElement1, parconsList); //ФИЛЬТР спецификаций
                    if (out == true) {

                        Artikls artRec = Artikls.get(constr, connspc.anumb, false);
                        //if(artRec.anumb.equals("V132P")) System.out.println("ТЕСТОВАЯ ЗАПЛАТКА");
                        Specification specif = new Specification(artRec, joinElement1, hmParam2);
                        //specif.setColor(this, joinElement1, connspc);
                        //TODO Непонятное назначение цвета, надо разобратьца.
                        Artsvst artsvst = Artsvst.get2(constr, artRec.anumb);
                        specif.colorBase = artsvst.clcod;
                        specif.colorInternal = artsvst.clcod;
                        specif.colorExternal = artsvst.clcod;
                        specif.element = "СОЕД";
                        joinElement1.addSpecifSubelem(specif);
                    }
                }
            }
        }
    }

    /**
     * Расчёт спецификации элементов конструкций.
     * Идем по списку профилей, смотрю есть аналог работаю с ним.
     * Но при проверке параметров использую вирт. мат. ценность.
     */
    public void compositionFirst() {
        ArrayList<Sysproa> sysproaList = Sysproa.find(constr, nuni);

        for (Sysproa sysproaRec : sysproaList) {
            boolean is = false;
            if (TypeProfile.FRAME.value == sysproaRec.atype) {
                ArrayList<Artsvst> svstList = Artsvst.find(constr, sysproaRec.anumb); //подбор текстуры, ищем не на аналоге
                for (Artsvst svst : svstList) {
                    if (svst.clcod == iwin.getColorProfile(1)) {
                        is = true;
                        LinkedList<ElemFrame> elemRamaList = root.getElemList(TypeElem.FRAME_BOX);
                        for (ElemFrame elemRama : elemRamaList) {
                            elemRama.setSpecifElement(sysproaRec);
                            ArrayList<Vstalst> vstalstList2 = Vstalst.find2(constr, elemRama.getArticlesRec().aseri); //состав для серии профилей
                            compositionSecond(vstalstList2, elemRama);
                            ArrayList<Vstalst> vstalstList = Vstalst.find(constr, elemRama.getArticlesRec().anumb); //состав для артикула профиля
                            compositionSecond(vstalstList, elemRama);

                            elemRama.getSpecificationRec().width = elemRama.getSpecificationRec().width + Float.valueOf(elemRama.getHmParam(0, 31052));
                        }
                    }
                }
            }
            if (is == true) break;
        }
        for (Sysproa sysproaRec : sysproaList) {
            boolean is = false;
            if (TypeProfile.IMPOST.value == sysproaRec.atype) {
                ArrayList<Artsvst> svstList = Artsvst.find(constr, sysproaRec.anumb); //подбор текстуры, ищем не на аналоге
                for (Artsvst svst : svstList) {
                    if (svst.clcod == iwin.getColorProfile(1)) {
                        is = true;
                        LinkedList<ElemImpost> impostList = root.getElemList(TypeElem.IMPOST);
                        for (ElemImpost elemInpost : impostList) {
                            elemInpost.setSpecifElement(sysproaRec);
                            ArrayList<Vstalst> vstalstList2 = Vstalst.find2(constr, elemInpost.getArticlesRec().aseri); //состав для серии профилей
                            compositionSecond(vstalstList2, elemInpost);
                            ArrayList<Vstalst> vstalstList = Vstalst.find(constr, elemInpost.getArticlesRec().anumb); //состав для артикула профиля
                            compositionSecond(vstalstList, elemInpost);
                        }
                    }
                }
            }
            if (is == true) break;
        }
        for (Sysproa sysproaRec : sysproaList) {
            boolean is = false;
            if (TypeProfile.STVORKA.value == sysproaRec.atype) {
                ArrayList<Artsvst> svstList = Artsvst.find(constr, sysproaRec.anumb); //подбор текстуры, ищем не на аналоге
                for (Artsvst svst : svstList) {
                    if (svst.clcod == iwin.getColorProfile(1)) {
                        is = true;
                        LinkedList<ElemFrame> elemStvorkaList = root.getElemList(TypeElem.FRAME_STV);
                        for (ElemFrame elemStvorka : elemStvorkaList) {
                            elemStvorka.setSpecifElement(sysproaRec);
                            ArrayList<Vstalst> vstalstList2 = Vstalst.find2(constr, elemStvorka.getArticlesRec().aseri); //состав для серии профилей
                            compositionSecond(vstalstList2, elemStvorka);
                            ArrayList<Vstalst> vstalstList = Vstalst.find(constr, elemStvorka.getArticlesRec().anumb); //состав для артикула профиля
                            compositionSecond(vstalstList, elemStvorka);
                        }
                    }
                }
            }
            if (is == true) break;
        }
    }

    /**
     * Соcтавы
     */
    protected boolean compositionSecond(ArrayList<Vstalst> vstalstList, ElemBase elemBase) {

        //цикл по составам
        for (Vstalst vstalstRec : vstalstList) {

            ArrayList<ITParam> parvstmList = Parvstm.find(constr, vstalstRec.vnumb);
            boolean out = paramVariant.checkParvstm(elemBase, parvstmList); //ФИЛЬТР вариантов
            if (out == true) {
                //artiklTech = elemBase.getArticlesRec(); //Artikls.get(constr, vstalstRec.anumb, false); //запишем технологический код контейнера
                ArrayList<Vstaspc> vstaspcList = Vstaspc.find(constr, vstalstRec.vnumb);
                //Цикл по спецификации
                for (Vstaspc vstaspcRec : vstaspcList) {

                    HashMap<Integer, String> hmParam = new HashMap(); //тут накапливаются параметры
                    ArrayList<ITParam> parvstsList = Parvsts.find(constr, vstaspcRec.aunic);
                    boolean out2 = paramSpecific.checkSpecific(hmParam, elemBase, parvstsList);//ФИЛЬТР спецификаций
                    if (out2 == true) {

                        Artikls artikl = Artikls.get(constr, vstaspcRec.anumb, false);
                        Specification specif = new Specification(artikl, elemBase, hmParam);
                        specif.setColor(this, elemBase, vstaspcRec);
                        specif.element = "СОСТ";
                        elemBase.addSpecifSubelem(specif); //добавим спецификацию в элемент
                    }
                }
            }
        }
        return false;
    }

    /**
     * Заполнения
     */
    public void fillingFirst() {
        for (paramSpecific.pass = 1; paramSpecific.pass < 4; paramSpecific.pass++) {

            String artiklSysProf = null;
            ArrayList<Sysproa> sysproaList = Sysproa.find(constr, nuni);
            LinkedList<ElemGlass> elemGlassList = root.getElemList(TypeElem.GLASS);
            //Цикл по стеклопакетам
            for (ElemGlass elemGlass : elemGlassList) {

                //Цикл по группам заполнений
                for (Glasgrp glasgrpRec : constr.glasgrpList) {

                    TypeProfile typeProf = (elemGlass.getOwner().getTypeElem() == TypeElem.FULLSTVORKA) ? TypeProfile.STVORKA : TypeProfile.FRAME;
                    //Цикл по системе конструкций, ищем артикул системы профилей
                    for (Sysproa sysproaRec : sysproaList) {
                        if (typeProf.value == sysproaRec.atype) {

                            Artikls artikls = Artikls.get(constr, sysproaRec.anumb, true); //запишем технологический код контейнера
                            artiklSysProf = (artikls.amain != null && artikls.amain.isEmpty() == false) ? artikls.amain : artikls.anumb;
                            break;
                        }
                    }

                    ArrayList<Glaspro> glasproList = Glaspro.find(constr, glasgrpRec.gnumb);
                    //Цикл по профилям в группе заполнений
                    for (Glaspro glasproRec : glasproList) {

                        if (artiklSysProf != null && artiklSysProf.equals(glasproRec.anumb) == true) { //если профиль есть в группе

                            //artiklTech = Artikls.get(constr, glasproRec.anumb, false);
                            elemGlass.getHmFieldVal().put("GZAZO", String.valueOf(glasgrpRec.gzazo));
                            fillingSecond(elemGlass, glasgrpRec);
                        }
                    }
                }
            }
        }
    }

    /**
     * Заполнения
     */
    protected boolean fillingSecond(ElemGlass elemGlass, Glasgrp glasgrpRec) {

        //TODO в заполненииях текстура подбирается неправильно
        ArrayList<ITParam> pargrupList = Pargrup.find(constr, glasgrpRec.gnumb);
        boolean out = paramVariant.checkPargrup(elemGlass, pargrupList); //ФИЛЬТР вариантов
        if (out == true) {

            if (paramSpecific.pass == 2) elemGlass.setSpecifElement(); //заполним спецификацию элемента
            ArrayList<Glasart> glasartList = Glasart.find(constr, glasgrpRec.gnumb, elemGlass.getArticlesRec().afric);

            //Цикл по списку спецификаций
            for (Glasart glasartRec : glasartList) {

                HashMap<Integer, String> hmParam = new HashMap(); //тут накапливаются параметры element и specific
                ArrayList<ITParam> parglasList = Parglas.find(constr, glasartRec.gunic); //список параметров спецификации
                if (paramSpecific.checkFilling(hmParam, elemGlass, parglasList) == true) { //ФИЛЬТР спец.

                    if (paramSpecific.pass == 1 || paramSpecific.pass == 3) continue; //если нулевой и второй цыкл ничего не создаём

                    Specification specif = null;
                    Artikls artikl = Artikls.get(constr, glasartRec.anumb, true);
                    float gzazo = Float.valueOf(elemGlass.getHmFieldVal().get("GZAZO"));
                    Float overLength = (hmParam.get(15050) == null) ? 0.f : Float.valueOf(hmParam.get(15050).toString());

                    //Стеклопакет
                    if (TypeArtikl.GLASS.value2 == artikl.atypp) {

                        //Штапик
                    } else if (TypeArtikl.SHTAPIK.value2 == artikl.atypp) {

                        Artikls art = Artikls.get(constr, glasartRec.anumb, false);
                        if (TypeElem.ARCH == elemGlass.getOwner().getTypeElem()) {

                            //По основанию арки
                            specif = new Specification(art, elemGlass, hmParam);
                            double dh2 = artikl.aheig - gzazo;

                            double r1 = elemGlass.getRadiusGlass() - dh2;
                            double h1 = elemGlass.getHeight() - 2 * dh2;
                            double l1 = Math.sqrt(2 * h1 * r1 - h1 * h1);  //верхний периметр
                            double r2 = elemGlass.getRadiusGlass();
                            double h2 = elemGlass.getHeight();
                            double l2 = Math.sqrt(2 * h2 * r2 - h2 * h2); //нижний периметр
                            double l3 = l2 - l1;
                            double ang = Math.toDegrees(Math.atan(dh2 / l3)); //угол реза

                            double r5 = elemGlass.getRadiusGlass() + gzazo;
                            double h5 = elemGlass.getHeight() + 2 * gzazo;
                            double l5 = overLength + 2 * Math.sqrt(2 * h5 * r5 - h5 * h5); //хорда

                            specif.width = (float) l5;
                            specif.height = artikl.aheig;
                            specif.anglCut2 = (float) ang;
                            specif.anglCut1 = (float) ang;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию

                            //По дуге арки
                            specif = new Specification(art, elemGlass, hmParam);
                            double ang2 = Math.toDegrees(Math.asin(l2 / r2));
                            double ang3 = 90 - (90 - ang2 + ang);
                            //TODO  ВАЖНО !!! Длина дуги штапика сделал примерный расчёт. Почему так, пока не понял. Поправочный коэф. надо вводить в зависимости от высоты импоста
                            double koef = 2;
                            ElemBase ramaArch = elemGlass.getRoot().getHmElemFrame().get(LayoutArea.ARCH);
                            double R2 = ((AreaArch) root).getRadiusArch() - ramaArch.getSpecificationRec().height + artikl.aheig;
                            double L2 = root.getWidth() - ramaArch.getSpecificationRec().height * 2 + artikl.aheig * 2 - koef;
                            double ANGL2 = Math.toDegrees(Math.asin(L2 / (R2 * 2)));
                            double M2 = (R2 * 2) * Math.toRadians(ANGL2); // +  overLength;
                            double Z = 3 * gzazo;
                            double R = elemGlass.getRadiusGlass();
                            double L = elemGlass.getWidth();
                            double ang5 = Math.toDegrees(Math.asin((L + (2 * Z)) / ((R + Z) * 2)));
                            double M = ((R + Z) * 2) * Math.toRadians(ang5);
                            specif.width = (float) (overLength + M2);
                            specif.height = artikl.aheig;
                            specif.anglCut2 = (float) ang3;
                            specif.anglCut1 = (float) ang3;

                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию

                        } else {
                            //По горизонтали
                            specif = new Specification(art, elemGlass, hmParam);
                            specif.width = elemGlass.getWidth() + 2 * gzazo;
                            specif.height = artikl.aheig;
                            specif.anglCut2 = 45;
                            specif.anglCut1 = 45;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (верхний/нижний)

                            elemGlass.addSpecifSubelem(new Specification(specif)); //добавим спецификацию в элемент (верхний/нижний)
                            //По вертикали
                            specif = new Specification(art, elemGlass, hmParam);
                            specif.width = elemGlass.getHeight() + 2 * gzazo;
                            specif.height = artikl.aheig;
                            specif.anglCut2 = 45;
                            specif.anglCut1 = 45;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (левый/правый)
                            elemGlass.addSpecifSubelem(new Specification(specif)); //добавим спецификацию в элемент (левый/правый)
                        }

                        //Уплотнитель
                    } else if (TypeArtikl.KONZEVPROF.value2 == artikl.atypp) { //уплотнитель
                        Artikls art = Artikls.get(constr, glasartRec.anumb, false);
                        if (TypeElem.ARCH == elemGlass.getOwner().getTypeElem()) { //если уплотнитель в арке
                            //По основанию арки
                            specif = new Specification(art, elemGlass, hmParam);

                            double dh2 = artikl.aheig - gzazo;
                            double r1 = elemGlass.getRadiusGlass() - dh2;
                            double h1 = elemGlass.getHeight() - 2 * dh2;
                            double l1 = Math.sqrt(2 * h1 * r1 - h1 * h1);  //верхний перимет
                            double r2 = elemGlass.getRadiusGlass();
                            double h2 = elemGlass.getHeight();
                            double l2 = Math.sqrt(2 * h2 * r2 - h2 * h2); //нижний периметр
                            double l3 = l2 - l1;
                            double r5 = elemGlass.getRadiusGlass() + gzazo;
                            double h5 = elemGlass.getHeight() + 2 * gzazo;
                            double l5 = 2 * Math.sqrt(2 * h5 * r5 - h5 * h5); //хорда
                            double ang = Math.toDegrees(Math.atan(dh2 / l3)); //угол реза
                            specif.width = (float) l5;
                            specif.height = specif.getArticRec().aheig;
                            specif.anglCut2 = (float) ang;
                            specif.anglCut1 = (float) ang;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (верхний/нижний)

                            //По дуге арки
                            specif = new Specification(art, elemGlass, hmParam);
                            double ang2 = Math.toDegrees(Math.asin(l2 / r2));
                            double ang3 = 90 - (90 - ang2 + ang);
                            double Z = 3 * gzazo;
                            double R = elemGlass.getRadiusGlass();
                            double L = elemGlass.getWidth();
                            double ang5 = Math.toDegrees(Math.asin((L + (2 * Z)) / ((R + Z) * 2)));
                            double M = ((R + Z) * 2) * Math.toRadians(ang5);
                            specif.width = (float) M;
                            specif.height = specif.getArticRec().aheig;
                            specif.anglCut2 = (float) ang3;
                            specif.anglCut1 = (float) ang3;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (верхний/нижний)
                        } else {
                            //По горизонтали
                            specif = new Specification(art, elemGlass, hmParam);
                            specif.width = elemGlass.getWidth() + 2 * gzazo;
                            specif.height = specif.getArticRec().aheig;
                            specif.anglCut2 = 45;
                            specif.anglCut1 = 45;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (левый/правый)
                            elemGlass.addSpecifSubelem(new Specification(specif)); //добавим спецификацию в элемент (левый/правый)
                            //По вертикали
                            specif = new Specification(art, elemGlass, hmParam);
                            specif.width = elemGlass.getHeight() + 2 * gzazo;
                            specif.height = specif.getArticRec().aheig;
                            specif.anglCut2 = 45;
                            specif.anglCut1 = 45;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (левый/правый)
                            elemGlass.addSpecifSubelem(new Specification(specif)); //добавим спецификацию в элемент (левый/правый)
                        }

                        //Всё остальное
                    } else {
                        Artikls art = Artikls.get(constr, glasartRec.anumb, false);
                        if (TypeElem.AREA == elemGlass.getOwner().getTypeElem()
                                || TypeElem.SQUARE == elemGlass.getOwner().getTypeElem()
                                || TypeElem.FULLSTVORKA == elemGlass.getOwner().getTypeElem()) {

                            for (int index = 0; index < 4; index++) {
                                specif = new Specification(art, elemGlass, hmParam);
                                specif.setColor(this, elemGlass, glasartRec);
                                elemGlass.addSpecifSubelem(specif);
                            }
                        } else if (TypeElem.ARCH == elemGlass.getOwner().getTypeElem()) {
                            for (int index = 0; index < 2; index++) {
                                specif = new Specification(art, elemGlass, hmParam);
                                specif.setColor(this, elemGlass, glasartRec);
                                elemGlass.addSpecifSubelem(specif);
                            }
                        } else {
                            specif = new Specification(art, elemGlass, hmParam);
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif);
                        }
                    }
                }
            }
        }
        return true;
    }

    protected boolean fillingSecond2(ElemGlass elemGlass, Glasgrp glasgrpRec) {

        //TODO в заполненииях текстура подбирается неправильно
        ArrayList<ITParam> pargrupList = Pargrup.find(constr, glasgrpRec.gnumb);
        boolean out = paramVariant.checkPargrup(elemGlass, pargrupList); //ФИЛЬТР вариантов
        if (out == true) {

            elemGlass.setSpecifElement(); //заполним спецификацию элемента
            ArrayList<Glasart> glasartList = Glasart.find(constr, glasgrpRec.gnumb, elemGlass.getArticlesRec().afric);
            //Цикл по списку спецификаций
            for (Glasart glasartRec : glasartList) {

                Specification specif = null;
                HashMap<Integer, String> hmParam = new HashMap(); //тут накапливаются параметры element и specific
                Artikls artikl = Artikls.get(constr, glasartRec.anumb, true);
                float gzazo = Float.valueOf(elemGlass.getHmFieldVal().get("GZAZO"));
                ArrayList<ITParam> parglasList = Parglas.find(constr, glasartRec.gunic); //список параметров спецификации
                out = paramSpecific.checkSpecific(hmParam, elemGlass, parglasList); //ФИЛЬТР спецификаций
                Float overLength = (hmParam.get(15050) == null) ? 0.f : Float.valueOf(hmParam.get(15050).toString());
                if (out == true) {
                    //Стеклопакет
                    if (TypeArtikl.GLASS.value2 == artikl.atypp) {

                        //Штапик
                    } else if (TypeArtikl.SHTAPIK.value2 == artikl.atypp) {

                        Artikls art = Artikls.get(constr, glasartRec.anumb, false);
                        if (TypeElem.ARCH == elemGlass.getOwner().getTypeElem()) {

                            //По основанию арки
                            specif = new Specification(art, elemGlass, hmParam);
                            double dh2 = artikl.aheig - gzazo;

                            double r1 = elemGlass.getRadiusGlass() - dh2;
                            double h1 = elemGlass.getHeight() - 2 * dh2;
                            double l1 = Math.sqrt(2 * h1 * r1 - h1 * h1);  //верхний периметр
                            double r2 = elemGlass.getRadiusGlass();
                            double h2 = elemGlass.getHeight();
                            double l2 = Math.sqrt(2 * h2 * r2 - h2 * h2); //нижний периметр
                            double l3 = l2 - l1;
                            double ang = Math.toDegrees(Math.atan(dh2 / l3)); //угол реза

                            double r5 = elemGlass.getRadiusGlass() + gzazo;
                            double h5 = elemGlass.getHeight() + 2 * gzazo;
                            double l5 = overLength + 2 * Math.sqrt(2 * h5 * r5 - h5 * h5); //хорда

                            specif.width = (float) l5;
                            specif.height = artikl.aheig;
                            specif.anglCut2 = (float) ang;
                            specif.anglCut1 = (float) ang;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию

                            //По дуге арки
                            specif = new Specification(art, elemGlass, hmParam);
                            double ang2 = Math.toDegrees(Math.asin(l2 / r2));
                            double ang3 = 90 - (90 - ang2 + ang);
                            //TODO  ВАЖНО !!! Длина дуги штапика сделал примерный расчёт. Почему так, пока не понял. Поправочный коэф. надо вводить в зависимости от высоты импоста
                            double koef = 2;
                            ElemBase ramaArch = elemGlass.getRoot().getHmElemFrame().get(LayoutArea.ARCH);
                            double R2 = ((AreaArch) root).getRadiusArch() - ramaArch.getSpecificationRec().height + artikl.aheig;
                            double L2 = root.getWidth() - ramaArch.getSpecificationRec().height * 2 + artikl.aheig * 2 - koef;
                            double ANGL2 = Math.toDegrees(Math.asin(L2 / (R2 * 2)));
                            double M2 = (R2 * 2) * Math.toRadians(ANGL2); // +  overLength;
                            double Z = 3 * gzazo;
                            double R = elemGlass.getRadiusGlass();
                            double L = elemGlass.getWidth();
                            double ang5 = Math.toDegrees(Math.asin((L + (2 * Z)) / ((R + Z) * 2)));
                            double M = ((R + Z) * 2) * Math.toRadians(ang5);
                            specif.width = (float) (overLength + M2);
                            specif.height = artikl.aheig;
                            specif.anglCut2 = (float) ang3;
                            specif.anglCut1 = (float) ang3;

                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию

                        } else {
                            //По горизонтали
                            specif = new Specification(art, elemGlass, hmParam);
                            specif.width = elemGlass.getWidth() + 2 * gzazo;
                            specif.height = artikl.aheig;
                            specif.anglCut2 = 45;
                            specif.anglCut1 = 45;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (верхний/нижний)

                            elemGlass.addSpecifSubelem(new Specification(specif)); //добавим спецификацию в элемент (верхний/нижний)
                            //По вертикали
                            specif = new Specification(art, elemGlass, hmParam);
                            specif.width = elemGlass.getHeight() + 2 * gzazo;
                            specif.height = artikl.aheig;
                            specif.anglCut2 = 45;
                            specif.anglCut1 = 45;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (левый/правый)
                            elemGlass.addSpecifSubelem(new Specification(specif)); //добавим спецификацию в элемент (левый/правый)
                        }

                        //Уплотнитель
                    } else if (TypeArtikl.KONZEVPROF.value2 == artikl.atypp) { //уплотнитель
                        Artikls art = Artikls.get(constr, glasartRec.anumb, false);
                        if (TypeElem.ARCH == elemGlass.getOwner().getTypeElem()) { //если уплотнитель в арке
                            //По основанию арки
                            specif = new Specification(art, elemGlass, hmParam);

                            double dh2 = artikl.aheig - gzazo;
                            double r1 = elemGlass.getRadiusGlass() - dh2;
                            double h1 = elemGlass.getHeight() - 2 * dh2;
                            double l1 = Math.sqrt(2 * h1 * r1 - h1 * h1);  //верхний перимет
                            double r2 = elemGlass.getRadiusGlass();
                            double h2 = elemGlass.getHeight();
                            double l2 = Math.sqrt(2 * h2 * r2 - h2 * h2); //нижний периметр
                            double l3 = l2 - l1;
                            double r5 = elemGlass.getRadiusGlass() + gzazo;
                            double h5 = elemGlass.getHeight() + 2 * gzazo;
                            double l5 = 2 * Math.sqrt(2 * h5 * r5 - h5 * h5); //хорда
                            double ang = Math.toDegrees(Math.atan(dh2 / l3)); //угол реза
                            specif.width = (float) l5;
                            specif.height = specif.getArticRec().aheig;
                            specif.anglCut2 = (float) ang;
                            specif.anglCut1 = (float) ang;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (верхний/нижний)

                            //По дуге арки
                            specif = new Specification(art, elemGlass, hmParam);
                            double ang2 = Math.toDegrees(Math.asin(l2 / r2));
                            double ang3 = 90 - (90 - ang2 + ang);
                            double Z = 3 * gzazo;
                            double R = elemGlass.getRadiusGlass();
                            double L = elemGlass.getWidth();
                            double ang5 = Math.toDegrees(Math.asin((L + (2 * Z)) / ((R + Z) * 2)));
                            double M = ((R + Z) * 2) * Math.toRadians(ang5);
                            specif.width = (float) M;
                            specif.height = specif.getArticRec().aheig;
                            specif.anglCut2 = (float) ang3;
                            specif.anglCut1 = (float) ang3;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (верхний/нижний)
                        } else {
                            //По горизонтали
                            specif = new Specification(art, elemGlass, hmParam);
                            specif.width = elemGlass.getWidth() + 2 * gzazo;
                            specif.height = specif.getArticRec().aheig;
                            specif.anglCut2 = 45;
                            specif.anglCut1 = 45;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (левый/правый)
                            elemGlass.addSpecifSubelem(new Specification(specif)); //добавим спецификацию в элемент (левый/правый)
                            //По вертикали
                            specif = new Specification(art, elemGlass, hmParam);
                            specif.width = elemGlass.getHeight() + 2 * gzazo;
                            specif.height = specif.getArticRec().aheig;
                            specif.anglCut2 = 45;
                            specif.anglCut1 = 45;
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif); //добавим спецификацию в элемент (левый/правый)
                            elemGlass.addSpecifSubelem(new Specification(specif)); //добавим спецификацию в элемент (левый/правый)
                        }

                        //Всё остальное
                    } else {
                        Artikls art = Artikls.get(constr, glasartRec.anumb, false);
                        if (TypeElem.AREA == elemGlass.getOwner().getTypeElem()
                                || TypeElem.SQUARE == elemGlass.getOwner().getTypeElem()
                                || TypeElem.FULLSTVORKA == elemGlass.getOwner().getTypeElem()) {

                            for (int index = 0; index < 4; index++) {
                                specif = new Specification(art, elemGlass, hmParam);
                                specif.setColor(this, elemGlass, glasartRec);
                                elemGlass.addSpecifSubelem(specif);
                            }
                        } else if (TypeElem.ARCH == elemGlass.getOwner().getTypeElem()) {
                            for (int index = 0; index < 2; index++) {
                                specif = new Specification(art, elemGlass, hmParam);
                                specif.setColor(this, elemGlass, glasartRec);
                                elemGlass.addSpecifSubelem(specif);
                            }
                        } else {
                            specif = new Specification(art, elemGlass, hmParam);
                            specif.setColor(this, elemGlass, glasartRec);
                            elemGlass.addSpecifSubelem(specif);
                        }
                        //if (specif != null)specif.element = "ЗАП";
                    }
                }
            }
        }
        return out;
    }

    public void kitsFirst() {

    }

    protected int determineColorCodeForArt(ElemBase elem, int color_side, ITSpecific par_specif, Specification specif) {

        int colorCode = getColorFromProduct(elem, color_side, par_specif);
        Colslst clslstRecr = Colslst.get2(constr, colorCode);
        //Фвтоподбор текстуры
        if (par_specif.clnum() == 0) {
            // Если этот цвет не подходит для данного артикула, то берем первую имеющуюся текстуру в тарифе
            // материальных ценностей указанного артикула (см. "Автоподбор" на http://help.profsegment.ru/?id=1107).
            boolean colorOK = false;
            int firstFoundColorNumber = 0;
            for (Artsvst artsvstRec : constr.artsvstMap.get(specif.artikl)) {
                if ((color_side == 1 && CanBeUsedAsBaseColor(artsvstRec))
                        || (color_side == 2 && CanBeUsedAsInsideColor(artsvstRec))
                        || (color_side == 3 && CanBeUsedAsOutsideColor(artsvstRec))) {

                    if (IsArtTariffAppliesForColor(artsvstRec, clslstRecr)) {
                        colorOK = true;
                        break;
                    } else if (firstFoundColorNumber == 0)
                        firstFoundColorNumber = artsvstRec.clnum;
                }
            }
            if (colorOK) return colorCode;
            else if (firstFoundColorNumber > 0) return Colslst.get(constr, firstFoundColorNumber).ccode;
            else return root.getIwin().getColorNone();

            //указана
            //} else if (vstaspcRec.clnum() == 1) {
            //    return colorCode;

        } else if (par_specif.clnum() == 100000) {
            return colorCode;


            //Текстура задана через параметр
        } else if (par_specif.clnum() < 0) {
            if (colorCode == root.getIwin().getColorNone()) {
                for (Parcols parcolsRec : constr.parcolsList) {
                    if (parcolsRec.pnumb == par_specif.clnum()) {
                        int code = Colslst.get3(constr, parcolsRec.ptext).ccode;
                        if (code != -1) return code;
                    }
                }
            }
            return colorCode;

            //В clnum указан цвет.
        } else {
            if (colorCode == root.getIwin().getColorNone()) {    //видимо во всех текстурах стоит значение "Указана"
                // Подбираем цвет так (придумала Л.Цветкова):
                // Если основная текстура изделия может быть основной текстурой для данного артикула (по тарифу мат.ценностей), то используем ее.
                // Иначе - используем CLNUM как основную текстуру. Аналогично для внутренней и внешней текстур.

                int colorNumber = Colslst.get2(constr, root.getIwin().getColorProfile(color_side)).cnumb;
                boolean colorFound = false;
                for (Artsvst artsvst : constr.artsvstMap.get(specif.artikl)) {

                    if ((color_side == 1 && CanBeUsedAsBaseColor(artsvst)) ||
                            (color_side == 2 && CanBeUsedAsInsideColor(artsvst)) ||
                            (color_side == 3 && CanBeUsedAsOutsideColor(artsvst))) {

                        if (IsArtTariffAppliesForColor(artsvst, clslstRecr) == true) {
                            for (Parcols parcolsRec : constr.parcolsList) {
                                if (parcolsRec.pnumb == par_specif.clnum()) {
                                    return Integer.valueOf(parcolsRec.ptext);
                                }
                            }
                        }
                    }
                }
            }
        }
        return colorCode;
    }

    // Выдает цвет из текущего изделия в соответствии с заданным вариантом подбора текстуры (dll->GetColorCodeFromProduct() - см. "SVN\iWin_Doc\trunk\Профстрой\Значения GLASART.CTYPE.xlsx".
    private int getColorFromProduct(ElemBase elem, int colorSide, ITSpecific vstaspcRec) {
        // Получаем код варианта подбора текстуры (см. "SVN\iWin_Doc\trunk\Профстрой\Значения GLASART.CTYPE.xlsx").
        int colorType = 0;
        if (colorSide == 1) colorType = vstaspcRec.ctype() % 16;
        else if (colorSide == 2) colorType = (vstaspcRec.ctype() / 16) % 16;
        else if (colorSide == 3) colorType = (vstaspcRec.ctype() / (16 * 16)) % 16;
        switch (colorType) {
            case 0:  //указана
                return Colslst.get(constr, vstaspcRec.clnum()).ccode;
            case 1:        // по основе изделия
            case 6:        // по основе в серии
                return root.getIwin().getColorProfile(1); //elem.getColor(1);
            case 2:        // по внутр.изделия
            case 7:        // по внутр. в серии
                return root.getIwin().getColorProfile(2); //elem.getColor(2);
            case 3:        // по внешн.изделия
            case 8:        // по внешн. в серии
                return root.getIwin().getColorProfile(3); //elem.getColor(3);
            case 11:    // по профилю
                return root.getIwin().getColorProfile(1);
            case 15:    // по заполнению
                return elem.getColor(1);
            default:    // без цвета
                return root.getIwin().getColorNone();
        }
    }
}
