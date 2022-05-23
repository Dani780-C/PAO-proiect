package Client;

import java.util.Scanner;

public class Adresa {
    private String tara;
    private String judet;
    private String oras;
    private String strada;

    public Adresa(){}

    public Adresa(String tara, String judet, String oras, String strada){
        this.tara = tara;
        this.judet = judet;
        this.oras = oras;
        this.strada = strada;
    }

    public String getTara(){
        return tara;
    }

    public void setTara(String tara){
        this.tara = tara;
    }

    public String getJudet(){
        return judet;
    }

    public void setJudet(String judet){
        this.judet = judet;
    }

    public String getOras(){
        return oras;
    }

    public void setOras(String oras){
        this.oras = oras;
    }

    public String getStrada(){
        return strada;
    }

    public void setStrada(String strada){
        this.strada = strada;
    }

    @Override
    public String toString(){
        String ret = "";
        ret = ret + "Tara: " + tara + "\nJudet: " + judet + "\nOras: " + oras + "\nStrada: " + strada + "\n";
        return ret;
    }

    // implementare citire adresa
    public void readAdresa(Scanner in){
        System.out.println("Introduceti adresa clientului");
        System.out.println("Tara: ");
        this.tara = in.nextLine();
        if(this.tara.length() > 0){
            this.tara = this.tara.substring(0, 1).toUpperCase() + this.tara.substring(1).toLowerCase();
        }
        System.out.println("Judet: ");
        this.judet = in.nextLine();
        if(this.judet.length() > 0){
            this.judet = this.judet.substring(0, 1).toUpperCase() + this.judet.substring(1).toLowerCase();
        }
        System.out.println("Oras: ");
        this.oras = in.nextLine();
        if(this.oras.length() > 0){
            this.oras = this.oras.substring(0, 1).toUpperCase() + this.oras.substring(1).toLowerCase();
        }
        System.out.println("Strada: ");
        this.strada = in.nextLine();
        if(this.strada.length() > 0){
            this.strada = this.strada.substring(0, 1).toUpperCase() + this.strada.substring(1).toLowerCase();
        }
    }

}
