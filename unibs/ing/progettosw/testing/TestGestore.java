package unibs.ing.progettosw.testing;

import org.junit.Test;
import unibs.ing.progettosw.ristorante.domain.Gestore;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TestGestore {
    @Test
    public void testMenuTematico() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        assertNotNull(gestore.getMenuT());
    }

    @Test
    public void testMenuAllaCarta() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        assertNotNull(gestore.getMenuAllaCarta());
    }

    @Test
    public void testPiatti() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        assertNotNull(gestore.getPiatti());
    }

    @Test
    public void testIngredienti() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        assertNotNull(gestore.getIngredienti());
    }

    @Test
    public void testGeneriExtra() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        assertNotNull(gestore.getGeneriExtra());
    }

    @Test
    public void testBevande() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        assertNotNull(gestore.getBevande());
    }

    @Test
    public void testRicette() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        assertNotNull(gestore.getRicette());
    }

    @Test
    public void testNumeroMenuTematiciValidi() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        int numeroMenuTValidi = gestore.getMenuTematiciValidi(0).size();
        // caso statico, i menu tematici validi di base sono 3, tuttavia per variare un piatto non è attualmente disponibile,
        // perciò i menu tematici validi sono solo 2.
        assertEquals(numeroMenuTValidi, 2);
    }

    @Test
    public void testNumeroPiattiValidi() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        int numeroMenuTValidi = gestore.getListaPiattiValidi(0).size();
        // caso statico, i piatti validi di base sono 6, tuttavia per variare un piatto non è attualmente disponibile,
        // perciò i piatti validi sono solo 5.
        assertEquals(numeroMenuTValidi, 5);
    }

    @Test
    public void testRistorante() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        assertNotNull(gestore.getRistorante());
    }
}
