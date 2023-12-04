package org.teste.tecnico.music;



import org.teste.tecnico.music.dao.MusicDAO;
import org.teste.tecnico.music.dto.MusicDTO;
import org.teste.tecnico.music.model.Music;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;


@RequestScoped
public class MusicService {

    @Inject
    MusicDAO musicDAO;

    @Inject
    MusicRepository musicRepository;


    public void createMusic(MusicDTO musicDTO) {
       this.musicDAO.create(musicDTO);
    }

    public Music findById(Long id) {
        return this.musicRepository.findById(id);
    }

    public List<Long> musicStats(String trackName, String artistName) {
        return this.musicRepository.musicStats(trackName, artistName);
    }

}
