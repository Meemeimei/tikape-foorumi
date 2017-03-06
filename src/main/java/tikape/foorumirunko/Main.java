package tikape.foorumirunko;

import java.sql.Timestamp;
import java.util.*;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.foorumirunko.database.*;
import tikape.foorumirunko.domain.*;

public class Main {

    public static void main(String[] args) throws Exception {
        //herokuun siirtymiseen liittyvä portinhakusetti
         if (System.getenv("PORT") != null) {
         port(Integer.valueOf(System.getenv("PORT")));
         }
         

        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.dropAllTables();
        database.init();
        database.instantiateTestData();

        KayttajaDao kayttajaDao = new KayttajaDao(database);
        ViestiDao viestiDao = new ViestiDao(database);
        AlueDao alueDao = new AlueDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        post("/", (req, res) -> {
            String nimi = req.queryParams("nimi");

            if (nimi != null) {
                int key = alueDao.kuinkaMontaAluetta() + 1;
                Alue a = new Alue(key, nimi);
                alueDao.InsertOne(a);
            }
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alue/:id", (req, res) -> {
//            HashMap<String, Object> data = new HashMap<>();
            Viesti v = new Viesti("mielensäpahoittaja", "pahoitin");
            HashMap data = new HashMap<String, Object>();
            data.put("Keskustelut", v);
//            data.put("alue_id", alueDao.findOne(Integer.parseInt(req.params(":id"))));
            return new ModelAndView(data, "alue");
        }, new ThymeleafTemplateEngine());

        post("/alue/:id", (req, res) -> {
            String nimimerkki = req.queryParams("nimimerkki");
            String otsikko = req.queryParams("otsikko");
            String sisalto = req.queryParams("sisalto");
            
            if (otsikko != null) {
                Viesti v = new Viesti(sisalto, otsikko);
                viestiDao.insertOne(v);
                if (kayttajaDao.findOne(nimimerkki) == null) {
                    Kayttaja uusiK = new Kayttaja(nimimerkki);
                    kayttajaDao.insertOne(uusiK);
                    v.setKayttaja(uusiK);
                    v.setAlueenId(Integer.parseInt(req.params("id")));
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    v.setAika(timestamp);
                } else {
                    Kayttaja k = kayttajaDao.findOne(nimimerkki);
                    kayttajaDao.insertOne(k);
                    v.setKayttaja(k);
                    v.setAlueenId(Integer.parseInt(req.params("id")));
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    v.setAika(timestamp);
                }
            }

            Viesti v = new Viesti("mielensäpahoittaja", "pahoitin");
            HashMap map = new HashMap<>();
            map.put("Keskustelut", v);
            map.put("Keskustelut", alueDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        get("/kayttajat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kayttajat", kayttajaDao.findAll());

            return new ModelAndView(map, "kayttajat");
        }, new ThymeleafTemplateEngine());

        get("/kayttaja/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kayttaja", kayttajaDao.findOne(req.params("id")));

            return new ModelAndView(map, "kayttaja");
        }, new ThymeleafTemplateEngine());

        get("/keskustelu/:otsikko", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kayttaja", kayttajaDao.findOne(req.params("id")));

            return new ModelAndView(map, "kayttaja");
        }, new ThymeleafTemplateEngine());
    }
}
