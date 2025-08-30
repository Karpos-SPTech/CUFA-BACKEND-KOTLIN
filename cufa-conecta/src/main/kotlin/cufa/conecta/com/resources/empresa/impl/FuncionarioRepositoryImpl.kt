package cufa.conecta.com.resources.empresa.impl

import cufa.conecta.com.model.data.Funcionario
import cufa.conecta.com.resources.empresa.FuncionarioRepository
import cufa.conecta.com.resources.empresa.dao.EmpresaDao
import cufa.conecta.com.resources.empresa.dao.FuncionarioDao
import cufa.conecta.com.resources.empresa.entity.FuncionarioEntity
import cufa.conecta.com.resources.empresa.exception.EmpresaNotFoundException
import org.springframework.stereotype.Repository

@Repository
class FuncionarioRepositoryImpl(
    private val dao: FuncionarioDao,
    private val empresaDao: EmpresaDao
): FuncionarioRepository {

    override fun criarFuncionario(data: Funcionario) {
        val empresa = buscarEmpresaPorId(data.empresaId!!)

        val funcionario = FuncionarioEntity(
            fkEmpresa = empresa,
            nome = data.nome,
            email = data.email,
            senha = data.senha,
            cargo = data.cargo,
        )

        dao.save(funcionario)
    }

    override fun existsByEmail(email: String): Boolean = dao.findByEmail(email).isPresent

    override fun buscarPeloEmpresaId(empresaId: Long): List<Funcionario> {
        val listaDeFuncionariosEntity = dao.findByEmpresaId(empresaId)

        return mapearFuncionarios(listaDeFuncionariosEntity)
    }

    override fun mostrarDados(id: Long): Funcionario {
        val funcionarioEntity = buscarFuncionarioPorId(id)

        val funcionario = Funcionario(
            empresaId = funcionarioEntity.fkEmpresa.idEmpresa!!,
            nome = funcionarioEntity.nome,
            email = funcionarioEntity.email,
            senha = funcionarioEntity.senha,
            cargo = funcionarioEntity.cargo,
        )

        return funcionario
    }

    override fun delete(id: Long) {
        val funcionario = buscarFuncionarioPorId(id)

        dao.delete(funcionario)
    }

    private final fun buscarFuncionarioPorId(id: Long): FuncionarioEntity =
        dao.findById(id)
            .orElseThrow { EmpresaNotFoundException("Empresa não encontrada") }

    private fun buscarEmpresaPorId(id: Long) =
        empresaDao.findById(id)
            .orElseThrow { RuntimeException("Empresa not found!") }

    private final fun mapearFuncionarios(funcionariosEntity: List<FuncionarioEntity>): List<Funcionario> {
        return funcionariosEntity.map { funcionarioEntity ->
            Funcionario(
                empresaId = funcionarioEntity.fkEmpresa.idEmpresa!!,
                nome = funcionarioEntity.nome,
                email = funcionarioEntity.email,
                senha = funcionarioEntity.senha,
                cargo = funcionarioEntity.cargo,
            )
        }
    }
}