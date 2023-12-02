package com.lol.teste.lol_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PerkStyleSelectionDTO(int perk, int var1, int var2, int var3) {
}
