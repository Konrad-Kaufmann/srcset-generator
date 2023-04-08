package com.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.awt.Image;
import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cz.vutbr.web.css.CSSException;
import cz.vutbr.web.css.CSSFactory;
import cz.vutbr.web.css.StyleSheet;
import cz.vutbr.web.domassign.Analyzer;

public class MainApp {
    private int[] sizes = { 1920, 1600, 1366, 1024, 768, 640 };
    private String outputFormat = "webp";

    public MainApp() throws InterruptedException {
        start();
    }

    private void start() throws InterruptedException {

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the absolute path to the html-file:");
        String path = in.nextLine();

        // imgMode(path, dimension, sizes);

        try {
            codeMode(path, sizes, in);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * This method resizes an BufferedImage to the specified dimensions.
     * <p>
     * It achieves that by internaly calling the
     * {@link #resizeImage(BufferedImage, int, int)}-method.
     * 
     * @param original - the original {@link BufferedImage}
     * @param scale    - the scaling as {@link double}
     * @return resized {@link BufferedImage} as a new Object
     * 
     * @see #resizeImage(BufferedImage, int)
     * @see #resizeImage(BufferedImage, int, int)
     */
    private BufferedImage resizeImage(BufferedImage original, double scale) {
        return resizeImage(original, (int) (scale * original.getWidth()), (int) (scale * original.getHeight()));
    }

    /**
     * This method resizes an BufferedImage to the specified dimensions. It scales
     * the image to the original proportion, to a given {@code width}.
     * <p>
     * It achieves that by internaly calling the
     * {@link #resizeImage(BufferedImage, int, int)}-method.
     * 
     * @param original - the original {@link BufferedImage}
     * @param targetW  - the width of the returned {@code BufferedImage}
     * @return resized {@link BufferedImage} as a new Object
     * 
     * @see #resizeImage(BufferedImage, double)
     * @see #resizeImage(BufferedImage, int, int)
     */
    private BufferedImage resizeImage(BufferedImage original, int targetW) {
        // calculate target height while applying the same resolution
        return resizeImage(original, targetW, (int) ((original.getHeight() * targetW) / original.getWidth()));
    }

    /**
     * This method resizes an BufferedImage to the specified dimensions.
     * 
     * @param original - the original {@link BufferedImage}
     * @param targetW  - the width of the returned {@code BufferedImage}
     * @param targetH  - the height of the returned {@code BufferedImage}
     * @return resized {@link BufferedImage} as a new Object
     * @see #resizeImage(BufferedImage, double)
     * @see #resizeImage(BufferedImage, int)
     */
    private BufferedImage resizeImage(BufferedImage original, int targetW, int targetH) {
        Image result = original.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
        BufferedImage output = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_RGB);
        output.getGraphics().drawImage(result, 0, 0, null);
        return output;
    }

    /**
     * Creates all the pictures for an given path to an picture and then saves them
     * in the same subfolder.
     * <p>
     * It creates the picures by resizing them with
     * {@link #resizeImage(BufferedImage, int)}-method. It resizes them to every
     * size in the {@code sizes[]}-Array multiplied by {@code dimension/100}.
     * <p>
     * It saves them using the
     * {@link #saveImages(BufferedImage[], File, String)}-method and returns their
     * names.
     * 
     * @param filePath  - file path to the picture as string
     * @param dimension - in vw, size of the picture on the page
     * @param sizes     - int[] with the standard-sizes of the pictures in px
     * 
     * @return {@link Set} of {@link String} with the filenames of the new images
     * 
     * @see #resizeImage(BufferedImage, int)
     * @see #saveImages(BufferedImage[], File, String)
     */
    public Set<String> imgMode(String filePath, int dimension, int[] sizes) {
        return imgMode(new File(filePath), dimension, sizes);
    }

    /**
     * Creates all the pictures for an given file describing an picture and then
     * saves them
     * in the same subfolder.
     * <p>
     * It creates the picures by resizing them with
     * {@link #resizeImage(BufferedImage, int)}-method. It resizes them to every
     * size in the {@code sizes[]}-Array multiplied by {@code dimension/100}.
     * <p>
     * It saves them using the
     * {@link #saveImages(BufferedImage[], File, String)}-method and returns their
     * names.
     * 
     * @param originalFile - file of the original picure
     * @param dimension    - in vw, size of the picture on the page
     * @param sizes        - int[] with the standard-sizes of the pictures in px
     * 
     * @return {@link Set} of {@link String} with the filenames of the new images
     * 
     * @see #resizeImage(BufferedImage, int)
     * @see #saveImages(BufferedImage[], File, String)
     */
    public Set<String> imgMode(File originalFile, int dimension, int[] sizes) {
        BufferedImage original = null;
        BufferedImage[] newImages = new BufferedImage[sizes.length];
        try {
            original = ImageIO.read(originalFile);
            for (int i = 0; i < sizes.length; i++) {
                if (original.getWidth() >= (int) (sizes[i] * dimension / 100)) {
                    newImages[i] = resizeImage(original, (int) (sizes[i] * dimension / 100));
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }
        return saveImages(newImages, originalFile, outputFormat);
    }

    /**
     * Asks for an dimension via {@link Scanner} of {@code System.in} and then
     * parses the dimension with the other parameters on to
     * {@link #imgMode(File, int, int[])}.
     * 
     * @param originalFile - file of the original picure
     * @param sizes        - int[] with the standard-sizes of the pictures in px
     * 
     * @return {@link Set} of {@link String} with the filenames of the new images
     * 
     * @see #imgMode(File, int, int[])
     * @see #resizeImage(BufferedImage, int)
     * @see #saveImages(BufferedImage[], File, String)
     */
    public Set<String> imgMode(File originalFile, int[] sizes) {
        Scanner in = new Scanner(System.in);
        int dimension = -1;

        System.out.println(
                "Please enter image width in percentage of screen size (1-100) or \"skip\" if you want to include this particular image:");
        while (dimension < 1) {

            String inputS = in.nextLine();
            if (inputS.equalsIgnoreCase("skip")) {
                in.close();
                return null;
            } else if (inputS.matches("[1-9]|[1-9][0-9]|100)")) {
                dimension = in.nextInt();
            } else {
                System.out.println("please enter a valid input");
            }
        }
        in.close();
        return imgMode(originalFile, dimension, sizes);
    }

    /**
     * This function saves an {@code BufferdImage[]} to the same folder as the
     * original file.
     * The naming of the saved {@link File}'s is the following:
     * 
     * <pre>
     * {@code
     * originalFile.getName() + "_"
     * } without the type, followed by the size of the {@link BufferedImage}, ended by {@code
     * "px" + "."
     * } and the {@code
     * fileType
     * }.
     * </pre>
     * 
     * @param images       the images to be saved
     * @param originalFile the originalFile
     * @param fileType     the fileType as which the image should be saved
     * 
     * @return {@link Set} of {@link String} with the filenames of the new images.
     * 
     * @throws IOException
     */
    private Set<String> saveImages(BufferedImage[] images, File originalFile, String fileType) {
        System.out.println(originalFile.getParent());
        Set<String> fileNames = new HashSet<>();
        for (BufferedImage img : images) {
            if (img != null) {
                // creates a new file in the same folder (parent) with the same name of the old
                // file (without the ending) + the size + the ending of the exported file
                File outputFile = new File(originalFile.getParent(),
                        originalFile.getName().replaceFirst("[.][^.]+$", "") + "_" + img.getWidth() + "."
                                + outputFormat);
                try {
                    ImageIO.write(img, outputFormat, outputFile);
                    fileNames.add(outputFile.getName());
                } catch (IOException e) {
                    System.out.println("afhsjifh");
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return fileNames;

    }

    /**
     * 
     * @param filePath
     * @param sizes
     * @throws IOException
     */
    public void codeMode(String filePath, int[] sizes, Scanner in) throws IOException {
        // File f = new File(filePath);
        Document doc = Jsoup.parse(Files.readString(Paths.get(filePath)));

        Elements images = (Elements) doc.getElementsByTag("img");

        imageIteratorLoop:
        for (Element img : images) {

            String src = img.attributes().get("src");
            System.out.println("src attribute is : " + src);
            Path absImgPath = Path.of(filePath).resolve("../").resolve(src).normalize();
            System.out.println(absImgPath.toString());
            // ALERT: TODO: HINT: dimension 100 needs to be replaced
            StringBuilder sb = new StringBuilder();
            Path relativeParentFolder = Path.of(img.attributes().get("src")).getParent();
            String srcName = Path.of(img.attributes().get("src")).getFileName().toString();

            int dimension = -1;

            System.out.println(
                    "Please enter image width in percentage of screen size (1-100) or \"skip\" if you want to include this particular image:");
            while (dimension < 1) {

                String inputS = in.nextLine();
                if (inputS.equalsIgnoreCase("skip")) {
                    continue imageIteratorLoop;
                } else if (inputS.matches("[1-9]|[1-9][0-9]|100)")) {
                    dimension = in.nextInt();
                } else {
                    System.out.println("please enter a valid input");
                }
            }


            for (String imgName : imgMode(absImgPath.toFile(), dimension, sizes)) {
                System.out.println(imgName);
                // find width of image from the string, since its already storesd int here and
                // everything else would be inefficient
                int imgWidth = Integer.parseInt(imgName.substring(srcName.length() - 4, imgName.length() - 5));
                sb.append(relativeParentFolder.toString() + "/" + imgName + " " + imgWidth + "w,");
            }
            // sometimes no image is needed, since the resolution is already low enough
            if (sb.length() > 0) {
                // Delete the last "," thats to much
                sb.deleteCharAt(sb.length() - 1);
                img.attr("srcset", sb.toString());
                // append the size in vw
                // findSizeVW(img, doc, Path.of(filePath));
                img.attr("sizes", dimension + "vw");
            }
        }
        // save the html
        Files.writeString(Path.of(filePath), doc.outerHtml());
        in.close();
    }

    // ONLY works with utf-8 stylesheets
    private int findSizeVW(Element img, Document doc, Path filePath) {
        // First we need to load all css
        Set<Path> StyleSheetPaths = getStyleSheetPath(doc);
        System.out.println(StyleSheetPaths.toString());
        ArrayList<StyleSheet> css = new ArrayList<>();
        // creatre the StyleSheets
        for (Path cssPath : StyleSheetPaths) {
            try {
                css.add(CSSFactory.parse(filePath.resolve("../").resolve(cssPath).normalize().toUri().toURL(),
                        "utf-8"));
                System.out.println(css.size());
            } catch (CSSException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Analyzer analyzer = new Analyzer(css);
        // StyleMap sM = analyzer.evaluateDOM(doc, new MediaSpecAll(), true);
        File htmlFile = new File(filePath.toAbsolutePath().toString());

        // analyzer.evaluateDOM(htmlFile,new MediaSpec("screen"), false);

        Stack<Element> stack = new Stack<>();
        Element currentElement = img;
        stack.push(currentElement);

        while (currentElement.parent() != null) {
            currentElement = currentElement.parent();
            stack.push(currentElement);
        }

        return 0;
    }

    /**
     * This method finds all {@code stylesheets} used in the {@code doc} and returns
     * their {@link Path}.
     * 
     * @param doc - the JSoup {@link Document} refering to the {@code HTML}-File
     * @return {@link Set} of {@link Path} with (relative) paths to the Stylesheets
     *         used in this Document
     */
    private Set<Path> getStyleSheetPath(Document doc) {
        Set<Path> pathSet = new HashSet<>();
        Elements cssLinks = doc.select("link[rel=stylesheet]");
        for (Element cssLink : cssLinks) {
            pathSet.add(Path.of(cssLink.attributes().get("href")));
        }
        return pathSet;
    }

}
