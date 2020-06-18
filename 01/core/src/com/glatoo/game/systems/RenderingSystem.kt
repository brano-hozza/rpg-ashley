package com.glatoo.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.glatoo.game.*
import com.glatoo.game.components.*
import com.google.inject.Inject

class RenderingSystem @Inject constructor(private val renderer: OrthogonalTiledMapRenderer,
                                          private val camera: OrthographicCamera) :
        IteratingSystem(
                Family.all(
                        TransformComponent::class.java
                ).one(
                        TextureRegionComponent::class.java
                ).get()) {
    override fun update(deltaTime: Float) {
        camera.update()
        renderer.setView(camera)
        renderer.render()
        renderer.batch.begin()
        super.update(deltaTime)
        renderer.batch.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity.transform.position
        entity.tryGet(TextureRegionComponent)?.let { textureRegionComponent ->
            val img = textureRegionComponent.textureRegion
            val width = img.regionWidth.pixelToMeter
            val height = img.regionHeight.pixelToMeter
            val scale = entity.transform.scale
            renderer.batch.draw(img,
                    position.x - width / 2f,
                    position.y - height / 2f,
                    width / 2,
                    height / 2,
                    width, height
                    , scale, scale,
                    entity.transform.angleRadian.toDegrees
            )
        }

    }

}