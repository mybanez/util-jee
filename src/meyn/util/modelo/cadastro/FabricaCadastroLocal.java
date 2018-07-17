package meyn.util.modelo.entidade;

import meyn.util.*;

/**
 * Fabrica de cadastros do tipo local (POJO).
 */
public final class FabricaCadastroLocal extends FabricaCadastro {
    protected Cadastro getCadastro(InfoCadastro info)
    throws Erro {
        CadastroImpl cadastro = (CadastroImpl)getInstanciaEmCache((Object)info.getModelo());
        if (cadastro == null) {
            cadastro = (CadastroImpl)getInstanciaEmCache(info.getModelo(), info.getTipo());
            cadastro.setModelo(info.getModelo());
        }
        return cadastro;
    }
}
