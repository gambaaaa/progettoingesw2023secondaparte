package unibs.ing.progettosw.ristorante.domain;

import java.util.Map;
import java.util.Set;

public class Magazzino {
    // classe che rappresenta un Magazzino con il proprio registro (lista della spesa)
    private Map<IMerce, Integer> registroMagazzino;

    public Magazzino(Map<IMerce, Integer> registroMagazzino) {
        this.registroMagazzino = registroMagazzino;
    }

    // Metodo che rimuove una certa quantità di merce dal magazzino, usato dalla classe cucina quando manca un ingrediente
    // durante la preparazione di una ricetta
    public void decrementaQuantitaMerce(IMerce merce, int decrementoQuantita) {
        Set<IMerce> listaMerce = registroMagazzino.keySet();

        if (listaMerce.contains(merce)) {
            if (decrementoQuantita <= merce.getQuantita()) {
                merce.rimuoviQuantita(decrementoQuantita);
            }
        }
    }
}
