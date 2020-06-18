package com.glatoo.game

import com.badlogic.ashley.core.*
import com.badlogic.gdx.*
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.google.inject.*


class Main : ApplicationAdapter() {
    lateinit var renderer: OrthogonalTiledMapRenderer
    lateinit var assetManager: AssetManager
    val engine = Engine()
    private lateinit var injector: Injector

    companion object {
        internal lateinit var img: Texture
    }


    override fun create() {
        assetManager = AssetManager()
        assetManager.setLoader(TiledMap::class.java, TmxMapLoader())
        assetManager.load("maps/01/mapa.tmx", TiledMap::class.java)
        assetManager.finishLoading()

        val map = assetManager.get("maps/01/mapa.tmx", TiledMap::class.java)
        renderer = OrthogonalTiledMapRenderer(map,1/16f)
        img = Texture("badlogic.jpg")
        injector = Guice.createInjector(GameModule(this))

        injector.getInstance(Systems::class.java).list.map { injector.getInstance(it) }.forEach { system ->
            engine.addSystem(system)
        }

        Gdx.input.inputProcessor = injector.getInstance(MyInputAdapter::class.java)
        injector.getInstance(GameInitializer::class.java).initialize()

    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        engine.update(Gdx.graphics.deltaTime)
    }

    override fun dispose() {
        assetManager.dispose()
        renderer.batch.dispose()
        img.dispose()
    }
}






