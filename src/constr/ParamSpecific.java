package constr;

import domain.*;
import enums.LayoutArea;
import enums.ParamJson;
import enums.TypeElem;
import enums.TypeProfile;
import model.AreaSimple;
import model.AreaStvorka;
import model.ElemBase;
import model.ElemFrame;

import java.util.*;

/**
 * Перечень параметров спецификаций (составов, заполнений...)
 * Параметры нижней части формы конструктива (составов, заполнений...)
 */
public class ParamSpecific {

    private final Constructive constr;
    private final AreaSimple root; //главное окно
    private CalcConstructiv calcConstr = null;
    public int pass = 1; //pass=1 ищем тех что попали, pass=2 основной цикл, pass=3 находим доступные параметры

    public ParamSpecific(AreaSimple root, CalcConstructiv calcConstr) {
        this.root = root;
        this.constr = root.getIwin().getConstr();
        this.calcConstr = calcConstr;
    }

    /**
     * Фильтр параметров по уьолчанию и i-okna
     */
    protected boolean filterParamJson(ElemBase elemBase, ArrayList<ITParam> paramList) {

        HashMap<Integer, Object[]> paramJson = new HashMap();
        HashMap<Integer, Object[]> paramTotal = new HashMap();
        paramTotal.putAll(root.getIwin().getHmParamDef()); //добавим параметры по умолчанию

        //Все владельцы этого элемента
        LinkedList<ElemBase> ownerList = new LinkedList();
        ElemBase el = elemBase;
        ownerList.add(el);
        do {
            el = el.getOwner();
            ownerList.add(el);
        } while (el != root);

        //Цикл по владельцам этого элемента
        for (int index = ownerList.size() - 1; index >= 0; index--) {

            el = ownerList.get(index);
            HashMap<Integer, Object[]> pJson = (HashMap) el.getHmParamJson().get(ParamJson.pro4Params2);
            if (pJson != null && pJson.isEmpty() == false) {  // если параметры от i-okna есть
                if(pass == 1) {
                    paramTotal.putAll(pJson); //к пар. по умолч. наложим парам. от i-win
                } else {
                    for (Map.Entry<Integer, Object[]> entry : pJson.entrySet()) {
                        Object[] val = entry.getValue();
                        if(val[2].equals(1)) { //
                            paramTotal.put(entry.getKey(), entry.getValue()); //по умолчанию и i-win
                        }
                    }
                }
                paramJson.putAll(pJson); //к парам. i-win верхнего уровня наложим парам. i-win нижнего уровня
            }
        }
        for (ITParam param : paramList) {
            if (param.pnumb() < 0) {

                if (paramTotal.get(param.pnumb()) == null) return false; //усли в базе парам. нет, сразу выход

                //В данной ветке есть попадание в param.pnumb()
                Object[] totalVal = paramTotal.get(param.pnumb());
                if (totalVal[1].equals(param.znumb()) == false) { //если в param.znumb() попадания нет

                    //на третьей итерации дополняю ...
                    return false;

                } else if (paramJson != null && paramJson.isEmpty() == false && paramJson.get(param.pnumb()) != null) {
                    totalVal[2] = 1; //если попадание было, то записываю 1 в третий элемент массива
                }
            }
        }
        return true;
    }

