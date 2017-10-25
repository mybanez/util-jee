package fsiscad.controle.struts;

/**
 * Cont�m as constantes necess�rias para a implementa��o de p�ginas JSP e
 * Servlets que interagem com o suporte Struts da <i>framework</i>.
 */
public interface ChavesControleStruts {
    String NOME_PACOTE = ChavesControleStruts.class.getPackage().getName()+".";
    //A��o parametriz�vel
    String PARAM_ACAO = (NOME_PACOTE+"PARAM_ACAO").replace('.', '_');
    //A��o cadastro
    String PA_CONSULTAR_POR_CHAVE_PRIMARIA = NOME_PACOTE+"PA_CONSULTAR_POR_CHAVE_PRIMARIA";
    String PA_CONSULTAR_TODOS = NOME_PACOTE+"PA_CONSULTAR_TODOS";
    String PA_INCLUIR = NOME_PACOTE+"PA_INCLUIR";
    String PA_ALTERAR = NOME_PACOTE+"PA_ALTERAR";
    String PA_EXCLUIR = NOME_PACOTE+"PA_EXCLUIR";
}
