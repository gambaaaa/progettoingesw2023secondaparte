package unibs.ing.progettosw.appUtente.view;

import unibs.ing.progettosw.appUtente.controller.UserController;
import unibs.ing.progettosw.utilities.DateUtility;
import unibs.ing.progettosw.utilities.InputDati;

import java.text.ParseException;
import java.util.Map;

public class UserViewFacade {

    private UserController controller = new UserController();
    private DateUtility du = new DateUtility();

    public UserViewFacade() throws ParseException {
    }

    public void startViewFacade(int dayPassed) throws ParseException {
        controller.start();

        System.out.println("Inserire la data della prenotazione: (formato dd/MM/yyyy)");
        String data = inserimentoData();

        int numeroCoperti = inserimentoNumeroCoperti();

        int numeroMenu = inserimentoNumeroMenuTematico(numeroCoperti);

        int numeroPiatti = inserimentoNumeroPiatti(numeroMenu, numeroCoperti);

        System.out.println("Scelta dei menù tematici da ordinare: ");
        System.out.println(controller.listaMenuDaStampare(dayPassed));
        Map<String, Integer> menuTematiciOrdinati = controller.ordinaMenuTematici(numeroMenu, dayPassed);

        System.out.println("Scelta dei piatti da ordinare: ");
        System.out.println(controller.listaPiattiDaStampare(dayPassed));
        Map<String, Integer> piattiOrdinati = controller.ordinaPiatti(numeroPiatti, dayPassed);

        controller.creaPrenotazione(data, numeroCoperti, menuTematiciOrdinati, piattiOrdinati);
        System.out.println("Prenotazione creata correttamente, alla prossima...");
    }

    // post : data (formato dd-MM-YYYY)
    private String inserimentoData() {
        // per comodità costruiamo la data partendo dal mese, giorno e anno
        int mese = inserimentoMese();
        int giorno = inserimentoGiorno(mese);
        int anno = inserimentoAnno();

        return controller.creaData(giorno, mese, anno);
    }

    private int inserimentoAnno() {
        System.out.println("Anno: ");
        return InputDati.leggiIntero(2023, 2024);
    }

    private int inserimentoMese() {
        System.out.println("Mese (1 = gennaio, 12 = dicembre): ");
        return InputDati.leggiIntero(1, 12);
    }

    // pre : mese >= 1 and mese <= 12
    // post : giorno > 0 and (giorno <= 31 or giorno <= 30 or giorno <= 28)
    private int inserimentoGiorno(int mese) {
        System.out.println("Giorno: ");
        return InputDati.leggiIntero(1, du.getLastDayOfMonth(mese));
    }

    public int inserimentoNumeroCoperti() {
        System.out.println("Numero coperti: ");
        // per comodità di mettiamo minimo 2 coperti, lasciando la possibilità di prenotare almeno un piatto e un menu tematico.
        return InputDati.leggiIntero(2, 10);
    }

    // pre : numeroCoperti >= 2
    // post : numeroMenu >= 1 and numeroMenu <= numeroCoperti - 1
    private int inserimentoNumeroMenuTematico(int numeroCoperti) {
        System.out.println("Numero di menu tematici da ordinare: ");
        return InputDati.leggiIntero(1, numeroCoperti - 1);
    }


    // pre : numeroCoperti >= 2 and numeroMenu >= 1 and numeroMenu <= numeroCoperti - 1
    // post : numeroPiatti == numeroCoperti - numeroMenu
    private int inserimentoNumeroPiatti(int numeroMenu, int numeroCoperti) {
        System.out.println("Numero di piatti da ordinare: ");
        return InputDati.leggiIntero(numeroCoperti - numeroMenu, numeroCoperti - numeroMenu);
    }
}
