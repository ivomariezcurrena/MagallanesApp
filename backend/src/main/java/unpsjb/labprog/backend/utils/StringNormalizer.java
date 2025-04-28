package unpsjb.labprog.backend.utils;

import java.text.Normalizer;

public class StringNormalizer {
    public static String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toUpperCase();
    }
}
