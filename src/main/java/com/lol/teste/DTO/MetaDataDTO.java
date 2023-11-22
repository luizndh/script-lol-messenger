package com.lol.teste.DTO;

import java.util.List;

public record MetaDataDTO(String dataVersion, String matchId, List<String> participants) {
}
