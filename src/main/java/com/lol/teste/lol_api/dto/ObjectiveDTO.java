package com.lol.teste.lol_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ObjectiveDTO(boolean first, int kills) {
}
