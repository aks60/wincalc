package builder.script;

import builder.script.test.Alutech3;
import builder.script.test.Alutex3;
import builder.script.test.Vidnal;
import builder.script.test.Sial3;
import builder.script.test.Krauss;
import builder.script.test.Bimax;
import builder.script.test.Sokol;
import common.eProperty;
import java.util.Arrays;
import java.util.List;

public class Winscript {

    public static GsonRoot rootGson;

    public static String test(Integer prj, boolean model) {
        String base_name = (eProperty.base_num.read().equals("1")) ? eProperty.base1.read()
                : (eProperty.base_num.read().equals("2")) ? eProperty.base2.read() : eProperty.base3.read();

        if (base_name.toLowerCase().contains("sial3.fdb")) {
            return Sial3.script(prj, model);

        } else if (base_name.toLowerCase().contains("alutex3.fdb")) {
            return Alutex3.script(prj, model);
            
        } else if (base_name.toLowerCase().contains("alutech3.fdb")) {
            return Alutech3.script(prj, model);
            
        } else if (base_name.toLowerCase().contains("bimax.fdb")) {
            return Bimax.script(prj, model);

        } else if (base_name.toLowerCase().contains("vidnal.fdb")) {
            return Vidnal.script(prj, model);

        } else if (base_name.toLowerCase().contains("krauss.fdb")) {
            return Krauss.script(prj, model);

        } else if (base_name.toLowerCase().contains("sokol.fdb")) {
            return Sokol.script(prj, model);
        }
        return null;
    }

    public static List<Integer> models(String p) {
        String base_name = (eProperty.base_num.read().equals("1")) ? eProperty.base1.read()
                : (eProperty.base_num.read().equals("2")) ? eProperty.base2.read() : eProperty.base3.read();

        if (base_name.toLowerCase().contains("sial3.fdb")) {
            return Arrays.asList(601001, 601002, 601003, 601004, 601007, 601008);

        } else if (base_name.toLowerCase().contains("alutech3.fdb")) {
            return Arrays.asList(601001, 601002, 601003, 601004);

        } else if (base_name.toLowerCase().contains("alutex3.fdb")) {
            return Arrays.asList(4);

        } else if (base_name.toLowerCase().contains("bimax.fdb")) {
            return ("max".equals(p)) ? Arrays.asList(601001, 601002, 601003, 601004, 601005, 601006, 601007, 601008, 601009, 601010, 700027, 604004, 604005, 604006, 604007, 604008, 604009, 604010)
                    : Arrays.asList(601010, 700027, 604004, 604005, 604006, 604007, 604008);

        } else if (base_name.toLowerCase().contains("vidnal.fdb")) {
            return Arrays.asList(26);

        } else if (base_name.toLowerCase().contains("krauss.fdb")) {
            return null;

        } else if (base_name.toLowerCase().contains("sokol.fdb")) {
            return Arrays.asList(1);
        }
        return null;
    }

    public static String path() {
        String base_name = (eProperty.base_num.read().equals("1")) ? eProperty.base1.read()
                : (eProperty.base_num.read().equals("2")) ? eProperty.base2.read() : eProperty.base3.read();

        if (base_name.toLowerCase().contains("sial3.fdb")) {
            return "D:\\Okna\\Database\\ps3\\sial3b.fdb";

        } else if (base_name.toLowerCase().contains("alutech3.fdb")) {
            return "D:\\Okna\\Database\\ps3\\alutech3.FDB";

        } else if (base_name.toLowerCase().contains("alutex3.fdb")) {
            return "D:\\Okna\\Database\\ps3\\alutex3.FDB";

        } else if (base_name.toLowerCase().contains("bimax.fdb")) {
            return "D:\\Okna\\Database\\ps4\\ITEST.FDB";

        } else if (base_name.toLowerCase().contains("vidnal.fdb")) {
            return "D:\\Okna\\Database\\ps4\\vidnal.fdb";

        } else if (base_name.toLowerCase().contains("krauss.fdb")) {
            return "D:\\Okna\\Database\\ps4\\krauss.fdb";

        } else if (base_name.toLowerCase().contains("sokol.fdb")) {
            return "D:\\Okna\\Database\\ps4\\sokol.fdb";
        }
        return null;
    }
}
