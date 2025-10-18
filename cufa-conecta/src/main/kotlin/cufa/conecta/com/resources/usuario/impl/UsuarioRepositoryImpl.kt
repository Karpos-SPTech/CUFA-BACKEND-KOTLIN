package cufa.conecta.com.resources.usuario.impl

import cufa.conecta.com.application.dto.request.LoginDto
import cufa.conecta.com.application.dto.response.usuario.UsuarioTokenDto
import cufa.conecta.com.config.GerenciadorTokenJwt
import cufa.conecta.com.model.data.Usuario
import cufa.conecta.com.model.data.result.UsuarioResult
import cufa.conecta.com.resources.empresa.exception.EmailExistenteException
import cufa.conecta.com.resources.empresa.exception.EmpresaNotFoundException
import cufa.conecta.com.resources.usuario.UsuarioRepository
import cufa.conecta.com.resources.usuario.dao.UsuarioDao
import cufa.conecta.com.resources.usuario.entity.UsuarioEntity
import cufa.conecta.com.resources.usuario.exception.UpdateCurriculoException
import cufa.conecta.com.resources.usuario.exception.UsuarioExistenteException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.Period

@Repository
class UsuarioRepositoryImpl(
    private val dao: UsuarioDao,
    private val gerenciadorTokenJwt: GerenciadorTokenJwt,
    private val authenticationManager: AuthenticationManager,
): UsuarioRepository {
    override fun cadastrarUsuario(data: Usuario) {
        validarEmailExistente(data.email!!)

        val usuario = UsuarioEntity(
            nome = data.nome,
            email = data.email,
            senha = data.senha,
            cpf = data.cpf,
            telefone = data.telefone,
            escolaridade = data.escolaridade,
            dtNascimento = data.dtNascimento,
            estadoCivil = data.estadoCivil,
            estado = data.estado,
            cidade = data.cidade,
            biografia = data.biografia,
            curriculoUrl = data.curriculoUrl
        )

        dao.save(usuario)
    }

    override fun autenticar(data: LoginDto): UsuarioTokenDto {
        authenticateCredentials(data.email, data.senha)

        val usuarioAutenticado = buscarUsuarioPorEmail(data.email)

        val authentication = UsernamePasswordAuthenticationToken(data.email, data.senha)

        SecurityContextHolder.getContext().authentication = authentication

        val token = gerenciadorTokenJwt.generateToken(authentication)

        return UsuarioTokenDto(
            nome = usuarioAutenticado.nome!!,
            email = usuarioAutenticado.email!!,
            token = token
        )
    }

    override fun mostrarDados(id: Long): UsuarioResult {
        val usuarioEntity = buscarUsuarioPeloId(id)

        return mapearUsuario(usuarioEntity)
    }

    override fun atualizar(data: Usuario) {
        val usuarioExistente = buscarUsuarioPeloId(data.id!!)

        val novoUsuario = UsuarioEntity(
            nome = data.nome,
            email = data.email,
            senha = usuarioExistente.senha,
            cpf = data.cpf,
            telefone = data.telefone,
            escolaridade = data.escolaridade,
            dtNascimento = data.dtNascimento,
            estadoCivil = data.estadoCivil,
            estado = data.estado,
            cidade = data.cidade,
            biografia = data.biografia
        )

        dao.save(novoUsuario)
    }

    override fun atualizarCurriculoUrl(userId: Long, curriculoUrl: String?) {
        runCatching {
            dao.atualizarCurriculoUrl(userId, curriculoUrl)
        }.getOrElse {
            throw UpdateCurriculoException("Falha ao atualizar o curriculo!!")
        }
    }

    private fun buscarUsuarioPeloId(id: Long): UsuarioEntity =
        dao.findById(id)
            .orElseThrow { throw UsuarioExistenteException("O usuario inserido já existe!!") }

    private fun validarEmailExistente(email: String) {
        if (dao.existsByEmail(email)) {
            throw EmailExistenteException("O email inserido já existe!!")
        }
    }

    private fun authenticateCredentials(email: String, password: String) {
        val credentials: Authentication = UsernamePasswordAuthenticationToken(email, password)

        authenticationManager.authenticate(credentials)

        SecurityContextHolder.getContext().setAuthentication(credentials)
    }

    private fun buscarUsuarioPorEmail(email: String) = dao.findByEmail(email)
        .orElseThrow { EmpresaNotFoundException("Email do usuário não encontrado") }

    private fun mapearUsuario(usuarioEntity: UsuarioEntity): UsuarioResult {
            val dtNascUsuario = usuarioEntity.dtNascimento
            val idade = definirIdadeDoUsuario(dtNascUsuario!!)

            val usuario = UsuarioResult(
                nome = usuarioEntity.nome!!,
                email = usuarioEntity.email!!,
                cpf = usuarioEntity.cpf!!,
                telefone = usuarioEntity.telefone!!,
                escolaridade = usuarioEntity.escolaridade!!,
                idade = idade,
                estadoCivil = usuarioEntity.estadoCivil!!,
                estado = usuarioEntity.estado!!,
                cidade = usuarioEntity.cidade!!,
                biografia = usuarioEntity.biografia!!,
                curriculoUrl = usuarioEntity.curriculoUrl!!
            )

        return usuario
    }

    private fun definirIdadeDoUsuario(dtNasc: LocalDate) = Period.between(dtNasc, LocalDate.now()).years
}