package fsiscad.modelo;

import fsiscad.modelo.cadastro.*;
import fsiscad.modelo.ot.*;
import java.util.*;

/**
 * Fachada que repassa operações em cima dos modelos para componentes do tipo
 * {@link Cadastro Cadastro} que implementam estas operações (<i>pattern
 * Facade</i>). Esta classe usa {@link FabricaCadastro FabricaCadastro} para
 * obter acesso aos cadastros.
 */
public abstract class FachadaModelo extends AcessoModeloImpl {
    /**
     * Retorna o cadastro associado a este nome lógico de modelo.
     *
     * @throws ErroModelo se ocorrer um erro na obtenção do cadastro
     */
    protected final static Cadastro getCadastro(String modelo)
    throws ErroModelo {
        return FabricaCadastro.getCadastro(modelo);
    }
    
    public final Collection<OT> consultarTodos(OT usuario, String modelo, OT molde)
    throws ErroModelo {
        return getCadastro(modelo).consultarTodos(usuario, molde);
    }
    
    public final OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, OT molde)
    throws ErroModelo {
        return getCadastro(modelo).consultarPorChavePrimaria(usuario, chave, molde);
    }
    
    public OT incluir(OT usuario, String modelo, OT ot)
    throws ErroModelo {
        return getCadastro(modelo).incluir(usuario, ot);
    }
    
    public OT alterar(OT usuario, String modelo, OT ot)
    throws ErroModelo {
        return getCadastro(modelo).alterar(usuario, ot);
    }
    
    public final void excluir(OT usuario, String modelo, OT ot)
    throws ErroModelo {
        getCadastro(modelo).excluir(usuario, ot);
    }
}
