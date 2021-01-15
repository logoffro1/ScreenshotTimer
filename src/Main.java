import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main
{

    public static void main(String[] args)
    {
        Main main = new Main();
        main.Start();
    }

    void Start()
    {
        GraphicsDevice[] devices = getDevices();
        assert devices != null;
        GraphicsConfiguration conf = devices[0].getDefaultConfiguration();
        int minX = (int) conf.getBounds().getWidth() * -1;
        int maxX = Math.abs((int) conf.getBounds().getWidth());

        takeScreenshot(minX, maxX, devices.length);
    }

    private GraphicsDevice[] getDevices()
    {

        try
        {
            // Get local graphics environment
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

            // Returns an array of all of the screen GraphicsDevice objects.
            return env.getScreenDevices();
        } catch (HeadlessException e)
        {
            // We'll get here if no screen devices was found.
            e.printStackTrace();
        }
        return null;
    }

    private void takeScreenshot(int minX, int maxX, int screens)
    {
        try
        {
            Robot bot = new Robot();

            Dimension d = new Dimension(maxX * screens, 864);
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter wait time between screenshots: ");
            int sleepTime = scanner.nextInt();
            int count = 0;
            while (true)
            {
                BufferedImage image = bot.createScreenCapture(new Rectangle(new Point(minX, 0), d));
                String imgName = String.format("screenshot%d.png", count++);
                ImageIO.write(image, "png", new File(imgName));
                System.out.println(imgName);
                TimeUnit.SECONDS.sleep(sleepTime);
            }

        } catch (Exception e)
        {
            e.printStackTrace();

        }
    }
}
