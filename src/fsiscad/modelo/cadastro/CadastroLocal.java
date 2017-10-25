package fsiscad.modelo.cadastro;

import fsiscad.modelo.ot.*;
import java.sql.*;

import java.util.*;

/**
 * Cadastro implementado como uma classe Java convencional (<i>pattern Data
 * Access Object</i>). As implementa��es dos m�todos da interface
 * <tt>Cadastro</tt> chamam os m�todos correspondentes desta classe que
 * aceitam a conex�o como um par�metro adicional. A conex�o passada � obtida
 * atrav�s de uma chamada a {@link CadastroLocal#getConexao getConexao},
 * cuja implementa��o tem de ser definida pelas subclasses de
 * <tt>CadastroLocal</tt>. Os m�todos <tt>incluir(usuario, ot)</tt>,
 * <tt>alterar(usuario, ot)</tt> e <tt>excluir(usuario, ot)</tt> j� executam o
 * <i>commit</i> em cima da conex�o ap�s a execu��o do m�todo correspondente
 * que foi chamado, ou o <i>rollback</i>, caso o m�todo chamado gere alguma
 * exce��o, e fecham a conex�o. Portanto, os m�todos <tt>incluir(conexao, usuario,
 * ot)</tt>, <tt>alterar(conexao, usuario, ot)</tt> e <tt>excluir(conexao, usuario,
 * ot)</tt> implementados nas subclasses n�o precisam fazer <i>commit</i>,
 * fechar a conex�o, e, normalmente, s� far�o <i>rollback</i> se uma condi��o
 * de erro l�gica for encontrada.
 */
public abstract class CadastroLocal extends CadastroImpl {
    private String modelo;
    
    public final void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public final String getModelo() {
        return modelo;
    }
    
    public final Collection<OT> consultarTodos(OT usuario, OT molde)
    throws ErroCadastro {
        Connection conexao = null;
        try {
            conexao = getConexao(usuario);
            return consultarTodos(conexao, usuario, molde);
        } catch (ErroCadastro ec) {
            throw ec;
        } catch (Exception e) {
            throw new ErroCadastro("Erro consultando itens do modelo '"+getModelo()+"'", e);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException se) {}
            }
        }
    }
    
    public final OT consultarPorChavePrimaria(OT usuario, OT chave, OT molde)
    throws ErroCadastro {
        Connection conexao = null;
        try {
            conexao = getConexao(usuario);
            return consultarPorChavePrimaria(conexao, usuario, chave, molde);
        } catch (ErroCadastro ec) {
            throw ec;
        } catch (Exception e) {
            throw new ErroCadastro("Erro consultando item do modelo '" +
                    getModelo() + "' por chave prim�ria", e);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException se) {}
            }
        }
    }
    
    public final OT incluir(OT usuario, OT ot) 
    throws ErroCadastro {
        Connection conexao = null;
        try {
            conexao = getConexao(usuario);
            OT res = incluir(conexao, usuario, ot);
            conexao.commit();
            return res;
        } catch (ErroCadastro ec) {
            try {
                conexao.rollback();
            } catch (SQLException se) {}
            throw ec;
        } catch (Exception e) {
            try {
                conexao.rollback();
            } catch (SQLException se) {}
            throw new ErroCadastro("Erro incluindo item do modelo '"+getModelo()+"'", e);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException se) {}
            }
        }
    }
    
    public final OT alterar(OT usuario, OT ot)
    throws ErroCadastro {
        Connection conexao = null;
        try {
            conexao = getConexao(usuario);
            OT res = alterar(conexao, usuario, ot);
            conexao.commit();
            return res;
        } catch (ErroCadastro ec) {
            try {
                conexao.rollback();
            } catch (SQLException se) {}
            throw ec;
        } catch (Exception e) {
            try {
                conexao.rollback();
            } catch (SQLException se) {}
            throw new ErroCadastro("Erro alterando item do modelo '"+getModelo()+"'", e);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException se) {}
            }
        }
    }
    
    public final void excluir(OT usuario, OT ot)
    throws ErroCadastro {
        Connection conexao = null;
        try {
            conexao = getConexao(usuario);
            excluir(conexao, usuario, ot);
            conexao.commit();
        } catch (ErroCadastro ec) {
            try {
                conexao.rollback();
            } catch (SQLException se) {}
            throw ec;
        } catch (Exception e) {
            try {
                conexao.rollback();
            } catch (SQLException se) {}
            throw new ErroCadastro("Erro excluindo item do modelo '"+getModelo()+"'", e);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException se) {}
            }
        }
    }
    
    /**
     * Chamado por {@link
     * CadastroLocal#consultarTodos(fsiscad.modelo.ot.OT,fsiscad.modelo.ot.OT) consultarTodos}.
     */
    public Collection<OT> consultarTodos(Connection conexao, OT usuario, OT molde)
    throws Exception {
        throw new UnsupportedOperationException("consultarTodos");
    }
    
    /**
     * Chamado por {@link
     * CadastroLocal#consultarPorChavePrimaria(fsiscad.modelo.ot.OT,
     * fsiscad.modelo.ot.OT,fsiscad.modelo.ot.OT) consultarPorChavePrimaria}.
     */
    public OT consultarPorChavePrimaria(Connection conexao, OT usuario, OT chave, OT molde) 
    throws Exception {
        throw new UnsupportedOperationException("consultarPorChavePrimaria");
    }
    
    /**
     * Chamado por {@link CadastroLocal#incluir(fsiscad.modelo.ot.OT,
     * fsiscad.modelo.ot.OT) incluir}.
     */
    public OT incluir(Connection conexao, OT usuario, OT ot)
    throws Exception {
        throw new UnsupportedOperationException("incluir");
    }
    
    /**
     * Chamado por {@link CadastroLocal#alterar(fsiscad.modelo.ot.OT,
     * fsiscad.modelo.ot.OT) alterar}.
     */
    public OT alterar(Connection conexao, OT usuario, OT ot)
    throws Exception {
        throw new UnsupportedOperationException("alterar");
    }
    
    /**
     * Chamado por {@link CadastroLocal#excluir(fsiscad.modelo.ot.OT,
     * fsiscad.modelo.ot.OT) excluir}.
     */
    public void excluir(Connection conexao, OT usuario, OT ot)
    throws Exception {
        throw new UnsupportedOperationException("excluir");
    }
    
    /**
     * Retorna a conex�o JDBC a ser usada em uma opera��o do cadastro.
     */
    protected abstract Connection getConexao(OT usuario)
    throws Exception;
}
