package constr;

import domain.*;
import enums.LayoutArea;
import enums.TypeElem;
import enums.VariantJoin;
import model.AreaSimple;
import model.ElemBase;
import model.ElemJoinig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Перечень параметров конструктива (составов, заполнений...)
 * Параметры верхней части формы конструктива (составов, заполнений...)
 */
public class ParamVariant {

    private final Constructive constr;
    private final AreaSimple root; //главное окно
    private CalcConstructiv calcConstr = null;

    public ParamVariant(AreaSimple root, CalcConstructiv calcConstr) {
        this.root = root;
        this.constr = root.getIwin().getConstr();
        this.calcConstr = calcConstr;
    }

    private boolean filterParamDef(ITParam param) {
        if (param.pnumb() < 0) {
            if (root.getIwin().getHmParamDef().get(param.pnumb()) == null) return false;
            if (root.getIwin().getHmParamDef().get(param.pnumb())[1].equals(param.znumb()) == false) return false;
        }
        return true;
    }

    // Составы
    // int[] parVstm = {31000, 31001, 31002, 31003, 31004, 31005, 31006, 31007, 31008, 31015, 31016, 31020, 31033, 31034, 31037, 31041, 31050,
    //31052, 31055, 31056, 31080, 31085, 31090, 31095, 31097, 31099, 37001, 37002, 37009, 37010, 37030, 37042, 37056, 37080, 37085, 37099};
    protected boolean checkParvstm(ElemBase elemBase, ArrayList<ITParam> tableList) {

        //Цикл по параметрам состава
        for (ITParam param : tableList) {

            if (filterParamDef(param) == false) return false;
            switch (param.pnumb()) {
                case 31000: //Для технологического кода контейнера
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
                case 31002://Если профиль прямой, арочный
                case 37002:
                    Object obj = elemBase.getLayout();
                    if (LayoutArea.ARCH == elemBase.getLayout() && "арочный".equals(param.ptext()) == false) {
                        return false;
                    } else if (LayoutArea.ARCH != elemBase.getLayout() && "прямой".equals(param.ptext()) == false) {
                        return false;
                    }
                    break;
                case 31004: //Если прилегающий артикул
//                    HashMap<String, ElemJoinig> hmJoinElem = elemBase.getRoot().getHmJoinElem();
//                    boolean ret = false;
//                    for (Map.Entry<String, ElemJoinig> elemJoin : hmJoinElem.entrySet()) {
//                        ElemJoinig el = elemJoin.getValue();
//                        if (VariantJoin.VAR4 == el.getVarJoin() &&
//                                el.getJoinElement(1).getArticlesRec().equals(elemBase.getArticlesRec()) &&
//                                el.getJoinElement(2).getArticlesRec().equals(param.ptext())) {
//                            ret = true;
//                        }
//                    }
//                    if (ret == false) return false;
                    break;
                case 31005: //Коды основной текстуры контейнера
                    if (CalcConstructiv.compareInt(param.ptext(), elemBase.getColor(1)) == false) return false;
                    break;
                case 31006: //Коды внутр. текстуры контейнера
                    if (CalcConstructiv.compareInt(param.ptext(), elemBase.getColor(2)) == false) return false;
                    break;
                case 31007://Коды внешн. текстуры контейнера
                    if (CalcConstructiv.compareInt(param.ptext(), elemBase.getColor(3)) == false) return false;
                    break;
                case 31015: //Форма заполнения
                    Object objx = elemBase.getHmParam("empty", 13015);
                    if (param.ptext().equals(elemBase.getHmParam("empty", 13015)) == false) return false;
                    break;
                case 31020: //Ограничение угла к горизонту
                    if (CalcConstructiv.compareFloat(param.ptext(), elemBase.getAnglHoriz()) == false) return false;
                    break;
                case 31037:  //Название фурнитуры содержит
                    if (TypeElem.FULLSTVORKA == elemBase.getOwner().getTypeElem()) {
                        return param.ptext().contains(elemBase.getOwner().getArticlesRec().aname);
                    } else return false;
                case 31041: //Ограничение длины профиля, мм
                    if (CalcConstructiv.compareFloat(param.ptext(), elemBase.getWidth()) == false) return false;
                    break;
                case 31050: //Контейнер имеет тип
                    TypeElem type = elemBase.getTypeElem();
                    if (type.value != Integer.valueOf(param.ptext())) return false;
                    break;
                case 31052:
                    if(elemBase.getLayout() == LayoutArea.ARCH) {
                        elemBase.putHmParam(param.pnumb(), param.ptext());
                    }
                    break;
                case 31055: //Коды внутр. и внешн. текстуры изд
                    if ((CalcConstructiv.compareInt(param.ptext(), elemBase.getColor(2)) == true &&
                            CalcConstructiv.compareInt(param.ptext(), elemBase.getColor(3)) == true) == false)
                        return false;
                    break;
                case 31056: //Коды внутр. или внеш. текстуры изд
                case 37056:
                    if ((CalcConstructiv.compareInt(param.ptext(), elemBase.getColor(2)) == true ||
                            CalcConstructiv.compareInt(param.ptext(), elemBase.getColor(3)) == true) == false)
                        return false;
                    break;
                case 31099:  //Трудозатраты, ч/ч.
                case 37099:
                    break;
                default:
                    paramMessage(param.pnumb());
                    break;
            }
        }
        return true;
    }

