package Cont;

import Client.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Card.Card;

public class ContNevoiPersonaleDatabase {

    Connection conexiune;

    public ContNevoiPersonaleDatabase(Connection conexiune){
        this.conexiune = conexiune;
    }

    public List<ContNevoiPersonale> read(){
        List<ContNevoiPersonale> conturiNevoiPersonale = new ArrayList<>();
        try{
            Statement statement = conexiune.createStatement();
            ResultSet rezultat = statement.executeQuery("select * from cont_nevoi_personale");

            while(rezultat.next()) {
                ContNevoiPersonale cont = new ContNevoiPersonale(rezultat);
                Statement statement1 = conexiune.createStatement();
                String query = "select id_client, CVV, numarCard, dataExpirare from card where id_client = " + cont.getId_client();
                ResultSet result_carduri = statement1.executeQuery(query);

                List<Card> carduri = new ArrayList<>();

                while(result_carduri.next()){
                    Card card = new Card(result_carduri);
                    carduri.add(card);
                }

                ///System.out.println(carduri.size());
                cont.setCarduri(carduri);
                conturiNevoiPersonale.add(cont);
                statement1.close();
            }
            statement.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return conturiNevoiPersonale;
    }

    public void create(ContNevoiPersonale conturiNevoiPersonale){
        try{
            String query = "insert into cont_nevoi_personale (IBAN, sold, moneda, id_client, nevoie) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, conturiNevoiPersonale.getIBAN());
            preparedStmt.setInt(2, conturiNevoiPersonale.getSold());
            preparedStmt.setString(3, conturiNevoiPersonale.getMoneda());
            preparedStmt.setInt(4, conturiNevoiPersonale.getId_client());
            preparedStmt.setString(5, conturiNevoiPersonale.getNevoie());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void delete(ContNevoiPersonale contNevoiPersonale){
        try{
            String query = "delete from cont_nevoi_personale where IBAN = ?";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, contNevoiPersonale.getIBAN());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void update(Cont cont) {
        try {
            String query = "update cont_nevoi_personale set sold = ? where IBAN = ?";
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
