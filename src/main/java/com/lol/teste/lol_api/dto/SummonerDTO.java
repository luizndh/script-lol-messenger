package com.lol.teste.lol_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SummonerDTO(
        String accountId, int profileIconId, long revisionDate, String name, String id, String puuid, long summonerLevel) {}

