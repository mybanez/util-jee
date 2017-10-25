package fsiscad.modelo.cadastro;

import fsiscad.modelo.*;
import fsiscad.util.*;

/**
 * Suporte para a implementação de fábricas de cadastro. Esta classe é usada por 
 * {@link fsiscad.modelo.FachadaModelo FachadaModelo} para obter acesso aos cadastros e, por 
 * sua vez, usa {@link MapaCadastros MapaCadastros} para recuperar os mapeamentos 
 * entre os nomes lógicos dos modelos e os componentes cadastro.
 */
public abstract class FabricaCadastro extends FabricaObjetoModelo {
    
    /**
     * Retorna uma instância de fábrica de cadastro deste tipo.
     *
     * @param tipo tipo da fábrica
     *
     * @return instância da fábrica de cadastro
     *
     * @throws ErroModelo se ocorrer um erro na obtenção da fábrica
     */
    public static FabricaCadastro getFabricaCadastro(String tipo) throws ErroModelo {
        return (FabricaCadastro)getInstanciaEmCache(tipo, tipo);
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
    public static Cadastro getCadastro(String modelo) throws ErroCadastro {
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
    
    /**
     * Retorna o cadastro associado a esta instância de {@link InfoCadastro InfoCadastro}.
     * 
     * @param info instância de InfoCadastro
     *
     * @return cadastro associado a esta instância de InfoCadastro
     *
     * @throws Erro se ocorrer um erro na obtenção do cadastro
     */
    protected abstract Cadastro getCadastro(InfoCadastro info) throws Erro;
}
