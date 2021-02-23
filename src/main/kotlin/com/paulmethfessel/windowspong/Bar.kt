package com.paulmethfessel.windowspong

import java.awt.Color
import java.awt.Dimension
import java.awt.Rectangle
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sign

private const val left = 0.1
private const val right = 0.9

private const val relWidth = 0.003
private const val relHeight = 0.2

private const val speed = 0.5


open class Bar(startLeft: Boolean): JFrame() {

    var open = true

    protected var y = 0.5
    protected var height = relHeight
    protected var movement = 0

    protected var locationX set(value) { location = location.apply { x = value }} get() = location.x
    protected var locationY set(value) { location = location.apply { y = value }} get() = location.y

    init {
        val width = relWidth.toScreenX()
        val height = height.toScreenY()

        minimumSize = Dimension(width, height)
        maximumSize = Dimension(width, height)

        isResizable = false
        layout = null
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE

        locationX = (if (startLeft) left else right).toScreenX() - rect().width / 2
    }

    open fun update(delta: Double) {
        y += movement * speed * delta
        y = y.coerceIn(height / 2, 1 - height / 2)
        locationY = (y - height / 2).toScreenY()
    }

    fun rect(): Rectangle {
        return bounds
    }
}

class PlayerBar(startLeft: Boolean): Bar(startLeft), KeyListener {

    private var down = false
    private var up = false

    init {
        addKeyListener(this)
        contentPane.background = Color.BLUE
    }

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyReleased(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_UP -> up = false
            KeyEvent.VK_DOWN -> down = false
        }
        updateKeys()
    }

    override fun keyPressed(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_UP -> up = true
            KeyEvent.VK_DOWN -> down = true
            KeyEvent.VK_ESCAPE -> open = false
        }
        updateKeys()
    }

    private fun updateKeys() {
        movement = when {
            up && down -> 0
            !up && !down -> 0
            up -> -1
            down -> 1
            else -> movement
        }
    }
}

const val enemyMoveMultiplier = 2.5
const val enemyMaxSpeed = 0.8

class EnemyBar(startLeft: Boolean): Bar(startLeft) {
    lateinit var ball: Ball

    init {
        contentPane.background = Color.GRAY
    }

    override fun update(delta: Double) {
        val dist = (ball.position.y - y) * enemyMoveMultiplier
        val move = dist.absoluteValue.coerceIn(0.0, enemyMaxSpeed) * dist.sign
        y += move * delta
        locationY = (y - height / 2).toScreenY()
    }
}