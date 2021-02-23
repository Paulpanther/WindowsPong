package com.paulmethfessel.windowspong

import java.awt.Rectangle
import java.awt.Robot

const val startSpeed = 0.2
const val endSpeed = 0.8
const val maxVerticalSpeed = 0.6
const val maxSpeedTime = 20 * 1000.0

class Ball(
    private val barLeft: Bar,
    private val barRight: Bar,
    val gameOverCallback: (bar: Bar) -> Unit
) {

    var position = Vector(0.5, 0.5)
        private set

    private val speed get() = lerp(
        startSpeed, endSpeed, 1 - (endSpeedTime - System.currentTimeMillis()) / maxSpeedTime)

    private var movement = Vector(1.0, 0.0)
    private val r = Robot()
    private val endSpeedTime = System.currentTimeMillis() + maxSpeedTime

    private val screenPosition get() = position.toScreen()

    fun update(delta: Double) {

        for (bar in listOf(barLeft, barRight)) {
            val rect = bar.rect()
            if (collidesWith(rect)) {
                bounceOf(rect)
            }
        }

        position += movement * speed * delta

        bounceOfWalls()
        if (checkWin()) {
            resetMouse()
        } else {
            r.mouseMove(screenPosition.x, screenPosition.y)
        }
    }

    private fun collidesWith(rect: Rectangle) = rect.contains(position.toScreen())

    private fun bounceOf(rect: Rectangle) {
        val center = rect.centerY
        val offset = (screenPosition.y - center) / (rect.height / 2)
        movement = Vector(-movement.x, offset * maxVerticalSpeed).normalized()
        print(movement.magnitude)
    }

    private fun bounceOfWalls() {
        if (position.y < 0) {
            position.y = 0.0
            movement.y = -movement.y
        }
        if (position.y > 1) {
            position.y = 1.0
            movement.y = -movement.y
        }
    }

    private fun checkWin() = when {
        position.x < 0 -> {
            gameOverCallback(barLeft)
            true
        }
        position.x > 1 -> {
            gameOverCallback(barRight)
            true
        }
        else -> {
            false
        }
    }

    private fun resetMouse() {
        r.mouseMove(0.5.toScreenX(), 0.5.toScreenY())
    }
}