package backend;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.*;

/**
 * Servidor HTTP simples em Java que realiza cálculos de rendimento.
 * Endpoint: http://localhost:8080/calcular
 * Método: POST
 * Corpo esperado (JSON):
 * {
 *   "investido": 1000,
 *   "compra": 10,
 *   "atual": 12
 * }
 *
 * Retorno (JSON):
 * {
 *   "quantidade": 100.0,
 *   "valorAtual": 1200.0,
 *   "lucro": 200.0
 * }
 */
public class CalculadoraServer {

    // Pasta onde os logs serão salvos
    private static final String LOG_DIR = "logs";

    public static void main(String[] args) throws IOException {
        // Criação e configuração do servidor
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/calcular", CalculadoraServer::handleCalculo);
        server.setExecutor(null);

        log("🚀 Servidor iniciado em http://localhost:8080/calcular");
        server.start();
    }

    private static void handleCalculo(HttpExchange exchange) throws IOException {
        log("➡️ Nova requisição recebida: " + exchange.getRequestMethod() + " " + exchange.getRequestURI());

        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Access-Control-Allow-Methods", "POST, OPTIONS");

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            log("✅ Requisição OPTIONS tratada (CORS).");
            return;
        }

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            log("⚠️ Método não permitido: " + exchange.getRequestMethod());
            return;
        }

        try {
            InputStream input = exchange.getRequestBody();
            String jsonInput = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            log("📩 Corpo recebido: " + jsonInput);

            JSONObject inputJson = new JSONObject(jsonInput);

            double investido = inputJson.getDouble("investido");
            double compra = inputJson.getDouble("compra");
            double atual = inputJson.getDouble("atual");

            log(String.format("📊 Dados → Investido: %.2f | Compra: %.2f | Atual: %.2f", investido, compra, atual));

            double quantidade = investido / compra;
            double valorAtual = quantidade * atual;
            double lucro = valorAtual - investido;

            JSONObject resposta = new JSONObject();
            resposta.put("quantidade", quantidade);
            resposta.put("valorAtual", valorAtual);
            resposta.put("lucro", lucro);

            byte[] respostaBytes = resposta.toString().getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, respostaBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(respostaBytes);
            }

            log("✅ Resposta enviada: " + resposta);

        } catch (Exception e) {
            log("❌ ERRO no processamento: " + e.getMessage());

            JSONObject erroJson = new JSONObject();
            erroJson.put("erro", e.getMessage());

            byte[] respostaErro = erroJson.toString().getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(500, respostaErro.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(respostaErro);
            }
        }
    }

    /**
     * Loga no console e em arquivo diário dentro da pasta logs/
     */
    private static void log(String mensagem) {
        try {
            // Cria a pasta "logs" se não existir
            Path logDir = Paths.get(LOG_DIR);
            if (!Files.exists(logDir)) {
                Files.createDirectories(logDir);
            }

            // Nome do arquivo com a data do dia
            String dataHoje = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Path logFile = logDir.resolve(dataHoje + ".log");

            // Formata data e hora
            String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String linha = "[" + dataHora + "] " + mensagem + System.lineSeparator();

            // Escreve no arquivo (append)
            Files.write(logFile, linha.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.err.println("⚠️ Falha ao gravar log: " + e.getMessage());
        }

        // Também exibe no terminal
        System.out.println(mensagem);
    }
}
