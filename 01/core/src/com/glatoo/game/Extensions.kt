package com.glatoo.game

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.MathUtils

val Int.pixelToMeter: Float
    get() = this / 32f

val Float.toDegrees: Float
    get() = MathUtils.radiansToDegrees * this


data class Systems(val list: List<Class<out EntitySystem>>)