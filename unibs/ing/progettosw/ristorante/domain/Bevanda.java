package unibs.ing.progettosw.ristorante.domain;

public class Bevanda implements IMerce {
    /*
     * Classe che implementa l'interfaccia IMerce e simula le caratteristiche di una bevanda
     * servita ai tavoli
     * */
    private int consumoProCapite;
    private String nome;
    private int quantita;
    private String unitaMisura;

    public Bevanda(String nome, int quantita, String unitaMisura, int consumoProCapite) {
        this.nome = nome;
        this.quantita = quantita;
        this.unitaMisura = unitaMisura;
        this.consumoProCapite = consumoProCapite;
    }

    public int getConsumoProCapite() {
        return consumoProCapite;
    }

    @Override
    public String toString() {
        return "{" +
                " nome = '" + nome + '\'' +
                ", quantita = " + quantita +
                ", unitaMisura ='" + unitaMisura + "', " +
                ", consumoProCapite = " + consumoProCapite +
                "}\n";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int getQuantita() {
        return quantita;
    }

    // Metodo usato per trovare una determinata bevanda dato il suo nome in input
    // pre : nome di una bevanda esistente
    // post : true se il nome in input coincide con il nome dell'oggetto bevanda
    @Override
    public boolean getIngredienteFromNome(String nome) {
        return nome.equals(this.nome);
    }

    @Override
    public void aggiungiQuantita(int quantita) {

    }

    // Metodo usato per rimuovere una certa quantità di una bevanda
    // pre : quantita > 0
    @Override
    public void rimuoviQuantita(int quantita) {
        this.quantita -= quantita;
    }
}