    //Cоединения, составы
    protected boolean checkSpecific(HashMap<Integer, String> hmParam, ElemBase
            elemBase, ArrayList<ITParam> tableList) {

        //Цикл по параметрам состава
        for (ITParam param : tableList) {

            String code = (String.valueOf(param.pnumb()).length() == 4) ? String.valueOf(param.pnumb()).substring(1, 4) : String.valueOf(param.pnumb()).substring(2, 5);
            switch (code) {
                case "000": //Для технологического кода контейнера

                    Sysproa sproaRec = elemBase.getSysproaRec();
                    Artikls articlesVRec = Artikls.get(constr, sproaRec.anumb, false);
                    if (articlesVRec.atech == null) return false;
                    String[] strList = param.ptext().split(";");
                    String[] strList2 = articlesVRec.atech.split(";");
                    boolean ret2 = false;
                    for (String str : strList) {
                        for (String str2 : strList2) {
                            if (str.equals(str2)) {
                                ret2 = true;
                            }
                        }
                    }
                    if (ret2 == false) return false;
                    break;
                case "005":
                    int m1 = elemBase.getRoot().getIwin().getColorProfile(1);
                    if (CalcConstructiv.compareInt(param.ptext(), m1) == false) return false;
                    break;
                case "006":
                    int m2 = elemBase.getRoot().getIwin().getColorProfile(2);
                    if (CalcConstructiv.compareInt(param.ptext(), m2) == false) return false;
                    break;
                case "007":
                    int m3 = elemBase.getRoot().getIwin().getColorProfile(3);
                    if (CalcConstructiv.compareInt(param.ptext(), m3) == false) return false;
                    break;
                case "010": //Расчёт армирования
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "020": //Поправка, мм
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "030": //[ * коэф-т ]
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "040": //Порог расчета, мм
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "050": //Поправка, мм
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "051": //Поправка, мм
                    if (elemBase.getHmParam("0", 31052).equals(param.ptext()) == false)
                        hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "060": //Количество
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "066": //Если номер стороны контура
                    if ("1".equals(param.ptext()) == true && LayoutArea.BOTTOM != elemBase.getLayout())
                        return false;
                    else if ("2".equals(param.ptext()) == true && LayoutArea.RIGHT != elemBase.getLayout())
                        return false;
                    else if ("3".equals(param.ptext()) == true && LayoutArea.TOP != elemBase.getLayout())
                        return false;
                    else if ("4".equals(param.ptext()) == true && LayoutArea.LEFT != elemBase.getLayout())
                        return false;
                    break;
                case "067": //Коды основной текстуры изделия
                    int c1 = elemBase.getRoot().getIwin().getColorProfile(1);
                    if (CalcConstructiv.compareInt(param.ptext(), c1) == false) return false;
                    break;
                case "068": //Код внутренней структуры изделия
                    int c2 = elemBase.getRoot().getIwin().getColorProfile(2);
                    if (CalcConstructiv.compareInt(param.ptext(), c2) == false) return false;
                    break;
                case "069"://Коды внешн. текстуры изделия
                    int c3 = elemBase.getRoot().getIwin().getColorProfile(3);
                    if (CalcConstructiv.compareInt(param.ptext(), c3) == false) return false;
                    break;
                case "070": //Длина, мм
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "095": //Если признак системы конструкции
                    Sysprof sysprofRec = Sysprof.get(constr, root.getIwin().getNuni());
                    String[] arr = param.ptext().split(";");
                    List<String> arrList = Arrays.asList(arr);
                    boolean ret = false;
                    for (String str : arrList) {
                        if (sysprofRec.typew == Integer.valueOf(str) == true) ret = true;
                    }
                    if (ret == false) return false;
                    break;
                case "099": //Трудозатраты, ч/ч.
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                default:
                    paramMessage(param.pnumb());
                    break;
            }
        }
        if (filterParamJson(elemBase, tableList) == false) return false; //параметры по умолчанию и I-OKNA
        return true;
    }


