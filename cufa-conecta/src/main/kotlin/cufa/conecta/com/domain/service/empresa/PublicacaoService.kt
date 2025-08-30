package cufa.conecta.com.domain.service.empresa

import cufa.conecta.com.model.data.Publicacao

interface PublicacaoService {
    fun criar(data: Publicacao)
    fun buscarTodas(): List<Publicacao>
    fun buscarPublicacoesPorEmpresaId(id: Long): List<Publicacao>
    fun findById(id: Long): Publicacao
    fun delete(id: Long)
}