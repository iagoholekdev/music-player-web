package org.teste.tecnico.fileupload;

import org.teste.tecnico.fileupload.dto.FileUploadArrayDTO;
import org.teste.tecnico.fileupload.dto.FileUploadDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Path("/fileupload")
public class FileUploadResource {

    @Inject
    FileUploadService fileUploadService;

    @POST
    @Path("/sendnewmusic")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFile(FileUploadDTO fileUploadDTO) {
        try {
            this.fileUploadService.createFile(fileUploadDTO);
        } catch (Exception e) {
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().status(Response.Status.OK).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FileUploadDTO uploadMusic(FileUploadDTO form) {
      FileUploadDTO fileUploadDTO;
      try {
           fileUploadDTO = this.fileUploadService.uploadMusic(form);
      } catch (Exception e) {
          return null;
      }
        return fileUploadDTO;
    }

    @GET
    @Path("/getfiles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FileUploadArrayDTO> getFiles() {
        return this.fileUploadService.getFiles();
    }

}
