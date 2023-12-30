package unibs.ing.progettosw.testing;

import org.junit.Test;
import unibs.ing.progettosw.ristorante.domain.AddettoPrenotazioni;
import unibs.ing.progettosw.ristorante.domain.Prenotazione;
import unibs.ing.progettosw.ristorante.domain.Ristorante;
import unibs.ing.progettosw.utilities.DateUtility;
import unibs.ing.progettosw.utilities.StringToDateConverter;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TestAddetto {

    @Test
    public void testPrenotazioni(){
        int giorniPassati = 0;
        AddettoPrenotazioni addetto = new AddettoPrenotazioni(giorniPassati);
        addetto.initPrenotazioni();
        assertNotNull(addetto.getPrenotazioni());
    }

    @Test
    public void testPrenotazioniAccettate(){
        Ristorante r = Ristorante.getInstance("bo",50,100);
        int giorniPassati = 0;
        AddettoPrenotazioni addetto = new AddettoPrenotazioni(giorniPassati);
        addetto.initPrenotazioni();
        assertNotNull(addetto.getPrenotazioniAccettate());
    }



    @Test
    public void testCaricoLavoroAttuale() {
        StringToDateConverter std = new StringToDateConverter();
        int numeriCoperti = 2; // un menu e un piatto
        Date data = std.creaDataDaStringa("27/12/2023");
        Date dataPrenotazione = std.creaDataDaStringa("29/12/2023");
        Map<String, Integer> menuTematicoPrenotato = new HashMap<>();
        menuTematicoPrenotato.put("MenuPesce", 1);
        Map<String, Integer> piattoPrenotato = new HashMap<>();
        piattoPrenotato.put("Pasta al salmone", 1);
        Prenotazione p = new Prenotazione(data,dataPrenotazione,numeriCoperti,menuTematicoPrenotato,piattoPrenotato);

        int giorniPassati = 0;
        AddettoPrenotazioni addetto = new AddettoPrenotazioni(giorniPassati);
        addetto.initPrenotazioni();
        int caricoLavoroAttuale = addetto.getCaricoLavoroAttuale(p);
        assertEquals(caricoLavoroAttuale, 19); // 13+6
    }

}
