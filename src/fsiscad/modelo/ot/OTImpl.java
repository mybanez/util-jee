package fsiscad.modelo.ot;

import fsiscad.util.*;
import fsiscad.beans.*;
import java.util.*;

/**
 * Suporte para implementa��es de objetos de transfer�ncia. Esta classe assume
 * que a interface de acesso padr�o �s propriedades � {@link fsiscad.beans.AcessoPropriedades
 * AcessoPropriedades}.
 *
 * @see FabricaOT
 */
public abstract class OTImpl implements OT {
    private Long idVersao;
    private Collection<String> clNomesPropsChave;
    private Collection<String> clNomesProps;
    
    /**
     * Cria um objeto de transfer�ncia.
     */
    public OTImpl() {
        this(Collections.<String>emptyList(), Collections.<String>emptyList());
    }
    
    /**
     * Cria um objeto de transfer�ncia com as propriedades definidas nesta cole��o.
     *
     * @param clNomesProps nomes das propriedades
     */
    public OTImpl(Collection<String> clNomesProps) {
        this(Collections.<String>emptyList(), clNomesProps);
    }
    
    /**
     * Cria um objeto de transfer�ncia com os propriedades chave definidas na 
     * primeira cole��o e com as propriedades definidas na segunda cole��o. 
     *
     * @param clNomesPropsChave nomes das propriedades que comp�em a
     *        chave prim�ria
     * @param clNomesProps nomes das propriedades
     */
    public OTImpl(Collection<String> clNomesPropsChave, Collection<String> clNomesProps) {
        this.clNomesPropsChave = clNomesPropsChave;
        this.clNomesProps = clNomesProps;        
    }
    
    public final Collection<String> getNomesPropriedadesChave() {
        return clNomesPropsChave;
    }
    
    /**
     * Define os nomes das propriedades do ot que comp�em a chave prim�ria.
     *
     * @param clNomesPropsChave cole��o com os nomes das propriedades do ot que
     *        comp�em a chave prim�ria
     */
    public final void setNomesPropriedadesChave(Collection<String> clNomesPropsChave) {
        this.clNomesPropsChave = clNomesPropsChave;
    }
    
    public final Collection<String> getNomesPropriedades() {
        return clNomesProps;
    }
    
    /**
     * Define os nomes das propriedades do ot.
     *
     * @param clNomesProps cole��o com os nomes das propriedades do ot
     */
    public final void setNomesPropriedades(Collection<String> clNomesProps) {
        this.clNomesProps = clNomesProps;
    }
    
    /**
     * Retorna {@link fsiscad.beans.AcessoPropriedades
     * AcessoPropriedades} como o tipo da interface de acesso �s propriedades
     * do ot.
     *
     * @return tipo {@link fsiscad.beans.AcessoPropriedades
     * AcessoPropriedades}
     */
    public Class getTipoAcessoPropriedades() {
        return AcessoPropriedades.class;
    }
    
    public final long getIdVersao() {
        return idVersao;
    }
    
    public final void setIdVersao(long idVersao) {
        this.idVersao = idVersao;
    }
    
    /*       SUPORTE PARA MANIPULA��O DE ATRIBUTOS         */
    
    /**
     * Neste ot, valida a propriedade com este nome.
     * 
     * @param ot objeto de transfer�ncia
     * @param nome nome da propriedade a ser validada
     *
     * @throws ErroPropriedadeOTNaoDefinida se a propriedade n�o estiver definida
     *        neste OT
     */
    public final static void validarPropriedade(OT ot, String nome) {
        if (!ot.getNomesPropriedades().contains(nome)) {
            throw new ErroPropriedadeOTNaoDefinida(nome);
        }
    }
    
    /**
     * Implementa��o est�tica da l�gica do m�todo {@link OTImpl#get() get()}.
     */
    public final static Map<String, Object> get(OT ot) {
        return get(ot, ot.getNomesPropriedades());
    }
    
    /**
     * Implementa��o est�tica da l�gica do m�todo {@link OTImpl#get(Collection) get(Collection)}.
     */
    public final static Map<String, Object> get(OT ot, Collection<String> clNomesProps) {
        Map<String, Object> res = new HashMap<String, Object>();
        for (String nome : clNomesProps) {
            res.put(nome, ot.get(nome));
        }
        return res;
    }
    
    /**
     * Implementa��o est�tica da l�gica do m�todo {@link OTImpl#set(Map) get(Map)}.
     */
    public final static void set(OT ot, Map<String, Object> mpProps) {
        Collection<Map.Entry<String, Object>> itens = mpProps.entrySet();
        for (Map.Entry<String, Object> item : itens) {
            ot.set(item.getKey(), item.getValue());
        }
    }
    
    /*          INTERFACE AcessoPropriedades           */
    
    public final Map<String, Object> get() {
        return get(this);
    }
    
    public final Map<String, Object> get(Collection<String> clNomesProps) {
        return get(this, clNomesProps);
    }
    
    public final void set(Map<String, Object> mpProps) {
        set(this, mpProps);
    }
}
