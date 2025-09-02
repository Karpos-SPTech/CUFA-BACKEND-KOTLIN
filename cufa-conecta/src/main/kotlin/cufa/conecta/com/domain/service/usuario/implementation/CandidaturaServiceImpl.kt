package cufa.conecta.com.domain.service.usuario.implementation

import cufa.conecta.com.domain.service.usuario.CandidaturaService
import cufa.conecta.com.model.data.Candidatura
import cufa.conecta.com.model.data.Publicacao
import cufa.conecta.com.model.data.result.CandidaturaResult
import cufa.conecta.com.resources.usuario.CandidaturaRepository
import org.springframework.stereotype.Service

@Service
class CandidaturaServiceImpl(
    private val repository: CandidaturaRepository
): CandidaturaService {
    override fun criarCandidatura(data: Candidatura) = repository.criarCandidatura(data)

    override fun listarDadosDaVaga(id: Long): CandidaturaResult = repository.listarDadosDaVaga(id)

    override fun verificarCandidaturaExistente(userId: Long, vagaId: Long): Boolean =
        repository.verificarCandidaturaExistente(userId, vagaId )

    override fun listarPublicacoesCandidatadasPorUsuario(id: Long): List<Publicacao> =
        repository.listarPublicacoesCandidatadasPorUsuario(id)
}