package Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client {

    private int id;
    private String sex;
    private String nume;
    private String prenume;
    private String email;
    private String numar_de_telefon;
    private Date data_nastere;
    private String CNP;
    private Adresa adresa;
    public static int id_static;

    static {
        id_static = 0;
    }

    {
        ++id_static;
    }

    public Client(){
        this.id = id_static;
        this.data_nastere = new Date();
    }

    public Client(String sex, String nume, String prenume, String email, String numar_de_telefon, Date data_nastere, String CNP, Adresa adresa){
        this.id = id_static;
        this.sex = sex;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.numar_de_telefon = numar_de_telefon;
        this.data_nastere = data_nastere;
        this.CNP = CNP;
        this.adresa = adresa;
    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getSex(){
        return sex;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    public String getNume(){
        return nume;
    }

    public void setNume(String nume){
        this.nume = nume;
    }

    public String getPrenume(){
        return prenume;
    }

    public void setPrenume(String prenume){
        this.prenume = prenume;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getNumar_de_telefon(){
        return numar_de_telefon;
    }

    public void setNumar_de_telefon(String numar_de_telefon){
        this.numar_de_telefon = numar_de_telefon;
    }

    public String getCNP(){
        return CNP;
    }

    public Date getData_nastere(){
        return data_nastere;
    }

    public Adresa getAdresa(){
        return adresa;
    }
    public void setAdresa(Adresa adresa){ this.adresa = adresa; }

    @Override
    public String toString(){
        String ret = "";
        ret = ret + "\nId client: " + id + "\n";
        ret = ret + "Nume: " + nume + "\n";
        ret = ret + "Prenume: " + prenume + "\n";
        ret = ret + "Numar telefon: " + numar_de_telefon + "\n";
        ret = ret + "Email: " + email + "\n";
        ret = ret + "Data nasterii: " + data_nastere.toString() + "\n";
        //ret = ret + adresa.toString();
        return ret;
    }

    // implementare citire client
    public void readClient(Scanner in) throws ParseException {
        System.out.println("Introduceti datele clientului: ");
        in = new Scanner(System.in);
        System.out.println("Nume: ");
        this.nume = in.nextLine();
        if(this.nume.length() > 0){
            this.nume = this.nume.substring(0, 1).toUpperCase() + this.nume.substring(1).toLowerCase();
        }
        System.out.println("Prenume: ");
        this.prenume = in.nextLine();
        if(this.prenume.length() > 0){
            this.prenume = this.prenume.substring(0, 1).toUpperCase() + this.prenume.substring(1).toLowerCase();
        }
        System.out.println("Sex (M - masculin, F - feminin): ");
        this.sex = in.nextLine();
        System.out.println("Email: ");
        this.email = in.nextLine();
        System.out.println("Numar de telefon: ");
        this.numar_de_telefon = in.nextLine();
        System.out.println("CNP: ");
        this.CNP = in.nextLine();
        System.out.println("Data nasterii (yyyy-MM-dd): ");
        this.data_nastere = new SimpleDateFormat("yyyy-MM-dd").parse(in.nextLine());
        this.adresa = new Adresa();
        this.adresa.readAdresa(in);
    }

    public void readFromResultSet(ResultSet in) throws SQLException {
        this.id = in.getInt("id");
        this.sex = in.getString("sex");
        this.nume = in.getString("nume");
        this.prenume = in.getString("prenume");
        this.CNP = in.getString("CNP");
        this.data_nastere = in.getDate("data_nastere");
        this.email = in.getString("email");
        this.numar_de_telefon = in.getString("numar_de_telefon");
        this.adresa = new Adresa();
    }

//    public static void main(String[] args) throws ParseException {
//        Scanner in = new Scanner(System.in);
//        Client client = new Client();
//        client.readClient(in);
//        System.out.println(client);
//    }

}




