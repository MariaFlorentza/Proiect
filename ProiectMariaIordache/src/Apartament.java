public class Apartament {
    private int nr_apartament;
    private int suprafata;
    private int nr_persoane;

    public Apartament(int nr_apartament, int suprafata, int nr_persoane) {
        this.nr_apartament = nr_apartament;
        this.suprafata = suprafata;
        this.nr_persoane = nr_persoane;
    }

    public int getNr_apartament() {
        return nr_apartament;
    }

    public void setNr_apartament(int nr_apartament) {
        this.nr_apartament = nr_apartament;
    }

    public int getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(int suprafata) {
        this.suprafata = suprafata;
    }

    public int getNr_persoane() {
        return nr_persoane;
    }

    public void setNr_persoane(int nr_persoane) {
        this.nr_persoane = nr_persoane;
    }

    @Override
    public String toString() {
        return "Apartament{" +
                "nr_apartament=" + nr_apartament +
                ", suprafata=" + suprafata +
                ", nr_persoane=" + nr_persoane +
                '}';
    }
}
