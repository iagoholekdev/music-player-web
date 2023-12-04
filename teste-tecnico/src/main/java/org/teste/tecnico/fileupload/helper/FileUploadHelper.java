package org.teste.tecnico.fileupload.helper;


import org.teste.tecnico.fileupload.dto.FileUploadDTO;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileUploadHelper {

    public static String getSongName(List<FileUploadDTO> entries, String musicFileName, String imageFileName) {
        return entries.stream()
                .findFirst()
                .map(FileUploadDTO::getSongName)
                .orElse(null);
    }

    public static String getArtistName(List<FileUploadDTO> entries, String musicFileName, String imageFileName) {
        return entries.stream()
                .findFirst()
                .map(FileUploadDTO::getArtistName)
                .orElse(null);
    }

    public static List<String> getFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        return Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(f -> !f.getName().equals("bg.jpg"))
                .map(File::getName)
                .collect(Collectors.toList());
    }

}
