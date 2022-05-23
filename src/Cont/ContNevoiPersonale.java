package Cont;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ContNevoiPersonale extends Cont {
    private String nevoie;

    public ContNevoiPersonale(String IBAN, int sold, String moneda, int id_client, String nevoie){
        super(IBAN, sold, moneda, id_client);
        this.nevoie = nevoie;
    }

    public ContNevoiPersonale(ResultSet in) throws SQLException {
        this.IBAN = in.getString("IBAN");
        this.sold = in.getInt("sold");
        this.moneda = in.getString("moneda");
        this.id_client = in.getInt("id_client");
        this.nevoie = in.getString("nevoie");
    }

    public ContNevoiPersonale(int id_client){
        super(id_client);
    }

    // citire cont nevoi personale
    public void read(Scanner in) {
        super.read(in);
        in = new Scanner(System.in);
        System.out.println("Nevoie: ");
        this.nevoie = in.nextLine();
    }

    @Override
    public String toString(){
        String ret = super.toString();
        ret = ret + "\nNevoie: " + nevoie + "\n";
        return ret;
    }

    public String getNevoie() {
        return nevoie;
    }
}