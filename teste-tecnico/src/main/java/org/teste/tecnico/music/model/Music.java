package org.teste.tecnico.music.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@Table(name = "music")
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "seq_music", sequenceName = "seq_music", allocationSize = 1)
public class Music {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_music", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "trackname")
    private String trackName;

    @Column(name = "artistname")
    private String artistName;

    @Column(name = "datelistened")
    private LocalDate dateListened;


}
