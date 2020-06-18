package com.glatoo.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

val Entity.transform: TransformComponent
    get() = TransformComponent[this]


val Entity.textureRegion: TextureRegionComponent
    get() = TextureRegionComponent[this]

val Entity.physics: PhysicsComponent
    get() = PhysicsComponent[this]

val Entity.player: PlayerComponent
    get() = PlayerComponent[this]

val Entity.animation: AnimationComponent
    get() = AnimationComponent[this]




fun <T : Component> Entity.tryGet(componentResolver: ComponentResolver<T>): T? {
    return componentResolver.MAPPER.get(this)
}