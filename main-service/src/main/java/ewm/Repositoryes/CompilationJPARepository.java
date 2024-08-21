package ewm.Repositoryes;


import ewm.Entityes.Compilation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CompilationJPARepository extends JpaRepository<Compilation, Long> {
    @Query(value = "SELECT c FROM Compilation c WHERE c.pinned = true")
    List<Compilation> findPinnedCompilationsWithPagination(@Param("size") int size, @Param("from") int from);

    @Query("SELECT c FROM Compilation c")
    List<Compilation> findAllCompilationsWithPagination(@Param("size") int size, @Param("from") int from);
}