    //public static int[]  parGlas = {14000 ,14030 ,14040 ,14050 ,14060 ,14065 ,14068 ,15000 ,15005 ,15011 ,15013 ,15027 ,15030 ,15040,15045 ,15050 ,15055 ,15068 ,15069};
    //Заполнения
    protected boolean checkFilling(HashMap<Integer, String> hmParam, ElemBase
            elemBase, ArrayList<ITParam> tableList) {

        //Цикл по параметрам состава
        for (ITParam param : tableList) {

            //if (compare(param) == false) return false;
            String code = (String.valueOf(param.pnumb()).length() == 4) ? String.valueOf(param.pnumb()).substring(1, 4) : String.valueOf(param.pnumb()).substring(2, 5);
            switch (code) {
                case "000": //Для технологического кода контейнера

                    Sysproa sproaRec = elemBase.getSysproaRec();
                    Artikls articlesVRec = Artikls.get(constr, sproaRec.anumb, false);
                    if (articlesVRec.atech == null) return false;
                    String[] strList = param.ptext().split(";");
                    String[] strList2 = articlesVRec.atech.split(";");
                    boolean ret2 = false;
                    for (String str : strList) {
                        for (String str2 : strList2) {
                            if (str.equals(str2)) {
                                ret2 = true;
                            }
                        }
                    }
                    if (ret2 == false) return false;
                    break;
                case "005":
                    int m1 = elemBase.getRoot().getIwin().getColorProfile(1);
                    if (CalcConstructiv.compareInt(param.ptext(), m1) == false) return false;
                    break;
                case "006":
                    int m2 = elemBase.getRoot().getIwin().getColorProfile(2);
                    if (CalcConstructiv.compareInt(param.ptext(), m2) == false) return false;
                    break;
                case "007":
                    int m3 = elemBase.getRoot().getIwin().getColorProfile(3);
                    if (CalcConstructiv.compareInt(param.ptext(), m3) == false) return false;
                    break;
                case "010": //Расчёт армирования
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "020": //Поправка, мм
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "030": //[ * коэф-т ]
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "040": //Порог расчета, мм
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "050": //Поправка, мм
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "051": //Поправка, мм
                    if (elemBase.getHmParam("0", 31052).equals(param.ptext()) == false)
                        hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "060": //Количество
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "066": //Если номер стороны контура
                    if ("1".equals(param.ptext()) == true && LayoutArea.BOTTOM != elemBase.getLayout())
                        return false;
                    else if ("2".equals(param.ptext()) == true && LayoutArea.RIGHT != elemBase.getLayout())
                        return false;
                    else if ("3".equals(param.ptext()) == true && LayoutArea.TOP != elemBase.getLayout())
                        return false;
                    else if ("4".equals(param.ptext()) == true && LayoutArea.LEFT != elemBase.getLayout())
                        return false;
                    break;
                case "067": //Коды основной текстуры изделия
                    int c1 = elemBase.getRoot().getIwin().getColorProfile(1);
                    if (CalcConstructiv.compareInt(param.ptext(), c1) == false) return false;
                    break;
                case "068": //Код внутренней структуры изделия
                    int c2 = elemBase.getRoot().getIwin().getColorProfile(2);
                    if (CalcConstructiv.compareInt(param.ptext(), c2) == false) return false;
                    break;
                case "069"://Коды внешн. текстуры изделия
                    int c3 = elemBase.getRoot().getIwin().getColorProfile(3);
                    if (CalcConstructiv.compareInt(param.ptext(), c3) == false) return false;
                    break;
                case "070": //Длина, мм
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case "095": //Если признак системы конструкции
                    Sysprof sysprofRec = Sysprof.get(constr, root.getIwin().getNuni());
                    String[] arr = param.ptext().split(";");
                    List<String> arrList = Arrays.asList(arr);
                    boolean ret = false;
                    for (String str : arrList) {
                        if (sysprofRec.typew == Integer.valueOf(str) == true) ret = true;
                    }
                    if (ret == false) return false;
                    break;
                case "099": //Трудозатраты, ч/ч.
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                default:
                    paramMessage(param.pnumb());
                    break;
            }
        }
        if (filterParamJson(elemBase, tableList) == false) return false; //параметры по умолчанию и I-OKNA
        return true;
    }


    //Фурнитура (Parfurs)
    protected boolean checkParfurs(HashMap<Integer, String> hmParam, ElemBase
            elemBase, ArrayList<ITParam> tableList) {
        //Цикл по параметрам состава
        for (ITParam param : tableList) {

            //if (compare(param) == false) return false;
            switch (param.pnumb()) {
                case 24001: //Форма контура
                    if (TypeElem.FULLSTVORKA == elemBase.getTypeElem() && "прямоугольная".equals(param.ptext()) == false) {
                        return false;
                    }
                    break;
                case 24002: //Если артикул створки
                case 25002: //Если артикул створки
                    if (elemBase.getArticlesRec().anumb.equals(param.ptext()) == false) {
                        return false;
                    }
                    break;
                case 24012: //Направление открывания
                    if (elemBase.getTypeOpen().side.equals(param.ptext()) == false) {
                        return false;
                    }
                    break;
                case 25013: //Укорочение от
                case 25030:
                    hmParam.put(param.pnumb(), param.ptext());  //УЧЕСТ В СПЕЦИФИКАЦИИ!!!
                    break;
                case 24030: //Количество
                case 25060://Количество
                    hmParam.put(param.pnumb(), param.ptext());  //УЧЕСТ В СПЕЦИФИКАЦИИ!!!
                    break;
                case 24033://Фурнитура штульповая
                case 25033://Фурнитура штульповая
                    if (elemBase.getTypeOpen().side.equals("левое")) {
                        ElemFrame el = elemBase.getRoot().getHmElemFrame().get(LayoutArea.LEFT);
                        if (param.ptext().equals("Да") && el.getTypeProfile() != TypeProfile.SHTULP) {
                            return false;
                        } else if (param.ptext().equals("Нет") && el.getTypeProfile() == TypeProfile.SHTULP) {
                            return false;
                        }
                    } else if (elemBase.getTypeOpen().side.equals("правое")) {
                        ElemFrame el = elemBase.getRoot().getHmElemFrame().get(LayoutArea.RIGHT);
                        if (param.ptext().equals("Да") && el.getTypeProfile() != TypeProfile.SHTULP) {
                            return false;
                        }
                        if (el.getTypeProfile() == TypeProfile.SHTULP && param.ptext().equals("Нет")) {
                            return false;
                        }
                    }
                    break;
                case 24038://Проверять Cторону_(L)/Cторону_(W)
                case 25038://Проверять Cторону_(L)/Cторону_(W)
                    //Тут полны непонятки
                    //System.out.println("++++++++++++++++++++++++++++++++++");
                    calcConstr.sideCheck = param.ptext();
                    hmParam.put(param.pnumb(), param.ptext());
                    break;
                case 25040://Длина, мм
                    hmParam.put(param.pnumb(), param.ptext());  //УЧЕСТ В СПЕЦИФИКАЦИИ!!!
                    break;
                case 24069://Коды внешн. текстуры изделия
                    int c3 = elemBase.getRoot().getIwin().getColorProfile(3);
                    if (CalcConstructiv.compareInt(param.ptext(), c3) == false) return false;
                    break;
                case 24070://Если высота ручки
                    String str = ((AreaStvorka) elemBase).handleHeight;
                    if ("по середине".equals(str) == true && param.ptext().equals("не константная") == false ||
                            "константная".equals(str) == true && param.ptext().equals("константная") == false) {
                        return false;
                    }
                    break;
                case 24072://Ручка от низа створки, мм
                    hmParam.put(param.pnumb(), param.ptext());  //УЧЕСТ В СПЕЦИФИКАЦИИ!!!
                    break;
                case 24099://Трудозатраты, ч/ч.
                    //paramMessage2(param.p_numb());
                    break;
                default:
                    paramMessage(param.pnumb());
                    break;
            }
        }
        if (filterParamJson(elemBase, tableList) == false) return false; //параметры по умолчанию и I-OKNA
        return true;
    }

