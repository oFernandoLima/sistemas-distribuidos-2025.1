const axios = require("axios");
const readlineSync = require("readline-sync");

class ClienteCorreios {
  constructor(baseURL = "http://127.0.0.1:8000") {
    this.api = axios.create({
      baseURL: baseURL,
      timeout: 5000,
      headers: {
        "Content-Type": "application/json",
      },
    });
  }

  async obterNomeLoja() {
    try {
      const response = await this.api.get("/loja/nome");
      console.log(`Nome da loja: ${response.data.nome}`);
      return response.data;
    } catch (error) {
      console.error("Erro ao obter nome da loja:", error.message);
    }
  }

  async registrarCarta(codigo, destinatario, endereco, selada) {
    try {
      const carta = {
        codigo,
        destinatario,
        endereco,
        selada: selada === "sim",
      };

      const response = await this.api.post("/correspondencias/carta", carta);
      console.log("Carta registrada:", response.data);
      return response.data;
    } catch (error) {
      console.error(
        "Erro ao registrar carta:",
        error.response?.data?.detail || error.message
      );
    }
  }

  async registrarEncomenda(codigo, destinatario, endereco, peso) {
    try {
      const encomenda = {
        codigo,
        destinatario,
        endereco,
        peso: parseFloat(peso),
      };

      const response = await this.api.post(
        "/correspondencias/encomenda",
        encomenda
      );
      console.log("Encomenda registrada:", response.data);
      return response.data;
    } catch (error) {
      console.error(
        "Erro ao registrar encomenda:",
        error.response?.data?.detail || error.message
      );
    }
  }

  async registrarTelegrama(codigo, destinatario, endereco, numeroPalavras) {
    try {
      const telegrama = {
        codigo,
        destinatario,
        endereco,
        numero_palavras: parseInt(numeroPalavras),
      };

      const response = await this.api.post(
        "/correspondencias/telegrama",
        telegrama
      );
      console.log("Telegrama registrado:", response.data);
      return response.data;
    } catch (error) {
      console.error(
        "Erro ao registrar telegrama:",
        error.response?.data?.detail || error.message
      );
    }
  }

  async listarCorrespondencias() {
    try {
      const response = await this.api.get("/correspondencias");
      console.log(`\nTotal de correspondências: ${response.data.total}`);
      if (response.data.correspondencias.length > 0) {
        console.log("\nCorrespondências cadastradas:");
        response.data.correspondencias.forEach((corresp, index) => {
          console.log(`\n${index + 1}. Código: ${corresp.codigo}`);
          console.log(`   Tipo: ${corresp.tipo}`);
          console.log(`   Destinatário: ${corresp.destinatario}`);
          console.log(`   Endereço: ${corresp.endereco}`);
          console.log(`   Preço: R$ ${corresp.preco.toFixed(2)}`);
        });
      } else {
        console.log("Nenhuma correspondência cadastrada.");
      }
      return response.data;
    } catch (error) {
      console.error("Erro ao listar correspondências:", error.message);
    }
  }

  async consultarPreco(codigo) {
    try {
      const response = await this.api.get(`/correspondencias/${codigo}/preco`);
      console.log(
        `Preço da correspondência ${codigo}: R$ ${response.data.preco.toFixed(
          2
        )}`
      );
      return response.data;
    } catch (error) {
      console.error(
        "Erro ao consultar preço:",
        error.response?.data?.detail || error.message
      );
    }
  }

  async entregarCorrespondencia(codigo) {
    try {
      const response = await this.api.delete(`/correspondencias/${codigo}`);
      console.log("Correspondência entregue:", response.data);
      return response.data;
    } catch (error) {
      console.error(
        "Erro ao entregar correspondência:",
        error.response?.data?.detail || error.message
      );
    }
  }

  async menuPrincipal() {
    console.log("\n=== CLIENTE JAVASCRIPT - SISTEMA DE CORREIOS ===");

    while (true) {
      console.log("\n1. Obter nome da loja");
      console.log("2. Registrar carta");
      console.log("3. Registrar encomenda");
      console.log("4. Registrar telegrama");
      console.log("5. Listar correspondências");
      console.log("6. Consultar preço");
      console.log("7. Entregar correspondência");
      console.log("8. Sair");

      const opcao = readlineSync.question("\nEscolha uma opcao: ");

      switch (opcao) {
        case "1":
          await this.obterNomeLoja();
          break;

        case "2":
          console.log("\n--- Registrar Carta ---");
          const codigoCarta = readlineSync.question("Codigo: ");
          const destinatarioCarta = readlineSync.question("Destinatario: ");
          const enderecoCarta = readlineSync.question("Endereco: ");
          const selada = readlineSync.question("Selada (sim/nao): ");
          await this.registrarCarta(
            codigoCarta,
            destinatarioCarta,
            enderecoCarta,
            selada
          );
          break;

        case "3":
          console.log("\n--- Registrar Encomenda ---");
          const codigoEncomenda = readlineSync.question("Codigo: ");
          const destinatarioEncomenda = readlineSync.question("Destinatario: ");
          const enderecoEncomenda = readlineSync.question("Endereco: ");
          const peso = readlineSync.question("Peso (kg): ");
          await this.registrarEncomenda(
            codigoEncomenda,
            destinatarioEncomenda,
            enderecoEncomenda,
            peso
          );
          break;

        case "4":
          console.log("\n--- Registrar Telegrama ---");
          const codigoTelegrama = readlineSync.question("Codigo: ");
          const destinatarioTelegrama = readlineSync.question("Destinatario: ");
          const enderecoTelegrama = readlineSync.question("Endereco: ");
          const numeroPalavras = readlineSync.question("Numero de palavras: ");
          await this.registrarTelegrama(
            codigoTelegrama,
            destinatarioTelegrama,
            enderecoTelegrama,
            numeroPalavras
          );
          break;

        case "5":
          await this.listarCorrespondencias();
          break;

        case "6":
          const codigoConsulta = readlineSync.question(
            "Codigo da correspondencia: "
          );
          await this.consultarPreco(codigoConsulta);
          break;

        case "7":
          const codigoEntrega = readlineSync.question(
            "Codigo da correspondencia: "
          );
          await this.entregarCorrespondencia(codigoEntrega);
          break;

        case "8":
          console.log("Saindo...");
          return;

        default:
          console.log("Opcao invalida!");
      }
    }
  }

  async testarConexao() {
    try {
      await this.api.get("/");
      console.log("Conexão com a API estabelecida com sucesso!");
      return true;
    } catch (error) {
      console.error("Erro ao conectar com a API:", error.message);
      console.error(
        "Certifique-se de que a API está rodando em http://127.0.0.1:8000"
      );
      return false;
    }
  }
}

async function main() {
  const cliente = new ClienteCorreios();

  if (await cliente.testarConexao()) {
    await cliente.menuPrincipal();
  }
}

if (require.main === module) {
  main();
}
