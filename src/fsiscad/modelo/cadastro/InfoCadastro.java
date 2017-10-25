package fsiscad.modelo.cadastro;

import fsiscad.modelo.*;
import fsiscad.util.*;

/**
 * Guarda informa��es sobre uma implementa��o de cadastro. As informa��es ficam acess�veis 
 * a partir do nome do modelo associado ao cadastro, sendo carregadas pela 
 * classe {@link MapaCadastros MapaCadastros}. Cada mapeamento vincula o nome l�gico 
 * de um modelo ao tipo de um componente que implementa a interface 
 * {@link fsiscad.modelo.cadastro.Cadastro Cadastro}, o qual � respons�vel pelas 
 * funcionalidades de consulta e manuten��o do modelo. Duas tecnologias de implementa��o 
 * de cadastro s�o suportadas: <i>entity beans</i> e classes Java convencionais 
 * (<i>pattern Data Access Object</i>).
 *
 * @see CadastroLocal
 * @see CadastroEntityLocal
 */
public abstract class InfoCadastro {
    private String modelo;
    private String tipo;
    
    /**
     * Cria uma instancia com as informa��es sobre o cadastro.
     *
     * @param modelo nome l�gico do modelo
     * @param tipo tipo do cadastro
     */
    public InfoCadastro(String modelo, String tipo) {
        this.modelo = modelo;
        this.tipo = tipo;
    }
    
    /**
     * Retorna uma inst�ncia da f�brica do cadastro.
     *
     * @return inst�ncia da f�brica do cadastro
     *
     * @throws ErroModelo se ocorrer um erro na obten��o da f�brica do cadastro
     */
    protected abstract FabricaCadastro getFabricaCadastro() throws ErroModelo;
    
    /**
     * Retorna o nome l�gico do modelo.
     *
     * @return nome l�gico do modelo
     */
    public final String getModelo() {
        return modelo;
    }
    
    /**
     * Retorna o tipo do cadastro.
     *
     * @return tipo do cadastro
     */
    public final String getTipo() {
        return tipo;
    }
    
    /**
     * Retorna uma inst�ncia do componente cadastro a partir
     * das informa��es guardadas. Este m�todo viabiliza a instancia��o
     * tardia dos cadastros.
     *
     * @return inst�ncia do cadastro
     *
     * @throws Erro se ocorrer um erro na obten��o do cadastro
     */
    public final Cadastro getCadastro() throws Erro {
        return getFabricaCadastro().getCadastro(this);
    }
}
