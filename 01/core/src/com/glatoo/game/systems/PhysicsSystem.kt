package com.glatoo.game.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.physics.box2d.World
import com.google.inject.Inject

class PhysicsSystem @Inject constructor(private val world: World) : EntitySystem() {

    private var accumulator = 0f

    companion object {
        private const val TIME_STEP = 1.0F / 300F
        private const val VELOCITY_ITERATIONS = 6
        private const val POSITION_ITERATIONS = 2
    }

    override fun update(deltaTime: Float) {
        val frameTime = Math.min(deltaTime, 0.25F)
        accumulator += frameTime
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
            accumulator -= TIME_STEP
        }
    }
}