
package tikape.foorumirunko.domain;

import java.sql.Timestamp;

/**
 * 
 * @author xvixvi
*/

public class Alue {
    
    private String nimi;
    private int id;
    private int viestienMaara;
    private Timestamp viimeisinViestiTimestamp;
    
    public Alue(int tunnus, String namen) {
        this.id = tunnus;
        nimi = namen;
        viestienMaara = 0;
        viimeisinViestiTimestamp = new Timestamp(1,2,3,4,5,6,7);
    }
    
    public Alue(int tunnus, String name, int viestienMaara, Timestamp ts) {
        id = tunnus;
        nimi = name;
        this.viestienMaara = viestienMaara;
        viimeisinViestiTimestamp = ts;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getViestienMaara() {
        return this.viestienMaara;
    }
    
    public Timestamp getViimeisinViesti() {
        return viimeisinViestiTimestamp;
    }
    
    public String getNimi() {
        return nimi;
    }
    
}
