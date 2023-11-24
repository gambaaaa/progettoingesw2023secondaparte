package unibs.ing.progettosw.ristorante.domain;

public class GenereExtra implements IMerce {
    /*
     * Classe che implementa l'interfaccia IMerce e simula le caratteristiche di un genere extra
     * servito ai tavoli
     * */
    private int consumoProCapite;
    private String nome;
    private int quantita;
    private String unitaMisura;

    public GenereExtra(String nome, int quantita, String unitaMisura, int consumoProCapite) {
        this.nome = nome;
        this.quantita = quantita;
        this.unitaMisura = unitaMisura;
        this.consumoProCapite = consumoProCapite;
    }

    public int getConsumoProCapite() {
        return consumoProCapite;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int getQuantita() {
        return quantita;
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

    // Metodo usato per trovare un determinato genere extra dato il suo nome in input
    // pre : nome di una genere extra esistente
    // post : true se il nome in input coincide con il nome dell'oggetto genereExtra
    @Override
    public boolean getIngredienteFromNome(String nome) {
        return nome.equals(this.nome);
    }

    @Override
    public void aggiungiQuantita(int quantita) {

    }

    // Metodo usato per rimuovere una certa quantità di un genere extra
    // pre : quantita > 0
    @Override
    public void rimuoviQuantita(int quantita) {
        this.quantita -= quantita;
    }

}
