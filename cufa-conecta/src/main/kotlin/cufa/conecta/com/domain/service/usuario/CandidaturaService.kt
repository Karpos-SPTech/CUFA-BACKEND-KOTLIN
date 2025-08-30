package cufa.conecta.com.domain.service.usuario

import cufa.conecta.com.model.data.Candidatura
import cufa.conecta.com.model.data.result.CandidaturaResult
import cufa.conecta.com.model.data.result.PublicacaoResult

interface CandidaturaService {
    fun criarCandidatura(data: Candidatura)
    fun listarDadosDaVaga(id: Long): CandidaturaResult
    fun verificarCandidaturaExistente(userId: Long, vagaId: Long): Boolean
    fun listarPublicacoesCandidatadasPorUsuario(id: Long): List<PublicacaoResult>
}