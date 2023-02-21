package com.example.tictactoe

class TicTacToe {
    private var turn = 0
    private val game: Array<IntArray>

    init {
        game = Array(SIDE) { IntArray(SIDE) }
        resetGame()
    }

    fun play(row: Int, col: Int): Int {
        val currentTurn = turn
        return if (row >= 0 && col >= 0 && row < SIDE && col < SIDE && game[row][col] == 0) {
            game[row][col] = turn
            turn = if (turn == 1) 2 else 1
            currentTurn
        } else 0
    }

    fun whoWon(): Int {
        val rows = checkRows()
        if (rows > 0) return rows
        val columns = checkColumns()
        if (columns > 0) return columns
        val diagonals = checkDiagonals()
        return if (diagonals > 0) diagonals else 0
    }

    protected fun checkRows(): Int {
        for (row in 0 until SIDE)
            if (game[row][0] != 0 && game[row][0] == game[row][1] && game[row][1] == game[row][2]) return game[row][0]
        return 0
    }

    protected fun checkColumns(): Int {
        for (col in 0 until SIDE)
            if (game[0][col] != 0 && game[0][col] == game[1][col] && game[1][col] == game[2][col]) return game[0][col]
        return 0
    }

    protected fun checkDiagonals(): Int {
        if (game[0][0] != 0 && game[0][0] == game[1][1] && game[1][1] == game[2][2])
            return game[0][0]
        else if (game[0][2] != 0 && game[0][2] == game[1][1] && game[1][1] == game[2][0]
        )
            return game[1][1]
        else
            return 0
    }

    fun canNotPlay(): Boolean {
        var result = true
        for (row in 0 until SIDE) for (col in 0 until SIDE) if (game[row][col] == 0) result = false
        return result
    }

    fun isGameOver(): Boolean {
        return canNotPlay()  || whoWon() > 0
    }

    fun resetGame() {
        for (row in 0 until SIDE) for (col in 0 until SIDE) game[row][col] = 0
        turn = 1
    }

    fun result(): String {
        if (whoWon() > 0)
            return "Player " + whoWon() + " won"
        else if (canNotPlay())
            return "Tie Game"
        else
            return "PLAY !!"
    }

    companion object {
        const val SIDE = 3
    }
}