package com.lol.teste.DTO;

import java.util.List;

public record PerkStyleDTO(String description, List<PerkStyleSelectionDTO> selections, int style) {
}
