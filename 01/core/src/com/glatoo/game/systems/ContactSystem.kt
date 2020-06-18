package com.glatoo.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.glatoo.game.components.*
import com.google.inject.Inject
import kotlin.math.sqrt
import kotlin.math.abs

class ContactSystem @Inject constructor() : IteratingSystem(
        Family.all(TransformComponent::class.java).get()
) {
    var playerPosition: Vector2 = Vector2(0f, 0f)
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.tryGet(PlayerComponent)?.let { _ ->
            playerPosition = entity.transform.position

        }
        entity.tryGet(EnemyComponent)?.let { _ ->
            val enemyPos = entity.transform.position
            val a = abs(enemyPos.x  - playerPosition.x)
            val b = abs(enemyPos.y - playerPosition.y)
            val distance = sqrt((a*a + b*b).toDouble())
            if (distance < 1.5f){
                println("Collision")
            }
        }
    }

}