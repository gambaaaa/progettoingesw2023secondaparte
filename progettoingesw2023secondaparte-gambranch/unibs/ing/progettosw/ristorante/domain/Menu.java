package unibs.ing.progettosw.ristorante.domain;

import java.util.List;

public class Menu {

    /*
     * Classe che rappresenta un Menu
     * */
    private String nome;
    private List<Piatto> piatti;
    // Il carico di lavoro è la somma dei carichi di lavoro di tutti i piatti
    private double caricoLavoro;

    public Menu(String nome, List<Piatto> piatti, double caricoLavoro) {
        this.nome = nome;
        this.piatti = piatti;
        this.caricoLavoro = caricoLavoro;
    }

    public String getNome() {
        return nome;
    }

    public List<Piatto> getPiatti() {
        return piatti;
    }

    public double getCaricoLavoro() {
        return caricoLavoro;
    }

    // Metodo usato per trovare un menu dato il suo nome in input
    // pre : nome del menu deve esistere
    // post : true se il nome in input coincide con il nome dell'oggetto menu
    public boolean getMenuFromNome(String nome) {
        return nome.equals(this.nome);
    }
}
