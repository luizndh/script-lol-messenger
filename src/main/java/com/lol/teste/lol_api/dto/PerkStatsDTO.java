package com.lol.teste.lol_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PerkStatsDTO(int defense, int flex, int offense) {
}
