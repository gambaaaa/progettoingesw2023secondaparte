package unibs.ing.progettosw.ristorante.view;

import unibs.ing.progettosw.ristorante.controller.Controller;
import unibs.ing.progettosw.ristorante.domain.*;
import unibs.ing.progettosw.utilities.InputDati;

import javax.swing.text.View;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static java.lang.Thread.sleep;

public class ViewRistorante {
    /*
     * View utilizzata per stampare su console le informazioni essenziali del ristorante.
     * */
    private Controller controller;

    public ViewRistorante() throws ParseException {
        controller = new Controller();
    }

    // carica le prenotazioni, tenendo conto "dello scorrere del tempo".
    // pre : giorniPassati >= 0
    // post : salva prenotazioni su file.
    public void caricaPrenotazioni(int giorniPassati) throws IOException, ParseException, InterruptedException {
        System.out.println("Caricamento delle prenotazioni...");
        controller.caricaGestore();
        controller.caricaAddetto(giorniPassati);
        sleep(3000);
        System.out.println("Fatto! Ci vediamo al ristorante!\n");
    }

    // stampa su console lo svolgimento della simulazione del ristorante
    public void interazioneInizialeUI() {
        System.out.println("Buongiorno, benvenuto nella simulazione del ristorante:");

        try {
            System.out.println("Caricamento dei dati del ristorante...");
            controller.caricaGestore();
            System.out.println(controller.stampaInformazioniRistorante());

            System.out.println("Premi invio per continuare...");
            new java.util.Scanner(System.in).nextLine();

            System.out.println("Fatto!\n\nSimulazione magazziniere...");
            controller.caricaMagazziniere();
            sleep(3000);

            System.out.println("Fatto!\n\nSimulazione magazzino...");
            controller.simulaMagazzino();
            System.out.println("Magazzino riempito correttamente!");

            System.out.print("\nSimulazione cucina...");
            controller.simulaCucina();
            System.out.println("\nFinito!\nChiusura del programma...");
        } catch (InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
