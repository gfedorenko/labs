package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

public class Main extends SimpleApplication {
       
    private Spatial Cannon;
    private Sphere mySphere;
    private Geometry geom;
    private Boolean isOn = false;
    
    float x , y , z , nx , ny , nz;
    static float g = -0.098f;
    float Resistanse = 0.999f;
    float Rubbing = 0.9f;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
       // inititalizing background
       Node backgroundModel = new Node("scene node");
       backgroundModel.attachChild(initGround());

       CollisionShape backgroundShape = CollisionShapeFactory.createMeshShape((Node) backgroundModel);
       RigidBodyControl landscape = new RigidBodyControl(backgroundShape, 0);
       backgroundModel.addControl(landscape);
       rootNode.attachChild(backgroundModel);
       
       viewPort.setBackgroundColor(ColorRGBA.White);
       
       // initializing cannon ball
       mySphere = new Sphere( 20, 20, 4 );
       geom = new Geometry("Sphere", mySphere);
       Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
       material.setColor("Color",ColorRGBA.Black);
       geom.setMaterial(material);

       // initial coordinates
       x = 1.5f; 
       y = 32.5f;
       z = 45.0f;
       
       // initial vector for the flight
       nx = 0.0f;
       ny = 9f;
       nz = 25f;
       
       geom.setLocalTranslation(x,y,z);
       
       rootNode.attachChild(geom);
      
       // initializing cannon model
       Cannon = assetManager.loadModel("Models/NavalCannon.obj");
       Cannon.setLocalTranslation(1f, 13f, 0f);
//        Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
//        Cannon.setMaterial(mat_default);
        rootNode.attachChild(Cannon);
      
        // setting up the light
        DirectionalLight light = new DirectionalLight();
        
        light.setColor(ColorRGBA.White);
        light.setDirection(new Vector3f(10f, -10f, -10f).normalizeLocal());
        rootNode.addLight(light);
        
        // setting up the camera
        flyCam.setMoveSpeed(30);
        cam.setLocation(new Vector3f(-80f,60f,200f));
        
        initKeys();
        
      
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(!isOn)return;
        
        // check if we need to change direction
        if(y <  4f ) {ny = -ny * 0.8f; y = 4f; nz *= Rubbing;}
        
        // changing vector coordinates for physics
        ny *= Resistanse;
        ny += g * tpf;
        nz *= Resistanse;
        
        z += tpf * nz;
        x += tpf * nx;
        y += tpf * ny;
        
        geom.setLocalTranslation(x,y,z);
       
        
       // tracing the ball
       Sphere  trace = new Sphere( 30, 30, 1f );
       Geometry geomTrace = new Geometry("Sphere", trace);
       Material matTrace = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
       
       matTrace.setColor("Color",ColorRGBA.Green);
       geomTrace.setMaterial(matTrace);
       geomTrace.setLocalTranslation(x,y,z);
       rootNode.attachChild(geomTrace);
             
    }
    
    
protected Geometry initGround() {
    Box box = new Box(1500, 0.01f, 1500);
    Geometry floor = new Geometry("the Floor", box);
    floor.setLocalTranslation(0, 0, 0);
    Material material1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    material1.setColor("Color", ColorRGBA.Brown);
    floor.setMaterial(material1);
    return floor;
}

  private void initKeys() {
    
    // mappig inputs to the actions
    inputManager.addMapping("Start",  new KeyTrigger(KeyInput.KEY_0));
    inputManager.addMapping("Up",   new KeyTrigger(KeyInput.KEY_N));
    inputManager.addMapping("Down",   new KeyTrigger(KeyInput.KEY_M));
                                     
    // initializing action listeners
    inputManager.addListener(actionListener,"Start");
    inputManager.addListener(actionListener,"Up");
    inputManager.addListener(actionListener,"Down");
 
  }
    private final  ActionListener actionListener = new ActionListener() {
    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {
     
      if (name.equals("Start") && !keyPressed) {
        isOn = true;
      }
      
      // changing the resistance
      if(name.equals("Up") && !keyPressed)  g += (float) 0.5;  
      if(name.equals("Down") && !keyPressed)  g += (float) -1;      
    }
  };
}
