package renderer;

import static java.awt.Color.*;

import XMLTool.xmlTool;
import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.AmbientLight;
import org.xml.sax.SAXException;
import primitives.*;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


/** Test rendering a basic image
 * @author Dan */
public class RenderTests {
   /** Scene of the tests */
   private final Scene          scene  = new Scene("Test scene");
   /** Camera builder of the tests */
   private final Camera.Builder camera = Camera.getBuilder()
      .setRayTracer(new SimpleRayTracer(scene))
      .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), Vector.Y)
      .setVpDistance(100)
      .setVpSize(500, 500);

   /** Produce a scene with basic 3D model and render it into a png image with a
    * grid
    * @throws CloneNotSupportedException*/
   @Test
   public void renderTwoColorTest() throws CloneNotSupportedException {
      scene.geometries.add(new Sphere(new Point(0, 0, -100), 50d),
                           new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                           // left
                           new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                                        new Point(-100, -100, -100)), // down
                           // left
                           new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
      scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), Double3.ONE))
         .setBackground(new Color(75, 127, 90));

      // right
      camera
         .setImageWriter(new ImageWriter("base render test", 1000, 1000))
         .build()
         .renderImage()
         .printGrid(100, new Color(YELLOW))
         .writeToImage();
   }

   /** Test for XML based scene - for bonus */
   @Test
   public void basicRenderXml() throws CloneNotSupportedException, ParserConfigurationException, IOException, SAXException {
       Scene temp = xmlTool.renderFromXmlFile("XmlRender/renderTestTwoColors.xml");
       scene.setAmbientLight(temp.ambientLight).setBackground(temp.background).setGeometries(temp.geometries);
        camera
            .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
            .build()
            .renderImage()
            .printGrid(100, new Color(YELLOW))
            .writeToImage();
    }
}


