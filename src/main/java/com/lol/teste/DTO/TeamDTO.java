package com.lol.teste.DTO;

import java.util.List;

public record TeamDTO(List<BanDTO> bans, ObjectivesDTO objectives, int teamId, boolean win) {
}
