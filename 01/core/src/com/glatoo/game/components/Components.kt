package com.glatoo.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body


//Transform component
class TransformComponent(val position: Vector2, var angleRadian: Float, var scale: Float) : Component {
    constructor(position: Vector2) : this(position, 0f, 1f)

    companion object : ComponentResolver<TransformComponent>(TransformComponent::class.java)
}


//Texture component
class TextureComponent(val texture: Texture) : Component {
    companion object : ComponentResolver<TextureComponent>(TextureComponent::class.java)
}


//Texture region component
class TextureRegionComponent(var textureRegion: TextureRegion) : Component {
    companion object : ComponentResolver<TextureRegionComponent>(TextureRegionComponent::class.java)
}


//Physics component
class PhysicsComponent(val body: Body) : Component {
    companion object : ComponentResolver<PhysicsComponent>(PhysicsComponent::class.java)
}

//Movement component
class PlayerComponent() : Component {
    var moving = false
    var facing: Char = 'D'
    var timeMoving = 0f

    fun facing(down: Boolean, up: Boolean, right: Boolean, left: Boolean){
        if (down)
            facing = 'D'
        if (up)
            facing = 'U'
        if (left)
            facing = 'L'
        if (right)
            facing = 'R'
    }

    companion object : ComponentResolver<PlayerComponent>(PlayerComponent::class.java)
}

//Movement component
class EnemyComponent() : Component {
    var moving = false
    var facing: Char = 'D'
    var timeMoving = 0f

    fun facing(down: Boolean, up: Boolean, right: Boolean, left: Boolean){
        if (down)
            facing = 'D'
        if (up)
            facing = 'U'
        if (left)
            facing = 'L'
        if (right)
            facing = 'R'
    }

    companion object : ComponentResolver<EnemyComponent>(EnemyComponent::class.java)
}


//Animation component
class AnimationComponent(
        private val moveRight: com.badlogic.gdx.utils.Array<out TextureRegion>,
        val faceRight: TextureRegion,
        private val moveLeft: com.badlogic.gdx.utils.Array<out TextureRegion>,
        val faceLeft: TextureRegion,
        private val moveUp: com.badlogic.gdx.utils.Array<out TextureRegion>,
        val faceUp: TextureRegion,
        private val moveDown: com.badlogic.gdx.utils.Array<out TextureRegion>,
        val faceDown: TextureRegion


) : Component {
    var moving = false
    val moveRightAnimation = Animation(0.12f, moveRight, Animation.PlayMode.LOOP)
    val moveLeftAnimation = Animation(0.12f, moveLeft, Animation.PlayMode.LOOP)
    val moveUpAnimation = Animation(0.12f, moveUp, Animation.PlayMode.LOOP)
    val moveDownAnimation = Animation(0.12f, moveDown, Animation.PlayMode.LOOP)

    companion object : ComponentResolver<AnimationComponent>(AnimationComponent::class.java)
}

//Attack component
class AttackComponent(var damage: Int): Component{
    companion object : ComponentResolver<AttackComponent>(AttackComponent::class.java)
}

//Health component
class HealthComponent(var health: Int): Component{
    companion object : ComponentResolver<HealthComponent>(HealthComponent::class.java)
}