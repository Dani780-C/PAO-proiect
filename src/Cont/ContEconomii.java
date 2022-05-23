package Cont;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ContEconomii extends Cont {

    private int dobanda;

    public ContEconomii(int dobanda, String IBAN, int sold, String moneda, int id_client){
        super(IBAN, sold, moneda, id_client);
        this.dobanda = dobanda;
    }

    public ContEconomii(int id_client){
        super(id_client);
    }

    public ContEconomii(ResultSet in) throws SQLException {
        this.IBAN = in.getString("IBAN");
        this.sold = in.getInt("sold");
        this.moneda = in.getString("moneda");
        this.id_client = in.getInt("id_client");
        this.dobanda = in.getInt("dobanda");
    }

    public int getDobanda(){
        return dobanda;
    }

    public void setDobanda(int dobanda){
        this.dobanda = dobanda;
    }

    // citire card economii
    public void read(Scanner in) {
        super.read(in);
        System.out.println("Dobanda: ");
        in = new Scanner(System.in);
        this.dobanda = Integer.parseInt(in.nextLine());
    }

    @Override
    public String toString(){
        String ret = super.toString();
        ret = ret + "\nDobanda: " + dobanda + "\n";
        return ret;
    }

}
