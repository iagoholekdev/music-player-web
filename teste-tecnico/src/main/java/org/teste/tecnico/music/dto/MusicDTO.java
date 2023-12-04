package org.teste.tecnico.music.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicDTO {

    private Long id;

    private String trackName;

    private String artistName;

    private LocalDate dateListened;

}
