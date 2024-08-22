package ewm.Repositoryes;


import ewm.Entityes.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CategoryJPARepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c")
    List<Category> findAllWithPagination(@Param("size") int size, @Param("from") int from);
    Optional<Category> findCategoryByName(String name);
}
