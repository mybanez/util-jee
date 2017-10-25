package fsiscad.modelo.chaveprimaria;

import fsiscad.modelo.*;
import java.util.*;

/**
 * Mapa das sequ�ncias utilizadas na camada de modelo.
 *
 * @see GeradorSequenciaBean
 */
public final class MapaSequencias extends HashMap<String, InfoSequencia> {
    /**
     * Retorna o mapa das sequ�ncias utilizadas na camada de modelo.
     *
     * @return mapa das sequ�ncias utilizadas na camada de modelo
     *
     * @throws ErroModelo se um erro for gerado durante a inicializa��o do
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
