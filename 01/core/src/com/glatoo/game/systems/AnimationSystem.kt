package com.glatoo.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.glatoo.game.components.*
import com.google.inject.Inject

class AnimationSystem @Inject constructor() : IteratingSystem(
        Family.all(AnimationComponent::class.java)
                .one(TextureRegionComponent::class.java,
                        TextureComponent::class.java)
                .get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.tryGet(AnimationComponent)?.let { animationComponent ->
            val time = entity.player.timeMoving
            if (entity.animation.moving) {
                if (entity.player.facing == 'D')
                    entity.textureRegion.textureRegion = animationComponent.moveDownAnimation.getKeyFrame(time)
                if (entity.player.facing == 'U')
                    entity.textureRegion.textureRegion = animationComponent.moveUpAnimation.getKeyFrame(time)
                if (entity.player.facing == 'R')
                    entity.textureRegion.textureRegion = animationComponent.moveRightAnimation.getKeyFrame(time)
                if (entity.player.facing == 'L')
                    entity.textureRegion.textureRegion = animationComponent.moveLeftAnimation.getKeyFrame(time)
            }else{
                if (entity.player.facing == 'D')
                    entity.textureRegion.textureRegion = animationComponent.faceDown
                if (entity.player.facing == 'U')
                    entity.textureRegion.textureRegion = animationComponent.faceUp
                if (entity.player.facing == 'R')
                    entity.textureRegion.textureRegion = animationComponent.faceRight
                if (entity.player.facing == 'L')
                    entity.textureRegion.textureRegion = animationComponent.faceLeft

            }
        }
    }
}