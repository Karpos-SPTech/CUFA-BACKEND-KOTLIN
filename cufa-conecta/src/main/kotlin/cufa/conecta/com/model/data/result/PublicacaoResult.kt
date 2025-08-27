package cufa.conecta.com.model.data.result

import java.time.LocalDateTime

data class PublicacaoResult(
    val titulo: String,
    val descricao: String,
    val tipoContrato: String,
    val dtExpiracao: LocalDateTime,
    val dtPublicacao: LocalDateTime,
    val nomeEmpresa: String,
    val fkEmpresa: Long
)