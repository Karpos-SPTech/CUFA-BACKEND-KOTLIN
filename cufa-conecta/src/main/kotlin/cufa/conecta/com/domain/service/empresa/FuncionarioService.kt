package cufa.conecta.com.domain.service.empresa

import cufa.conecta.com.model.data.Funcionario

interface FuncionarioService {
    fun criarFuncionario(data: Funcionario)
    fun existsByEmail(email: String): Boolean
    fun buscarPeloEmpresaId(empresaId: Long): List<Funcionario>
    fun mostrarDados(id: Long): Funcionario
    fun delete(id: Long)
}