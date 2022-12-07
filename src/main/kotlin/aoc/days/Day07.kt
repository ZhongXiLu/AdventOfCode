package aoc.days

import aoc.Day

private const val TOTAL_SPACE = 70000000
private const val REQUIRED_SPACE_FOR_UPDATE = 30000000

class Day07 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return buildFileSystem(input)
            .getAllDirs()
            .map { it.calculateSize() }
            .filter { it <= 100000 }
            .sum()
    }

    override fun solvePart2(input: List<String>): Any {
        val fileSystem = buildFileSystem(input)
        val requiredSpace = REQUIRED_SPACE_FOR_UPDATE - (TOTAL_SPACE - fileSystem.calculateSize())

        return fileSystem
            .getAllDirs()
            .map { it.calculateSize() }
            .sorted()
            .first { it >= requiredSpace }
    }

    private fun buildFileSystem(input: List<String>): File {
        val root = File("/")
        var currentDir = root

        input
            .joinToString("\n")
            .split("$")
            .map { it.split("\n") }
            .drop(1)

            .forEach {
                when (it[0].trimStart()) {
                    "cd /" -> currentDir = root
                    "cd .." -> currentDir = currentDir.parent ?: root
                    "ls" -> {
                        it
                            .drop(1)
                            .map { file -> file.split(" ") }
                            .dropLast(1)
                            .map { (filePrefix, filename) ->
                                File(filename, currentDir, if (filePrefix == "dir") 0 else filePrefix.toInt())
                            }
                            .forEach { file -> currentDir.addFile(file) }
                    }

                    // "cd dir"
                    else -> currentDir = currentDir.getDir(it[0].substringAfter("cd "))
                }
            }

        return root
    }

}

private data class File(
    val name: String,
    val parent: File? = null,
    val size: Int = 0,
    val files: MutableList<File> = mutableListOf()
) {
    fun isDirectory() = files.isNotEmpty()

    fun getDir(dir: String): File {
        return files.first { it.name == dir }
    }

    fun addFile(file: File) {
        files.add(file)
    }

    fun calculateSize(): Int {
        return if (isDirectory()) files.sumOf { it.calculateSize() } else size
    }

    fun getAllDirs(): List<File> {
        return files
            .asSequence()
            .filter { it.isDirectory() }
            .map { it.getAllDirs() }
            .flatten()
            .toMutableList()
            .plus(this)
            .toList()
    }
}
