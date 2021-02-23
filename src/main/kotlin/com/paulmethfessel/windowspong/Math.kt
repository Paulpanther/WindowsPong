package com.paulmethfessel.windowspong

import kotlin.math.abs
import kotlin.math.sqrt


class Vector(
    val values: MutableList<Double>
): Iterable<Double> {
    constructor(x: Double, y: Double) : this(mutableListOf(x, y))
    constructor(x: Double, y: Double, z: Double) : this(mutableListOf(x, y, z))
    constructor(x: Double, y: Double, z: Double, w: Double) : this(mutableListOf(x, y, z, w))
    constructor(size: Int, generator: (i: Int) -> Double) : this(MutableList(size, generator))

    var x set(value) { values[0] = value } get() = values[0]
    var y set(value) { values[1] = value } get() = values[1]
    var z set(value) { values[2] = value } get() = values[2]
    var w set(value) { values[3] = value } get() = values[3]

    val size get() = values.size
    val magnitude get() = sqrt(values.map { it * it }.sum())

    fun normalized() = this / magnitude

    operator fun plus(v: Double) = map { it + v}.asVector()
    operator fun plus(o: Vector) = mapIndexed { i, v -> v + o[i] }.asVector()
    operator fun minus(v: Double) = map { it - v}.asVector()
    operator fun minus(o: Vector) = mapIndexed { i, v -> v - o[i] }.asVector()
    operator fun times(v: Double) = map { it * v}.asVector()
    operator fun times(o: Vector) = mapIndexed { i, v -> v * o[i] }.asVector()
    operator fun div(v: Double) = map { it / v}.asVector()
    operator fun div(o: Vector) = mapIndexed { i, v -> v / o[i] }.asVector()
    operator fun unaryMinus() = map { -it }.asVector()

    operator fun get(i: Int) = values[i]
    operator fun set(i: Int, v: Double) { values[i] = v }

    override fun iterator() = values.iterator()

    override fun equals(other: Any?): Boolean {
        return if (other is Vector) {
            zip(other.values).all { it.first saveEquals it.second }
        } else {
            false
        }
    }

    override fun hashCode() = values.hashCode()

    override fun toString() = values.joinToString(", ", "(", ")") { it.format(4) }
}

fun List<Double>.asVector() = Vector(this.toMutableList())

const val epsilon = 0.0001

fun Double.saveEquals(o: Double, eps: Double) = abs(this - o) < eps
infix fun Double.saveEquals(o: Double) = saveEquals(o, epsilon)

fun Double.format(digits: Int) = "%.${digits}f".format(this)

fun lerp(a: Double, b: Double, t: Double) = a + t * (b - a)