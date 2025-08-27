package cufa.conecta.com.model.data

import java.time.LocalDateTime

data class Publicacao(
    val empresaId: Long,
    val titulo: String,
    val descricao: String,
    val tipoContrato: String,
    val dtExpiracao: LocalDateTime,
    val dtPublicacao: LocalDateTime
)