package org.teste.tecnico.fileupload;


import org.teste.tecnico.fileupload.dao.FileUploadDAO;
import org.teste.tecnico.fileupload.dto.FileUploadArrayDTO;
import org.teste.tecnico.fileupload.dto.FileUploadDTO;
import org.teste.tecnico.fileupload.helper.FileUploadHelper;
import org.teste.tecnico.fileupload.translate.FileUploadObjectToDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;


@RequestScoped
public class FileUploadService  {

    @Inject
    FileUploadDAO fileUploadDAO;

    @Inject
    FileUploadRepository fileUploadRepository;

    public void createFile(FileUploadDTO fileUploadDTO) {
        this.fileUploadDAO.create(fileUploadDTO);
    }

    private String generateNameFile(boolean type) {
        Random random = new Random();
        int randomNumber = random.nextInt(100000);
        if (type) {
          return "music" + randomNumber + ".mp3";
        }
        return "img" + randomNumber + ".jpg";
    }

    private void downloadAndSaveFile(String fileUrl, String savePath, String fileName) {
        try {
            URL url = new URL(fileUrl);
            InputStream in = url.openStream();
            Files.copy(in, Paths.get(savePath, fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileUploadDTO uploadMusic(FileUploadDTO form) {
        String fileNameSong = this.generateNameFile(true);
        String fileNameImg = this.generateNameFile(false);
        String userHome = System.getProperty("user.home");
        String imagePath = Paths.get(userHome, "javascript-music-player", "img").toString();
        String musicPath = Paths.get(userHome, "javascript-music-player", "music").toString();
        if (form.getPathToSong() != null && form.getPathToImage() != null) {
            downloadAndSaveFile(form.getPathToSong(), musicPath, fileNameSong);
            downloadAndSaveFile(form.getPathToImage(), imagePath, fileNameImg);
            return new FileUploadDTO(
                    "./music/" + fileNameSong,
                    "./img/" + fileNameImg,
                    form.getArtistName(),
                    form.getSongName()
            );

        }
        return null;

    }

    public List<FileUploadArrayDTO> getFiles() {
        String userHome = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "test";
        String imagePath = Paths.get(userHome, "javascript-music-player", "img").toString();
        String musicPath = Paths.get(userHome, "javascript-music-player", "music").toString();

        List<String> musicFiles = FileUploadHelper.getFilesInFolder(musicPath);
        List<String> imageFiles = FileUploadHelper.getFilesInFolder(imagePath);

        List<Object[]> existingEntries = fileUploadRepository.getAllEntries();
        List<FileUploadDTO> existingEntriesDTO = FileUploadObjectToDto.toDto(existingEntries);

        List<FileUploadArrayDTO> matchedFiles = new ArrayList<>();
        for (String musicFileName : musicFiles) {
            for (String imageFileName : imageFiles) {

                 FileUploadArrayDTO dto = new FileUploadArrayDTO(
                            Paths.get(musicPath, musicFileName).toString(),
                            Paths.get(imagePath, imageFileName).toString(),
                            FileUploadHelper.getArtistName(existingEntriesDTO, musicFileName, imageFileName),
                            FileUploadHelper.getSongName(existingEntriesDTO, musicFileName, imageFileName)
                    );
                    matchedFiles.add(dto);
                }

        }
        return matchedFiles;
    }

}
