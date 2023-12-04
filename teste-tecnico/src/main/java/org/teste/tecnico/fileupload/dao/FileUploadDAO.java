package org.teste.tecnico.fileupload.dao;


import org.teste.tecnico.fileupload.FileUpload;
import org.teste.tecnico.fileupload.dto.FileUploadDTO;
import org.teste.tecnico.utils.interfaces.Crudable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;


@RequestScoped
public class FileUploadDAO implements Crudable<FileUploadDTO> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void create(FileUploadDTO fileUploadDTO) {
        FileUpload fileUpload = FileUpload
                .builder()
                .uuid(UUID.randomUUID())
                .pathToSong(fileUploadDTO.getPathToSong())
                .pathToImage(fileUploadDTO.getPathToImage())
                .artistName(fileUploadDTO.getArtistName())
                .songName(fileUploadDTO.getSongName())
                .build();
        this.entityManager.persist(fileUpload);
        this.entityManager.flush();
    }

}
