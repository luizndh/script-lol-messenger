package com.lol.teste.lol_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PerkStyleDTO(String description, List<PerkStyleSelectionDTO> selections, int style) {
}
