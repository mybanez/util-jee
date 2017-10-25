package fsiscad.modelo.cadastro;

import fsiscad.modelo.*;
import fsiscad.util.*;

/**
 * Suporte para a implementa��o de f�bricas de cadastro. Esta classe � usada por 
 * {@link fsiscad.modelo.FachadaModelo FachadaModelo} para obter acesso aos cadastros e, por 
 * sua vez, usa {@link MapaCadastros MapaCadastros} para recuperar os mapeamentos 
 * entre os nomes l�gicos dos modelos e os componentes cadastro.
 */
public abstract class FabricaCadastro extends FabricaObjetoModelo {
    
    /**
     * Retorna uma inst�ncia de f�brica de cadastro deste tipo.
     *
     * @param tipo tipo da f�brica
     *
     * @return inst�ncia da f�brica de cadastro
     *
     * @throws ErroModelo se ocorrer um erro na obten��o da f�brica
     */
    public static FabricaCadastro getFabricaCadastro(String tipo) throws ErroModelo {
        return (FabricaCadastro)getInstanciaEmCache(tipo, tipo);
    }
    
    /**
     * Retorna o cadastro associado a este nome l�gico de modelo.
     *
     * @param modelo nome l�gico do modelo
     *
     * @return cadastro associado a este modelo
     *
     * @throws ErroCadastro se ocorrer um erro na obten��o do cadastro
     */
    public static Cadastro getCadastro(String modelo) throws ErroCadastro {
        MapaCadastros mapa = MapaCadastros.getMapaCadastros();
        InfoCadastro info = (InfoCadastro) mapa.get(modelo);
        if (info == null) {
            throw new ErroExecucao("N�o existe entrada no mapa de cadastros para o modelo '"+modelo+"'");
        }
        try {
            return info.getCadastro();
        } catch (Erro e) {
            throw new ErroCadastro("Erro obtendo cadastro para o modelo '"+modelo+"'", e);
        }
    }
    
    /**
     * Retorna o cadastro associado a esta inst�ncia de {@link InfoCadastro InfoCadastro}.
     * 
     * @param info inst�ncia de InfoCadastro
     *
     * @return cadastro associado a esta inst�ncia de InfoCadastro
     *
     * @throws Erro se ocorrer um erro na obten��o do cadastro
     */
    protected abstract Cadastro getCadastro(InfoCadastro info) throws Erro;
}
