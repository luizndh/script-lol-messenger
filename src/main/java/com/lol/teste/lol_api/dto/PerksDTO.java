package com.lol.teste.lol_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PerksDTO(PerkStatsDTO statPerks, List<PerkStyleDTO> styles) {
}
