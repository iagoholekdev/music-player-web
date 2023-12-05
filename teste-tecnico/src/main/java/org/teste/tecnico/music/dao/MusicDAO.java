package org.teste.tecnico.music.dao;

import org.teste.tecnico.music.dto.MusicDTO;
import org.teste.tecnico.music.model.Music;
import org.teste.tecnico.utils.interfaces.Crudable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;

@RequestScoped
public class MusicDAO implements Crudable<MusicDTO> {

    @Inject
    private EntityManager entityManager;

    @Transactional
    public void create(MusicDTO musicDTO) {
        try {
            Music music = Music.builder()
                    .artistName(musicDTO.getArtistName())
                    .dateListened(LocalDate.now())
                    .trackName(musicDTO.getTrackName())
                    .build();

            this.entityManager.persist(music);
            this.entityManager.flush();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create music", e);
        }
    }

}
