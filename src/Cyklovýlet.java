import java.time.LocalDate;

public class Cyklovýlet {
    private String cíl;
    private int délka;
    private LocalDate datum;

    public Cyklovýlet(String cíl, int délka, LocalDate datum) {
        this.cíl = cíl;
        this.délka = délka;
        this.datum = datum;
    }

    public String getCíl() {
        return cíl;
    }

    public void setCíl(String cíl) {
        this.cíl = cíl;
    }

    public int getDélka() {
        return délka;
    }

    public void setDélka(int délka) {
        this.délka = délka;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String toStrin(){
        return "," + cíl + "," + délka + "," + datum + "\n";
    }
}
