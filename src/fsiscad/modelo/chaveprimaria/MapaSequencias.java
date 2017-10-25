package fsiscad.modelo.chaveprimaria;

import fsiscad.modelo.*;
import java.util.*;

/**
 * Mapa das sequências utilizadas na camada de modelo.
 *
 * @see GeradorSequenciaBean
 */
public final class MapaSequencias extends HashMap<String, InfoSequencia> {
    /**
     * Retorna o mapa das sequências utilizadas na camada de modelo.
     *
     * @return mapa das sequências utilizadas na camada de modelo
     *
     * @throws ErroModelo se um erro for gerado durante a inicialização do
     *         mapa
     */
    public static MapaSequencias getMapaSequencias()
    throws ErroModelo {
        try {
            return (MapaSequencias)FabricaObjetoModelo.getInstanciaEmCache(MapaSequencias.class.getName(), MapaSequencias.class.getName());
        } catch (Exception e) {
            throw new ErroModelo("Erro carregando mapa das sequencias", e);
        }
    }
}
