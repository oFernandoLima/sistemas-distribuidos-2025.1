public class Telegrama extends Correspondencia {
    private int numeroPalavras;

    public Telegrama(String codigo, String destinatario, String endereco, int numeroPalavras) {
        super(codigo, destinatario, endereco);
        this.numeroPalavras = numeroPalavras;
    }

    @Override
    public double calcularPreco() {
        return numeroPalavras * 0.50;
    }
}