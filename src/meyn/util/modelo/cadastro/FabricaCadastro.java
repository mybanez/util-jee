package meyn.util.modelo.cadastro;

import meyn.util.*;
import meyn.util.modelo.*;
import meyn.util.modelo.ot.OT;

/**
 * Suporte para a implementação de fábricas de cadastro. Esta classe é usada por 
 * {@link meyn.util.modelo.FachadaModelo FachadaModelo} para obter acesso aos cadastros e, por 
 * sua vez, usa {@link MapaCadastros MapaCadastros} para recuperar os mapeamentos 
 * entre os nomes lógicos dos modelos e os componentes cadastro.
 */
public class FabricaCadastro extends FabricaObjetoModelo {
    
    /**
     * Retorna o cadastro associado a esta instância de {@link InfoCadastro InfoCadastro}.
     * 
     * @param info instância de InfoCadastro
     *
     * @return cadastro associado a esta instância de InfoCadastro
     *
     * @throws Erro se ocorrer um erro na obtenção do cadastro
     */
    @SuppressWarnings("unchecked")
	static <U extends OT, T extends OT>Cadastro<U, T> getCadastro(InfoCadastro info)
    throws Erro {
        Cadastro<U, T> cadastro = (Cadastro<U, T>)getInstanciaEmCache(info.getModelo());
        if (cadastro == null) {
            cadastro = (Cadastro<U, T>)getInstanciaEmCache(info.getModelo(), info.getTipo());
            cadastro.setModelo(info.getModelo());
        }
        return cadastro;
    }

    /**
     * Retorna o cadastro associado a este nome lógico de modelo.
     *
     * @param modelo nome lógico do modelo
     *
     * @return cadastro associado a este modelo
     *
     * @throws ErroCadastro se ocorrer um erro na obtenção do cadastro
     */
    public static <U extends OT, T extends OT>Cadastro<U, T> getCadastro(String modelo) throws ErroCadastro {
        MapaCadastros mapa = MapaCadastros.getMapaCadastros();
        InfoCadastro info = (InfoCadastro) mapa.get(modelo);
        if (info == null) {
            throw new ErroExecucao("Não existe entrada no mapa de cadastros para o modelo '"+modelo+"'");
        }
        try {
            return info.getCadastro();
        } catch (Erro e) {
            throw new ErroCadastro("Erro obtendo cadastro para o modelo '"+modelo+"'", e);
        }
    }
}
