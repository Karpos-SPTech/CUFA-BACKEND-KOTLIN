package cufa.conecta.com.domain.service.empresa

import cufa.conecta.com.model.data.Publicacao

interface PublicacaoService {
    fun criar(data: Publicacao)
    fun buscarTodas(): List<Publicacao>
    fun buscarPublicacoesDaEmpresa(): List<Publicacao>
    fun findById(id: Long): Publicacao
    fun editarPublicacao(id:Long, data: Publicacao)
    fun delete(id: Long)
}