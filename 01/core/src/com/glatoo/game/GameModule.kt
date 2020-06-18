package com.glatoo.game

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2D
import com.badlogic.gdx.physics.box2d.World
import com.glatoo.game.systems.*
import com.google.inject.Binder
import com.google.inject.Module
import com.google.inject.Provides
import com.google.inject.Singleton

class GameModule(private val main: Main) : Module {


    override fun configure(binder: Binder) {
        binder.requireAtInjectOnConstructors()
        binder.requireExactBindingAnnotations()
        binder.bind(OrthogonalTiledMapRenderer::class.java).toInstance(main.renderer)
        binder.bind(AssetManager::class.java).toInstance(main.assetManager)
    }

    @Provides
    @Singleton
    fun systems(): Systems {
        return Systems(listOf(
                PhysicsSystem::class.java,
                PhysicsSynchronizationSystem::class.java,
                RenderingSystem::class.java,
                AnimationSystem::class.java,
                PhysicsDebugSystem::class.java,
                PlayerMovementSystem::class.java,
                ContactSystem::class.java
        ))
    }


    @Provides
    @Singleton
    fun camera(): OrthographicCamera {
        val viewportWidth = Gdx.graphics.width.pixelToMeter;
        val viewportHeight = Gdx.graphics.height.pixelToMeter

        return OrthographicCamera(viewportWidth, viewportHeight).apply {
            position.set(viewportWidth / 2F, viewportHeight / 2F, 0F)
            update()
        }
    }

    @Provides
    @Singleton
    fun world(): World {
        Box2D.init()
        return World(Vector2(0f, 0f), true)
    }

    @Provides @Singleton
    fun engine() : Engine {
        return main.engine
    }



}