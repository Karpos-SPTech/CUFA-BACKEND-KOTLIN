package cufa.conecta.com.resources.usuario

import cufa.conecta.com.model.data.Experiencia
import cufa.conecta.com.resources.usuario.entity.ExperienciaEntity

interface ExperienciaRepository {
    fun criarExperiencia(data: Experiencia)
    fun listarPorUsuario(id: Long): List<Experiencia>
    fun atualizar(data: Experiencia)
    fun deletarExperiencia(id: Long)
}