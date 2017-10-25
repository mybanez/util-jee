package fsiscad.controle.struts;

import fsiscad.util.*;
import fsiscad.beans.*;
import fsiscad.modelo.ot.*;
import java.util.*;
import org.apache.struts.action.*;

/**
 * Suporte para a implementação de forms Struts que podem ser utilizados
 * como objeto de transferência. A implementação da interface {@link fsiscad.modelo.ot.OT OT}
 * segue as diretrizes adotadas em {@link fsiscad.modelo.ot.OTTipado
 * OTTipado}, com a diferença que, caso uma interface de acesso às propriedades
 * não seja fornecida, o método <tt>getTipoAcessoPropriedades</tt> retorna o
 * tipo {@link fsiscad.beans.AcessoPropriedades AcessoPropriedades}.
 */
public class FormAcaoOT extends ActionForm implements OT {
    private long idVersao;
    private Collection<String> clNomesPropsChave = new ArrayList<String>();
    private Collection<String> clNomesProps = new ArrayList<String>();
    private Class tipoAcessoProps = AcessoPropriedades.class;
    
    public FormAcaoOT(Collection<String> clNomesProps) {
        this(null, clNomesProps);
    }
    
    public FormAcaoOT(Collection<String> clNomesPropsChave, Collection<String> clNomesProps) {
        setNomesPropriedadesChave(clNomesPropsChave);
        setNomesPropriedades(clNomesProps);
    }
    
    public FormAcaoOT(Class tipoAcessoProps) {
        this(null, tipoAcessoProps);
    }
    
    public FormAcaoOT(Collection<String> clNomesPropsChave, Class tipoAcessoProps) {
        this(clNomesPropsChave, new ArrayList(Componentes.getDescritoresPropriedades(tipoAcessoProps).keySet()), tipoAcessoProps);
    }
    
    public FormAcaoOT(Collection<String> clNomesPropsChave, Collection<String> clNomesProps, Class tipoAcessoProps) {
        this(clNomesPropsChave, clNomesProps);
        setTipoAcessoPropriedades(tipoAcessoProps);
    }
    
    public final long getIdVersao() {
        return idVersao;
    }
    
    public final void setIdVersao(long idVersao) {
        this.idVersao = idVersao;
    }
    
    public final Collection<String> getNomesPropriedadesChave() {
        return clNomesPropsChave;
    }
    
    public final void setNomesPropriedadesChave(Collection<String> clNomesPropsChave) {
        this.clNomesPropsChave = clNomesPropsChave;
    }
    
    public final Collection<String> getNomesPropriedades() {
        return clNomesProps;
    }
    
    public final void setNomesPropriedades(Collection<String> clNomesProps) {
        this.clNomesProps = clNomesProps;
    }
    
    public final Class getTipoAcessoPropriedades() {
        return tipoAcessoProps;
    }
    
    public final void setTipoAcessoPropriedades(Class tipoAcessoProps) {
        if (!tipoAcessoProps.isAssignableFrom(getClass())) {
            throw new ErroExecucao("OT não implementa interface de acesso: "+tipoAcessoProps.getName());
        }
        this.tipoAcessoProps = tipoAcessoProps;
    }
    
    /**
     * Converte a instância do form em um ot sem dependências com as classes do
     * Struts. O form não pode ser utilizado diretamente como ot quando a fachada
     * é remota, pois as classes do Struts tipicamente não são visíveis pelo 
     * <i>class loader</i> do módulo EJB.
     */
    public final <T extends OT> T getProxy() {
        return (T)FabricaOT.getInstancia(getNomesPropriedadesChave(), get(), getTipoAcessoPropriedades());
    }
    
    public final <T extends OT> T getProxy(Collection<String> clNomesProps) {
        return (T)FabricaOT.getInstancia(getNomesPropriedadesChave(), get(clNomesProps), getTipoAcessoPropriedades());
    }

    /*          INTERFACE AcessoPropriedades           */
    
    public final Map<String, Object> get() {
        return OTTipado.get(this);
    }
    
    public final Map<String, Object> get(Collection<String> clNomesProps) {
        return OTTipado.get(this, clNomesProps);
    }
    
    public final void set(Map<String, Object> mpProps) {
        OTTipado.set(this, mpProps);
    }
    
    public final Object get(String nome) {
        return OTTipado.get(this, nome);
    }
    
    public final void set(String nome, Object valor) {
        OTTipado.set(this, nome, valor);
    }
}
