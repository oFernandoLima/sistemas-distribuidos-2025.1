import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LojaCorreios implements Serializable {
    private String nome;
    private List<Correspondencia> correspondencias;

    public LojaCorreios(String nome) {
        this.nome = nome;
        this.correspondencias = new ArrayList<>();
    }

    public void adicionarCorrespondencia(Correspondencia c) {
        correspondencias.add(c);
    }

    public List<Correspondencia> getCorrespondencias() {
        return correspondencias;
    }

    public String getNome() {
        return nome;
    }
}
