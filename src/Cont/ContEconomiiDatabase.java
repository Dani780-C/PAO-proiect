package Cont;

import Client.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Card.Card;

public class ContEconomiiDatabase {

    Connection conexiune;

    public ContEconomiiDatabase(Connection conexiune) {
        this.conexiune = conexiune;
    }

    public List<ContEconomii> read(){
        List<ContEconomii> conturiEconomii = new ArrayList<>();
        try{
            Statement statement = conexiune.createStatement();
            ResultSet result = statement.executeQuery("select * from cont_economii");

            while(result.next()) {
                ContEconomii cont = new ContEconomii(result);
                Statement statement1 = conexiune.createStatement();
                String query = "select id_client, CVV, numarCard, dataExpirare from card where id_client = " + cont.getId_client();
                ResultSet result_carduri = statement1.executeQuery(query);

                List<Card> carduri = new ArrayList<>();

                while(result_carduri.next()){
                    Card card = new Card(result_carduri);
                    carduri.add(card);
                }

                //System.out.println(carduri.size());
                cont.setCarduri(carduri);
                conturiEconomii.add(cont);
                statement1.close();
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return conturiEconomii;
    }

    public void create(ContEconomii conturiEconomii){
        try{
            String query = "insert into cont_economii (IBAN, sold, moneda, id_client, dobanda) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, conturiEconomii.getIBAN());
            preparedStmt.setInt(2, conturiEconomii.getSold());
            preparedStmt.setString(3, conturiEconomii.getMoneda());
            preparedStmt.setInt(4, conturiEconomii.getId_client());
            preparedStmt.setInt(5, conturiEconomii.getDobanda());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void delete(ContEconomii contEconomii){
        try{
            String query = "delete from cont_economii where IBAN = ?";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, contEconomii.getIBAN());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void update(Cont cont) {
        try {
            String query = "update cont_economii set sold = ? where IBAN = ?";
            PreparedStatement statement = conexiune.prepareStatement(query);
            statement.setInt(1, cont.getSold());
            statement.setString(2, cont.getIBAN());

            statement.execute();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
