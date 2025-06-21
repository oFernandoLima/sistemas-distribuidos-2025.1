package com.example.correios.servico;

import java.util.List;
import com.example.correios.modelo.Correspondencia;

/**
 * Serviço simples para gerenciamento de correspondências via UDP
 */
public class EntregasService {
    private LojaCorreios loja;

    public EntregasService(String nomeLoja) {
        this.loja = new LojaCorreios(nomeLoja);
    }

    public void registrarCorrespondencia(Correspondencia correspondencia) {
        loja.adicionarCorrespondencia(correspondencia);
    }

    public List<Correspondencia> listarCorrespondencias() {
        return loja.getCorrespondencias();
    }

    public double consultarPreco(String codigo) {
        for (Correspondencia c : loja.getCorrespondencias()) {
            if (c.getCodigo().equals(codigo))
                return c.calcularPreco();
        }
        return 0;
    }

    public boolean entregar(String codigo) {
        return loja.getCorrespondencias().removeIf(c -> c.getCodigo().equals(codigo));
    }
}
