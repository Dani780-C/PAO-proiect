import java.text.ParseException;
import java.util.*;

import Card.Card;
import Card.GeneratorCard;
import Client.Client;
import Cont.Cont;
import Cont.ContEconomii;
import Cont.ContNevoiPersonale;
import Tranzactie.Tranzactie;
import Client.*;
import Cont.*;
import Tranzactie.*;
import Card.*;

public class Service {

    private List<Client> clienti = new ArrayList<>();
    private List<ContNevoiPersonale> conturiNevoiPersonale = new ArrayList<>();
    private List<ContEconomii> conturiEconomii = new ArrayList<>();
    private List<Tranzactie> tranzactii = new ArrayList<>();
    private HashSet<String> IBANuri = new HashSet<String>();
    private Map<String, Cont> contMap = new HashMap<>();

    private TranzactieDatabase tranzactieDatabase;
    private ContNevoiPersonaleDatabase contNevoiPersonaleDatabase;
    private ContEconomiiDatabase contEconomiiDatabase;
    private ClientDatabase clientDatabase;
    private CardDatabase cardDatabase;

    //public Service(){}

    public Service(TranzactieDatabase _tranzactieDatabase, ContNevoiPersonaleDatabase _contNevoiPersonaleDatabase, ContEconomiiDatabase _contEconomiiDatabaseB, ClientDatabase _clientDatabase, CardDatabase _cardDatabase) {
        this.tranzactieDatabase = _tranzactieDatabase;
        this.contNevoiPersonaleDatabase = _contNevoiPersonaleDatabase;
        this.contEconomiiDatabase = _contEconomiiDatabaseB;
        this.clientDatabase = _clientDatabase;
        this.cardDatabase = _cardDatabase;
    }

    public List<Client> getClienti() {
        return clienti;
    }

    public void setClienti(List<Client> clienti) {
        this.clienti = clienti;
    }

    public List<ContNevoiPersonale> getConturiNevoiPersonale() {
        return conturiNevoiPersonale;
    }

    public void setConturiNevoiPersonale(List<ContNevoiPersonale> conturi) {
        this.conturiNevoiPersonale = conturi;
    }

    public List<ContEconomii> getConturiEconomii() {
        return conturiEconomii;
    }

    public void setConturiEconomii(List<ContEconomii> conturi) {
        this.conturiEconomii = conturi;
    }

    public List<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    public void setTranzactii(List<Tranzactie> tranzactii) {
        this.tranzactii = tranzactii;
    }

    public void adaugaClient(Scanner in) throws ParseException {
        Client c = new Client();
        c.readClient(in);
        clienti.add(c);
        clientDatabase.create(c);
    }

    public void stergeClient(Scanner in){
        System.out.println("Introduceti ID client");
        int id = in.nextInt();
        boolean ok = false;
        for(Client client : clienti){
            if (client.getId() == id){
                ok = true;
                clientDatabase.delete(client);
            }

        }
        if(ok == false)
            System.out.println("Clientul cu id-ul dat nu exista\n");
    }

    public void schimbaNumePrenumeClient(Scanner in){
        System.out.println("Introduceti ID client");
        int id = in.nextInt();
        boolean ok = false;
        for(Client client : clienti){
            if(client.getId() == id){
                System.out.println("Introduceti numele nou: ");
                in = new Scanner(System.in);
                String nume_nou = in.nextLine();
                System.out.println("Introduceti prenumele nou: ");
                String prenume_nou = in.nextLine();
                clientDatabase.update(client, nume_nou, prenume_nou);
                System.out.println("Nume si prenume actualizate");
                ok = true;
            }
        }
        if(ok == false)
            System.out.println("Nu exista niciun client cu id-ul dat");
    }

    public void stergeContDupaIdSiIBAN(){
        Scanner in = new Scanner(System.in);
        System.out.println("Introduceti ID client: ");
        int id = in.nextInt();
        System.out.println("Introduceti IBAN cont: ");
        in = new Scanner(System.in);
        String IBAN = in.nextLine();
        boolean ok = false;
        for (ContEconomii cont : conturiEconomii)
            if(cont.getIBAN().equals(IBAN) == true && cont.getId_client() == id){
                IBANuri.remove(cont.getIBAN());
                contEconomiiDatabase.delete(cont);
                ok = true;
            }
        for (ContNevoiPersonale cont : conturiNevoiPersonale)
            if(cont.getIBAN().equals(IBAN) == true && cont.getId_client() == id){
                IBANuri.remove(cont.getIBAN());
                contNevoiPersonaleDatabase.delete(cont);
                ok = true;
            }

        if(ok == false){
            System.out.println("Nu s-a gasit un client si un cont cu datele primite\n");
        }else{
            System.out.println("Cont sters!\n");
        }
    }

