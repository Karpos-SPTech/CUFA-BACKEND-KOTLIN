package cufa.conecta.com.domain.service.empresa.implementation

import cufa.conecta.com.application.dto.request.LoginDto
import cufa.conecta.com.application.dto.response.empresa.EmpresaTokenDto
import cufa.conecta.com.domain.service.empresa.EmpresaService
import cufa.conecta.com.model.data.Empresa
import cufa.conecta.com.model.data.result.EmpresaResult
import cufa.conecta.com.resources.empresa.EmpresaRepository
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(
    private val repository: EmpresaRepository
): EmpresaService {
    override fun cadastrarEmpresa(data: Empresa) = repository.cadastrarEmpresa(data)

    override fun autenticar(dadosLogin: LoginDto): EmpresaTokenDto = repository.autenticar(dadosLogin)

    override fun listarTodos(): List<EmpresaResult> = repository.listarTodos()

    override fun mostrarDados(id: Long): EmpresaResult = repository.mostrarDados(id)

    override fun atualizarDados(data: Empresa) = repository.atualizarDados(data)

    override fun atualizarBiografia(texto: String) = repository.atualizarBiografia(texto)
}