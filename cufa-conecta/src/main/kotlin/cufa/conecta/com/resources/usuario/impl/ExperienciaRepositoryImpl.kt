package cufa.conecta.com.resources.usuario.impl

import cufa.conecta.com.model.data.Experiencia
import cufa.conecta.com.resources.usuario.ExperienciaRepository
import cufa.conecta.com.resources.usuario.dao.ExperienciaDao
import cufa.conecta.com.resources.usuario.entity.ExperienciaEntity
import org.springframework.stereotype.Repository

@Repository
class ExperienciaRepositoryImpl(
    private val dao: ExperienciaDao
): ExperienciaRepository {
    override fun criarExperiencia(data: Experiencia) {
        val experienciaEntity = ExperienciaEntity(
            usuarioId = data.usuarioId,
            cargo = data.cargo,
            empresa = data.empresa,
            dtInicio = data.dtInicio,
            dtFim = data.dtFim
        )

        dao.save(experienciaEntity)
    }

    override fun listarPorUsuario(id: Long): List<Experiencia> {
        val listaDeExperienciasEntity = dao.findByUsuarioId(id)

        val listaDeExperiencias = mutableListOf<Experiencia>()

        for (experienciaEntity in listaDeExperienciasEntity) {
            val experiencia = Experiencia(
                cargo = experienciaEntity.cargo,
                empresa = experienciaEntity.empresa,
                dtInicio = experienciaEntity.dtInicio,
                dtFim = experienciaEntity.dtFim
            )

            listaDeExperiencias.add(experiencia)
        }

        return listaDeExperiencias
    }

    override fun atualizar(data: Experiencia) {
        buscarExperienciaPeloId(data.id!!)

        val novaExperiencia = ExperienciaEntity(
            cargo = data.cargo,
            empresa = data.empresa,
            dtInicio = data.dtInicio,
            dtFim = data.dtFim
        )

        dao.save(novaExperiencia)
    }

    override fun deletarExperiencia(id: Long) {
        val experiencia = buscarExperienciaPeloId(id)

        dao.delete(experiencia)
    }

    private fun buscarExperienciaPeloId(id: Long): ExperienciaEntity =
        dao.findById(id)
            .orElseThrow { RuntimeException("Experiencia pelo ID: $id") }
}