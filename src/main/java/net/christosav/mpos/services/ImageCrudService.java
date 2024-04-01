package net.christosav.mpos.services;

import net.christosav.mpos.data.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class ImageCrudService extends AbstractCrudService<ImageEntity, Long> {
    public ImageCrudService(JpaRepository<ImageEntity, Long> repository, JpaSpecificationExecutor<ImageEntity> jpaSpecificationExecutor) {
        super(repository, jpaSpecificationExecutor);
    }
    /*
    Maybe include an option like DATAPACK in a new service that would preload the data from the database,
    and then use it in the application.

    Datapacks can also be used for the static content of the application, like images, and other resources.
    e.g so we could personalize the application with the user's image.
    datapacks would be written in sql
   


    Also, we could have a service that would handle the image upload and storage in the database.






     */
}
