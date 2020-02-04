package model;

import script.Area;
import domain.Constructive;

public class Test {

    /*
     * Тест всех проектов
     */
    public static void main(String[] args) {
        try {
            String _case = "dll";            
            IWindows.production = false;
            IWindows iwin = new IWindows(Constructive.getConstructive((short) 177));
            if (_case.equals("con")) {                
                Constructive.getConstructive((short) 177);
                
            } else if (_case.equals("dll")) {
                iwin.create(Area.test(604005));
                System.out.println();
                
            } else {
                if (_case.equals("min")) {
                    iwin.create(Area.test(601001));
                    iwin.create(Area.test(601002));
                    iwin.create(Area.test(601004));
                    iwin.create(Area.test(601007));
                    iwin.create(Area.test(601010));
                    iwin.create(Area.test(604005));
                    iwin.create(Area.test(604010));

                } else if (_case.equals("max")) {
                    iwin.create(Area.test(601001));
                    iwin.create(Area.test(601002));
                    iwin.create(Area.test(601003));
                    iwin.create(Area.test(601004));
                    iwin.create(Area.test(601005));
                    iwin.create(Area.test(601006));
                    iwin.create(Area.test(601007));
                    iwin.create(Area.test(601008));
                    iwin.create(Area.test(601009));
                    iwin.create(Area.test(601010));
                    iwin.create(Area.test(604005));
                    iwin.create(Area.test(604006));
                    iwin.create(Area.test(604007));
                    iwin.create(Area.test(604008));
                    iwin.create(Area.test(604009));
                    iwin.create(Area.test(604010));
                }
            }
            System.out.println("Тестирование <" + _case + "> завершено!");
        } catch (Exception e) {
            System.out.println("Test.main() " + e);
        }
    }
}
