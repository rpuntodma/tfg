package ramon.del.moral.buscadormtg;

import java.util.Objects;

public class Carta {
    private String name;
    private String tipos;
    private String manaCost;
    private String oracle;
    private String imagen;

    public Carta(String name, String tipos, String manaCost, String oracle, String imagen) {
        this.name = name;
        this.tipos = tipos;
        this.manaCost = manaCost;
        this.oracle = oracle;
        this.imagen = imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipos() {
        return tipos;
    }

    public void setTipos(String tipos) {
        this.tipos = tipos;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getOracle() {
        return oracle;
    }

    public void setOracle(String oracle) {
        this.oracle = oracle;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return Objects.equals(name, carta.name) && Objects.equals(tipos, carta.tipos) && Objects.equals(manaCost, carta.manaCost) && Objects.equals(oracle, carta.oracle) && Objects.equals(imagen, carta.imagen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tipos, manaCost, oracle, imagen);
    }

    @Override
    public String toString() {
        return "Carta{" +
                "name='" + name + '\'' +
                ", tipos='" + tipos + '\'' +
                ", manaCost='" + manaCost + '\'' +
                ", oracle='" + oracle + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
