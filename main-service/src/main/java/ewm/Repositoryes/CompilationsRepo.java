package ewm.Repositoryes;

import ewm.Entityes.Compilation;
import ewm.Entityes.CompilationEvents;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CompilationsRepo {
    private final CompilationJPARepository jpa;
    private final CompilationEventJPARepository eJpa;

    public List<Compilation> getCompilations(boolean pinned,
                                             int from,
                                             int size) {
        Pageable page = PageRequest.of(from, size);

        if (pinned) {
            return jpa.findPinnedCompilationsWithPagination(page);
        } else {
            return jpa.findAllCompilationsWithPagination(page);
        }
    }

    public void setCompilationEvent(CompilationEvents cEvent) {
        eJpa.saveAndFlush(cEvent);
    }

    public boolean checkUniqueTitle(String title, Long id) {
        Compilation current = jpa.findCompilationByTitle(title).orElse(null);
        return current == null || (Objects.equals(current.getId(), id) && Objects.equals(current.getTitle(), title));
    }

    public Compilation getCurrent(Long id) {
        return jpa.findById(id).orElse(null);
    }

    @Transactional
    public Compilation setEmptyCompilation(boolean pinned, String title) {
        Compilation comp = new Compilation();
        comp.setPinned(pinned);
        comp.setTitle(title);
        return jpa.saveAndFlush(comp);
    }

    public String deleteCompilation(Compilation comp) {
        jpa.delete(comp);
        return "Подборка удалена";
    }

    public List<CompilationEvents> getCompilationEvents(Long id) {
        return eJpa.findAllByCompilationId(id);
    }

    public void removeCompilationEvents(Long id) {
        eJpa.deleteById(id);
    }
}
