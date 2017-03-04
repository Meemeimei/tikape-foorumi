
package tikape.foorumirunko.domain;

public class Viesti {
    
    private Kayttaja kayttaja;
    private String otsikko;
    private String sisalto;
    private String aika;
    private int alueenId;
    
    public Viesti(String otsikko, String sisalto) {
        this.otsikko = otsikko;
        this.sisalto = sisalto;
    }
    
    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
    }
    
    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
    
    public void setSisalto() {
        this.sisalto = sisalto;
    }
    
    public void setAika(String aika) {
        this.aika = aika;
    }
    
    public void setAlueenId(int id) {
        this.alueenId = id;
    }
    
    public Kayttaja getKayttaja() {
        return this.kayttaja;
    }
    
    public String getOtsikko() {
        return this.otsikko;
    }
    
    public String getSisalto() {
        return this.sisalto;
    }
    
    public String getAika() {
        return this.aika;
    }
    
    public int getAlueenId() {
        return this.alueenId;
    }
    
}
