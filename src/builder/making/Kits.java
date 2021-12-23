package builder.making;

import builder.Wincalc;
import builder.param.KitDet;
import dataset.Query;

public class Kits extends Cal5e {

    private KitDet kitDet = null;

    public Kits(Wincalc iwin) {
        super(iwin);
    }

    public void calc(float Q, float L, float H, Query kitDet) {
        super.calc();
        try {

        } catch (Exception e) {
            System.err.println("Ошибка:Elements.calc() " + e);
        } finally {
            Query.conf = conf;
        }
    }
}