    public void afiseazaClienti() {
        for (int i = 0; i < clienti.size(); i++) {
            System.out.println(clienti.get(i));

        }
    }

    public void afiseazaConturi() {
        System.out.println("Conturi de nevoi personale [" + conturiNevoiPersonale.size() + "]: \n");
        for(ContNevoiPersonale cont: conturiNevoiPersonale) {
            System.out.println(cont);
        }

        System.out.println("Conturi de economii [" + conturiEconomii.size() + "]: \n");
        for(ContEconomii cont: conturiEconomii) {
            System.out.println(cont);
        }
    }

    public Client getClientDupaId(int id) throws Exception {

        for(Client client : clienti)
            if(client.getId() == id)
                return client;

        throw new Exception("Nu exista niciun client cu id-ul dat\n");
    }

    public void adaugaContNevoiPersonale(int id_client, Scanner in) throws Exception {

        int nr = 0;
        for(Client client : clienti)
            if(client.getId() != id_client)
                nr += 1;

        if(nr == clienti.size())
            throw new Exception("Nu exista niciun client cu id-ul dat\n");
        else {
            ContNevoiPersonale cont = new ContNevoiPersonale(id_client);
            cont.read(in);
            if (IBANuri.contains(cont.getIBAN()) == true) {
                throw new Exception("Nu pot fi mai multe conturi cu acelasi IBAN\n");
            }
            else {
                IBANuri.add(cont.getIBAN());
                this.conturiNevoiPersonale.add(cont);
                this.mapCont();
                contNevoiPersonaleDatabase.create(cont);
            }
        }
    }

    public void adaugaContEconomii(int id_client, Scanner in) throws Exception {

        int nr = 0;
        for(Client client : clienti)
            if(client.getId() != id_client)
                nr += 1;

        if(nr == clienti.size())
            throw new Exception("Nu exista niciun client cu id-ul dat\n");
        else {
            ContEconomii cont = new ContEconomii(id_client);
            cont.read(in);
            if (IBANuri.contains(cont.getIBAN()) == true) {
                throw new Exception("Nu pot fi mai multe conturi cu acelasi IBAN\n");
            }
            else {
                IBANuri.add(cont.getIBAN());
                this.conturiEconomii.add(cont);
                this.mapCont();
                contEconomiiDatabase.create(cont);
            }
        }
    }

    public int getSoldTotalInRON(int id_client) {
        int ret = 0;
        for(ContNevoiPersonale cont : conturiNevoiPersonale){
            if (cont.getId_client() == id_client) {
                if (cont.getMoneda().equals("RON"))
                    ret += cont.getSold();
                else if (cont.getMoneda().equals("EURO"))
                    ret += cont.getSold() * 5;
                else if (cont.getMoneda().equals("DOLAR"))
                    ret += cont.getSold() * 5;
            }
        }

        for(ContEconomii cont : conturiEconomii) {
            if (cont.getId_client() == id_client) {
                if (cont.getMoneda().equals("RON"))
                    ret += cont.getSold();
                else if (cont.getMoneda().equals("EURO"))
                    ret += cont.getSold() * 5;
                else if (cont.getMoneda().equals("DOLAR"))
                    ret += cont.getSold() * 5;
            }
        }

        return ret;
    }

    public void creazaCardClient(int id_client, String IBAN) throws Exception {
        boolean ok = false;
        for(ContNevoiPersonale cont: conturiNevoiPersonale) {
            if (cont.getId_client() == id_client && cont.getIBAN().equals(IBAN)) {
                Card card = GeneratorCard.genereazaCard();
                card.setId_client(id_client);
                cardDatabase.create(card);
                cont.adaugaCard(card);
                ok = true;
            }
        }
        for(ContEconomii cont: conturiEconomii) {
            if (cont.getId_client() == id_client && cont.getIBAN().equals(IBAN)) {
                Card card = GeneratorCard.genereazaCard();
                card.setId_client(id_client);
                cardDatabase.create(card);
                cont.adaugaCard(card);
                ok = true;
            }
        }
        if(ok == false) {
            throw new Exception("Nu s-a gasit niciun cont cu IBAN-ul respectiv\n");
        }
    }

