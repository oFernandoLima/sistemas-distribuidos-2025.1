package com.example.correios.servico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import com.example.correios.modelo.Correspondencia;

public class EntregasService extends UnicastRemoteObject implements EntregasServiceRemote {
    private LojaCorreios loja;

    public EntregasService(String nomeLoja) throws RemoteException {
        super();
        this.loja = new LojaCorreios(nomeLoja);
    }

    public void registrarCorrespondencia(Correspondencia correspondencia) throws RemoteException {
        loja.adicionarCorrespondencia(correspondencia);
    }

    public List<Correspondencia> listarCorrespondencias() throws RemoteException {
        return loja.getCorrespondencias();
    }

    public double consultarPreco(String codigo) throws RemoteException {
        for (Correspondencia c : loja.getCorrespondencias()) {
            if (c.getCodigo().equals(codigo))
                return c.calcularPreco();
        }
        return 0;
    }

    public boolean entregar(String codigo) throws RemoteException {
        return loja.getCorrespondencias().removeIf(c -> c.getCodigo().equals(codigo));
    }

    public String getNomeLoja() throws RemoteException {
        return loja.getNome();
    }
}
