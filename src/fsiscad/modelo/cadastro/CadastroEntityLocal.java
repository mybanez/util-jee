package fsiscad.modelo.cadastro;

import fsiscad.modelo.ot.*;
import java.util.*;
import javax.ejb.*;

/**
 * Define os m�todos adicionais que um cadastro do tipo <i>entity bean</i> deve 
 * implementar. A interface <i>home</i> do <i>entity bean</i> tem de extender 
 * esta interface. Os m�todos de consulta permitem a defini��o de moldes para
 * a instancia��o de ots que ir�o conter os dados dos <i>entities</i> manipulados 
 * atrav�s do cadastro e de <i>outros entities relacionados com o entity raiz via
 * propriedades de relacionamento</i>. Por exemplo, se um componente <tt>Empresa</tt> 
 * se relaciona com componentes <tt>Departamento</tt>, via propriedade <tt>deptos</tt>,
 * e cada um deles se relaciona com componentes <tt>Empregado</tt>, via propriedade 
 * <tt>emps</tt>, o seguinte c�digo poderia ser utilizado para recuperar todas as
 * empresas, seus departamentos e os empregados de cada departamento:
 * <pre>
 *    ...
 *    OT usuario = ...;
 *    CadastroEntityLocal cadEmpresa = ...;
 *    OT moldeEmpresa = FabricaOT.getInstancia(Arrays.asList("CNPJ","razaoSocial","deptos")); 
 *    OT moldeDepto = FabricaOT.getInstancia(Arrays.asList("codigo","nome","emps")); 
 *    OT moldeEmpregado = FabricaOT.getInstancia(Arrays.asList("matricula","nome")); 
 *    Map<String,OT> mpMoldes = new HashMap<String,OT>;
 *    mpMoldes.put("",moldeEmpresa);
 *    mpMoldes.put("dptos",moldeDepto);
 *    mpMoldes.put("dptos.emps",moldeEmpregado);
 *    Collection<OT> empresas = cadEmpresa.consultarTodos(usuario, mpMoldes);
 *    ...
 *  </pre>
 */
public interface CadastroEntityLocal extends EJBLocalHome, Cadastro {
    /**
     * Retorna um mapa associando as propriedades dos ots que representam chaves 
     * extrangeiras aos relacionamentos do <i>entity bean</i> usados para atualizar 
     * as chaves extrangeiras. Tais propriedades virtuais podem ser definidas nos 
     * ots passados para os m�todos incluir/alterar do cadastro, mesmo n�o tendo 
     * um correspondente direto na interface local do <i>entity</i>. Ver discuss�o
     * em <a href="CadastroEntityLocalImpl.html#mapaRelChavesExt">CadastroEntityLocalImpl</a>.
     *
     * @return mapa com os relacionamentos associados �s chaves extrangeiras
     */
    Map<List<String>, InfoRelacionamentoEntity> getMapaRelacionamentosChavesExtrangeiras();

    /**
     * Consulta um <i>entity bean</i> a partir desta chave prim�ria. Os nomes das
     * propriedades que comp�em a chave prim�ria devem ser obtidas atrav�s de uma
     * chamada a <tt>chave.getNomesPropriedadesChave(modelo)</tt>.
     *
     * @param chave ot contendo a chave de consulta do <i>entity bean</i>
     *
     * @return <i>entity</i> com o resultado da consulta
     *
     * @throws ErroCadastro se algum erro ocorreu durante a opera��o
     */
    EJBLocalObject consultarEntityPorChavePrimaria(OT chave)
    throws ErroCadastro;
    
    /**
     * Para este usu�rio, consulta todos os itens deste modelo. A malha de ots 
     * retornada deve seguir este mapa de moldes.
     * 
     * @param usuario usuario logado
     * @param mpMoldes moldes para os ots a serem retornados
     *
     * @return lista de ots com o resultado da consulta
     *
     * @throws ErroCadastro se algum erro ocorreu durante a opera��o
     */
    Collection<OT> consultarTodos(OT usuario, Map<String, OT> mpMoldes)
    throws ErroCadastro;

    /**
     * Para este usu�rio, consulta os dados de um item a partir desta chave
     * prim�ria. Os nomes das propriedades que comp�em a chave prim�ria devem ser
     * obtidos atrav�s de uma chamada a <tt>chave.getNomesPropriedadesChave(modelo)</tt>. 
     * A malha de ots retornada deve seguir este mapa de moldes.
     *
     * @param usuario usuario logado
     * @param chave ot contendo a chave de consulta do item
     * @param mpMoldes moldes para os ots a serem retornados
     *
     * @return ot com o resultado da consulta
     *
     * @throws ErroCadastro se algum erro ocorreu durante a opera��o
     */
    OT consultarPorChavePrimaria(OT usuario, OT chave, Map<String, OT> mpMoldes)
    throws ErroCadastro;
}