    public boolean verifica(List<Card> lista, Card card){

        for(Card c : lista){
            if(c.getCVV() == card.getCVV() && c.getNumarCard().equals(card.getNumarCard()) == true && c.getDataExpirare().equals(card.getDataExpirare()))
                return true;
        }
        return false;
    }

    public void afiseazaCarduriDupaIdClient(int id_client) {
        List<Card> lista = new ArrayList<>();
        List<Card> carduri = new ArrayList<>();
        for(ContNevoiPersonale cont: conturiNevoiPersonale) {
            if (cont.getId_client() == id_client) {
                carduri = cont.getCarduri();
                for(Card card : carduri)
                    if(this.verifica(lista, card) == false)
                        lista.add(card);
            }
        }
        for(ContEconomii cont: conturiEconomii) {
            if (cont.getId_client() == id_client) {
                carduri = cont.getCarduri();
                for(Card card : carduri)
                    if(this.verifica(lista, card) == false)
                        lista.add(card);
            }
        }

        for(Card card : lista) {
            System.out.println(card.toString());
        }
    }

    public void mapCont(){
        for(ContNevoiPersonale cont : this.conturiNevoiPersonale)
            this.contMap.put(cont.getIBAN(), cont);
        for(ContEconomii cont : this.conturiEconomii)
            this.contMap.put(cont.getIBAN(), cont);
    }

    public void creazaTranzactie() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Cont sursa [IBAN]: ");
        String sursaIBAN = in.nextLine();
        System.out.println("Cont destinatie [IBAN]: ");
        String dstIBAN = in.nextLine();
        System.out.println("Suma transferata: ");
        int suma_transferata = in.nextInt();
        Cont cont_sursa = null, cont_destinatie = null;

        if(contMap.containsKey(sursaIBAN))
            cont_sursa = contMap.get(sursaIBAN);
        if(contMap.containsKey(dstIBAN))
            cont_destinatie = contMap.get(dstIBAN);

        if(cont_sursa.getSold() < suma_transferata)
            throw new Exception("Sold insuficient!");

        cont_sursa.setsold(cont_sursa.getSold() - suma_transferata);
        cont_destinatie.setsold(cont_destinatie.getSold() + suma_transferata);

        if(cont_sursa instanceof ContEconomii)
            contEconomiiDatabase.update(cont_sursa);
        else
            contNevoiPersonaleDatabase.update(cont_sursa);


        if(cont_destinatie instanceof ContEconomii)
            contEconomiiDatabase.update(cont_destinatie);
        else
            contNevoiPersonaleDatabase.update(cont_destinatie);


        Tranzactie tranzactie = new Tranzactie(suma_transferata, cont_sursa.getMoneda(), sursaIBAN, dstIBAN, new Date());
        this.tranzactii.add(tranzactie);
        tranzactieDatabase.create(tranzactie);
    }

    public void stergeTranzactie(){
        System.out.println("Introduceti sursa IBAN: ");
        Scanner in = new Scanner(System.in);
        String sursa_iban = in.nextLine();
        System.out.println("Introduceti destinatie IBAN: ");
        in = new Scanner(System.in);
        String dest_iban = in.nextLine();
        boolean ok = false;
        for(Tranzactie tranzactie : tranzactii)
            if(tranzactie.getSursaIBAN().equals(sursa_iban) == true && tranzactie.getDestIBAN().equals(dest_iban) == true){
                ok = true;
                tranzactieDatabase.delete(tranzactie);
            }
        if(ok == false){
            System.out.println("Nu s-a gasit o tranzactie cu datele primite");
        }
        else{
            System.out.println("Tranzactii sterse\n");
        }
    }

    public void schimbaMonedaTranzactie(){
        System.out.println("Introduceti sursa IBAN: ");
        Scanner in = new Scanner(System.in);
        String sursa_iban = in.nextLine();
        System.out.println("Introduceti destinatie IBAN: ");
        in = new Scanner(System.in);
        String dest_iban = in.nextLine();
        boolean ok = false;
        for(Tranzactie tranzactie : tranzactii)
            if(tranzactie.getSursaIBAN().equals(sursa_iban) == true && tranzactie.getDestIBAN().equals(dest_iban) == true){
                tranzactie.setMoneda("EURO");
                ok = true;
                tranzactieDatabase.update(tranzactie);
            }
        if(ok == false){
            System.out.println("Nu s-a gasit o tranzactie cu datele primite");
        }
        else{
            System.out.println("Tranzactii actualizate\n");
        }
    }
    public void afiseazaTranzactii() {
        for(Tranzactie tran: tranzactii) {
            System.out.println(tran);
        }
    }

}
