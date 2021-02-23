package com.paulmethfessel.windowspong

import java.awt.Point
import java.awt.Toolkit

private val screen = Toolkit.getDefaultToolkit().screenSize

fun Double.toScreenX() = (this * screen.width).toInt()
fun Double.toScreenY() = (this * screen.height).toInt()

fun Vector.toScreen(): Point {
    val screenV = this * Vector(screen.width.toDouble(), screen.height.toDouble())
    return Point(screenV.x.toInt(), screenV.y.toInt())
}
