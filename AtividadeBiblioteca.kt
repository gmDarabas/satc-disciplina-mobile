class Livro(val titulo: String, val autor: String, var disponivel: Boolean = true) {

    fun exibirDetalhes() {
        val disponivelString = if (disponivel) "Sim" else "Não"
        println("| %-15s | %-5s | %-15s |".format(titulo, autor, disponivelString))
    }
}

class Usuario(val nome: String) {

    val livrosEmprestados: MutableList<Livro> = mutableListOf()

    fun emprestarLivro(livro: Livro) {
        if (!livro.disponivel) {
            println("Erro: Livro indisponível")
        }

        livrosEmprestados.add(livro)
        livro.disponivel = false
    }

    fun devolverLivro(livro: Livro) {
        if (!livrosEmprestados.contains(livro)) {
            println("Erro: Usuário nao possui o livro \"${livro.titulo}\"")
        }

        livrosEmprestados.remove(livro)
        livro.disponivel = true
    }
}

class Biblioteca(val nome: String) {

    val livros: MutableList<Livro> = mutableListOf()

    fun adicionarLivro(livro: Livro) {
        livros.add(livro)
    }

    fun exibirLivrosDisponiveis() {
        println("+-----------------+-------+-----------------+")
        println("| %-15s | %-5s | %-15s |".format("Nome", "Autor", "Disponivel"))
        println("+-----------------+-------+-----------------+")

        livros.filter({ it.disponivel }).forEach({ it.exibirDetalhes() })

        println("+-----------------+-------+-----------------+")
    }
}

fun main() {
    val bibliotecaSatc = Biblioteca("Biblioteca SATC")
    val aluno = Usuario("Joaozinho")

    val livro1 = Livro("Livro 1", "Autor")
    val livro2 = Livro("Livro 2", "Autor")
    val livro3 = Livro("Livro 3", "Autor")

    bibliotecaSatc.adicionarLivro(livro1)
    bibliotecaSatc.adicionarLivro(livro2)
    bibliotecaSatc.adicionarLivro(livro3)

    bibliotecaSatc.exibirLivrosDisponiveis()

    aluno.emprestarLivro(livro1)
    aluno.emprestarLivro(livro1) // causa "erro" de livro indisponivel

    bibliotecaSatc.exibirLivrosDisponiveis()

    aluno.devolverLivro(livro2) // causa "erro" usuario nao possui o livro
    aluno.devolverLivro(livro1)

    bibliotecaSatc.exibirLivrosDisponiveis()
}