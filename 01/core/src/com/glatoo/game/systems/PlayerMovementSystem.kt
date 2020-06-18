package com.glatoo.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.glatoo.game.components.*
import com.google.inject.Inject
import kotlin.math.abs

class PlayerMovementSystem @Inject constructor(private val camera: OrthographicCamera) : IteratingSystem(Family.all(PlayerComponent::class.java).get()) {


    private val maximumVelocity = 4f
    private val moveSpeed = 2f


    companion object {
        var moveLeft = false
        var moveRight = false
        var moveUp = false
        var moveDown = false
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val body = entity.physics.body
        var horizontalVelocity = body.linearVelocity.x * 0.8f
        var verticalVelocity = body.linearVelocity.y * 0.8f


        if (moveLeft) {
            if (horizontalVelocity > -maximumVelocity) {
                horizontalVelocity = -moveSpeed
                //body.applyForceToCenter(-moveSpeed * deltaTime, 0f, true)
            }
        }

        if (moveRight) {
            if (horizontalVelocity < maximumVelocity) {
                horizontalVelocity = moveSpeed
                //body.applyForceToCenter(moveSpeed * deltaTime, 0f, true)
            }
        }

        if (moveDown) {
            if (verticalVelocity > -maximumVelocity) {
                verticalVelocity = -moveSpeed
                //body.applyForceToCenter(0f, -moveSpeed * deltaTime, true)
            }
        }

        if (moveUp) {
            if (verticalVelocity < maximumVelocity) {
                verticalVelocity = moveSpeed
                //body.applyForceToCenter(0f, moveSpeed * deltaTime, true)
            }
        }
        if (abs(verticalVelocity) > 0.1 || abs(horizontalVelocity) > 0.1) {
            entity.animation.moving = true
            body.setLinearVelocity(horizontalVelocity, verticalVelocity)
        } else {
            entity.animation.moving = false
            body.setLinearVelocity(0f, 0f)

        }

        entity.tryGet(PlayerComponent)?.apply {
            moving = moveLeft || moveRight || moveUp || moveDown
            if (moving) {
                facing(moveDown, moveUp, moveRight, moveLeft)
                timeMoving += deltaTime


            } else {
                timeMoving = 0f

            }
        }

        camera.position.set(Vector3(entity.transform.position.x, entity.transform.position.y, 0f))
    }

}