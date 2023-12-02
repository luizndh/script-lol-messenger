package com.lol.teste.lol_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lol.teste.lol_api.dto.SummonerDTO;
import com.lol.teste.lol_api.dto.MatchDTO;
import com.lol.teste.lol_api.dto.ParticipantDTO;
import com.lol.teste.util.JsonBodyHandler;
import com.lol.teste.discord_api.service.DiscordApiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

@Component
public class LolApiService {

    private static final String API_KEY = "sua api key da riot aqui";

    private static final String SUMMONER_API_URL = "https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    private static final String HISTORICO_API_URL = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/";
    private static final String MATCH_API_URL = "https://americas.api.riotgames.com/lol/match/v5/matches/";

    private static final String[] nomes = new String[]{"lista", "com", "nomes"};

    public DiscordApiService discordService = new DiscordApiService();

    @Scheduled(cron = "0 */1 * * * ?")
    public void checaHistoricos() {
        int count = 0;
        try {
            for(String nome: nomes) {
                count += 3;
                ObjectMapper mapper = new ObjectMapper();

                String puuid = recuperaPuuid(nome);
                String[] matchIds = mapper.readValue(recuperaHistorico(puuid), String[].class);

                if(matchIds.length == 0) continue;

                String matchId = matchIds[0];
                MatchDTO dadosPartida = mapper.readValue(recuperaPartida(matchId), MatchDTO.class);
                String mensagem = recuperaMensagemPorParticipante(nome, dadosPartida, puuid);
                long fimDeJogo =  dadosPartida.info().gameEndTimestamp() / 1000;

                if(fimDeJogo > Instant.now().getEpochSecond() - (60 + count))
                    discordService.sendMessage(mensagem);
            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String recuperaMensagemPorParticipante(String nome, MatchDTO dadosPartida, String puuid) {
        var participantes = dadosPartida.info().participants();
        boolean venceu = false;
        for(ParticipantDTO player : participantes) {
            if(player.puuid().equals(puuid)) {
                venceu = player.win();
            }
        }

        String mensagem;

        if(venceu) mensagem = nome + " venceu uma partida no lol";
        else mensagem = nome + " perdeu uma partida no lol";
        return mensagem;
    }

    private String recuperaPuuid(String nome) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                    URI.create(SUMMONER_API_URL + nome.replace(" ", "%20")))
                .header("X-Riot-Token", API_KEY)
                .build();

        return client.send(request, new JsonBodyHandler<>(SummonerDTO.class)).body().get().puuid();
    }

    private String recuperaHistorico(String puuid) throws IOException, InterruptedException {
        long endTime = Instant.now().getEpochSecond();
        long startTime = endTime - 3600;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(HISTORICO_API_URL + puuid + "/ids?startTime="+startTime+"&endTime="+endTime+"&start=0&count=1"))
                .header("X-Riot-Token", API_KEY)
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private String recuperaPartida(String matchId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(MATCH_API_URL + matchId))
                .header("X-Riot-Token", API_KEY)
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
