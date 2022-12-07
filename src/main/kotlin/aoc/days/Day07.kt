package aoc.days

import aoc.Day

class Day07 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val root = buildFileSystem(input)

        return root
            .getDirsWithMaxSize(100000)
            .sumOf { it.calculateSize() }
    }

    override fun solvePart2(input: List<String>): Any {
        val root = buildFileSystem(input)

        val neededSpace = 30000000 - (70000000 - root.calculateSize())
        return root
            .getAllDirs()
            .sortedBy { it.calculateSize() }
            .first { it.calculateSize() >= neededSpace }
            .calculateSize()
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
                        it.drop(1)
                            .map { file -> file.split(" ") }
                            .dropLast(1)
                            .forEach { (filePrefix, filename) ->
                                currentDir.addFile(
                                    File(filename, currentDir, if (filePrefix == "dir") -1 else filePrefix.toInt())
                                )
                            }
                    }

                    else -> currentDir = currentDir.changeDir(it[0].substringAfter("cd "))
                }
            }

        return root
    }

}

private data class File(
    val name: String,
    val parent: File? = null,
    val size: Int = -1,
    val files: MutableList<File> = mutableListOf()
) {
    fun isDirectory() = files.isNotEmpty()

    fun changeDir(dir: String): File {
        return files.first { it.name == dir }
    }

    fun addFile(file: File) {
        files.add(file)
    }

    fun calculateSize(): Int {
        return if (size == -1) {
            files.sumOf { it.calculateSize() }
        } else {
            size
        }
    }

    fun getDirsWithMaxSize(maxSize: Int): List<File> {
        val dirs = files
            .filter { it.isDirectory() }
            .map { it.getDirsWithMaxSize(maxSize) }
            .flatten()
            .toMutableList()

        if (calculateSize() <= maxSize) {
            dirs.add(this)
        }

        return dirs
    }

    fun getAllDirs(): List<File> {
        val dirs = files
            .filter { it.isDirectory() }
            .map { it.getAllDirs() }
            .flatten()
            .toMutableList()

        dirs.add(this)
        return dirs
    }
}
