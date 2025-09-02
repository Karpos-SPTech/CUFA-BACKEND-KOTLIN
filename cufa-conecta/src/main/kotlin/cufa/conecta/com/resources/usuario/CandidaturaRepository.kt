package cufa.conecta.com.resources.usuario

import cufa.conecta.com.model.data.Candidatura
import cufa.conecta.com.model.data.Publicacao
import cufa.conecta.com.model.data.result.CandidaturaResult

interface CandidaturaRepository {
    fun criarCandidatura(data: Candidatura)
    fun listarDadosDaVaga(id: Long): CandidaturaResult
    fun verificarCandidaturaExistente(userId: Long, vagaId: Long): Boolean
    fun listarPublicacoesCandidatadasPorUsuario(id: Long): List<Publicacao>
}