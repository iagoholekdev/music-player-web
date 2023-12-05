package org.teste.tecnico.fileupload.dto;


import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadArrayDTO {

    private String pathToSong;
    private String pathToImage;
    private String artistName;
    private String songName;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FileUploadArrayDTO that = (FileUploadArrayDTO) obj;
        return Objects.equals(pathToSong, that.pathToSong) &&
                Objects.equals(pathToImage, that.pathToImage) &&
                Objects.equals(artistName, that.artistName) &&
                Objects.equals(songName, that.songName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathToSong, pathToImage, artistName, songName);
    }

}
