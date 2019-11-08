package domain;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class Constructive {

    public final short regionId = 177;
    public static final HashMap<Short, Constructive> constructivesMap = new HashMap<>();
    public static boolean fromPS = true;  // Признак, что конструктив из ПС-4, а не из i-окон.

    public final HashMap<Object, Sysprof> sysprofMap = new HashMap();
    public final HashMap<Object, Sysdata> sysdataMap = new HashMap();
    public final ArrayList<Sysproa> sysproaList = new ArrayList();
    public final ArrayList<Artikls> artiklsList = new ArrayList();
    public final HashMap<String, Artikls> artiklsMap = new HashMap();
    public final HashMap<Integer, Artikls> artiklsMap2 = new HashMap();
    public final ArrayList<Artsvst> artsvstList = new ArrayList();
    public final HashMap<Object, ArrayList<Artsvst>> artsvstMap = new HashMap();
    public final HashMap<Object, Artsvst> artsvstMap2 = new HashMap();
    public final ArrayList<Colslst> colslstList = new ArrayList();
    public final HashMap<Object, Colslst> colslstMap = new HashMap();
    public final HashMap<Object, Colslst> colslstMap2 = new HashMap();
    public final HashMap<Object, Colslst> colslstMap3 = new HashMap();
    public final ArrayList<Syssize> syssizeList = new ArrayList();
    public final HashMap<Integer, Syssize> syssizeMap = new HashMap();
    public final ArrayList<Connlst> connlstList = new ArrayList();
    public final ArrayList<Connspc> connspcList = new ArrayList();
    public final ArrayList<Connvar> connvarList = new ArrayList();
    public final ArrayList<Vstalst> vstalstList = new ArrayList();
    public final ArrayList<Vstaspc> vstaspcList = new ArrayList();
    public final HashMap<String, Parlist> parlistMap = new HashMap();
    public final ArrayList<Parvstm> parvstmList = new ArrayList();
    public final ArrayList<Parconv> parconvList = new ArrayList();
    public final HashMap<String, Parconv> parconvMap = new HashMap();
    public final ArrayList<Parcons> parconsList = new ArrayList();
    public final ArrayList<Parvsts> parvstsList = new ArrayList();
    public final ArrayList<Glasgrp> glasgrpList = new ArrayList();
    public final ArrayList<Glaspro> glasproList = new ArrayList();
    public final ArrayList<Glasart> glasartList = new ArrayList();
    public final ArrayList<Parglas> parglasList = new ArrayList();
    public final ArrayList<Parsysp> parsyspList = new ArrayList();
    public final ArrayList<Pargrup> pargrupList = new ArrayList();
    public final ArrayList<Correnc> correncList = new ArrayList();
    public final HashMap<String, Correnc> correncMap = new HashMap();
    public final ArrayList<Ruleclk> ruleclkList = new ArrayList();
    public final HashMap<Object, Grupcol> grupcolMap = new HashMap();
    public final ArrayList<Parcols> parcolsList = new ArrayList();
    public final ArrayList<Grupart> grupartList = new ArrayList();
    public final ArrayList<Furnlst> furnlstList = new ArrayList();
    public final ArrayList<Furnlen> furnlenList = new ArrayList();
    public final ArrayList<Parfurl> parfurlList = new ArrayList();
    public final ArrayList<Furnspc> furnspcList = new ArrayList();
    public final ArrayList<Furnles> furnlesList = new ArrayList();
    public final ArrayList<Parfurs> parfursList = new ArrayList();
    public final ArrayList<Syspros> sysprosList = new ArrayList();
    public final ArrayList<Komplst> komplstList = new ArrayList();
    public final ArrayList<Kompspc> kompspcList = new ArrayList();

    public Constructive(short regionId, java.sql.Connection dbConn) throws java.sql.SQLException {

        try (java.sql.Statement stmt = dbConn.createStatement()) {
            Sysprof.load(this, stmt);
            Sysdata.load(this, stmt);
            Sysproa.load(this, stmt);
            Artikls.load(this, stmt);
            Artsvst.load(this, stmt);
            Colslst.load(this, stmt);
            Syssize.load(this, stmt);
            Connlst.load(this, stmt);
            Connspc.load(this, stmt);
            Connvar.load(this, stmt);
            Vstalst.load(this, stmt);
            Vstaspc.load(this, stmt);
            Parlist.load(this, stmt);
            Parvstm.load(this, stmt);
            Parconv.load(this, stmt);
            Parcons.load(this, stmt);
            Parvsts.load(this, stmt);
            Glasgrp.load(this, stmt);
            Glaspro.load(this, stmt);
            Glasart.load(this, stmt);
            Parglas.load(this, stmt);
            Parsysp.load(this, stmt);
            Pargrup.load(this, stmt);
            Correnc.load(this, stmt);
            Ruleclk.load(this, stmt);
            Grupcol.load(this, stmt);
            Parcols.load(this, stmt);
            Grupart.load(this, stmt);
            Furnlst.load(this, stmt);
            Furnlen.load(this, stmt);
            Parfurl.load(this, stmt);
            Furnspc.load(this, stmt);
            Furnles.load(this, stmt);
            Parfurs.load(this, stmt);
            Syspros.load(this, stmt);
            Komplst.load(this, stmt);
            Kompspc.load(this, stmt);
        }
    }

    public static Constructive getOrLoadConstructive(short regionId) throws Exception {

        Constructive constructive;
        synchronized (constructivesMap) {
            constructive = constructivesMap.get(regionId);
            if (constructive == null) {
                try (Connection conn = java.sql.DriverManager.getConnection(
                        "jdbc:firebirdsql:localhost/3050:D:\\Okna\\Profstroy\\ITEST.FDB?encoding=win1251", "sysdba", "masterkey")) {
                    constructive = new Constructive(regionId, conn);
                }
                 constructivesMap.put(regionId, constructive);
            }
        }
        return constructive;
    }
}
