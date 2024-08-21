package ewm.Repositoryes;

import ewm.Entityes.Compilation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompilationsRepo {
    private final CompilationJPARepository jpa;
    public List<Compilation> getCompilations(boolean pinned,
                                             int from,
                                             int size) {

        if (pinned) {
            return jpa.findPinnedCompilationsWithPagination(from, size);
        } else {
            return jpa.findAllCompilationsWithPagination(from, size);
        }
    }

    public Compilation getCurrent(Long id) {
        return jpa.findById(id).orElse(null);
    }

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
}