    private void paramMessage(int code) {
        //System.out.println("ParamSpecific ОШИБКА! КОД " + code + " НЕ ОБРАБОТАН.");
    }

    //Все спецификации вместе
    /*public static int[] paramSum = {
            11000, 11009, 11010, 11020, 11030, 11040, 11050, 11060, 11067, 11068, 11069, 11070, 11072, 11095, 12000, 12008, 12010, 12020, 12030, 12050, 12060,
            12063, 12065, 12068, 12069, 12070, 12072, 12075, 14000, 14030, 14040, 14050, 14060, 14065, 14068, 15000, 15005, 15011, 15013, 15027, 15030, 15040,
            15045, 15050, 15055, 15068, 15069, 24001, 24002, 24004, 24006, 24010, 24012, 24030, 24033, 24038, 24063, 24067, 24068, 24069, 24070, 24072, 24073, 24074, 24075, 24095,
            24099, 25002, 25010, 25013, 25030, 25035, 25040, 25060, 25067, 33000, 33005, 33008, 33030, 33040, 33050, 33060, 33066, 33067, 33069, 33078, 33095, 34000, 34004, 34005, 34006,
            34007, 34008, 34010, 34011, 34015, 34030, 34051, 34060, 34066, 34067, 34068, 34069, 34070, 34071, 34075, 34095, 34099, 38004, 38010, 38030, 38050,
            38060, 38067, 38068, 38069, 39002, 39005, 39020, 39060, 39068, 39069, 39075, 39077, 39093, 40010, 40067, 40068, 40069};

    //Соединения
    public static int[] parCons = {11000, 11009, 11010, 11020, 11030, 11040, 11050, 11060, 11067, 11068, 11069, 11070, 11072, 11095,
            12000, 12008, 12010, 12020, 12030, 12050, 12060, 12063, 12065, 12068, 12069, 12070, 12072, 12075};
    //Составы
    public static int[] parVsts = {33000, 33005, 33008, 33030, 33040, 33050, 33060, 33066, 33067, 33069, 33078, 33095, 34000, 34004, 34005,
            34006, 34007, 34008, 34010, 34011, 34015, 34030, 34051, 34060, 34066, 34067, 34068, 34069, 34070, 34071, 34075, 34095, 34099,
            38004, 38010, 38030, 38050, 38060, 38067, 38068, 38069, 39002, 39005, 39020, 39060, 39068, 39069, 39075, 39077, 39093,
            40010, 40067, 40068, 40069};
    //Заполнения
    public static int[] parGlas = {14000, 14030, 14040, 14050, 14060, 14065, 14068, 15000, 15005, 15011, 15013, 15027, 15030, 15040,
            15045, 15050, 15055, 15068, 15069};
    //фурнитура
    public static int[] parFurs = {24001, 24002, 25002, 24004, 24006, 24010, 25010, 24012, 24030, 25030, 24033, 24038, 24063, 24067, 25067, 24068, 24069, 24070, 24072, 24073, 24074, 24075,
            24095, 24099, 25013, 25035, 25040, 25060, 25067};*/
}
