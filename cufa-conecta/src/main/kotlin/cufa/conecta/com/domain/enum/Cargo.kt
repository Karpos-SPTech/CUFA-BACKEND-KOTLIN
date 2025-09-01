package cufa.conecta.com.domain.enum

enum class Cargo {
    FUNCIONARIO,
    ADMINISTRADOR;

    companion object {
        fun fromString(value: String): Cargo {
            val cargo = entries.firstOrNull() { it.name == value }

            if (cargo != null) return cargo

            throw IllegalArgumentException("Nenhuma enum cufa.conecta.com.domain.enum.$value")
        }
    }
}
