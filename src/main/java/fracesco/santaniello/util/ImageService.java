package fracesco.santaniello.util;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;

public class ImageService {

    private Image appleImage;

    private static class InnerClass{
        private static final ImageService instance = new ImageService();
    }

    private ImageService(){
        try{
            appleImage = ImageIO.read(new File("./source/image/food/apple.png"));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static ImageService getInstance(){
        return InnerClass.instance;
    }

    public Image getAppleImage(){
        return appleImage;
    }
}
