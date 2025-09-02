package cufa.conecta.com.domain.service.usuario.implementation

import cufa.conecta.com.domain.service.usuario.ExperienciaService
import cufa.conecta.com.model.data.Experiencia
import cufa.conecta.com.resources.usuario.ExperienciaRepository
import org.springframework.stereotype.Service

@Service
class ExperienciaServiceImpl(
    private val repository: ExperienciaRepository
): ExperienciaService {
    override fun criarExperiencia(data: Experiencia) = repository.criarExperiencia(data)

    override fun listarPorUsuario(id: Long): List<Experiencia> = repository.listarPorUsuario(id)

    override fun atualizar(data: Experiencia) = repository.atualizar(data)

    override fun deletarExperiencia(id: Long) = repository.deletarExperiencia(id)
}