package cufa.conecta.com.domain.service.empresa

import cufa.conecta.com.application.dto.response.empresa.EmpresaTokenDto
import cufa.conecta.com.model.data.Empresa
import cufa.conecta.com.model.data.result.EmpresaResult
import cufa.conecta.com.resources.empresa.entity.EmpresaEntity

interface EmpresaService {
    fun cadastrarEmpresa(data: Empresa)
    fun autenticar(dadosLogin: Empresa): EmpresaTokenDto
    fun listarTodos(): List<EmpresaEntity>
    fun mostrarDados(id: Long): EmpresaResult
    fun atualizarDados(data: Empresa)
    fun atualizarBiografia(texto: String)
}