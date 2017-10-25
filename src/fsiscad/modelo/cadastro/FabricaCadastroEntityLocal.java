package fsiscad.modelo.cadastro;

import fsiscad.modelo.*;
import fsiscad.util.*;

/**
 * Fabrica de cadastros do tipo <i>entity bean</i> local.
 */
public final class FabricaCadastroEntityLocal extends FabricaCadastro {
    protected Cadastro getCadastro(InfoCadastro info)
    throws Erro {
        CadastroEntityLocal cadastro = (CadastroEntityLocal) getInstanciaEmCache((Object)info.getModelo());
        if (cadastro == null) {
            CacheModelo cache = CacheModelo.getCacheModelo();
            InfoCadastroEntityLocal infoEJB = (InfoCadastroEntityLocal)info;
            synchronized (cache) {
                LocalizadorEJB gerEJB = LocalizadorEJB.getLocalizadorEJB();
                cadastro = (CadastroEntityLocal) gerEJB.getLocalHome(infoEJB.getNomeJNDI());
                cadastro.setModelo(infoEJB.getModelo());
                cache.put(infoEJB.getModelo(), cadastro);
            }
        } 
        return cadastro;
    }
}
