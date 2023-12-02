package com.lol.teste.discord_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lol.teste.discord_api.dto.DiscordJsonDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static java.net.http.HttpResponse.BodyHandlers;

@Component
public class DiscordApiService {

    private static final String API_TOKEN = "sua api token do discord aqui";
    private static final String API_URI = "https://discord.com/api/v9/channels/423275916987465730/messages";

    public void sendMessage(String mensagem) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(new DiscordJsonDTO(mensagem));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(API_URI))

                .header("authorization", API_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.send(request, BodyHandlers.ofString());
    }
}