    // Заполнения
    //int[] parGrup = {13015, 13017, 13081, 13099};
    protected boolean checkPargrup(ElemBase elemBase, ArrayList<ITParam> tableList) {

        //Цикл по параметрам состава
        for (ITParam param : tableList) {

            if (filterParamDef(param) == false) return false;
            switch (param.pnumb()) {
                case 13015: //Форма заполнения
                    Object objx = elemBase.getHmParam("empty", 13015);
                    if (param.ptext().equals(elemBase.getHmParam("empty", 13015)) == false) return false;
                    break;
                case 13017: //Код системы содержит строку
                    Sysprof sysprofRec = Sysprof.get(constr, root.getIwin().getNuni());
                    if (sysprofRec.npref.contains(param.ptext()) == false)
                        return false;
                    break;
                case 13099: //Трудозатраты, ч/ч.
                    break;
                default:
                    paramMessage(param.pnumb());
                    break;
            }
        }
        return true;
    }

    // Фурнитура
    //int[] parFurl = {2101, 2104, 2140, 2185};
    protected boolean checkParfurl(ElemBase elemBase, ArrayList<ITParam> tableList) {

        //Цикл по параметрам состава
        for (ITParam param : tableList) {

            if (filterParamDef(param) == false) return false;

            switch (param.pnumb()) {
                case 21001: //Форма контура прямоугольная трапециевидная
                    if (TypeElem.FULLSTVORKA == elemBase.getTypeElem() && "прямоугольная".equals(param.ptext()) == false) {
                        return false;
                    }
                    break;
                case 21004: //Артикул створки
                    if (elemBase.getArticlesRec().anumb.equals(param.ptext()) == false) {
                        return false;
                    }
                    break;
                default:
                    paramMessage(param.pnumb());
                    break;
            }
        }
        return true;
    }

