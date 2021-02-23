package com.paulmethfessel.windowspong

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.swing.JDialog
import javax.swing.JOptionPane
import kotlin.system.exitProcess


private val enemy = EnemyBar(startLeft = true)
private val player = PlayerBar(startLeft = false)
private val ball = Ball(player, enemy, ::onGameOver)

private var lastUpdate = System.currentTimeMillis()

fun onGameOver(bar: Bar) {
    val message = if (bar == player) "Player won" else "Enemy won"
    JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE)
    player.open = false
}

fun main() {
    enemy.ball = ball
    GlobalScope.launch {
        while (player.open) {
            val delta = (System.currentTimeMillis() - lastUpdate) / 1000.0
            lastUpdate = System.currentTimeMillis()

            player.update(delta)
            enemy.update(delta)
            ball.update(delta)
            delay(50)
        }

        player.isVisible = false
        enemy.isVisible = false
        exitProcess(0)
    }
}
