package cufa.conecta.com.resources.empresa.impl

import cufa.conecta.com.application.dto.request.LoginDto
import cufa.conecta.com.application.dto.response.empresa.EmpresaTokenDto
import cufa.conecta.com.config.GerenciadorTokenJwt
import cufa.conecta.com.model.data.Empresa
import cufa.conecta.com.model.data.result.EmpresaResult
import cufa.conecta.com.resources.empresa.EmpresaRepository
import cufa.conecta.com.resources.empresa.dao.EmpresaDao
import cufa.conecta.com.resources.empresa.entity.EmpresaEntity
import cufa.conecta.com.resources.empresa.exception.EmailExistenteException
import cufa.conecta.com.resources.empresa.exception.EmpresaNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class EmpresaRepositoryImpl (
    private val dao: EmpresaDao,
    private val gerenciadorTokenJwt : GerenciadorTokenJwt,
    private val authenticationManager : AuthenticationManager
): EmpresaRepository {

    override fun cadastrarEmpresa(data: Empresa) {

        val email = data.email
        validarEmailExistente(email)

        val empresaEntity = EmpresaEntity(
            nome = data.nome,
            email = email,
            senha = data.senha,
            cep = data.cep,
            numero = data.numero,
            endereco = data.endereco,
            cnpj = data.cnpj,
            area = data.area,
            dtCadastro = LocalDate.now()
        )

        dao.save(empresaEntity)
    }

    override fun autenticar(dadosLogin: LoginDto): EmpresaTokenDto {
        val email = dadosLogin.email
        val senha = dadosLogin.senha

        validarEmpresa(email, senha)

        val empresaAutenticada = buscarEmpresaPorEmail(email)

        val dadosAutenticados = UsernamePasswordAuthenticationToken(
            empresaAutenticada,
            null,
            emptyList()
        )

        SecurityContextHolder.getContext().authentication = dadosAutenticados

        val tokenJwt = gerenciadorTokenJwt.generateToken(dadosAutenticados)

        return EmpresaTokenDto(
            empresaAutenticada.email,
            empresaAutenticada.nome,
            tokenJwt
        )
    }

    override fun listarTodos(): List<EmpresaResult> {
        val listaDeEmpresasEntity = dao.findAll()

        return mapearEmpresas(listaDeEmpresasEntity)
    }

    override fun mostrarDados(id: Long): EmpresaResult {
        val empresaEntity = buscarEmpresaPorId(id)

        return mapearEmpresa(empresaEntity)
    }

    override fun atualizarDados(data: Empresa) {
        buscarEmpresaPorId(data.id!!)

        validarEmailExistente(data.email)

        val empresaAtualizada = EmpresaEntity(
            nome = data.nome,
            email = data.email,
            senha = data.senha,
            cep = data.cep,
            numero = data.numero,
            endereco = data.endereco,
            cnpj = data.cnpj,
            area = data.area
        )

        dao.save(empresaAtualizada)
    }

    override fun atualizarBiografia(texto: String) = dao.atualizarBiografia(texto)


    private fun validarEmpresa(email: String, senha: String) {
        val authRequest = UsernamePasswordAuthenticationToken(email, senha)

        val authResult = authenticationManager.authenticate(authRequest)

        SecurityContextHolder.getContext().authentication = authResult
    }

    private fun buscarEmpresaPorEmail(email: String): EmpresaEntity =
        dao.findByEmail(email)
            .orElseThrow { EmpresaNotFoundException("Email do usuário não encontrado") }


    private fun  validarEmailExistente(email: String) {
        if (dao.existsByEmail(email)) {
            throw EmailExistenteException("O email inserido já existe!!")
        }
    }

    private fun buscarEmpresaPorId(id: Long): EmpresaEntity =
        dao.findById(id)
            .orElseThrow { EmpresaNotFoundException("Empresa não encontrada") }

    private fun mapearEmpresa(entity: EmpresaEntity): EmpresaResult {
        val empresa = EmpresaResult(
            nome = entity.nome,
            email = entity.email,
            cep = entity.cep,
            endereco = entity.endereco,
            numero = entity.numero,
            cnpj = entity.cnpj,
            area = entity.area,
            biografia = entity.biografia!!
        )

        return empresa
    }

    private fun mapearEmpresas(empresasEntity: List<EmpresaEntity>): List<EmpresaResult> {
        return empresasEntity.map { entity ->
            EmpresaResult(
                nome = entity.nome,
                email = entity.email,
                cep = entity.cep,
                endereco = entity.endereco,
                numero = entity.numero,
                cnpj = entity.cnpj,
                area = entity.area,
                biografia = entity.biografia!!
            )
        }
    }
}
