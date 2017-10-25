package fsiscad.util;

import fsiscad.contexto.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

/**
 * Repositório de conexões para uma base de dados. As conexões são obtidas
 * através de um objeto <tt>DataSource</tt>, recuperado do contexto JNDI a
 * partir da chave fornecida no momento da instanciação do repositório. Esta
 * classe não implementa um mecanismo de <b><i>pool</i> de conexões</b>
 * propriamente dito, pois assume que este recurso seja suportado pelo
 * container J2EE sendo utilizado. Ou seja, qualquer configuração de
 * <i>pool</i> de conexões deve ser feita no momento da configuração do
 * <i>data source</i> no container. Diversos repositórios de conexões podem
 * ser criados simultaneamente, sendo cada um associado a um nome. Pode ser definido
 * também um repositório padrão, que não precisa de um nome para ser recuperado.
 * Os repositórios são guardados no <a href="../contexto/Contexto.html">
 * contexto de execução</a> mantido pela <i>framework</i>.
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
                //ErroVinculoJaDefinido pode ocorrer devido a condições de concorrência
            }
        }
        return (Map)ctx.buscar(MAPA_REPOSITORIOS_CONEXAO);
    } 
    
    /**
     * Testa se o repositório padrão foi definido.
     *
     * @return <tt>true</tt> se o repositório padrão foi definido
     */
    public static boolean repositorioDefinido() {
        return repositorioDefinido(RepositorioConexoes.class.getName());
    }
    
    /**
     * Testa se o repositório identificado por este nome foi definido.
     *
     * @param nome do repositório de conexões
     *
     * @return <tt>true</tt> se o repositório foi definido
     */
    public static boolean repositorioDefinido(String nome) {
        return getMapaRepositorios().containsKey(nome);
    }
    
    /**
     * Retorna o repositório de conexões padrão.
     *
     * @return repositório de conexões padrão
     */
    public static RepositorioConexoes getRepositorioConexoes() {
        return getRepositorioConexoes(RepositorioConexoes.class.getName());
    }
    
    /**
     * Retorna o repositório de conexões identificado por este nome.
     *
     * @param nome do repositório de conexões
     *
     * @return repositório de conexões
     */
    public static RepositorioConexoes getRepositorioConexoes(String nome) {
        if (!repositorioDefinido(nome)) {
            throw new ErroRepositorioConexoesNaoDefinido(nome);
        }
        return getMapaRepositorios().get(nome);
    }
    
    
    
    /**
     * Define o repositório padrão, que obtém conexões a partir do <i>data
     * source</i> registrado no contexto JNDI com este nome.
     *
     * @param nomeDS nome do data source no contexto JNDI
     */
    public static synchronized void definirRepositorio(String nomeDS) {
        definirRepositorio(RepositorioConexoes.class.getName(), nomeDS);
    }
    
    /**
     * Define um repositório com este nome, que obtém conexões a partir do
     * <i>data source</i> registrado no contexto JNDI com este nome.
     *
     * @param nome nome do repositório de conexões
     * @param nomeDS nome do data source no contexto JNDI
     */
    public static synchronized void definirRepositorio(String nome, String nomeDS) {
        if (repositorioDefinido(nome)) {
            throw new ErroRepositorioConexoesJaDefinido(nome);
        }
        getMapaRepositorios().put(nome, new RepositorioConexoes(nomeDS));
    }
    
    /**
     * Remove a definição do repositório padrão.
     */
    public static synchronized void removerRepositorio() {
        removerRepositorio(RepositorioConexoes.class.getName());
    }
    
    /**
     * Remove a definição do repositório com este nome.
     *
     * @param nome nome do repositório de conexões
     */
    public static synchronized void removerRepositorio(String nome) {
        if (!repositorioDefinido(nome)) {
            throw new ErroRepositorioConexoesNaoDefinido(nome);
        }
        getMapaRepositorios().remove(nome);
    }
    
    /**
     * Redefine o repositório padrão para obter conexões a partir do <i>data
     * source</i> registrado no contexto JNDI com este nome.
     *
     * @param nomeDS nome do data source no contexto JNDI
     */
    public static synchronized void redefinirRepositorio(String nomeDS) {
        redefinirRepositorio(RepositorioConexoes.class.getName(), nomeDS);
    }
    
    /**
     * Redefine o repositório com este nome para obter conexões a partir do
     * <i>data source</i> registrado no contexto JNDI com este nome.
     *
     * @param nome nome do repositório de conexões
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
     * Retorna uma conexão JDBC. Assume que os dados de usuário/senha foram
     * definidos na configuração do <i>data source</i>.
     *
     * @return conexão JDBC
     *
     * @throws ErroConexao se ocorrer um erro na abertura da conexão JDBC
     */
    public Connection getConexao() throws ErroConexao {
        try {
            return getDataSource().getConnection();
        } catch (Exception e) {
            throw new ErroConexao("Erro obtendo conexão", e);
        }
    }
    
    /**
     * Retorna uma conexão JDBC usando este usuário e esta senha.
     *
     * @return conexão JDBC
     *
     * @throws ErroConexao se ocorrer um erro na abertura da conexão JDBC
     */
    public Connection getConexao(String usuario, String senha)
    throws ErroConexao {
        try {
            return getDataSource().getConnection(usuario, senha);
        } catch (Exception e) {
            throw new ErroConexao("Erro obtendo conexão", e);
        }
    }
}
