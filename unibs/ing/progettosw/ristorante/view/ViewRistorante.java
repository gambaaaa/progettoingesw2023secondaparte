package unibs.ing.progettosw.ristorante.view;

import unibs.ing.progettosw.exceptions.ErrorDialog;
import unibs.ing.progettosw.exceptions.ErrorLogger;
import unibs.ing.progettosw.ristorante.Initializer;
import unibs.ing.progettosw.ristorante.controller.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;

import static java.lang.Thread.sleep;

public class ViewRistorante {
    /*
     * View utilizzata per stampare su console le informazioni essenziali del ristorante.
     * */
    private Controller controller;

    public ViewRistorante() {
        controller = new Controller();
    }

    // carica le prenotazioni, tenendo conto "dello scorrere del tempo".
    // pre : giorniPassati >= 0
    // post : salva prenotazioni su file.
    public void caricaPrenotazioni(int giorniPassati) {
        System.out.println("Caricamento delle prenotazioni...");
        controller.caricaGestore();
        controller.caricaAddetto(giorniPassati);
        try {
            sleep(3000);
        } catch (InterruptedException e){
            StringWriter sWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError("Il programma si è interrotto in maniera improvvisa.\n");
            ErrorLogger.getInstance().logError(sWriter.toString()+"\n");
        }
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
        } catch (InterruptedException e) {
            StringWriter sWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError("Il programma si è interrotto in maniera improvvisa.\n");
            ErrorLogger.getInstance().logError(sWriter.toString()+"\n");
        }
    }
}
