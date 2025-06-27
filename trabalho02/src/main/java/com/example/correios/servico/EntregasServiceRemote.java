package com.example.correios.servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import com.example.correios.modelo.Correspondencia;

public interface EntregasServiceRemote extends Remote {

    void registrarCorrespondencia(Correspondencia correspondencia) throws RemoteException;

    List<Correspondencia> listarCorrespondencias() throws RemoteException;

    double consultarPreco(String codigo) throws RemoteException;

    boolean entregar(String codigo) throws RemoteException;

    String getNomeLoja() throws RemoteException;
}
