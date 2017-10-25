package fsiscad.util;

import fsiscad.contexto.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

/**
 * Reposit�rio de conex�es para uma base de dados. As conex�es s�o obtidas
 * atrav�s de um objeto <tt>DataSource</tt>, recuperado do contexto JNDI a
 * partir da chave fornecida no momento da instancia��o do reposit�rio. Esta
 * classe n�o implementa um mecanismo de <b><i>pool</i> de conex�es</b>
 * propriamente dito, pois assume que este recurso seja suportado pelo
 * container J2EE sendo utilizado. Ou seja, qualquer configura��o de
 * <i>pool</i> de conex�es deve ser feita no momento da configura��o do
 * <i>data source</i> no container. Diversos reposit�rios de conex�es podem
 * ser criados simultaneamente, sendo cada um associado a um nome. Pode ser definido
 * tamb�m um reposit�rio padr�o, que n�o precisa de um nome para ser recuperado.
 * Os reposit�rios s�o guardados no <a href="../contexto/Contexto.html">
 * contexto de execu��o</a> mantido pela <i>framework</i>.
 *
 * @see Contexto
 */
public final class RepositorioConexoes {
    private static String MAPA_REPOSITORIOS_CONEXAO =
            RepositorioConexoes.class.getPackage().getName()+".MAPA_REPOSITORIOS_CONEXAO";
    
    private String nomeDS;
    private DataSource ds;
    private String usuario;
    private String senha;
    
    private RepositorioConexoes(String nomeDS) {
        this.nomeDS = nomeDS;
    }
    
    private static Map<String, RepositorioConexoes> getMapaRepositorios() {
        Contexto ctx = Contexto.getContextoCarregador();
        if (!ctx.itemDefinido(MAPA_REPOSITORIOS_CONEXAO)) {
            try {
                ctx.definir(MAPA_REPOSITORIOS_CONEXAO, new HashMap<String, RepositorioConexoes>());
            } catch (ErroItemContextoJaDefinido e) {
                //ErroVinculoJaDefinido pode ocorrer devido a condi��es de concorr�ncia
            }
        }
        return (Map)ctx.buscar(MAPA_REPOSITORIOS_CONEXAO);
    } 
    
    /**
     * Testa se o reposit�rio padr�o foi definido.
     *
     * @return <tt>true</tt> se o reposit�rio padr�o foi definido
     */
    public static boolean repositorioDefinido() {
        return repositorioDefinido(RepositorioConexoes.class.getName());
    }
    
    /**
     * Testa se o reposit�rio identificado por este nome foi definido.
     *
     * @param nome do reposit�rio de conex�es
     *
     * @return <tt>true</tt> se o reposit�rio foi definido
     */
    public static boolean repositorioDefinido(String nome) {
        return getMapaRepositorios().containsKey(nome);
    }
    
    /**
     * Retorna o reposit�rio de conex�es padr�o.
     *
     * @return reposit�rio de conex�es padr�o
     */
    public static RepositorioConexoes getRepositorioConexoes() {
        return getRepositorioConexoes(RepositorioConexoes.class.getName());
    }
    
    /**
     * Retorna o reposit�rio de conex�es identificado por este nome.
     *
     * @param nome do reposit�rio de conex�es
     *
     * @return reposit�rio de conex�es
     */
    public static RepositorioConexoes getRepositorioConexoes(String nome) {
        if (!repositorioDefinido(nome)) {
            throw new ErroRepositorioConexoesNaoDefinido(nome);
        }
        return getMapaRepositorios().get(nome);
    }
    
    
    
    /**
     * Define o reposit�rio padr�o, que obt�m conex�es a partir do <i>data
     * source</i> registrado no contexto JNDI com este nome.
     *
     * @param nomeDS nome do data source no contexto JNDI
     */
    public static synchronized void definirRepositorio(String nomeDS) {
        definirRepositorio(RepositorioConexoes.class.getName(), nomeDS);
    }
    
    /**
     * Define um reposit�rio com este nome, que obt�m conex�es a partir do
     * <i>data source</i> registrado no contexto JNDI com este nome.
     *
     * @param nome nome do reposit�rio de conex�es
     * @param nomeDS nome do data source no contexto JNDI
     */
    public static synchronized void definirRepositorio(String nome, String nomeDS) {
        if (repositorioDefinido(nome)) {
            throw new ErroRepositorioConexoesJaDefinido(nome);
        }
        getMapaRepositorios().put(nome, new RepositorioConexoes(nomeDS));
    }
    
    /**
     * Remove a defini��o do reposit�rio padr�o.
     */
    public static synchronized void removerRepositorio() {
        removerRepositorio(RepositorioConexoes.class.getName());
    }
    
    /**
     * Remove a defini��o do reposit�rio com este nome.
     *
     * @param nome nome do reposit�rio de conex�es
     */
    public static synchronized void removerRepositorio(String nome) {
        if (!repositorioDefinido(nome)) {
            throw new ErroRepositorioConexoesNaoDefinido(nome);
        }
        getMapaRepositorios().remove(nome);
    }
    
    /**
     * Redefine o reposit�rio padr�o para obter conex�es a partir do <i>data
     * source</i> registrado no contexto JNDI com este nome.
     *
     * @param nomeDS nome do data source no contexto JNDI
     */
    public static synchronized void redefinirRepositorio(String nomeDS) {
        redefinirRepositorio(RepositorioConexoes.class.getName(), nomeDS);
    }
    
    /**
     * Redefine o reposit�rio com este nome para obter conex�es a partir do
     * <i>data source</i> registrado no contexto JNDI com este nome.
     *
     * @param nome nome do reposit�rio de conex�es
     * @param nomeDS nome do data source no contexto JNDI
     */
    public static synchronized void redefinirRepositorio(String nome, String nomeDS) {
        getMapaRepositorios().put(nome, new RepositorioConexoes(nomeDS));
    }
    
    private DataSource getDataSource() throws NamingException {
        if (ds == null) {
            synchronized (this) {
                if (ds == null) {
                    ds = (DataSource) new InitialContext().lookup(nomeDS);
                }
            }
        }
        return ds;
    }
    
    /**
     * Retorna uma conex�o JDBC. Assume que os dados de usu�rio/senha foram
     * definidos na configura��o do <i>data source</i>.
     *
     * @return conex�o JDBC
     *
     * @throws ErroConexao se ocorrer um erro na abertura da conex�o JDBC
     */
    public Connection getConexao() throws ErroConexao {
        try {
            return getDataSource().getConnection();
        } catch (Exception e) {
            throw new ErroConexao("Erro obtendo conex�o", e);
        }
    }
    
    /**
     * Retorna uma conex�o JDBC usando este usu�rio e esta senha.
     *
     * @return conex�o JDBC
     *
     * @throws ErroConexao se ocorrer um erro na abertura da conex�o JDBC
     */
    public Connection getConexao(String usuario, String senha)
    throws ErroConexao {
        try {
            return getDataSource().getConnection(usuario, senha);
        } catch (Exception e) {
            throw new ErroConexao("Erro obtendo conex�o", e);
        }
    }
}
