package cufa.conecta.com.domain.service.usuario

import cufa.conecta.com.model.data.Experiencia

interface ExperienciaService {
    fun criarExperiencia(data: Experiencia)
    fun listarPorUsuario(id: Long): List<Experiencia>
    fun atualizar(data: Experiencia)
    fun deletarExperiencia(id: Long)
}