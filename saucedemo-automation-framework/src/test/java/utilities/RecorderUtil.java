package utilities;

import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class RecorderUtil {

    private static ScreenRecorder screenRecorder;

    public static void startRecording(String testName) throws Exception {

        File folder = new File("test-recordings");
        if (!folder.exists()) {
            folder.mkdir();
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle captureSize = new Rectangle(0, 0, screenSize.width, screenSize.height);

        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();


        Format fileFormat = new Format(
                MediaTypeKey, MediaType.FILE,
                MimeTypeKey, MIME_AVI
        );


        Format screenFormat = new Format(
                MediaTypeKey, MediaType.VIDEO,
                EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                DepthKey, 24,
                FrameRateKey, new Rational(15, 1),
                QualityKey, 1.0f,
                KeyFrameIntervalKey, 15 * 60
        );

        // Ses kullanmıyoruz, istersen burada gerçek bir Audio formatı tanımlayabilirsin
        Format audioFormat = null;

        screenRecorder = new SpecializedScreenRecorder(
                gc,
                captureSize,
                fileFormat,
                screenFormat,
                audioFormat,
                null,
                folder,
                testName
        );

        screenRecorder.start();
    }

    public static void stopRecording() throws Exception {
        if (screenRecorder != null) {
            screenRecorder.stop();
            screenRecorder = null;
        }
    }
}

class SpecializedScreenRecorder extends ScreenRecorder {

    private final String name;

    public SpecializedScreenRecorder(GraphicsConfiguration cfg,
                                     Rectangle captureArea,
                                     Format fileFormat,
                                     Format screenFormat,
                                     Format audioFormat,
                                     Format mouseFormat,
                                     File movieFolder,
                                     String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    @Override
    protected File createMovieFile(Format fileFormat) {
        return new File("test-recordings/" + name + ".avi");
    }
}
