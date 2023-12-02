package com.lol.teste.lol_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InfoDTO(
        long gameCreation, long gameDuration, long gameEndTimestamp, long gameId, String gameMode,
        String gameName, long gameStartTimestamp, String gameType, String gameVersion, int mapId,
        List<ParticipantDTO> participants, String platformId, int queueId, List<TeamDTO> teams, String tournamentCode) {
}
