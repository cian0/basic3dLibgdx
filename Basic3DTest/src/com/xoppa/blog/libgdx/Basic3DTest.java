package com.xoppa.blog.libgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
//
public class Basic3DTest implements ApplicationListener {
	
	public PerspectiveCamera cam;
	public Model model;
	public AssetManager assets;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
	public ModelBatch modelBatch;
    public ModelInstance instance;
    public Lights lights;
    public CameraInputController camController;
    public boolean loading;
    
	@Override
	public void create() {		
		modelBatch = new ModelBatch();
		lights = new Lights();
        lights.ambientLight.set(0.4f, 0.4f, 0.4f, 1f);
        lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(1f, 1f, 1f);
        cam.lookAt(0,0,0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();
        
        
       
        
        
//        ModelBuilder modelBuilder = new ModelBuilder();
//        model = modelBuilder.createBox(5f, 5f, 5f, 
//            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
//            Usage.Position | Usage.Normal);
//        instance = new ModelInstance(model);
        
//        ModelLoader loader = new ObjLoader();
//        model = loader.loadModel(Gdx.files.internal("ship.obj"));
//        instance = new ModelInstance(model);
        
        
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
        
        assets = new AssetManager();
        assets.load("ship.obj", Model.class);
        loading = true;
	}
	private void doneLoading() {
        Model ship = assets.get("ship.obj", Model.class);
        ModelInstance shipInstance = new ModelInstance(ship); 
        instances.add(shipInstance);
        loading = false;
    }
	@Override
	public void dispose() {		
		modelBatch.dispose();
        instances.clear();
        assets.dispose();
	}

	@Override
	public void render() {	
		if (loading && assets.update())
            doneLoading();
		
		camController.update();
        
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
 
        modelBatch.begin(cam);
        for (ModelInstance instance : instances)
            modelBatch.render(instance, lights);
        modelBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
