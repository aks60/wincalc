package builder.param;

import dataset.Record;
import java.util.HashMap;
import java.util.List;
import builder.Wincalc;
import domain.eKitdet;
import domain.eKitpar2;

//Заполнения
public class KitDet extends Par5s {

    private float Q, L, H;

    public KitDet(float Q, float L, float H) {
        super(new Wincalc());
        this.Q = Q;
        this.L = L;
        this.H = H;
    }

    public boolean filter(HashMap<Integer, String> mapParam, Record kitdetRec) {

        List<Record> paramList = eKitpar2.find(kitdetRec.getInt(eKitdet.id)); //список параметров детализации  
        if (filterParamDef(paramList) == false) {
            return false; //параметры по умолчанию
        }
        //Цикл по параметрам заполнения
        for (Record rec : paramList) {
            if (check(mapParam, rec) == false) {
                return false;
            }
        }
        return true;
    }

    public boolean check(HashMap<Integer, String> mapParam, Record rec) {

        int grup = rec.getInt(GRUP);
        try {
            switch (grup) {
                case 7030: //Количество 
                    message(rec.getInt(GRUP));
                    break;
                case 7031: //Количество 
                {
                    Object v = calcScript(Q, L, H, rec.getStr(TEXT));
                    mapParam.put(grup, String.valueOf(v));
                }
                break;
                case 7040:  //Порог расчета, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 7050:  //Шаг, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 7060:  //Количество на шаг 
                    message(rec.getInt(GRUP));
                    break;
                case 7081:  //Если ширина комплекта, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 7098:  //Бригада (участок) 
                    message(rec.getInt(GRUP));
                    break;
                case 7099:  //Трудозатраты, ч/ч. 
                    message(rec.getInt(GRUP));
                    break;
                case 8050:  //Поправка, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 8060:  //Количество" 
                    message(rec.getInt(GRUP));
                case 8061: //Количество" 
                {
                    Object v = calcScript(Q, L, H, rec.getStr(TEXT));
                    mapParam.put(grup, String.valueOf(v));
                }
                break;
                case 8065:  //Длина, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 8066: //Длина, мм 
                {
                    Object v = calcScript(Q, L, H, rec.getStr(TEXT));
                    mapParam.put(grup, String.valueOf(v));
                }
                break;
                case 8070:  //"Ширина, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 8071: //"Ширина, мм
                {
                    Object v = calcScript(Q, L, H, rec.getStr(TEXT));
                    mapParam.put(grup, String.valueOf(v));
                }
                break;
                case 8075: //Углы реза 
                    mapParam.put(rec.getInt(GRUP), rec.getStr(TEXT));
                    break;
                case 8081:  //Ширина комплекта, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 8083:  //Набрать_длину_с_нахлестом, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 8097:  //Трудозатраты по длине 
                    message(rec.getInt(GRUP));
                    break;
                case 8098:  //Бригада (участок) 
                    message(rec.getInt(GRUP));
                    break;
                case 8099:  //Трудозатраты, ч/ч. 
                    message(rec.getInt(GRUP));
                    break;
                case 9050:  //Поправка длины, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 9055:  //Поправка ширины, мм
                    message(rec.getInt(GRUP));
                    break;
                case 9060:  //Количество 
                    message(rec.getInt(GRUP));
                    break;
                case 9061: //Количество 
                {
                    Object v = calcScript(Q, L, H, rec.getStr(TEXT));
                    mapParam.put(grup, String.valueOf(v));
                }
                break;
                case 9065:  //Длина, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 9066: //Длина, мм 
                {
                    Object v = calcScript(Q, L, H, rec.getStr(TEXT));
                    mapParam.put(grup, String.valueOf(v));
                }
                break;
                case 9070:  //Ширина, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 9071: //Ширина, мм 
                {
                    Object v = calcScript(Q, L, H, rec.getStr(TEXT));
                    mapParam.put(grup, String.valueOf(v));
                }
                break;
                case 9081:  //Если ширина комплекта, мм 
                    message(rec.getInt(GRUP));
                    break;
                case 9083:  //Набрать длину с нахлестом/Длина , мм 
                    message(rec.getInt(GRUP));
                    break;
                case 9097:  //Трудозатраты по площади 
                    message(rec.getInt(GRUP));
                    break;
                case 9098:  //Бригада (участок) 
                    message(rec.getInt(GRUP));
                    break;
                case 9099:  //Трудозатраты, ч/ч. 
                    message(rec.getInt(GRUP));
                    break;
                default:
                    assert !(grup > 0 && grup < 50000) : "Код " + grup + "  не обработан!!!";
            }
        } catch (Exception e) {
            System.err.println("Ошибка:param.FillingDet.check()  parametr=" + grup + "    " + e);
            return false;
        }
        return true;
    }
}
