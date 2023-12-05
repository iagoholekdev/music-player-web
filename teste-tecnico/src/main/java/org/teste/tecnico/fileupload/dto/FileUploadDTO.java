package org.teste.tecnico.fileupload.dto;

import lombok.*;
import org.teste.tecnico.fileupload.FileUpload;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDTO {

    private Long id;
    private UUID uuid;
    private String pathToSong;
    private String pathToImage;
    private String artistName;
    private String songName;

    public FileUploadDTO(String pathToSong,
     String pathToImage,
     String artistName,
     String songName) {
        this.pathToSong = pathToSong;
        this.pathToImage = pathToImage;
        this.artistName = artistName;
        this.songName = songName;
    }

    public FileUploadDTO(Long id, String artistName, String songName) {
        this.id = id;
        this.artistName = artistName;
        this.songName = songName;
    }

}
