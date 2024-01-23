package renderer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    public void writeToImage() {
        ImageWriter imageWriter = new ImageWriter("test", 800, 500);
    }
}