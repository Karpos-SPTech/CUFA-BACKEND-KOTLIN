package cufa.conecta.com.domain.service.empresa.implementation

import cufa.conecta.com.domain.service.empresa.FuncionarioService
import cufa.conecta.com.model.data.Funcionario
import cufa.conecta.com.resources.empresa.FuncionarioRepository
import org.springframework.stereotype.Service

@Service
class FuncionarioServiceImpl(
    private val repository: FuncionarioRepository
): FuncionarioService {

    override fun criarFuncionario(data: Funcionario) = repository.criarFuncionario(data)

    override fun existsByEmail(email: String): Boolean = repository.existsByEmail(email)

    override fun buscarPeloEmpresaId(empresaId: Long): List<Funcionario> = repository.buscarPeloEmpresaId(empresaId)

    override fun mostrarDados(id: Long): Funcionario = repository.mostrarDados(id)

    override fun deletar(id: Long) = repository.delete(id)
}