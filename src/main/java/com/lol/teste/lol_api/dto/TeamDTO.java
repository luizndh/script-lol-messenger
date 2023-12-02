package com.lol.teste.lol_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TeamDTO(List<BanDTO> bans, ObjectivesDTO objectives, int teamId, boolean win) {
}
