package unibs.ing.progettosw.testing;

import org.junit.Test;
import unibs.ing.progettosw.appUtente.controller.UserController;
import unibs.ing.progettosw.ristorante.domain.Gestore;
import unibs.ing.progettosw.ristorante.domain.Prenotazione;
import unibs.ing.progettosw.utilities.DateUtility;
import unibs.ing.progettosw.utilities.JSONFileReader;
import unibs.ing.progettosw.utilities.StringToDateConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAppUtente {

    // Metodi da testare : - creaPrenotazione di UserController, - ordinaPiatti, -ordinaMenu o crea + listaMenu e listaPiatti
    UserController controller;
    DateUtility du = new DateUtility();
    StringToDateConverter std = new StringToDateConverter();
    JSONFileReader jfr = new JSONFileReader();
    Gestore gestore;

    @Test
    public void testPrenotazione() {
        gestore = new Gestore();
        gestore.initAll();
        controller = new UserController();
        controller.start();

        Date data = std.creaDataDaStringa("05/01/2024");

        int numeroCoperti = 3;

        Map<String, Integer> piattiPrenotati = new HashMap<>();
        piattiPrenotati.put(gestore.getListaPiattiValidi(0).get(3).getNome(), 1);

        Map<String, Integer> menuTematiciPrenotati = new HashMap<>();
        menuTematiciPrenotati.put(gestore.getMenuTematiciValidi(0).get(0).getNome(), 1);
        menuTematiciPrenotati.put(gestore.getMenuTematiciValidi(0).get(1).getNome(), 1);

        Prenotazione p = new Prenotazione(data, du.todaysDate(),
                numeroCoperti, menuTematiciPrenotati, piattiPrenotati);

        Prenotazione prenotazioneDaFile = jfr.leggiPrenotazioni("/unibs/ing/progettosw/testing/prenotazioniTest.json", "prenotazioni").get(0);
        assertTrue(checkIfPrenotazioniAreEqual(p, prenotazioneDaFile));
    }

    private boolean checkIfPrenotazioniAreEqual(Prenotazione actual, Prenotazione expected) {
        boolean expectedResult = true;

        if (actual.getData().compareTo(expected.getData()) != 0)
            expectedResult = false;
        if (actual.getNumeroCoperti() != expected.getNumeroCoperti())
            expectedResult = false;
        if (!actual.getPiattoPrenotato().equals(expected.getPiattoPrenotato()))
            expectedResult = false;
        if (!actual.getMenuTematicoPrenotato().equals(expected.getMenuTematicoPrenotato()))
            expectedResult = false;

        return expectedResult;
    }
}
