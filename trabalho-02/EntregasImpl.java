import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
            if (c.codigo.equals(codigo))
                return c.calcularPreco();
        }
        return 0;
    }

    public List<Correspondencia> listarCorrespondencias() throws RemoteException {
        return loja.getCorrespondencias();
    }

    public boolean entregar(String codigo) throws RemoteException {
        return loja.getCorrespondencias().removeIf(c -> c.codigo.equals(codigo));
    }
}
