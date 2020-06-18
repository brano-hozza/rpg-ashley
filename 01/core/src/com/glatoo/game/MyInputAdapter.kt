package com.glatoo.game

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.glatoo.game.components.*
import com.glatoo.game.systems.PlayerMovementSystem
import com.google.inject.Inject
import javafx.scene.input.KeyCode

class MyInputAdapter @Inject constructor(private val camera: OrthographicCamera,
                                         private val engine: Engine,
                                         private val world: World) :
        InputAdapter() {
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val worldCords = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))

        engine.addEntity(Entity().apply {
            add(TextureRegionComponent(TextureRegion(Main.img)))

            add(TransformComponent(Vector2(worldCords.x, worldCords.y), 0f, 0.25f))

            val body = world.createBody(BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
            })

            body.createFixture(PolygonShape().apply {
                setAsBox(1f, 1f)
            }, 1.0f)

            body.setTransform(transform.position, 0f)
            add(PhysicsComponent(body))
        })

        return true
    }


    override fun keyDown(keycode: Int): Boolean {
        if(Input.Keys.LEFT == keycode)
            PlayerMovementSystem.moveLeft = true
        if(Input.Keys.RIGHT == keycode)
            PlayerMovementSystem.moveRight = true
        if(Input.Keys.UP == keycode)
            PlayerMovementSystem.moveUp = true
        if(Input.Keys.DOWN == keycode)
            PlayerMovementSystem.moveDown = true

        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        if(Input.Keys.LEFT == keycode)
            PlayerMovementSystem.moveLeft = false
        if(Input.Keys.RIGHT == keycode)
            PlayerMovementSystem.moveRight = false
        if(Input.Keys.UP == keycode)
            PlayerMovementSystem.moveUp = false
        if(Input.Keys.DOWN == keycode)
            PlayerMovementSystem.moveDown = false

        return true
    }
}