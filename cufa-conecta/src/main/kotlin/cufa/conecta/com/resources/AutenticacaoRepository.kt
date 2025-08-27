package cufa.conecta.com.resources

import cufa.conecta.com.resources.empresa.dao.EmpresaDao
import cufa.conecta.com.resources.empresa.dao.FuncionarioDao
import cufa.conecta.com.resources.usuario.dao.UsuarioDao
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class AutenticacaoRepository(
    private val usuarioDao: UsuarioDao,
    private val empresaDao: EmpresaDao,
    private val funcionarioDao: FuncionarioDao
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return funcionarioDao.findByEmail(username).orElse(null)?.let {
            User(it.email, it.senha, emptyList())
        } ?: usuarioDao.findByEmail(username).orElse(null)?.let {
            User(it.email, it.senha, emptyList())
        } ?: empresaDao.findByEmail(username).orElse(null)?.let {
            User(it.email, it.senha, emptyList())
        } ?: throw UsuarioNotFoundException("Usuário ou empresa com este e-mail não foi encontrado.")
    }
}