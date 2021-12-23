package builder.model;

import enums.Layout;
import enums.LayoutJoin;
import enums.TypeJoin;
import builder.Wincalc;
import builder.script.GsonRoot;
import domain.eArtikl;
import domain.eSyssize;
import enums.Type;

public class AreaRectangl extends AreaSimple {

    public AreaRectangl(Wincalc iwin, GsonRoot gson, int color1, int color2, int color3) {
        super(iwin, null, gson.id(), Type.RECTANGL, gson.layout(), gson.width(), gson.height(), color1, color2, color3, gson.param());
        setDimension(0, 0, gson.width(), gson.height());
    }
    
    //@Override
    public void joining() {
        
        super.joining(); //T - соединения
        
        ElemSimple elemBott = mapFrame.get(Layout.BOTT), elemRight = mapFrame.get(Layout.RIGHT),
                elemTop = mapFrame.get(Layout.TOP), elemLeft = mapFrame.get(Layout.LEFT);
        
        //Угловое соединение правое нижнее
        ElemJoining.create(elemBott.joinPoint(1), iwin, TypeJoin.VAR20, LayoutJoin.RBOT, elemBott, elemRight, 90);
        //Угловое соединение правое верхнее
        ElemJoining.create(elemRight.joinPoint(1), iwin, TypeJoin.VAR20, LayoutJoin.RTOP, elemRight, elemTop, 90);
        //Угловое соединение левое верхнее    
        ElemJoining.create(elemTop.joinPoint(1),iwin, TypeJoin.VAR20, LayoutJoin.LTOP, elemTop, elemLeft, 90); 
        //Угловое соединение левое нижнее
        ElemJoining.create(elemLeft.joinPoint(1), iwin, TypeJoin.VAR20, LayoutJoin.LBOT, elemLeft, elemBott, 90);
    }
}
