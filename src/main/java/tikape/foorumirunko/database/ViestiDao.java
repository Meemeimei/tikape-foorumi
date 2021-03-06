package tikape.foorumirunko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.foorumirunko.domain.Viesti;

/**
 *
 * @author eemitant
 * @authro xvixvi
 */
public class ViestiDao implements Dao<Viesti, String> {

    private Database database;

    public ViestiDao(Database d) {
        database = d;
    }

    @Override
    public Viesti findOne(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE alue = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String sisalto = rs.getString("sisalto");
        String otsikko = rs.getString("otsikko");

        Viesti v = new Viesti(otsikko, sisalto);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            String sisalto = rs.getString("sisalto");
            String otsikko = rs.getString("otsikko");

            viestit.add(new Viesti(otsikko, sisalto));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Viesti WHERE sisalto = ?");

        stmt.setObject(1, key);

        stmt.execute();

        stmt.close();
        connection.close();
    }
    
        public void insertOne(Viesti a) throws SQLException {
        Connection con = database.getConnection();
        PreparedStatement stmt = con.prepareStatement("INSERT INTO Viesti VALUES (?,?,?,?,?)");
        stmt.setObject(1, a.getKayttaja());
        stmt.setObject(2, a.getAlueenId());
        stmt.setObject(3, a.getOtsikko());
        stmt.setObject(4, a.getSisalto());
        stmt.setObject(5, a.getAika());
        
        stmt.executeUpdate();

        stmt.close();
        con.close();
    }
}
