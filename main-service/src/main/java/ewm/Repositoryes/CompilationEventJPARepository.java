package ewm.Repositoryes;

import ewm.Entityes.CompilationEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CompilationEventJPARepository extends JpaRepository<CompilationEvents, Long> {
    List<CompilationEvents> findAllByCompilationId(Long id);
}
