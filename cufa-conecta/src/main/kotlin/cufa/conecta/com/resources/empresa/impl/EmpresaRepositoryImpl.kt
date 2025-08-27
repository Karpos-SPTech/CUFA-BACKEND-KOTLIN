package cufa.conecta.com.resources.empresa.impl

import cufa.conecta.com.application.dto.response.empresa.EmpresaTokenDto
import cufa.conecta.com.config.GerenciadorTokenJwt
import cufa.conecta.com.model.data.Empresa
import cufa.conecta.com.resources.empresa.EmpresaRepository
import cufa.conecta.com.resources.empresa.dao.EmpresaDao
import cufa.conecta.com.resources.empresa.entity.EmpresaEntity
import cufa.conecta.com.resources.empresa.exception.EmailAlreadyExistsException
import cufa.conecta.com.resources.empresa.exception.EmpresaNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDate
import java.time.LocalDateTime

class EmpresaRepositoryImpl (
    private val gerenciadorTokenJwt : GerenciadorTokenJwt,
    private val authenticationManager : AuthenticationManager,
    private val empresaDao: EmpresaDao
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

        empresaDao.save(empresaEntity)
    }

    override fun autenticar(dadosLogin: Empresa): EmpresaTokenDto {
        val email = dadosLogin.email
        val senha = dadosLogin.senha

        validarEmpresa(email, senha)

        val empresaAutenticada = buscarEmpresaPorEmail(email)

        val idEmpresa = empresaAutenticada.idEmpresa
        val emailEmpresa = empresaAutenticada.email
        val nomeEmpresa = empresaAutenticada.nome

        val dadosAutenticados = UsernamePasswordAuthenticationToken(email, senha)

        SecurityContextHolder.getContext().authentication = dadosAutenticados

        val tokenJwt = gerenciadorTokenJwt.generateToken(dadosAutenticados)

        return EmpresaTokenDto(
            idEmpresa!!,
            emailEmpresa,
            nomeEmpresa,
            tokenJwt
        )
    }

    override fun listarTodos(): List<EmpresaEntity> = empresaDao.findAll()

    override fun buscarPorId(id: Long): EmpresaEntity = buscarEmpresaPorId(id)

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

        empresaDao.save(empresaAtualizada)
    }

    override fun atualizarBiografia(texto: String) = empresaDao.atualizarBiografia(texto)


    private fun validarEmpresa(email: String, senha: String) {
        val autenticacao: Authentication = UsernamePasswordAuthenticationToken(email, senha)

        authenticationManager.authenticate(autenticacao)

        SecurityContextHolder.getContext().authentication = autenticacao
    }

    private fun buscarEmpresaPorEmail(email: String): EmpresaEntity =
        empresaDao.findByEmail(email)
            .orElseThrow { EmpresaNotFoundException("Email do usuário não encontrado") }


    private fun  validarEmailExistente(email: String) {
        if (empresaDao.existsByEmail(email)) {
            throw EmailAlreadyExistsException("O email inserido já existe!!")
        }
    }

    private fun buscarEmpresaPorId(id: Long): EmpresaEntity =
        empresaDao.findById(id)
            .orElseThrow { EmpresaNotFoundException("Empresa não encontrada") }
}
