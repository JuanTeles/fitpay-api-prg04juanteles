package br.com.ifba.fitpay.api.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/alunos") // URL da sua API
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // Requisição GET Síncrona
        String response = webClient.get()
                .uri("/findall?page=0&size=10")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Exibe a resposta no Log
        log.info("--- RESPOSTA DA API ---");
        log.info(response);
    }
}