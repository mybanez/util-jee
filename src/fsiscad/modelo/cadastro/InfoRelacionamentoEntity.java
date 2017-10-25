package fsiscad.modelo.cadastro;

import java.util.*;

/**
 * Guarda informações sobre um relacionamento de um <i>entity bean</i>.
 *
 * @see CadastroEntityLocalImpl
 */
public final class InfoRelacionamentoEntity {
    
    private String nomeProp;
    private String modeloRel;
    private List<String> nomesPropsChaveRel;
    
    /**
     * Cria uma instancia com as informações sobre o relacionamento.
     */
    public InfoRelacionamentoEntity(String nomeProp, String modeloRel, List<String> nomesPropsChaveRel) {
       this.nomeProp = nomeProp;
       this.modeloRel = modeloRel;
       this.nomesPropsChaveRel = nomesPropsChaveRel;
    }

    /**
     * Retorna o nome da propriedade de relacionamento do <i>entity</i>.
     */
    public String getNomePropriedade() {
        return nomeProp;
    }

    /**
     * Retorna o nome do modelo do <i>entity</i> relacionado.
     */
    public String getModeloRelacionado() {
        return modeloRel;
    }

    /**
     * Retorna os nomes das propriedades que compõem a chave primária do 
     * <i>entity</i> relacionado.
     */
    public List<String> getNomesPropriedadesChaveRelacionada() {
        return nomesPropsChaveRel;
    }
}
