package org.teste.tecnico.fileupload.processfiles;

import org.teste.tecnico.fileupload.FileUploadRepository;
import org.teste.tecnico.fileupload.dto.FileUploadArrayDTO;
import org.teste.tecnico.fileupload.dto.FileUploadDTO;
import org.teste.tecnico.fileupload.helper.FileUploadHelper;
import org.teste.tecnico.fileupload.translate.FileUploadObjectToDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * @author iagoholekdev
 * Faz todo o processamento pro front-end, relacionado a questão de vincular
 * as musicas com o arquivo .mp3 e as fotos.
 *
 */
@RequestScoped
public class ProcessFiles {

    @Inject
    FileUploadRepository fileUploadRepository;

    public List<FileUploadArrayDTO> getFiles() {
        String userHome = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "test";
        String imagePath = Paths.get(userHome, "javascript-music-player", "img").toString();
        String musicPath = Paths.get(userHome, "javascript-music-player", "music").toString();

        List<String> musicFiles = FileUploadHelper.getFilesInFolder(musicPath);
        List<String> imageFiles = FileUploadHelper.getFilesInFolder(imagePath);

        List<FileUploadArrayDTO> matchedFiles = new ArrayList<>();

        List<Object[]> existingEntries = fileUploadRepository.getAllEntries();
        List<FileUploadDTO> existingEntriesDTO = FileUploadObjectToDto.toDto(existingEntries);

        for (FileUploadDTO fileUploadDTO : existingEntriesDTO) {
            String musicFileName = "music" + fileUploadDTO.getArtistName().trim().replaceAll("\\s+", "") + fileUploadDTO.getSongName().trim().replaceAll("\\s+", "") + ".mp3";
            String imageFileName = "img" + fileUploadDTO.getArtistName().trim().replaceAll("\\s+", "") + fileUploadDTO.getSongName().trim().replaceAll("\\s+", "") + ".jpg";


            if (musicFiles.contains(musicFileName) && imageFiles.contains(imageFileName)) {
                FileUploadArrayDTO dto = new FileUploadArrayDTO(

                        Paths.get(musicPath, musicFileName).toString(),
                        Paths.get(imagePath, imageFileName).toString(),
                        fileUploadDTO.getArtistName(),
                        fileUploadDTO.getSongName()

                );

                matchedFiles.add(dto);
            }
        }

        return matchedFiles;
    }

    public FileUploadDTO uploadMusic(FileUploadDTO form) throws Exception {
        String fileNameSong = FileUploadHelper.generateNameFile(true, form.getArtistName(), form.getSongName());
        String fileNameImg = FileUploadHelper.generateNameFile(false, form.getArtistName(), form.getSongName());
        String userHome = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "test";

        String imagePath = Paths.get(userHome, "javascript-music-player", "img").toString();
        String musicPath = Paths.get(userHome, "javascript-music-player", "music").toString();
        if (form.getPathToSong() != null && form.getPathToImage() != null) {
            FileUploadHelper.downloadAndSaveFile(form.getPathToSong(), musicPath, fileNameSong);
            FileUploadHelper.downloadAndSaveFile(form.getPathToImage(), imagePath, fileNameImg);
            return new FileUploadDTO(
                    "./music/" + fileNameSong,
                    "./img/" + fileNameImg,
                    form.getArtistName(),
                    form.getSongName()
            );

        }
        return null;

    }

}
