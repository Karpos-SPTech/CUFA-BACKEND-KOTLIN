package cufa.conecta.com.resources.empresa

import cufa.conecta.com.model.data.Funcionario

interface FuncionarioRepository {
    fun criarFuncionario(data: Funcionario)
    fun existsByEmail(email: String): Boolean
    fun findByEmpresaId(empresaId: Long): List<Funcionario>
    fun buscarPorId(id: Long): Funcionario
    fun delete(id: Long)
}