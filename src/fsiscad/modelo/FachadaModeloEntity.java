package fsiscad.modelo;

import fsiscad.modelo.cadastro.*;
import fsiscad.modelo.ot.*;
import java.util.*;

/**
 * Fachada que repassa operações em cima dos modelos para componentes do tipo
 * {@link CadastroEntityLocal CadastroEntityLocal} que implementam estas operações 
 * (<i>pattern Facade</i>).
 */
public class FachadaModeloEntity extends FachadaModelo implements AcessoModeloEntity {
    
    public Collection<OT> consultarTodos(String modelo, Map<String, OT> mpMoldes)
    throws ErroModelo {
        return consultarTodos(null, modelo, mpMoldes);
    }

    public final Collection<OT> consultarTodos(OT usuario, String modelo, Map<String, OT> mpMoldes)
    throws ErroModelo {
        return ((CadastroEntityLocal)getCadastro(modelo)).consultarTodos(usuario, mpMoldes);
    }

    public OT consultarPorChavePrimaria(String modelo, OT chave, Map<String, OT> mpMoldes)
    throws ErroModelo {
        return consultarPorChavePrimaria(null, modelo, chave, mpMoldes);
    }
    
    public final OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, Map<String, OT> mpMoldes)
    throws ErroModelo {
        return ((CadastroEntityLocal)getCadastro(modelo)).consultarPorChavePrimaria(usuario, chave, mpMoldes);
    }
}
