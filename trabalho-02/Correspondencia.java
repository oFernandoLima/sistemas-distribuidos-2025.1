import java.io.Serializable;

public abstract class Correspondencia implements Serializable {
    protected String codigo;
    protected String destinatario;
    protected String endereco;

    public Correspondencia(String codigo, String destinatario, String endereco) {
        this.codigo = codigo;
        this.destinatario = destinatario;
        this.endereco = endereco;
    }

    public abstract double calcularPreco();
}