package org.teste.tecnico.fileupload.dto;


import lombok.*;

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

}
