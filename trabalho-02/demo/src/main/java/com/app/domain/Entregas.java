package com.app.domain;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Entregas extends Remote {
    void registrarCorrespondencia(Correspondencia c) throws RemoteException;

    double consultarPreco(String codigo) throws RemoteException;

    List<Correspondencia> listarCorrespondencias() throws RemoteException;

    boolean entregar(String codigo) throws RemoteException;
}
