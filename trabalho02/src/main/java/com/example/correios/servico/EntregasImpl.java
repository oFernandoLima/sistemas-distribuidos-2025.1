package com.example.correios.servico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.example.correios.modelo.Correspondencia;

public class EntregasImpl extends UnicastRemoteObject implements Entregas {
    private LojaCorreios loja;

    public EntregasImpl(String nomeLoja) throws RemoteException {
        this.loja = new LojaCorreios(nomeLoja);
    }

    public void registrarCorrespondencia(Correspondencia c) throws RemoteException {
        loja.adicionarCorrespondencia(c);
    }

    public double consultarPreco(String codigo) throws RemoteException {
        for (Correspondencia c : loja.getCorrespondencias()) {
            if (c.getCodigo().equals(codigo))
                return c.calcularPreco();
        }
        return 0;
    }

    public List<Correspondencia> listarCorrespondencias() throws RemoteException {
        return loja.getCorrespondencias();
    }

    public boolean entregar(String codigo) throws RemoteException {
        return loja.getCorrespondencias().removeIf(c -> c.getCodigo().equals(codigo));
    }
}
