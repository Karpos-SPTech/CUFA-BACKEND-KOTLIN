package cufa.conecta.com.domain.service.usuario.implementation

import cufa.conecta.com.application.exception.InvalidPageNumberException
import cufa.conecta.com.application.exception.InvalidSizeNumberException
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

    override fun listarDadosDaVaga(id: Long, page: Int, size: Int): CandidaturaResult {
        validatePageAndSize(page, size)

        return repository.listarDadosDaVaga(id, page, size)
    }

    override fun verificarCandidaturaExistente(userId: Long, vagaId: Long): Boolean =
        repository.verificarCandidaturaExistente(userId, vagaId )

    override fun listarPublicacoesCandidatadasPorUsuario(id: Long): List<Publicacao> =
        repository.listarPublicacoesCandidatadasPorUsuario(id)

    private fun validatePageAndSize(page: Int, size: Int) {
        if (page < 1) throw InvalidPageNumberException("A página $page não foi encontrada")

        if (size < 1) throw InvalidSizeNumberException("O tamanho da lista desejado é inválido")
    }
}