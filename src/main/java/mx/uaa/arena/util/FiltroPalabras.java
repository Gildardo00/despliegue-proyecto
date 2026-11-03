package mx.uaa.arena.util;

import mx.uaa.arena.dao.PalabraProhibidaDAO;
import java.text.Normalizer;
import java.util.List;

public class FiltroPalabras {

    private static final PalabraProhibidaDAO dao = new PalabraProhibidaDAO();

    public static String palabraDetectada(String texto) {
        if (texto == null) return null;
        String objetivo = normalizar(texto);
        for (String palabra : dao.listar()) {
            String p = normalizar(palabra);
            if (!p.isEmpty() && objetivo.contains(p)) {
                return palabra;
            }
        }
        return null;
    }

    public static boolean esApropiado(String texto) {
        return palabraDetectada(texto) == null;
    }

    private static String normalizar(String s) {
        String n = Normalizer.normalize(s.toLowerCase(), Normalizer.Form.NFD);
        return n.replaceAll("\\p{M}", "");
    }
}
