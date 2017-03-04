
package tikape.foorumirunko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.foorumirunko.domain.Kayttaja;

public class KayttajaDao implements Dao<Kayttaja, String> {

    private Database database;
    
    public KayttajaDao(Database d) {
        this.database = d;
    }
    
    @Override
    public Kayttaja findOne(String nimimerkki) throws SQLException {
        Connection con = database.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM Kayttaja WHERE nimimerkki = ?");
        stmt.setObject(1, nimimerkki);
        
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String nimim = rs.getString("nimimerkki");

        Kayttaja k = new Kayttaja(nimim);

        rs.close();
        stmt.close();
        con.close();

        return k;
    }

    @Override
    public List findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttaja");

        ResultSet rs = stmt.executeQuery();
        List<Kayttaja> kayttajat = new ArrayList<>();
        while (rs.next()) {
            String nimimerkki = rs.getString("nimimerkki");

            kayttajat.add(new Kayttaja(nimimerkki));
        }

        rs.close();
        stmt.close();
        connection.close();

        return kayttajat;
    }

    @Override
    public void delete(String nimimerkki) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Kayttaja WHERE nimimerkki = ?");
        stmt.setObject(1, nimimerkki);
        
        stmt.execute(); // Eikö pidä olla stmt.execute(); ?
        
        int kuinkaMoneenRiviinVaikutettiin = stmt.executeUpdate(); // Toimiikohan executeUdate() vai pitäisikö olla stmt.execute();
        System.out.println("KayttajaDao poisti käyttäjän " + nimimerkki + "muuttaen " + kuinkaMoneenRiviinVaikutettiin + "kpl rivejä");
        
        stmt.close();
        connection.close();
    }
    
}
