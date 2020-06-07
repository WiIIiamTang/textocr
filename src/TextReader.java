import org.sikuli.basics.Debug;
import org.sikuli.script.Screen;
import org.sikuli.script.Region;

public class TextReader {
    private Region region;
    private final Screen screen;

    public TextReader()
    {
        this.screen = new Screen();
        Debug.info("Screen: " + this.screen.getBounds());
    }

    public void setRegion()
    {
        region = screen.selectRegion();
    }

    public Region getRegion()
    {
        return region;
    }

    public String ocr()
    {
        if (screen == null)
        {
            return new String("");
        }
        else
        {
            return region.text();
        }
    }
}
