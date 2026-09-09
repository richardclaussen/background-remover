import java.io.File;

public class ImageProcessorService {

    public int removeBackground(
            File inputFile,
            String outputPath
    ) throws Exception {

        ProcessBuilder pb = new ProcessBuilder(
                ".venv\\Scripts\\python.exe",
                "python\\remove_bg.py",
                inputFile.getAbsolutePath(),
                outputPath
        );

        Process process = pb.start();

        return process.waitFor();
    }
}