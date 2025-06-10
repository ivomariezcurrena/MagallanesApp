package unpsjb.labprog.backend.business.reporteUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.ArrayList;

public class PaginadorUtils {
    public static <T> Page<T> paginar(List<T> lista, int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, lista.size());
        List<T> pageContent = start < end ? lista.subList(start, end) : new ArrayList<>();
        return new PageImpl<>(pageContent, PageRequest.of(page, size), lista.size());
    }
}
