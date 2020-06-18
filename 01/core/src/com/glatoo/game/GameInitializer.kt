package com.glatoo.game

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array
import com.glatoo.game.components.*
import com.google.inject.Inject
import com.google.inject.Singleton
import javax.xml.soap.Text

@Singleton
class GameInitializer @Inject constructor(
        private val world: World,
        private val engine: Engine,
        private val assetManager: AssetManager) {


    fun initialize() {
        this.createWorld()
        this.createPlayer()
        this.createEnemies()
    }

    private fun createWorld() {
        val map: TiledMap = assetManager.get("maps/01/mapa.tmx", TiledMap::class.java)
        val layer: TiledMapTileLayer = map.layers.get("Walls") as TiledMapTileLayer
        for (x in 0 until 50)
            for (y in 0 until 50) {
                val cell = layer.getCell(x, y)
                if (cell != null) {
                    engine.addEntity(Entity().apply {
                        add(TransformComponent(Vector2(x.toFloat() + .5f, y.toFloat() + .5f), 0f, 0.10f))

                        val body = world.createBody(BodyDef().apply {
                            type = BodyDef.BodyType.StaticBody
                            fixedRotation = true
                        })
                        body.createFixture(PolygonShape().apply {
                            setAsBox(.5f, .5f)
                        }, 1.0f)
                        body.setTransform(transform.position, 0f)

                        add(PhysicsComponent(body))
                    })
                }
            }

    }

    private fun createPlayer() {
        engine.addEntity(Entity().apply {
            add(TextureRegionComponent(TextureRegion(Texture("player.jpg"))))

            add(AnimationComponent(
                    faceDown = TextureRegion(Texture("animation/player/down/1.png")),
                    faceRight = TextureRegion(Texture("animation/player/right/1.png")),
                    faceLeft = TextureRegion(Texture("animation/player/left/1.png")),
                    faceUp = TextureRegion(Texture("animation/player/up/1.png")),
                    moveDown = Array<TextureRegion>().apply {
                        add(TextureRegion(Texture("animation/player/down/2.png")))
                        add(TextureRegion(Texture("animation/player/down/3.png")))
                    },
                    moveLeft = Array<TextureRegion>().apply {
                        add(TextureRegion(Texture("animation/player/left/2.png")))
                        add(TextureRegion(Texture("animation/player/left/3.png")))
                    },
                    moveRight = Array<TextureRegion>().apply {
                        add(TextureRegion(Texture("animation/player/right/2.png")))
                        add(TextureRegion(Texture("animation/player/right/3.png")))
                    },
                    moveUp = Array<TextureRegion>().apply {
                        add(TextureRegion(Texture("animation/player/up/2.png")))
                        add(TextureRegion(Texture("animation/player/up/3.png")))
                    }
            ))
            add(TransformComponent(Vector2(3f, 3f), 0f, 3f))
            val body = world.createBody(BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                fixedRotation = true
            })
            body.createFixture(CircleShape().apply {
                radius = 0.5f
                position = Vector2(0f, -0.5f)
            }, 1f)
            body.createFixture(PolygonShape().apply {
                setAsBox(0.5f, 0.5f)
            }, 1f)

            body.createFixture(CircleShape().apply {
                radius = 0.5f
                position = Vector2(0f, 0.5f)
            }, 1f)

            body.setTransform(transform.position, 0f)
            add(PhysicsComponent(body))
            add(PlayerComponent())

        })

    }
    private fun createEnemies(){
        engine.addEntity(Entity().apply {
            add(TextureRegionComponent(TextureRegion(Texture("badlogic.jpg"))))


            add(TransformComponent(Vector2(5f, 5f), 0f, 0.25f))
            val body = world.createBody(BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                fixedRotation = true
            })
            body.createFixture(CircleShape().apply {
                radius = 0.5f
                position = Vector2(0f, -0.5f)
            }, 1f)
            body.createFixture(PolygonShape().apply {
                setAsBox(0.5f, 0.5f)
            }, 1f)

            body.createFixture(CircleShape().apply {
                radius = 0.5f
                position = Vector2(0f, 0.5f)
            }, 1f)

            body.setTransform(transform.position, 0f)
            add(PhysicsComponent(body))
            add(EnemyComponent())
        })
    }

}