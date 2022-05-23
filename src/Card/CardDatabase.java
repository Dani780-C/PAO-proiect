package Card;

import Cont.ContEconomii;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CardDatabase {

    private Connection conexiune;

    public CardDatabase(Connection conexiune) {
        this.conexiune = conexiune;
    }

    public List<Card> read(){
        List<Card> carduri = new ArrayList<>();
        try{
            Statement statement = conexiune.createStatement();
            ResultSet result = statement.executeQuery("select * from card");
            while(result.next()) {
                Card card = new Card(result);
                carduri.add(card);
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return carduri;
    }

    public void create(Card card){
        try{
            String query = "insert into card (id_client, CVV, numarCard, dataExpirare) values (?, ?, ?, ?)";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setInt(1, card.getId_client());
            preparedStmt.setInt(2, card.getCVV());
            preparedStmt.setString(3, card.getNumarCard());
            preparedStmt.setString(4, (new SimpleDateFormat("yyyy-MM-dd h:m:s").format(card.getDataExpirare())));
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

}
