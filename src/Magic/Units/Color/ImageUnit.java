/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Color;


 import java.awt.Color;
 import java.awt.image.BufferedImage;

 public abstract class ImageUnit
 {
   protected Color color;
   protected BufferedImage image;
   protected int width;
   protected int height;

   ImageUnit(Color color, int width, int height)
   {
     this.color = color;
     this.width = width;
     this.height = height;
   }

   public BufferedImage getImage() {
     return this.image; }

   public Color getColor() {
     return this.color;
   }
 } 