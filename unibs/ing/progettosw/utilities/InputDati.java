package unibs.ing.progettosw.utilities;

import unibs.ing.progettosw.exceptions.ErrorDialog;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputDati {
    private final static String ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto";
    private final static String ERRORE_MINIMO = "Attenzione: e' richiesto un valore maggiore o uguale a ";
    private final static String ERRORE_MASSIMO = "Attenzione: e' richiesto un valore minore o uguale a ";
    private final static ErrorDialog ed = new ErrorDialog();
    /*
     * Classe di utilità. Contiene metodi utili per effettuare correttamente gli input da tastiera.
     *
     * */
    private static Scanner lettore = creaScanner();

    private static Scanner creaScanner() {
        Scanner creato = new Scanner(System.in);
        //creato.useDelimiter(System.getProperty("line.separator"));
        return creato;
    }

    public static int leggiIntero() {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            try {
                valoreLetto = lettore.nextInt();
                lettore.nextLine();
                finito = true;
            } catch (InputMismatchException e) {
                ed.logError(ERRORE_FORMATO);
                String daButtare = lettore.next();
            }
        } while (!finito);
        return valoreLetto;
    }

    public static int leggiIntero(int minimo, int massimo) {
        boolean finito = false;
        int valoreLetto;
        do {
            valoreLetto = leggiIntero();
            if (valoreLetto >= minimo && valoreLetto <= massimo)
                finito = true;
            else if (valoreLetto < minimo)
                ed.logError(ERRORE_MINIMO + minimo);
            else
                ed.logError(ERRORE_MASSIMO + massimo);
        } while (!finito);

        return valoreLetto;
    }
}
