package fsiscad.modelo;

import fsiscad.modelo.ot.*;
import java.util.*;

/**
 * Suporte para a implementação das classes de acesso ao modelo.
 */
public abstract class AcessoModeloImpl implements AcessoModelo {
    public Collection<OT> consultarTodos(String modelo, Class molde)
    throws ErroModelo {
        return consultarTodos(null, modelo, molde);
    }
    
    public Collection<OT> consultarTodos(String modelo, OT molde)
    throws ErroModelo {
        return consultarTodos(null, modelo, molde);
    }
    
    public Collection<OT> consultarTodos(OT usuario, String modelo, Class molde)
    throws ErroModelo {
        return consultarTodos(usuario, modelo, FabricaOT.getInstancia(molde));
    }
    
    public abstract Collection<OT> consultarTodos(OT usuario, String modelo, OT molde)
    throws ErroModelo;
    
    public OT consultarPorChavePrimaria(String modelo, OT chave)
    throws ErroModelo {
        return consultarPorChavePrimaria(null, modelo, chave);
    }

    public OT consultarPorChavePrimaria(String modelo, OT chave, Class molde)
    throws ErroModelo {
        return consultarPorChavePrimaria(null, modelo, chave, molde);
    }
    
    public OT consultarPorChavePrimaria(String modelo, OT chave, OT molde)
    throws ErroModelo {
        return consultarPorChavePrimaria(null, modelo, chave, molde);
    }
    
    public OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave)
    throws ErroModelo {
        return consultarPorChavePrimaria(usuario, modelo, chave, chave);
    }

    public OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, Class molde)
    throws ErroModelo {
        return consultarPorChavePrimaria(usuario, modelo, chave, FabricaOT.getInstancia(molde));
    }
    
    public abstract OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, OT molde)
    throws ErroModelo;
    
    public OT incluir(String modelo, OT ot)
    throws ErroModelo {
        return incluir(null, modelo, ot);
    }
    
    public abstract OT incluir(OT usuario, String modelo, OT ot)
    throws ErroModelo;

    public OT alterar(String modelo, OT ot)
    throws ErroModelo {
        return alterar(null, modelo, ot);
    }
    
    public abstract OT alterar(OT usuario, String modelo, OT ot)
    throws ErroModelo;
    
    public void excluir(String modelo, OT ot) 
    throws ErroModelo {
        excluir(null, modelo, ot);
    }
    
    public abstract void excluir(OT usuario, String modelo, OT ot)
    throws ErroModelo;
}
