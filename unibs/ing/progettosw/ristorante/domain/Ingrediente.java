package unibs.ing.progettosw.ristorante.domain;

import java.util.Date;

public class Ingrediente implements IMerce {
    /*
     * Classe che eredita l'interfaccia IMerce, simula le caratteristiche di un ingrediente usato per cucinare una particolare ricetta
     * */
    private Date dataScadenza;
    private String nome;
    private int quantita;//in magazzino / eventualmente acquistata
    private String unitaMisura;//SI PUO' ANCHE TOGLIERE SE VOLETE

    public Ingrediente(String nome, int quantita, String unitaMisura, Date dataScadenza) {
        this.nome = nome;
        this.quantita = quantita;
        this.unitaMisura = unitaMisura;
        this.dataScadenza = dataScadenza;
    }

    // Metodo usato per trovare un determinato ingrediente dato il suo nome in input
    // pre : nome di un ingrediente esistente
    // post : true se il nome in input coincide con il nome dell'oggetto ingrediente
    @Override
    public boolean getIngredienteFromNome(String nome) {
        return nome.equals(this.nome);
    }

    @Override
    public void aggiungiQuantita(int quantita) {

    }

    // Metodo usato per rimuovere una certa quantità di un ingrediente durante la cottura di un piatto
    // pre : quantita > 0
    @Override
    public void rimuoviQuantita(int quantita) {
        this.quantita -= quantita;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int getQuantita() {
        return quantita;
    }

    @Override
    public int getConsumoProCapite() {
        return 0;
    }
}
