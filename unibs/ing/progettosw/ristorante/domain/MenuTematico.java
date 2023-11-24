package unibs.ing.progettosw.ristorante.domain;

import java.util.List;

public class MenuTematico extends Menu {
    /*
     * Classe che estende il comportamento della classe Menu
     * */
    private String nome;
    private List<Piatto> piatti;
    private double caricoLavoro;
    public MenuTematico(String nome, List<Piatto> piatti, double caricoLavoro) {
        super(nome, piatti, caricoLavoro);
    }

    // Metodo che restituisce il carico di lavoro del menu tematico, cioè la somma dei singoli carico di lavoro dei piatti
    // in questo caso il valore lo leggiamo da un file .json
    public double getCaricoLavoroMenuTematico() {
        double sumCaricoLavoro = 0;
        for (Piatto aPiatto : piatti) {
            sumCaricoLavoro += aPiatto.getCaricoLavoro();
        }
        return sumCaricoLavoro;
    }
}
