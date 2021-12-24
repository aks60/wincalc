package frames.swing;

import builder.Wincalc;
import java.awt.BasicStroke;
import java.awt.Color;

public class Draw {

    public static void drawLine(Wincalc iwin, float x1, float y1, float x2, float y2) {
        iwin.gc2d.drawLine((int) (x1), (int) (y1), (int) (x2), (int) (y2));
    }

    public static void strokePolygon(Wincalc iwin, float x1, float x2, float x3, float x4,
            float y1, float y2, float y3, float y4, int rgbFill, Color rgbStroke) {

        if (rgbStroke == Color.RED) {
            iwin.gc2d.setStroke(new BasicStroke(6)); //толщина линии 
        } else {
            iwin.gc2d.setStroke(new BasicStroke(1)); //толщина линии 
        }
        iwin.gc2d.setColor(new java.awt.Color(rgbFill));
        iwin.gc2d.fillPolygon(new int[]{(int) x1, (int) x2, (int) x3, (int) x4},
                new int[]{(int) y1, (int) y2, (int) y3, (int) y4}, 4);
        iwin.gc2d.setColor(rgbStroke);
        iwin.gc2d.drawPolygon(new int[]{(int) (x1), (int) (x2), (int) (x3), (int) (x4)},
                new int[]{(int) y1, (int) y2, (int) y3, (int) y4}, 4);
    }

    public static void strokeArc(Wincalc iwin, double x, double y, double w, double h,
            double startAngle, double arcExtent, int rgbStroke, double lineWidth) {
        int sw = (int) (lineWidth);
        iwin.gc2d.setStroke(new BasicStroke(sw)); //толщина линии
        iwin.gc2d.setColor(new java.awt.Color(rgbStroke));
        iwin.gc2d.drawArc((int) (x), (int) (y), (int) (w), (int) (h), (int) startAngle, (int) arcExtent);
    }

//    public static void drawString(Wincalc iwin, String txt, float x, float y) {
//        iwin.gc2d.rotate(Math.toRadians(-90), 10, curY + dy - dy / 2 + dw / 2);
//        iwin.gc2d.drawString(df1.format(gson.height() + dh), 10, curY + dy - dy / 2 + dw / 2);
//        iwin.gc2d.rotate(Math.toRadians(90), 10, curY + dy - dy / 2 + dw / 2);
//    }

//    public static void line(Wincalc iwin, float x1, float y1, float x2, float y2, float dy) {
//
//        iwin.gc2d.setColor(java.awt.Color.BLACK);
//        int size = (iwin.scale > .3) ? 40 : (iwin.scale > .2) ? 55 : 70;
//        iwin.gc2d.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, size));
//        iwin.gc2d.setStroke(new BasicStroke(6)); //толщина линии
//        y1 = y1 + dy;
//        y2 = y2 + dy;
//        Draw.drawLine(iwin, x1, y1, x2, y2);
//        if (x1 == x2 && y2 - y1 != 0) {
//            Draw.drawLine(iwin, x1 - 24, y1, x1 + 24, y1);
//            Draw.drawLine(iwin, x2 - 24, y2, x2 + 24, y2);
//            Draw.drawLine(iwin, x1, y1, x1 + 12, y1 + 24);
//            Draw.drawLine(iwin, x1, y1, x1 - 12, y1 + 24);
//            Draw.drawLine(iwin, x2, y2, x2 + 12, y2 - 24);
//            Draw.drawLine(iwin, x2, y2, x2 - 12, y2 - 24);
//            iwin.gc2d.rotate(Math.toRadians(270), x1 + 60, y1 + (y2 - y1) / 2);
//            iwin.gc2d.drawString(UGui.df.format((float) (y2 - y1)), x1 + 60, y1 + (y2 - y1) / 2);
//            iwin.gc2d.rotate(Math.toRadians(-270), x1 + 60, y1 + (y2 - y1) / 2);
//        } else if (y1 == y2 && x2 - x1 != 0) {
//            Draw.drawLine(iwin, x1, y1 - 24, x1, y1 + 24);
//            Draw.drawLine(iwin, x2, y2 - 24, x2, y2 + 24);
//            Draw.drawLine(iwin, x1, y1, x1 + 24, y1 - 12);
//            Draw.drawLine(iwin, x1, y1, x1 + 24, y1 + 12);
//            Draw.drawLine(iwin, x2, y2, x2 - 24, y2 - 12);
//            Draw.drawLine(iwin, x2, y2, x2 - 24, y2 + 12);
//            iwin.gc2d.drawString(UGui.df.format((float) (x2 - x1)), x1 + (x2 - x1) / 2, y2 + 60);
//        }
//    }
}
