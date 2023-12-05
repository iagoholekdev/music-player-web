package org.teste.tecnico.fileupload.helper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Classe helper, auxilia nas lógicas complexas.
 * @author iagoholekdev
 */

public class FileUploadHelper {

    public static List<String> getFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        return Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .map(File::getName)
                .filter(name -> !name.equals("bg.jpg"))
                .collect(Collectors.toList());
    }

    public static String generateNameFile(boolean type, String artistName, String songName) {
        if (type) {
            return "music" + artistName.trim().replaceAll("\\s+", "") + songName.trim().replaceAll("\\s+", "") + ".mp3";
        }
        return "img" + artistName.trim().replaceAll("\\s+", "") + songName.trim().replaceAll("\\s+", "") + ".jpg";
    }

    public static void downloadAndSaveFile(String fileUrl, String savePath, String fileName) throws Exception {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream in = new BufferedInputStream(connection.getInputStream());
                     BufferedOutputStream out = new BufferedOutputStream(
                             Files.newOutputStream(Paths.get(savePath, fileName)))) {

                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                System.err.println("HTTP request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

}
