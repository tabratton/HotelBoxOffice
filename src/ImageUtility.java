import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageUtility {

  /**
   * Utility method to get an image from a specified URL.
   *
   * @param imageUrl The URL of the image to get.
   * @param width    The width that you want the image to be resized to.
   * @param height   The height that you want the image to be resized to.
   * @return         An ImageView object that has the desired dimensions
   */
  public static ImageView getImage(String imageUrl, int width, int height) {
    try {
      URL url = new URL(imageUrl);
      BufferedImage bf = ImageIO.read(url);
      bf = Scalr.resize(bf, width, height);
      Image image = SwingFXUtils.toFXImage(bf, null);
      return new ImageView(image);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }
}
