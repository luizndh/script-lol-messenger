package com.lol.teste.Service;

import com.lol.teste.DTO.SummonerDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Component
public class LolApiService {

    private static final String API_KEY = "RGAPI-83358d9c-c9a8-4bee-a7aa-c66b21be1b6f";
    private static final String SUMMONER_API_URL = "https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    private static final String[] nomes = new String[]{"Birelio","Gnolenda","Besessa2570","bolinha nobi","TAXULE", "maingara", "theoboi"};

    @Scheduled(cron = "0 */1 * * * ?")
    public void checaHistoricos() {
        System.out.println("CHECANDO HISTORICOS...");
        try {
            for(String nome: nomes) {
                String uuid = recuperaPuuid(nome);
                System.out.println(uuid);
            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private String recuperaPuuid(String nome) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(SUMMONER_API_URL + "pingsp"))
                .header("X-Riot-Token", API_KEY)
                .build();

        SummonerDTO responseBody = (SummonerDTO) client.send(request, null).body();
        return responseBody.puuid();
    }
}
