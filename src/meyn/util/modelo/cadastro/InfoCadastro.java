package meyn.util.modelo.cadastro;

import meyn.util.Erro;
import meyn.util.modelo.entidade.Entidade;

/**
 * Guarda informações sobre uma implementação de cadastro. As informações ficam acessíveis 
 * a partir do nome do modelo associado ao cadastro, sendo carregadas pela 
 * classe {@link MapaCadastros MapaCadastros}. Cada mapeamento vincula o nome lógico 
 * de um modelo ao tipo de um componente que implementa a interface 
 * {@link meyn.util.modelo.entidade.Cadastro Cadastro}, o qual é responsável pelas 
 * funcionalidades de consulta e manutenção do modelo. 
 */
class InfoCadastro {
    private String modelo;
    private String tipo;
    
    /**
     * Cria uma instancia com as informações sobre o cadastro.
     *
     * @param modelo nome lógico do modelo
     * @param tipo tipo do cadastro
     */
    InfoCadastro(String modelo, String tipo) {
        this.modelo = modelo;
        this.tipo = tipo;
    }
    
    /**
     * Retorna o nome lógico do modelo.
     *
     * @return nome lógico do modelo
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
     * Retorna uma instância do componente cadastro a partir
     * das informações guardadas. Este método viabiliza a instanciação
     * tardia dos cadastros.
     *
     * @return instância do cadastro
     *
     * @throws Erro se ocorrer um erro na obtenção do cadastro
     */
    final <U extends Entidade, T extends Entidade>Cadastro<U, T> getCadastro() throws Erro {
        return FabricaCadastro.getCadastro(this);
    }
}
