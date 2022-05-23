package Tranzactie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TranzactieDatabase {

    Connection conexiune;

    public TranzactieDatabase(Connection conexiune){
        this.conexiune = conexiune;
    }

    public List<Tranzactie> read(){
        List<Tranzactie> tranzactii = new ArrayList<>();
        try{
            Statement statement = conexiune.createStatement();
            ResultSet result = statement.executeQuery("select * from tranzactie");
            while(result.next()) {
                Tranzactie tranzactie = new Tranzactie(result);
                tranzactii.add(tranzactie);
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return tranzactii;
    }

    public void create(Tranzactie tranzactie){
        try{
            String query = "insert into tranzactie (suma, moneda, sursaIBAN, destIBAN, data) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setDouble(1, tranzactie.getSuma());
            preparedStmt.setString(2, tranzactie.getMoneda());
            preparedStmt.setString(3, tranzactie.getSursaIBAN());
            preparedStmt.setString(4, tranzactie.getDestIBAN());
            preparedStmt.setString(5, (new SimpleDateFormat("yyyy-MM-dd h:m:s")).format(tranzactie.getDate()));
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void delete(Tranzactie tranzactie){
        try{
            String query = "delete from tranzactie where sursaIBAN = ? and destIBAN = ?";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, tranzactie.getSursaIBAN());
            preparedStmt.setString(2, tranzactie.getDestIBAN());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void update(Tranzactie tranzactie){
        try{
            String query = "update tranzactie set moneda = ? where sursaIBAN = ? and destIBAN = ?";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, tranzactie.getMoneda());
            preparedStmt.setString(2, tranzactie.getSursaIBAN());
            preparedStmt.setString(3, tranzactie.getDestIBAN());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
