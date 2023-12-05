package org.teste.tecnico.fileupload;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@RequestScoped
public class FileUploadRepository {

    @Inject
    EntityManager entityManager;

    public List<Object[]> getAllEntries() {
            String query = "SELECT ID, ARTIST_NAME, SONG_NAME FROM FILEUPLOAD ORDER BY ID";
            return this.entityManager.createNativeQuery(query).getResultList();
    }

}
