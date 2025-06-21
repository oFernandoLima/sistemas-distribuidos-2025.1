package com.example.middleware.core;

import com.example.correios.modelo.Correspondencia;
import com.example.correios.servico.Entregas;
import com.example.correios.servico.EntregasImpl;
import com.example.middleware.transport.*;

public class ServidorMiddleware {
    public static void main(String[] args) throws Exception {
        System.out.println("Middleware aguardando requisições...");
        RequestHandler handler = new RequestHandler(5052);

        // Suponha que temos o objeto remoto disponível:
        Entregas servico = new EntregasImpl("Correios Middleware");
        while (true) {
            byte[] requestBytes = handler.getRequest();
            Request req = Marshaller.unmarshall(requestBytes, Request.class);

            // Decode método
            byte[] resultBytes;
            switch (req.methodId) {
                case 0: // registrar
                    Correspondencia c = Marshaller.unmarshall(req.arguments, Correspondencia.class);
                    servico.registrarCorrespondencia(c);
                    resultBytes = Marshaller.marshall("OK".getBytes());
                    break;
                case 1: // listar
                    resultBytes = Marshaller.marshall(servico.listarCorrespondencias());
                    break;
                case 2: // consultarPreco
                    String codigo = Marshaller.unmarshall(req.arguments, String.class);
                    double preco = servico.consultarPreco(codigo);
                    resultBytes = Marshaller.marshall(preco);
                    break;
                case 3: // entregar
                    String cod = Marshaller.unmarshall(req.arguments, String.class);
                    boolean ok = servico.entregar(cod);
                    resultBytes = Marshaller.marshall(ok);
                    break;
                default:
                    resultBytes = Marshaller.marshall("Método desconhecido".getBytes());
            }
            handler.sendReply(Marshaller.marshall(new Reply(req.requestId, resultBytes)));
        }
    }
}
