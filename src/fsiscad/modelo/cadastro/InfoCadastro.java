package fsiscad.modelo.cadastro;

import fsiscad.modelo.*;
import fsiscad.util.*;

/**
 * Guarda informações sobre uma implementação de cadastro. As informações ficam acessíveis 
 * a partir do nome do modelo associado ao cadastro, sendo carregadas pela 
 * classe {@link MapaCadastros MapaCadastros}. Cada mapeamento vincula o nome lógico 
 * de um modelo ao tipo de um componente que implementa a interface 
 * {@link fsiscad.modelo.cadastro.Cadastro Cadastro}, o qual é responsável pelas 
 * funcionalidades de consulta e manutenção do modelo. Duas tecnologias de implementação 
 * de cadastro são suportadas: <i>entity beans</i> e classes Java convencionais 
 * (<i>pattern Data Access Object</i>).
 *
 * @see CadastroLocal
 * @see CadastroEntityLocal
 */
public abstract class InfoCadastro {
    private String modelo;
    private String tipo;
    
    /**
     * Cria uma instancia com as informações sobre o cadastro.
     *
     * @param modelo nome lógico do modelo
     * @param tipo tipo do cadastro
     */
    public InfoCadastro(String modelo, String tipo) {
        this.modelo = modelo;
        this.tipo = tipo;
    }
    
    /**
     * Retorna uma instância da fábrica do cadastro.
     *
     * @return instância da fábrica do cadastro
     *
     * @throws ErroModelo se ocorrer um erro na obtenção da fábrica do cadastro
     */
    protected abstract FabricaCadastro getFabricaCadastro() throws ErroModelo;
    
    /**
     * Retorna o nome lógico do modelo.
     *
     * @return nome lógico do modelo
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
     * Retorna uma instância do componente cadastro a partir
     * das informações guardadas. Este método viabiliza a instanciação
     * tardia dos cadastros.
     *
     * @return instância do cadastro
     *
     * @throws Erro se ocorrer um erro na obtenção do cadastro
     */
    public final Cadastro getCadastro() throws Erro {
        return getFabricaCadastro().getCadastro(this);
    }
}
