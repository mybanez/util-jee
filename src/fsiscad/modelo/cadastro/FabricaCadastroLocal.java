package fsiscad.modelo.cadastro;

import fsiscad.util.*;

/**
 * Fabrica de cadastros do tipo local (objeto java convencional).
 */
public final class FabricaCadastroLocal extends FabricaCadastro {
    protected Cadastro getCadastro(InfoCadastro info)
    throws Erro {
        CadastroLocal cadastro = (CadastroLocal)getInstanciaEmCache((Object)info.getModelo());
        if (cadastro == null) {
            cadastro = (CadastroLocal)getInstanciaEmCache(info.getModelo(), info.getTipo());
            cadastro.setModelo(info.getModelo());
        }
        return cadastro;
    }
}
