package cufa.conecta.com.application.dto.request.usuario

import org.jetbrains.annotations.NotNull
import org.springframework.web.multipart.MultipartFile

data class CurriculoRequestDto(
    @field:NotNull("Arquivo é obrigatório")
    val file: MultipartFile
)