package com.glatoo.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity

open class ComponentResolver<T : Component>(componentClass: Class<T>) {
    val MAPPER: ComponentMapper<T> = ComponentMapper.getFor(componentClass)
    operator fun get(entity: Entity): T = MAPPER.get(entity)
}