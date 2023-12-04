package org.teste.tecnico.fileupload;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
@Builder
@Table(name = "fileupload")
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "seq_file_upload", sequenceName = "seq_music", allocationSize = 1)
public class FileUpload {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_file_upload", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "uuid", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "path_file")
    private String pathToImage;

    @Column(name = "artist_name")
    private String artistName;

    @Column(name = "song_name")
    private String songName;

    @Column(name = "path_song")
    private String pathToSong;

}
