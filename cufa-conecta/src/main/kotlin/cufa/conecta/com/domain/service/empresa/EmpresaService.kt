package cufa.conecta.com.domain.service.empresa

import cufa.conecta.com.application.dto.request.LoginDto
import cufa.conecta.com.application.dto.response.empresa.EmpresaTokenDto
import cufa.conecta.com.model.data.Empresa
import cufa.conecta.com.model.data.result.EmpresaResult

interface EmpresaService {
    fun cadastrarEmpresa(data: Empresa)
    fun autenticar(dadosLogin: LoginDto): EmpresaTokenDto
    fun listarTodos(): List<EmpresaResult>
    fun mostrarDados(id: Long): EmpresaResult
    fun atualizarDados(data: Empresa)
    fun atualizarBiografia(texto: String)
}