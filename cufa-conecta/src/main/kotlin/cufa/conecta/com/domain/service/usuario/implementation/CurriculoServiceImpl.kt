package cufa.conecta.com.domain.service.usuario.implementation


import cufa.conecta.com.domain.service.usuario.CurriculoService
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class CurriculoServiceImpl : CurriculoService {

    private val uploadDir: Path = Paths.get("uploads/curriculos").also {
        runCatching { Files.createDirectories(it) }
            .onFailure { throw RuntimeException("Não foi possível criar diretório de uploads: ${it.message}", it) }
    }

    override fun salvarArquivoCurriculo(file: MultipartFile): String {
        require(!file.isEmpty) { "Arquivo vazio." }

        val filename = "${UUID.randomUUID()}-${file.originalFilename}"
        val targetPath = uploadDir.resolve(filename)

        runCatching {
            Files.copy(file.inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING)
        }.getOrElse { e ->
            throw RuntimeException("Erro ao salvar arquivo: ${e.message}", e)
        }

        return filename
    }

    override fun downloadCurriculo(filename: String): Resource {
        val filePath = uploadDir.resolve(filename).normalize()

        return runCatching {
            UrlResource(filePath.toUri()).apply {
                require(exists() && isReadable) { "Arquivo não encontrado" }
            }
        }.getOrElse { e ->
            throw RuntimeException("Erro ao carregar arquivo: ${e.message}", e)
        }
    }

    override fun deletarArquivoFisico(filename: String) {
        val filePath = uploadDir.resolve(filename).normalize()

        runCatching {
            require(Files.exists(filePath)) { "Arquivo não encontrado para exclusão" }
            Files.delete(filePath)
        }.getOrElse { e ->
            throw RuntimeException("Erro ao excluir arquivo: ${e.message}", e)
        }
    }

    override fun gerarUrlArquivo(filename: String): String =
        "http://localhost:8080/curriculos/download/$filename"
}