    /**
     * 1000 - прилегающее соединение
     * 2000 - угловое на ус
     * 3000 - угловое (левое, правое)
     * 4000 - Т образное соединение
     */
    //Соединения
    //  int[] parConv = {1005, 1008, 1010, 1011, 1012, 1013, 1020, 1040, 1085, 1099, 2005, 2012, 2013, 2020, 2030, 2061, 2099, 3002, 3003, 3005, 3015,
    //  3020, 3031, 3050, 3099, 4002, 4005, 4011, 4012, 4013, 4015, 4018, 4020, 4040, 4044, 4085, 4095, 4099};
    protected boolean checkParconv(ElemJoinig elemJoin, ArrayList<ITParam> parconvList) {

        ElemBase joinElement = (ElemBase.SIDE_START == ElemJoinig.FIRST_SIDE) ? elemJoin.getJoinElement(1) : elemJoin.getJoinElement(2);
        float angl = (ElemBase.SIDE_START == ElemJoinig.FIRST_SIDE) ? elemJoin.getAnglJoin(1) : elemJoin.getAnglJoin(2);
        ElemBase joinElement1 = elemJoin.getJoinElement(1);
        ElemBase joinElement2 = elemJoin.getJoinElement(2);
        boolean result = true;
        String strTxt = "";
        //цикл по параметрам элементов соединения
        for (ITParam param : parconvList) {

            String code = (String.valueOf(param.pnumb()).length() == 4) ? String.valueOf(param.pnumb()).substring(1, 4) : String.valueOf(param.pnumb()).substring(2, 5);
            switch (code) {
                case "002": //Вид Т-образного варианта (простое Т-обр. крестовое Т-обр. сложное Y-обр.)
                    if (elemJoin.getVarJoin() == VariantJoin.VAR4 && "Простое Т-обр.".equals(param.ptext()) == false)
                        return false;
                    break;
                case "005": //Контейнер имеет тип Артикула1/Артикула2
                    try {
                        strTxt = param.ptext();
                        int type1 = joinElement1.getTypeElem().value;
                        int type2 = joinElement2.getTypeElem().value;

                        char symmetry = strTxt.charAt(strTxt.length() - 1);
                        if (symmetry == '@') {
                            strTxt = strTxt.substring(0, strTxt.length() - 1);
                        }
                        String arr2[] = strTxt.split("/");//парсинг параметра
                        int[] arr3 = Arrays.asList(arr2).stream().mapToInt(Integer::parseInt).toArray();
                        if (arr2.length == 1) {
                            if (!(arr3[0] == type1 || arr3[0] == type2)) {
                                return false;
                            }
                        } else if (arr2.length == 2) {
                            if (symmetry == '@') {
                                if (!((arr3[0] == type1 && arr3[1] == type2) || (arr3[0] == type2 && arr3[1] == type1))) {
                                    return false;
                                }
                            } else {
                                if (!((arr3[0] == type1 && arr3[1] == type2))) {
                                    return false;
                                }
                            }
                        } else {
                            if (symmetry == '@') {
                                if (!((type1 >= arr3[0] && type1 < arr3[1]) && (type2 >= arr3[2] && type2 < arr3[3]) ||
                                        (type2 >= arr3[0] && type2 < arr3[1]) && (type1 >= arr3[2] && type1 < arr3[3]))) {
                                    return false;
                                }
                            } else {
                                if (!((type1 >= arr3[0] && type1 < arr3[1]) && (type2 >= arr3[2] && type2 < arr3[3]))) {
                                    return false;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка ParamVariant.checkParconv() " + e);
                        return false;
                    }
                    break;
                case "011": //Для Артикула 1 указан состав
                    strTxt = param.ptext();
                    ArrayList<Vstalst> vstalstList1 = Vstalst.find(constr, joinElement1.getArticlesRec().anumb, joinElement1.getArticlesRec().aseri);
                    boolean substr1 = false;
                    for (Vstalst vstalst1 : vstalstList1) {
                        if (vstalst1.vname.contains(strTxt)) {
                            ArrayList<ITParam> parvstmList = Parvstm.find(constr, vstalst1.vnumb);
                            substr1 = checkParvstm(joinElement1, parvstmList);
                            break;
                        }
                    }
                    if (substr1 == false) {
                        return false;
                    }
                    break;
                case "012": //Для Артикула 2 указан состав
                    strTxt = param.ptext();
                    boolean substr2 = false;
                    ArrayList<Vstalst> vstalstList2 = Vstalst.find(constr, joinElement2.getArticlesRec().anumb, joinElement1.getArticlesRec().aseri);
                    for (Vstalst vstalst2 : vstalstList2) {
                        if (vstalst2.vname.contains(strTxt)) {
                            ArrayList<ITParam> parvstmList = Parvstm.find(constr, vstalst2.vnumb);
                            substr2 = checkParvstm(joinElement1, parvstmList);
                            break;
                        }
                    }
                    if (substr2 == false) {
                        return false;
                    }
                    break;
                case "013": //Для Артикулов не указан состав
                    strTxt = param.ptext();
                    ArrayList<Vstalst> vstalstList1a = Vstalst.find(constr, joinElement1.getArticlesRec().anumb, joinElement1.getArticlesRec().aseri);
                    boolean substr1a = false;
                    for (Vstalst vstalst1 : vstalstList1a) {
                        if (vstalst1.vname.contains(strTxt)) {
                            ArrayList<ITParam> parvstmList = Parvstm.find(constr, vstalst1.vnumb);
                            substr1a = checkParvstm(joinElement1, parvstmList);
                            break;
                        }
                    }
                    boolean substr2a = false;
                    ArrayList<Vstalst> vstalstList2a = Vstalst.find(constr, joinElement2.getArticlesRec().anumb, joinElement1.getArticlesRec().aseri);
                    for (Vstalst vstalst2 : vstalstList2a) {
                        if (vstalst2.vname.contains(strTxt)) {
                            ArrayList<ITParam> parvstmList = Parvstm.find(constr, vstalst2.vnumb);
                            substr2a = checkParvstm(joinElement1, parvstmList);
                            break;
                        }
                    }
                    if (substr1a == true || substr2a == true) {
                        return false;
                    }
                    break;
                case "020":  //Ограничение угла
                    if (CalcConstructiv.compareFloat(param.ptext(), angl) == false) return false;
                    break;
                case "030":  //Припуск Артикула1/Артикула2 , мм
                    strTxt = param.ptext();
                    char symmetry = strTxt.charAt(strTxt.length() - 1);
                    if (symmetry == '@') {
                        strTxt = strTxt.substring(0, strTxt.length() - 1);
                    }
                    String arr2[] = strTxt.split("/");
                    joinElement1.putHmParam(2030, arr2[0]);
                    joinElement2.putHmParam(2030, arr2[1]);
                    break;
                case "031":
                case "032":
                case "033":
                case "034":
                case "035":
                case "036":
                case "037":
                case "038":
                case "039":
                case "040":
                    return false; //Т. к. есть системные константы
                case "085":
                    System.out.println("надпись на элем. записать");
                    elemJoin.getJoinElement(1).getRoot().getIwin().setLabelSketch(param.ptext());
                    break;
                case "095": //Если признак системы конструкции
                    Sysprof sysprofRec = Sysprof.get(constr, root.getIwin().getNuni());
                    String[] arr = param.ptext().split(";");
                    boolean empty = true;
                    for (int i = 0; i < arr.length; i++) {
                        if (sysprofRec.typew == Integer.valueOf(arr[i])) {
                            empty = false;
                        }
                    }
                    if (empty == true) {
                        return false;
                    }
                case "099":  //Трудозатраты, ч/ч.
                    elemJoin.costsJoin = param.ptext();
                    break;
                default:
                    paramMessage(param.pnumb());
                    break;
            }
        }
        return result;
    }

    private void paramMessage(int code) {
        //System.out.println("ParamVariant ОШИБКА! КОД " + code + " НЕ ОБРАБОТАН.");
    }

    /* Все варианты вместе
    public static int[] paramSum = {13015, 13017, 13081, 13099, 31000, 31001, 31002, 31003, 31004, 31005, 31006, 31007, 31008, 31015, 31016, 31020,
            31033, 31034, 31037, 31041, 31050, 31052, 31055, 31056, 31080, 31085, 31090, 31095, 31097, 31099, 37001, 37002, 37009, 37010, 37030,
            37042, 37056, 37080, 37085, 37099};*/
}
