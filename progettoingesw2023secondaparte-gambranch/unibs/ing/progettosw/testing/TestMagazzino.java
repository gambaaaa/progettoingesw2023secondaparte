package unibs.ing.progettosw.testing;

import org.junit.Test;
import unibs.ing.progettosw.ristorante.domain.Gestore;
import unibs.ing.progettosw.ristorante.domain.Magazziniere;
import unibs.ing.progettosw.ristorante.domain.Magazzino;

import static junit.framework.TestCase.assertNotNull;

public class TestMagazzino {

    @Test
    public void testCreazioneMagazzino() {
        Gestore gestore = new Gestore();
        gestore.initAll();
        Magazziniere magazziniere = new Magazziniere(gestore);
        Magazzino magazzino = new Magazzino(magazziniere.getListaDellaSpesa());
        assertNotNull(magazzino.getRegistroMagazzino());
    }
}
