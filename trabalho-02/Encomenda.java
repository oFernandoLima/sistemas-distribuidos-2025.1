public class Encomenda extends Correspondencia {
    private double peso;

    public Encomenda(String codigo, String destinatario, String endereco, double peso) {
        super(codigo, destinatario, endereco);
        this.peso = peso;
    }

    @Override
    public double calcularPreco() {
        return peso * 5.0;
    }
}