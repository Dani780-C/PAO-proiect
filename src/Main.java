

//
//        - clase simple cu atribute private / protected și metode de acces
//        - cel puțin 2 colecții diferite capabile să gestioneze obiectele definite anterior (eg: List, Set,
//        Map, etc.) dintre care cel puțin una sa fie sortata – se vor folosi array-uri uni-
//        /bidimensionale în cazul în care nu se parcurg colectiile pana la data checkpoint-ului.
//        - utilizare moștenire pentru crearea de clase adiționale și utilizarea lor încadrul colecțiilor;
//        - cel puțin o clasă serviciu care sa expună operațiile sistemului
//        - o clasa Main din care sunt făcute apeluri către servicii

import Card.CardDatabase;
import Client.Client;
import Cont.ContEconomiiDatabase;
import Cont.ContNevoiPersonaleDatabase;
import Tranzactie.TranzactieDatabase;
import Client.*;

import java.sql.Connection;
import java.text.ParseException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Main {

    public static Connection getConexiune() {
        try {
            String url = "jdbc:mysql://localhost:3306/proiect";
            String user = "root";
            String password = "";

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static void afiseazaServicii() {
        System.out.println("1 - Adauga un client");
        System.out.println("2 - Sterge un client");
        System.out.println("3 - Afiseaza toti clientii");
        System.out.println("4 - Adauga un cont de nevoi personale");
        System.out.println("5 - Adauga un cont de economii");
        System.out.println("6 - Afiseaza toate conturile");
        System.out.println("7 - Afiseaza sold-ul unui client cu un ID dat");
        System.out.println("8 - Creaza un card pentru un client (dupa ID si dupa IBAN-ul unui cont)");
        System.out.println("9 - Afiseaza cardurile unui client (dupa ID)");
        System.out.println("10 - Fa o tranzactie");
        System.out.println("11 - Afiseaza toate tranzactiile");
        System.out.println("12 - Schimbati numele si prenumele unui client cu un ID dat");
        System.out.println("13 - Stergeti un cont dupa ID-ul clientului si IBAN-ul contului");
        System.out.println("14 - Stergeti tranzactii sursaIBAN -> destIBAN");
        System.out.println("15 - Schimba moneda tranzactiilor sursaIBAN -> destIBAN in EURO");
        System.out.println("0 - END");
    }

    public static void main(String[] args) {

        boolean end = false;
        Connection connection = Main.getConexiune();
        TranzactieDatabase tranzactieDatabase = new TranzactieDatabase(connection);
        ContNevoiPersonaleDatabase contNevoiPersonaleDatabase = new ContNevoiPersonaleDatabase(connection);
        ContEconomiiDatabase contEconomiiDatabase = new ContEconomiiDatabase(connection);
        ClientDatabase clientDatabase = new ClientDatabase(connection);
        CardDatabase cardDatabase = new CardDatabase(connection);
        Service service = new Service(tranzactieDatabase, contNevoiPersonaleDatabase, contEconomiiDatabase, clientDatabase, cardDatabase);


        Scanner in = new Scanner(System.in);
        while (!end) {
            afiseazaServicii();
            System.out.println("Alege serviciu: ");
            int nr_serviciu = -1;
            int id;
            String IBAN;
            try {
                /// Operatiile de READ
                service.setConturiNevoiPersonale(contNevoiPersonaleDatabase.read());
                service.setClienti(clientDatabase.read());
                service.setTranzactii(tranzactieDatabase.read());
                service.setConturiEconomii(contEconomiiDatabase.read());

                nr_serviciu = in.nextInt();
                switch (nr_serviciu) {
                    case 1 -> service.adaugaClient(in);
                    case 2 -> service.stergeClient(in);
                    case 3 -> service.afiseazaClienti();
                    case 4 -> {
                        System.out.println("Introduceti ID client: ");
                        id = in.nextInt();
                        service.adaugaContNevoiPersonale(id, in);
                    }
                    case 5 -> {
                        System.out.println("Introduceti ID client: ");
                        id = in.nextInt();
                        service.adaugaContEconomii(id, in);
                    }
                    case 6 -> {
                        service.afiseazaConturi();
                    }
                    case 7 -> {
                        System.out.println("Introduceti ID client: ");
                        id = in.nextInt();
                        System.out.println(service.getSoldTotalInRON(id));
                    }
                    case 8 -> {
                        System.out.println("Introduceti ID client: ");
                        id = in.nextInt();
                        in = new Scanner(System.in);
                        System.out.println("Introduceti IBAN-ul contului: ");
                        IBAN = in.nextLine();
                        service.creazaCardClient(id, IBAN);
                    }
                    case 9 -> {
                        System.out.println("Introduceti ID client: ");
                        id = in.nextInt();
                        service.afiseazaCarduriDupaIdClient(id);
                    }
                    case 10 -> {
                        service.mapCont();
                        service.creazaTranzactie();
                    }
                    case 11 -> {
                        service.mapCont();
                        service.afiseazaTranzactii();
                    }
                    case 12 -> {
                        service.schimbaNumePrenumeClient(in);
                    }
                    case 13 -> {
                        service.stergeContDupaIdSiIBAN();
                    }
                    case 14 -> {
                        service.stergeTranzactie();
                    }
                    case 15 -> {
                        service.schimbaMonedaTranzactie();
                    }
                    case 0 -> end = true;
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}

