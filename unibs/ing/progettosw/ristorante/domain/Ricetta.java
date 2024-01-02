package unibs.ing.progettosw.ristorante.domain;

import java.util.Map;

/*
 * 	   Classe che intende rappresentare una Ricetta costituita da ingredienti.
 *     Nota: Vengono considerate solo ricette che prevedono tempi di preparazione compatibili con l’intervallo che intercorre fra
 *     l’inizio giornaliero dei lavori in cucina e quello dell’unico pasto servito.
 */

public class Ricetta {

    //Attributi privati
    private String nome;
    private Map<String, Integer> ingredienti;
    private int porzioni;
    /*
     * Nota : il carico di lavoro per porzione è una frazione (minore dell’unità) del carico di lavoro per persona(intero).
     */
    private double caricoLavoroPorzione;

    /*
     * costruttore
     * creazione di una Ricetta in base a quattro parametri da specificare: nome,ingredienti , porzioni ,
     * caricoLavoroPorzione.(il nome della Ricetta e altri tre parametri intesi a specificare gli ingredienti
     * costituenti della ricetta, il numero di porzioni che ne derivano ed il carico di lavoro per realizzare una porzione.
     *
     * pre : ingredienti != NULL
     * post : creata correttamente una Ricetta
     */
    public Ricetta(String nome, Map<String, Integer> ingredienti, int porzioni, double caricoLavoroPorzione) {
        // caricoLavoroPorzione = caricoLavoroPersona / x;
        this.nome = nome;
        this.ingredienti = ingredienti;
        this.porzioni = porzioni;
        this.caricoLavoroPorzione = caricoLavoroPorzione;
    }

    public String getNome() {
        return nome;
    }

    public Map<String, Integer> getIngredienti() {//Map<Ingrediente,Double>
        return ingredienti;
    }

    public int getPorzioni() {
        return porzioni;
    }

    public double getCaricoLavoroPorzione() {
        return caricoLavoroPorzione;
    }

    // metodo che restituisce :
    //                          true se il nome specificato corrisponde al nome della Ricetta
    //                          false se il nome specificato non corrisponde al nome della Ricetta
    public boolean getRicettaFromNome(String nome) {
        return nome.equals(this.nome);
    }

    public int quantitaIngredientiRicetta() {
        return this.ingredienti.size();
    }


}
