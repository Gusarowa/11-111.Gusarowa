package repositories;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<Entity> {
    List<Entity> findAll();

    Optional<Entity> findById(Long id);

    void save(Entity entity);

    void update(Entity entity);

    void delete(Entity entity);

    void deleteById(Long id);
}
