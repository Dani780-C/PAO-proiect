package Cont;

import Card.Card;
import java.util.*;
import Tranzactie.Tranzactie;
import Card.*;

public class Cont {

    protected String IBAN;
    protected int sold;
    protected String moneda;
    protected int id_client;

    private List<Card> carduri = new ArrayList<>();

    public Cont(String IBAN, int sold, String moneda, int id_client){
        this.IBAN = IBAN;
        this.sold = sold;
        this.moneda = moneda;
        this.id_client = id_client;
    }

    public Cont(int id_client){
        this.id_client = id_client;
    }
    public Cont(){}

    public List<Tranzactie> getTranzactiiFacute(List<Tranzactie> lista){
        List<Tranzactie> tranzactii = new ArrayList<>();
        for(Tranzactie tranzactie : lista)
            if(tranzactie.getSursaIBAN().equals(this.IBAN))
                tranzactii.add(tranzactie);
        return tranzactii;
    }

    public List<Tranzactie> getTranzactiiPrimite(List<Tranzactie> lista){
        List<Tranzactie> tranzactii = new ArrayList<>();
        for(Tranzactie tranzactie : lista)
            if(tranzactie.getDestIBAN().equals(this.IBAN))
                tranzactii.add(tranzactie);
        return tranzactii;
    }

    public List<Tranzactie> getTranzactiiFacuteDupaZi(List<Tranzactie> lista, int an, int luna, int zi){
        List<Tranzactie> tranzactii = new ArrayList<>();
        for(Tranzactie tranzactie : lista) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tranzactie.getDate());

            int _an = calendar.get(Calendar.YEAR);
            int _luna = calendar.get(Calendar.MONTH);
            int _zi = calendar.get(Calendar.DAY_OF_MONTH);

            if(tranzactie.getSursaIBAN().equals(this.IBAN) && an == _an && luna == _luna && zi == _zi)
                tranzactii.add(tranzactie);
        }
        return tranzactii;
    }

    public List<Tranzactie> getTranzactiiPrimiteDupaZi(List<Tranzactie> lista, int an, int luna, int zi){
        List<Tranzactie> tranzactii = new ArrayList<>();
        for(Tranzactie tranzactie : lista) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tranzactie.getDate());

            int _an = calendar.get(Calendar.YEAR);
            int _luna = calendar.get(Calendar.MONTH);
            int _zi = calendar.get(Calendar.DAY_OF_MONTH);

            if(tranzactie.getDestIBAN().equals(this.IBAN) && an == _an && luna == _luna && zi == _zi)
                tranzactii.add(tranzactie);
        }
        return tranzactii;
    }

    public void read(Scanner in) {
        System.out.println("Introduceti datele contului:");
        in = new Scanner(System.in);
        System.out.println("IBAN: ");
        this.IBAN = in.nextLine();
        System.out.println("Sold: ");
        this.sold = Integer.parseInt(in.nextLine());
        System.out.println("Moneda: ");
        this.moneda = in.nextLine();
    }

    public void adaugaCard(Card card) {
        this.carduri.add(card);
        card.setId_client(id_client);
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public int getSold() {
        return sold;
    }

    public void setsold(int sold) {
        this.sold = sold;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public List<Card> getCarduri() {
        return carduri;
    }

    public void setCarduri(List<Card> carduri) {
        this.carduri = carduri;
    }

    public String toString() {
        String ret = "";
        ret = ret + "IBAN: " + IBAN + "\n";
        ret = ret + "Sold: " + sold + "\n";
        ret = ret + "Moneda: " + moneda + "\n";
        ret = ret + "Id client: " + id_client;
        return ret;
    }

}
