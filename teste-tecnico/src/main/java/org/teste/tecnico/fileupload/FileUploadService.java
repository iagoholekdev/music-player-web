package org.teste.tecnico.fileupload;


import org.teste.tecnico.fileupload.dao.FileUploadDAO;
import org.teste.tecnico.fileupload.dto.FileUploadArrayDTO;
import org.teste.tecnico.fileupload.dto.FileUploadDTO;
import org.teste.tecnico.fileupload.processfiles.ProcessFiles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import java.util.*;


@RequestScoped
public class FileUploadService {

    @Inject
    FileUploadDAO fileUploadDAO;

    @Inject
    ProcessFiles processFiles;

    public void createFile(FileUploadDTO fileUploadDTO) {
        this.fileUploadDAO.create(fileUploadDTO);
    }

    public FileUploadDTO uploadMusic(FileUploadDTO form) throws Exception {
      return this.processFiles.uploadMusic(form);

    }

    public List<FileUploadArrayDTO> getFiles() {
       return this.processFiles.getFiles();
    }

}
