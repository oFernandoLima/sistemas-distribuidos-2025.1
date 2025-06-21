package com.example.correios.servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.example.correios.modelo.Correspondencia;

public interface Entregas extends Remote {
    void registrarCorrespondencia(Correspondencia c) throws RemoteException;

    double consultarPreco(String codigo) throws RemoteException;

    List<Correspondencia> listarCorrespondencias() throws RemoteException;

    boolean entregar(String codigo) throws RemoteException;
}
