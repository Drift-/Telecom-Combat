package city.graphics.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class CustomFont {
	
	public Font font(int size) {
		Font customFont = null;
		InputStream file = CustomFont.class.getResourceAsStream("/fonts/font.ttf");
		try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont((float)size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
        }
		return customFont;
	}

}
