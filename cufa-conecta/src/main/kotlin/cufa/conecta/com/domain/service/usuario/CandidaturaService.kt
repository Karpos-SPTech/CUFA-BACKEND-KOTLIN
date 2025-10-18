package cufa.conecta.com.domain.service.usuario

import cufa.conecta.com.model.data.Candidatura
import cufa.conecta.com.model.data.Publicacao
import cufa.conecta.com.model.data.result.CandidaturaResult

interface CandidaturaService {
    fun criarCandidatura(data: Candidatura)
    fun listarDadosDaVaga(id: Long, page: Int, size: Int): CandidaturaResult
    fun verificarCandidaturaExistente(userId: Long, vagaId: Long): Boolean
    fun listarPublicacoesCandidatadasPorUsuario(id: Long): List<Publicacao>
}