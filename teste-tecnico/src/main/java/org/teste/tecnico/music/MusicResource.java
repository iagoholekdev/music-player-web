package org.teste.tecnico.music;

import org.teste.tecnico.music.dto.MusicDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Path("/music")
public class MusicResource {

    @Inject
    MusicService musicService;

    @POST
    @Path("/sendmusic")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMusic(MusicDTO musicDTO) {
        try {
            this.musicService.createMusic(musicDTO);
        } catch (Exception e) {
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().status(Response.Status.OK).build();
    }

    @GET
    @Path("getcontador/{trackname}/{artistname}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Long> getTrackCountByName(@PathParam("trackname") String trackName,
                                          @PathParam("artistname") String artistName) {
        return this.musicService.musicStats(trackName, artistName);
    }
}
