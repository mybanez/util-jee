package meyn.util.modelo.cadastro;

import meyn.util.Erro;
import meyn.util.modelo.entidade.Entidade;

/**
 * Guarda informa��es sobre uma implementa��o de cadastro. As informa��es ficam acess�veis 
 * a partir do nome do modelo associado ao cadastro, sendo carregadas pela 
 * classe {@link MapaCadastros MapaCadastros}. Cada mapeamento vincula o nome l�gico 
 * de um modelo ao tipo de um componente que implementa a interface 
 * {@link meyn.util.modelo.entidade.Cadastro Cadastro}, o qual � respons�vel pelas 
 * funcionalidades de consulta e manuten��o do modelo. 
 */
class InfoCadastro {
    private String modelo;
    private String tipo;
    
    /**
     * Cria uma instancia com as informa��es sobre o cadastro.
     *
     * @param modelo nome l�gico do modelo
     * @param tipo tipo do cadastro
     */
    InfoCadastro(String modelo, String tipo) {
        this.modelo = modelo;
        this.tipo = tipo;
    }
    
    /**
     * Retorna o nome l�gico do modelo.
     *
     * @return nome l�gico do modelo
     */
    final String getModelo() {
        return modelo;
    }
    
    /**
     * Retorna o tipo do cadastro.
     *
     * @return tipo do cadastro
     */
    final String getTipo() {
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
    final <U extends Entidade, T extends Entidade>Cadastro<U, T> getCadastro() throws Erro {
        return FabricaCadastro.getCadastro(this);
    }
}
