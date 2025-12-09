package br.com.ifba.fitpay.api.client.viacep;

import lombok.extern.log4j.Log4j2; // Ou use System.out.println
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
public class ViaCepClient {

    public static void main(String[] args) {

        // Configuração Manual
        WebClient webClient = WebClient.builder()
                .baseUrl("https://viacep.com.br/ws/") // URL Externa
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // Requisição Síncrona
        String resposta = webClient.get()
                .uri("46990000/json/") // CEP fixo para teste
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Bloqueia e espera a resposta

        System.out.println("--- Resposta da API ---");
        log.info(resposta);
    }
}