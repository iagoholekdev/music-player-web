package org.teste.tecnico.music;


import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.teste.tecnico.music.model.Music;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class MusicRepository {

    @Inject
    private EntityManager entityManager;


    public Music findById(Long id) {
        return this.entityManager.find(Music.class, id);
    }

    public List<Long> musicStats(String trackName, String artistName) {
        String query = "SELECT COUNT(*) FROM music \n" +
                "\t\tWHERE LOWER(trackname) = LOWER(?1) \n" +
                "\t\tAND LOWER(artistname) = LOWER(?2)\n" +
                "\tand datelistened = current_date\n" +
                "\tunion all\n" +
                "\tSELECT COUNT(*) FROM music \n" +
                "\t\tWHERE LOWER(trackname) = LOWER(?1) \n" +
                "\t\tAND LOWER(artistname) = LOWER(?2)\n" +
                "\t\tand datelistened < current_date;";
        List<BigInteger> resultList = this.entityManager
                .createNativeQuery(query)
                .setParameter(1, trackName)
                .setParameter(2, artistName)
                .getResultList();

        // Convert the list of BigInteger to a list of Long
        List<Long> resultLongList = resultList.stream()
                .map(BigInteger::longValue)
                .collect(Collectors.toList());

        return resultLongList;
    }

}
