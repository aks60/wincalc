package builder.model;

import enums.Layout;
import builder.Wincalc;
import builder.script.GsonRoot;
import enums.Type;

public class AreaTriangl extends AreaSimple {

    public AreaTriangl(Wincalc iwin, GsonRoot gson, int color1, int color2, int color3) {
        super(iwin, null, gson.id(), Type.TRIANGL, gson.layout(), gson.width(), gson.height(), color1, color2, color3, gson.param());
        setDimension(0, 0, gson.width(), gson.height());
    }

    @Override
    public void joining() {
        
        super.joining();
        
        System.out.println("Реализация не определена");
    }
}
