package cufa.conecta.com.domain.service.usuario.implementation

import cufa.conecta.com.domain.service.usuario.CandidaturaService
import cufa.conecta.com.model.data.Candidatura
import cufa.conecta.com.model.data.result.CandidaturaResult
import cufa.conecta.com.model.data.result.PublicacaoResult
import cufa.conecta.com.resources.usuario.CandidaturaRepository

class CandidaturaServiceImpl(
    private val repository: CandidaturaRepository
): CandidaturaService {
    override fun criarCandidatura(data: Candidatura) = repository.criarCandidatura(data)

    override fun listarDadosDaVaga(id: Long): CandidaturaResult = repository.listarDadosDaVaga(id)

    override fun verificarCandidaturaExistente(userId: Long, vagaId: Long): Boolean =
        repository.verificarCandidaturaExistente(userId, vagaId )

    override fun listarPublicacoesCandidatadasPorUsuario(id: Long): List<PublicacaoResult> =
        repository.listarPublicacoesCandidatadasPorUsuario(id)
}