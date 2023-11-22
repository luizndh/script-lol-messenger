package com.lol.teste.DTO;

import java.util.List;

public record InfoDTO(
        long gameCreation, long gameDuration, long gameEndTimestamp, long gameId, String gameMode,
        String gameName, long gameStartTimestamp, String gameType, String gameVersion, int mapId,
        List<ParticipantDTO> participants, String platformId, int queueId, List<TeamDTO> teams, String tournamentCode) {
}
