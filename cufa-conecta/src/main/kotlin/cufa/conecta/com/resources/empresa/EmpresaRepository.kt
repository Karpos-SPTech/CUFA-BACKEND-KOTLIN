package cufa.conecta.com.resources.empresa

import cufa.conecta.com.application.dto.response.empresa.EmpresaTokenDto
import cufa.conecta.com.model.data.Empresa
import cufa.conecta.com.resources.empresa.entity.EmpresaEntity

interface EmpresaRepository {
    fun cadastrarEmpresa(data: Empresa)
    fun autenticar(dadosLogin: Empresa): EmpresaTokenDto
    fun listarTodos(): List<EmpresaEntity>
    fun buscarPorId(id: Long): EmpresaEntity
    fun atualizarDados(data: Empresa)
    fun atualizarBiografia(texto: String)
}