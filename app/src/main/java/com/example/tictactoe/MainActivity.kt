package com.example.tictactoe

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var ttt: TicTacToe
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ttt = TicTacToe()
        // setContentView(R.layout.activity_main)

        val width: Int = Resources.getSystem().displayMetrics.widthPixels
        Log.w("CMSC", "width of deice is $width")
        val w: Int = width / 3

        val gridLayout: GridLayout = GridLayout(this)
        gridLayout.rowCount = TicTacToe.SIDE + 1
        gridLayout.columnCount = TicTacToe.SIDE

//        buttons = Array<Array<Button>>(TicTacToe.SIDE) { i ->
//            Array<Button>(TicTacToe.SIDE) { j ->
//                Button(this)
//            }
//        }
        buttons = Array(TicTacToe.SIDE) { i ->
            Array(TicTacToe.SIDE) { j ->
                Button(this)
            }
        }
        val bh: ButtonHandler = ButtonHandler()

        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                gridLayout.addView(buttons[i][j], w, w)
                buttons[i][j].textSize = 72.0f
                buttons[i][j].setOnClickListener(bh)
            }
        }

        var rowSpec: GridLayout.Spec = GridLayout.spec(TicTacToe.SIDE, 1)
        var colSpec: GridLayout.Spec = GridLayout.spec(0, TicTacToe.SIDE)
        var lp: GridLayout.LayoutParams = GridLayout.LayoutParams(rowSpec, colSpec)

        status = TextView(this)
        status.layoutParams = lp
        status.gravity = Gravity.CENTER
        status.textSize = 72.0f
        status.text = ttt.result()
        status.setBackgroundColor(Color.GREEN)
        status.width =  w * TicTacToe.SIDE

        gridLayout.addView(status)
        setContentView(gridLayout)
    }

    fun update(row: Int, col: Int) {
        // play (update the state of the Model
        val play: Int = ttt.play(row, col)
        // update the View
        buttons[row][col].text = when(play) {
            1 -> "X"
            2 -> "O"
            else -> buttons[row][col].text
        }

        // check if game is over
        if(ttt.isGameOver()) {
            enableButtons(false)
            status.setBackgroundColor(Color.RED)
            status.text = ttt.result()

            // ask user to play again
            showNewGameDialog()
        }
    }

    private fun showNewGameDialog() {
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
//        alert.setPositiveButton("Yes") {
//            Log.w("MainActivity", "test")
//        }
    }

    fun enableButtons(enabled: Boolean): Unit {
        for (row in buttons) {
            for (col in row) {
                col.isEnabled = enabled
            }
        }
    }

    inner class ButtonHandler : View.OnClickListener {
        override fun onClick(v: View?) {
            Log.w("CMSC", "clicked on $v")
            for (i in buttons.indices) {
                for (j in 0..buttons[i].size - 1) {
                    if (v == buttons[i][j]) {
                        update(i, j)
                    }
                }
            }
        }
    }
}