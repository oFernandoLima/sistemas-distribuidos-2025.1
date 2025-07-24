package com.ufc.correios;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufc.correios.model.Carta;
import com.ufc.correios.model.Encomenda;
import com.ufc.correios.model.Telegrama;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Scanner;

public class ClienteCorreios {
    private static final String BASE_URL = "http://localhost:8000";
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Scanner scanner;

    public ClienteCorreios() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
        this.scanner = new Scanner(System.in);
    }

    private String executarRequisicao(HttpRequestBase request) throws IOException {
        HttpResponse response = httpClient.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity());

        if (response.getStatusLine().getStatusCode() >= 400) {
            JsonNode errorNode = objectMapper.readTree(responseBody);
            throw new RuntimeException("Erro na API: " + errorNode.get("detail").asText());
        }

        return responseBody;
    }

    public void obterNomeLoja() {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/loja/nome");
            String response = executarRequisicao(request);

            JsonNode jsonNode = objectMapper.readTree(response);
            System.out.println("Nome da loja: " + jsonNode.get("nome").asText());
        } catch (Exception e) {
            System.err.println("Erro ao obter nome da loja: " + e.getMessage());
        }
    }

    public void registrarCarta(String codigo, String destinatario, String endereco, boolean selada) {
        try {
            Carta carta = new Carta(codigo, destinatario, endereco, selada);

            HttpPost request = new HttpPost(BASE_URL + "/correspondencias/carta");
            request.setHeader("Content-Type", "application/json");

            String json = objectMapper.writeValueAsString(carta);
            request.setEntity(new StringEntity(json));

            String response = executarRequisicao(request);
            JsonNode jsonNode = objectMapper.readTree(response);

            System.out.println("Carta registrada: " + jsonNode.get("message").asText());
            System.out.println("Detalhes: " + jsonNode.get("correspondencia").toString());
        } catch (Exception e) {
            System.err.println("Erro ao registrar carta: " + e.getMessage());
        }
    }

    public void registrarEncomenda(String codigo, String destinatario, String endereco, double peso) {
        try {
            Encomenda encomenda = new Encomenda(codigo, destinatario, endereco, peso);

            HttpPost request = new HttpPost(BASE_URL + "/correspondencias/encomenda");
            request.setHeader("Content-Type", "application/json");

            String json = objectMapper.writeValueAsString(encomenda);
            request.setEntity(new StringEntity(json));

            String response = executarRequisicao(request);
            JsonNode jsonNode = objectMapper.readTree(response);

            System.out.println("Encomenda registrada: " + jsonNode.get("message").asText());
            System.out.println("Detalhes: " + jsonNode.get("correspondencia").toString());
        } catch (Exception e) {
            System.err.println("Erro ao registrar encomenda: " + e.getMessage());
        }
    }

    public void registrarTelegrama(String codigo, String destinatario, String endereco, int numeroPalavras) {
        try {
            Telegrama telegrama = new Telegrama(codigo, destinatario, endereco, numeroPalavras);

            HttpPost request = new HttpPost(BASE_URL + "/correspondencias/telegrama");
            request.setHeader("Content-Type", "application/json");

            String json = objectMapper.writeValueAsString(telegrama);
            request.setEntity(new StringEntity(json));

            String response = executarRequisicao(request);
            JsonNode jsonNode = objectMapper.readTree(response);

            System.out.println("Telegrama registrado: " + jsonNode.get("message").asText());
            System.out.println("Detalhes: " + jsonNode.get("correspondencia").toString());
        } catch (Exception e) {
            System.err.println("Erro ao registrar telegrama: " + e.getMessage());
        }
    }

    public void listarCorrespondencias() {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/correspondencias");
            String response = executarRequisicao(request);

            JsonNode jsonNode = objectMapper.readTree(response);
            int total = jsonNode.get("total").asInt();

            System.out.println("\nTotal de correspondências: " + total);

            if (total > 0) {
                System.out.println("\nCorrespondências cadastradas:");
                JsonNode correspondencias = jsonNode.get("correspondencias");

                for (int i = 0; i < correspondencias.size(); i++) {
                    JsonNode corresp = correspondencias.get(i);
                    System.out.println("\n" + (i + 1) + ". Código: " + corresp.get("codigo").asText());
                    System.out.println("   Tipo: " + corresp.get("tipo").asText());
                    System.out.println("   Destinatário: " + corresp.get("destinatario").asText());
                    System.out.println("   Endereço: " + corresp.get("endereco").asText());
                    System.out.println("   Preço: R$ " + String.format("%.2f", corresp.get("preco").asDouble()));
                }
            } else {
                System.out.println("Nenhuma correspondência cadastrada.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar correspondências: " + e.getMessage());
        }
    }

    public void consultarPreco(String codigo) {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/correspondencias/" + codigo + "/preco");
            String response = executarRequisicao(request);

            JsonNode jsonNode = objectMapper.readTree(response);
            double preco = jsonNode.get("preco").asDouble();

            System.out.println("Preço da correspondência " + codigo + ": R$ " + String.format("%.2f", preco));
        } catch (Exception e) {
            System.err.println("Erro ao consultar preço: " + e.getMessage());
        }
    }

    public void entregarCorrespondencia(String codigo) {
        try {
            HttpDelete request = new HttpDelete(BASE_URL + "/correspondencias/" + codigo);
            String response = executarRequisicao(request);

            JsonNode jsonNode = objectMapper.readTree(response);
            System.out.println("Correspondência entregue: " + jsonNode.get("message").asText());
            System.out.println("Detalhes: " + jsonNode.get("correspondencia").toString());
        } catch (Exception e) {
            System.err.println("Erro ao entregar correspondência: " + e.getMessage());
        }
    }

    public void menuPrincipal() {
        System.out.println("\n=== CLIENTE JAVA - SISTEMA DE CORREIOS ===");

        while (true) {
            System.out.println("\n1. Obter nome da loja");
            System.out.println("2. Registrar carta");
            System.out.println("3. Registrar encomenda");
            System.out.println("4. Registrar telegrama");
            System.out.println("5. Listar correspondências");
            System.out.println("6. Consultar preço");
            System.out.println("7. Entregar correspondência");
            System.out.println("8. Sair");

            System.out.print("\nEscolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    obterNomeLoja();
                    break;

                case 2:
                    System.out.println("\n--- Registrar Carta ---");
                    System.out.print("Código: ");
                    String codigoCarta = scanner.nextLine();
                    System.out.print("Destinatário: ");
                    String destinatarioCarta = scanner.nextLine();
                    System.out.print("Endereço: ");
                    String enderecoCarta = scanner.nextLine();
                    System.out.print("Selada (true/false): ");
                    boolean selada = scanner.nextBoolean();
                    scanner.nextLine(); // Consumir quebra de linha
                    registrarCarta(codigoCarta, destinatarioCarta, enderecoCarta, selada);
                    break;

                case 3:
                    System.out.println("\n--- Registrar Encomenda ---");
                    System.out.print("Código: ");
                    String codigoEncomenda = scanner.nextLine();
                    System.out.print("Destinatário: ");
                    String destinatarioEncomenda = scanner.nextLine();
                    System.out.print("Endereço: ");
                    String enderecoEncomenda = scanner.nextLine();
                    System.out.print("Peso (kg): ");
                    double peso = scanner.nextDouble();
                    scanner.nextLine(); // Consumir quebra de linha
                    registrarEncomenda(codigoEncomenda, destinatarioEncomenda, enderecoEncomenda, peso);
                    break;

                case 4:
                    System.out.println("\n--- Registrar Telegrama ---");
                    System.out.print("Código: ");
                    String codigoTelegrama = scanner.nextLine();
                    System.out.print("Destinatário: ");
                    String destinatarioTelegrama = scanner.nextLine();
                    System.out.print("Endereço: ");
                    String enderecoTelegrama = scanner.nextLine();
                    System.out.print("Número de palavras: ");
                    int numeroPalavras = scanner.nextInt();
                    scanner.nextLine(); // Consumir quebra de linha
                    registrarTelegrama(codigoTelegrama, destinatarioTelegrama, enderecoTelegrama, numeroPalavras);
                    break;

                case 5:
                    listarCorrespondencias();
                    break;

                case 6:
                    System.out.print("Código da correspondência: ");
                    String codigoConsulta = scanner.nextLine();
                    consultarPreco(codigoConsulta);
                    break;

                case 7:
                    System.out.print("Código da correspondência: ");
                    String codigoEntrega = scanner.nextLine();
                    entregarCorrespondencia(codigoEntrega);
                    break;

                case 8:
                    System.out.println("Saindo...");
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public boolean testarConexao() {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/");
            executarRequisicao(request);
            System.out.println("Conexão com a API estabelecida com sucesso!");
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao conectar com a API: " + e.getMessage());
            System.err.println("Certifique-se de que a API está rodando em http://localhost:8000");
            return false;
        }
    }

    public void fecharRecursos() {
        try {
            httpClient.close();
            scanner.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ClienteCorreios cliente = new ClienteCorreios();

        if (cliente.testarConexao()) {
            cliente.menuPrincipal();
        }

        cliente.fecharRecursos();
    }
}
