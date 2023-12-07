package unibs.ing.progettosw.ristorante.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class    Magazziniere implements Dipendente {
    /*
     * Classe che implementa l'interfaccia dipendente e simula le azioni svolte da un magazziniere.
     * */
    private Magazzino magazzino;
    private Map<IMerce, Integer> listaDellaSpesa;

    public Magazziniere(Gestore gestore) {
        creaRegistroMagazzino(gestore);
    }

    // Metodo che utlizza la lista della spesa per creare l'oggetto magazzino
    private void creaRegistroMagazzino(Gestore gestore) {
        this.listaDellaSpesa = creaListaDellaSpesa(gestore);
        this.magazzino = new Magazzino(listaDellaSpesa);
    }

    // metodo che crea la lista della spesa, contenente le bevande, generiextra, ingredienti e la loro relativa quantità
    // pre : gestore not null
    // post : listaDellaSpesa not null, listaDellaSpesa not empty
    public Map<IMerce, Integer> creaListaDellaSpesa(Gestore gestore) {
        this.listaDellaSpesa = new HashMap<>();

        int quantitaMerceMagazzino = 5000;

        aggiungiMerce(gestore.getBevande(), quantitaMerceMagazzino, listaDellaSpesa);
        aggiungiMerce(gestore.getGeneriExtra(), quantitaMerceMagazzino, listaDellaSpesa);
        aggiungiMerce(gestore.getIngredienti(), quantitaMerceMagazzino, listaDellaSpesa);

        return listaDellaSpesa;
    }

    // metodo che crea la lista della spesa per la cucina, cioè un sottinsieme più piccolo della lista del magazzino
    // pre : gestore not null
    // post : listaDellaSpesa not null, listaDellaSpesaCucina not empty
    public Map<IMerce, Integer> creaListaCucina(Gestore gestore) {
        Map<IMerce, Integer> listaDellaSpesaCucina = new HashMap<>();

        int quantitaMerceCucina = 2000;

        aggiungiMerce(gestore.getBevande(), quantitaMerceCucina, listaDellaSpesaCucina);
        aggiungiMerce(gestore.getGeneriExtra(), quantitaMerceCucina, listaDellaSpesaCucina);
        aggiungiMerce(gestore.getIngredienti(), quantitaMerceCucina, listaDellaSpesaCucina);

        return listaDellaSpesaCucina;
    }

    // metodo che aggiunge una certa quantità di una certa merce alla lista della spesa del magazzino
    // pre : merceList not null and merceList not empty
    //       quantità > 0
    //       listaDellaSpesa not null
    public void aggiungiMerce(List<IMerce> merceList, int quantita, Map<IMerce, Integer> listaDellaSpesa) {
        for (IMerce merce : merceList) {
            listaDellaSpesa.put(merce, quantita);
        }
    }
}
