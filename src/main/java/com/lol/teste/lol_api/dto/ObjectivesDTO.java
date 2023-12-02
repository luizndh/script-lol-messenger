package com.lol.teste.lol_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ObjectivesDTO(ObjectiveDTO baron, ObjectiveDTO champion, ObjectiveDTO dragon, ObjectiveDTO inhibitor,
                            ObjectiveDTO riftHerald, ObjectiveDTO tower) {
}
