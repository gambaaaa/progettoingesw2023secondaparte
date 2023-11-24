package unibs.ing.progettosw.ristorante.domain;

import java.util.List;

public class MenuAllaCarta extends Menu {
    /*
     * Classe che estende il comportamento della classe Menu
     * */
    public MenuAllaCarta(String nome, List<Piatto> piatti, double caricoLavoro) {
        super(nome, piatti, caricoLavoro);
    }
}
