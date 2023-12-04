package org.teste.tecnico.fileupload.translate;

import org.teste.tecnico.fileupload.dto.FileUploadDTO;

import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class FileUploadObjectToDto {

    public static List<FileUploadDTO> toDto(List<Object[]> list) {
        return list.stream()
                .map(row -> new FileUploadDTO((String) row[0], (String) row[1]))
                .collect(Collectors.toList());

    }

}
