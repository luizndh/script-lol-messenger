package com.lol.teste.Service;

import com.lol.teste.DTO.MatchDTO;
import com.lol.teste.DTO.ParticipantDTO;
import com.lol.teste.DTO.SummonerDTO;
import com.lol.teste.util.JsonBodyHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class LolApiService {

    private static final String API_KEY = "RGAPI-fb41d1c3-b4e0-4e4b-b199-eb626e53085d";

    private static final String SUMMONER_API_URL = "https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    private static final String HISTORICO_API_URL = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/";
    private static final String MATCH_API_URL = "https://americas.api.riotgames.com/lol/match/v5/matches/";

    private static final String[] nomes = new String[]{"Birelio","Gnolenda","Besessa2570","bolinha%20nobi","TAXULE", "maingara", "theoboi"};

    @Scheduled(cron = "*/30 * * * * ?")
    public void checaHistoricos() {
        System.out.println("CHECANDO HISTORICOS...");
        try {
            for(String nome: nomes) {
                String puuid = recuperaPuuid(nome);
                System.out.println("UUID: " + puuid);

                String[] matchIds = recuperaHistorico(puuid);
                if(matchIds.length == 0) continue;
                String matchId = matchIds[0];
                System.out.println("MATCH ID: " + matchId);

                MatchDTO dadosPartida = recuperaPartida(matchId);
                var participantes = dadosPartida.infoDto().participants();
                boolean venceu = false;
                for(ParticipantDTO player : participantes) {
                    if(player.puuid().equals(puuid)) {
                        venceu = player.win();
                    }
                }

                // Integrar com app de mensagem

            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String recuperaPuuid(String nome) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                    URI.create(SUMMONER_API_URL + nome))
                .header("X-Riot-Token", API_KEY)
                .build();

        return client.send(request, new JsonBodyHandler<>(SummonerDTO.class)).body().get().puuid();
    }

    private String[] recuperaHistorico(String puuid) throws IOException, InterruptedException {
        long endTime = Instant.now().getEpochSecond();
        long startTime = endTime - 60;
        System.out.println("START TIME: " + startTime);
        System.out.println("END TIME: " + endTime);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(HISTORICO_API_URL + puuid + "/ids?startTime="+startTime+"&endTime="+endTime+"&start=0&count=1"))
                .header("X-Riot-Token", API_KEY)
                .build();

        return client.send(request, new JsonBodyHandler<>(String[].class)).body().get();
    }

    private MatchDTO recuperaPartida(String matchId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(MATCH_API_URL + matchId))
                .header("X-Riot-Token", API_KEY)
                .build();

        return client.send(request, new JsonBodyHandler<>(MatchDTO.class)).body().get();
    }
}
