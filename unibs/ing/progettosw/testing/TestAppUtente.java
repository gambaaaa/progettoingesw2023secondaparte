package unibs.ing.progettosw.testing;

import org.junit.Test;
import unibs.ing.progettosw.appUtente.controller.UserController;
import unibs.ing.progettosw.ristorante.domain.Ingrediente;
import unibs.ing.progettosw.ristorante.domain.MenuTematico;
import unibs.ing.progettosw.ristorante.domain.Prenotazione;
import unibs.ing.progettosw.utilities.DateUtility;
import unibs.ing.progettosw.utilities.JSONFileReader;
import unibs.ing.progettosw.utilities.StringToDateConverter;
import unibs.ing.progettosw.ristorante.domain.Piatto;
import unibs.ing.progettosw.ristorante.domain.Ricetta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TestAppUtente {

    // Metodi da testare : - creaPrenotazione di UserController, - ordinaPiatti, -ordinaMenu o crea + listaMenu e listaPiatti

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
        int numeroCoperti = 1;
        String data = "20/12/2023";

        Map<String, Integer> menuTematiciPrenotati = new HashMap<>();
        menuTematiciPrenotati.put("MenuPesce",numeroMenu);
        controller.creaPrenotazione(data,numeroCoperti,menuTematiciPrenotati,null);

        Map<String, Integer> ingredienti1 = new HashMap<>();
        // String nome, int quantita, String unitaMisura, Date dataScadenza
        ingredienti1.put("Pasta",100);
        ingredienti1.put("Salmone",100);
        ingredienti1.put("Panna",50);

        Map<String, Integer> ingredienti2 = new HashMap<>();
        ingredienti2.put("Branzino",100);
        ingredienti2.put("Aglio",5);
        ingredienti2.put("Sale fino",5);
        ingredienti2.put("Olio extravergine di oliva",100);
        ingredienti2.put("Timo",5);
        ingredienti2.put("Olive taggiasche",10);
        ingredienti2.put("Pepe nero",5);


       /* List<Piatto> listaPiattiMenu = new ArrayList<Piatto>();
        listaPiattiMenu.add(new Piatto("Pasta al salmone",new Ricetta("Pasta al salmone",ingredienti1,2,6.0),6.0,std.creaDataDaStringa("01/01/2023"), std.creaDataDaStringa("23/11/2023")));
        listaPiattiMenu.add(new Piatto("Branzino alla ligure",new Ricetta("Branzino alla ligure",ingredienti2,2,7.0),7.0,std.creaDataDaStringa("01/01/2023"), std.creaDataDaStringa("31/12/2025")));
       */

        Prenotazione p = new Prenotazione(std.creaDataDaStringa(data),du.todaysDate(),numeroCoperti,menuTematiciPrenotati,null);

        JSONFileReader jfr = new JSONFileReader();

        List<Prenotazione> prenotazioni = jfr.leggiPrenotazioni("/initFiles/prenotazioni.json", "prenotazioni");
        Prenotazione p1 = prenotazioni.stream().filter(prenotazione -> prenotazione.getMenuTematicoPrenotato().containsKey("MenuPesce") && prenotazione.getDataPrenotazione().equals(du.todaysDate())).findAny().orElse(null);

        assert p1 != null;
        assertEquals(p.getMenuTematicoPrenotato(),p1.getMenuTematicoPrenotato());
    }

    @Test
    public void testPiattiPrenotati(){


    }

    @Test
    public void testPrenotazione(){



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
