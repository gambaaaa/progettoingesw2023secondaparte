package unibs.ing.progettosw.testing;

import org.junit.Test;
import unibs.ing.progettosw.appUtente.controller.UserController;
import unibs.ing.progettosw.ristorante.domain.Ingrediente;
import unibs.ing.progettosw.ristorante.domain.MenuTematico;
import unibs.ing.progettosw.ristorante.domain.Prenotazione;
import unibs.ing.progettosw.utilities.DateUtility;
import unibs.ing.progettosw.utilities.JSONFileReader;
import unibs.ing.progettosw.utilities.StringToDateConverter;
import unibs.ing.progettosw.ristorante.domain.Gestore;
import unibs.ing.progettosw.ristorante.domain.Piatto;
import unibs.ing.progettosw.ristorante.domain.Ricetta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;


public class TestAppUtente {

    @Test
    public void testStart(){
        UserController controller = new UserController();
        controller.start();

        assertNotNull(controller.getGestore());
    }

    @Test
    public void testMenuTematici(){
        StringToDateConverter std = new StringToDateConverter();
        DateUtility du = new DateUtility();

        UserController controller = new UserController();
        controller.start();

        int numeroMenu = 1;
        int dayPassed = 0;
        int numeroCoperti = 2;
        String data = "08/01/2024";

        Map<String, Integer> piattiPrenotati = new HashMap<>();
        piattiPrenotati.put(controller.getGestore().getListaPiattiValidi(dayPassed).get(3).getNome(), 1);

        Map<String, Integer> menuTematiciPrenotati = new HashMap<>();
        menuTematiciPrenotati.put(controller.getGestore().getMenuTematiciValidi(dayPassed).get(0).getNome(),numeroMenu); // "MenuCarne"

        Prenotazione p = new Prenotazione(std.creaDataDaStringa(data),du.todaysDate(),numeroCoperti,menuTematiciPrenotati,piattiPrenotati);

        JSONFileReader jfr = new JSONFileReader();

        List<Prenotazione> prenotazioni = jfr.leggiPrenotazioni("/unibs/ing/progettosw/testing/prenotazioniTest.json", "prenotazioni");
        Prenotazione pe = prenotazioni.stream().filter(prenotazione -> prenotazione.getMenuTematicoPrenotato().containsKey("MenuCarne") && prenotazione.getDataPrenotazione().equals(du.todaysDate())).findAny().orElse(null);

        assert pe != null;
        assertEquals(p.getMenuTematicoPrenotato(),pe.getMenuTematicoPrenotato());
    }

    @Test
    public void testPiattiPrenotati(){
        StringToDateConverter std = new StringToDateConverter();
        DateUtility du = new DateUtility();

        UserController controller = new UserController();
        controller.start();

        int numeroMenu = 1;
        int dayPassed = 0;
        int numeroCoperti = 2;
        String data = "08/01/2024";

        Map<String, Integer> piattiPrenotati = new HashMap<>();
        piattiPrenotati.put(controller.getGestore().getListaPiattiValidi(dayPassed).get(3).getNome(), 1);

        Map<String, Integer> menuTematiciPrenotati = new HashMap<>();
        menuTematiciPrenotati.put(controller.getGestore().getMenuTematiciValidi(dayPassed).get(0).getNome(),numeroMenu); // "MenuCarne"

        Prenotazione p = new Prenotazione(std.creaDataDaStringa(data),du.todaysDate(),numeroCoperti,menuTematiciPrenotati,piattiPrenotati);

        JSONFileReader jfr = new JSONFileReader();

        List<Prenotazione> prenotazioni = jfr.leggiPrenotazioni("/unibs/ing/progettosw/testing/prenotazioniTest.json", "prenotazioni");
        Prenotazione pe = prenotazioni.stream().filter(prenotazione -> prenotazione.getMenuTematicoPrenotato().containsKey("MenuCarne") && prenotazione.getDataPrenotazione().equals(du.todaysDate())).findAny().orElse(null);

        assert pe != null;
        assertEquals(p.getPiattoPrenotato(),pe.getPiattoPrenotato());
    }

    @Test
    public void testPrenotazione(){
        StringToDateConverter std = new StringToDateConverter();
        DateUtility du = new DateUtility();

        UserController controller = new UserController();
        controller.start();

        int numeroMenu = 1;
        int dayPassed = 0;
        int numeroCoperti = 2;
        String data = "08/01/2024";

        Map<String, Integer> piattiPrenotati = new HashMap<>();
        piattiPrenotati.put(controller.getGestore().getListaPiattiValidi(dayPassed).get(3).getNome(), 1);

        Map<String, Integer> menuTematiciPrenotati = new HashMap<>();
        menuTematiciPrenotati.put(controller.getGestore().getMenuTematiciValidi(dayPassed).get(0).getNome(),numeroMenu); // "MenuCarne"

        Prenotazione p = new Prenotazione(std.creaDataDaStringa(data),du.todaysDate(),numeroCoperti,menuTematiciPrenotati,piattiPrenotati);

        JSONFileReader jfr = new JSONFileReader();

        List<Prenotazione> prenotazioni = jfr.leggiPrenotazioni("/unibs/ing/progettosw/testing/prenotazioniTest.json", "prenotazioni");
        Prenotazione pe = prenotazioni.stream().filter(prenotazione -> prenotazione.getMenuTematicoPrenotato().containsKey("MenuCarne") && prenotazione.getDataPrenotazione().equals(du.todaysDate())).findAny().orElse(null);

        assert pe != null;
        assertTrue(checkIfPrenotazioniAreEqual(p,pe));
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


    @Test
    public void testPiattiValidi(){
        UserController controller = new UserController();
        controller.start();

        int dayPassed = 0;

        String listaPiattiValidi = "0 - Bistecca alla fiorentina\n1 - Tagliatelle al ragù\n2 - Branzino alla ligure\n3 - Pizza margherita\n4 - Tomahawk alla griglia\n";
        assertEquals(listaPiattiValidi,controller.listaPiattiDaStampare(0));
    }

    @Test
    public void testMenuValidi(){
        UserController controller = new UserController();
        controller.start();

        int dayPassed = 0;

        String listaMenuValidi = "0 - MenuCarne\n1 - MenuCarnePesce\n";
        assertEquals(listaMenuValidi,controller.listaMenuDaStampare(0));
    }
}
