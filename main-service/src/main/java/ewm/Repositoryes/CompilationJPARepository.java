package ewm.Repositoryes;

import ewm.Entityes.Compilation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CompilationJPARepository extends JpaRepository<Compilation, Long> {
    @Query(value = "SELECT c FROM Compilation c WHERE c.pinned = true")
    List<Compilation> findPinnedCompilationsWithPagination(Pageable pageable);

    @Query("SELECT c FROM Compilation c")
    List<Compilation> findAllCompilationsWithPagination(Pageable pageable);

    Optional<Compilation> findCompilationByTitle(String title);
}
