package Card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class Card {
    private int id_client;
    private int CVV;
    private String numarCard;
    private Date dataExpirare;

    public Card(int id_client, int CVV, String numarCard, Date dataExpirare) {
        this.id_client = id_client;
        this.CVV = CVV;
        this.numarCard = numarCard;
        this.dataExpirare = dataExpirare;
    }

    public Card(int CVV, String numarCard, Date dataExpirare) {
        this.CVV = CVV;
        this.numarCard = numarCard;
        this.dataExpirare = dataExpirare;
    }

    public Card(ResultSet in) throws SQLException {
        this.id_client = in.getInt("id_client");
        this.CVV = in.getInt("CVV");
        this.numarCard = in.getString("numarCard");
        this.dataExpirare = in.getDate("dataExpirare");
    }

    public void setId_client(int id_client){
        this.id_client = id_client;
    }
    public int getId_client(){
        return id_client;
    }

    public String getNumarCard(){
        return numarCard;
    }

    public void setNumarCard(String numarCard){
        this.numarCard = numarCard;
    }

    public int getCVV(){
        return CVV;
    }

    public void setCVV(int CVV){
        this.CVV = CVV;
    }

    public Date getDataExpirare(){
        return dataExpirare;
    }

    public void setDataExpirare(Date dataExpirare){
        this.dataExpirare = dataExpirare;
    }

    @Override
    public String toString() {
        String ret = "";
        ret = ret + "CVV: " + this.CVV + "\n";
        ret = ret + "Numar Card: " + this.numarCard + "\n";
        ret = ret + "Data expirare: ";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dataExpirare);
        ret = ret + (calendar.get(Calendar.DAY_OF_MONTH)) + "-" + (calendar.get(Calendar.MONTH)) + "-" + (calendar.get(Calendar.YEAR)) + "\n";
        return ret;
    }

}
