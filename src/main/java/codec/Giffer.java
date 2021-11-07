package codec;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

public abstract class Giffer {

    public static void generateFromFiles(String[] filenames, String output, int delay, boolean loop)
            throws IIOException, IOException {
        int length = filenames.length;
        BufferedImage[] img_list = new BufferedImage[length];

        for (int i = 0; i < length; i++) {
            BufferedImage img = ImageIO.read(new File(filenames[i]));
            img_list[i] = img;
        }

        generateFromBI(img_list, output, delay, loop);
    }

    public static void generateFromBI(BufferedImage[] images, String output, int delay, boolean loop)
            throws IIOException, IOException {
        ImageWriter gifWriter = getWriter();
        ImageOutputStream ios = getImageOutputStream(output);
        IIOMetadata metadata = getMetadata(gifWriter, delay, loop);

        gifWriter.setOutput(ios);
        gifWriter.prepareWriteSequence(null);
        for (BufferedImage img : images) {
            IIOImage temp = new IIOImage(img, null, metadata);
            gifWriter.writeToSequence(temp, null);
        }
        gifWriter.endWriteSequence();
    }

    private static ImageWriter getWriter() throws IIOException {
        Iterator<ImageWriter> itr = ImageIO.getImageWritersByFormatName("gif");
        if (itr.hasNext())
            return itr.next();

        throw new IIOException("GIF writer doesn't exist on this JVM!");
    }

    private static ImageOutputStream getImageOutputStream(String output) throws IOException {
        File outfile = new File(output);
        return ImageIO.createImageOutputStream(outfile);
    }

    private static IIOMetadata getMetadata(ImageWriter writer, int delay, boolean loop)
            throws IIOInvalidTreeException {
        ImageTypeSpecifier img_type = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_ARGB);
        IIOMetadata metadata = writer.getDefaultImageMetadata(img_type, null);
        String native_format = metadata.getNativeMetadataFormatName();
        IIOMetadataNode node_tree = (IIOMetadataNode) metadata.getAsTree(native_format);

        IIOMetadataNode graphics_node = getNode("GraphicControlExtension", node_tree);
        graphics_node.setAttribute("delayTime", String.valueOf(delay));
        graphics_node.setAttribute("disposalMethod", "none");
        graphics_node.setAttribute("userInputFlag", "FALSE");

        if (loop)
            makeLoopy(node_tree);

        metadata.setFromTree(native_format, node_tree);

        return metadata;
    }

    private static void makeLoopy(IIOMetadataNode root) {
        IIOMetadataNode app_extensions = getNode("ApplicationExtensions", root);
        IIOMetadataNode app_node = getNode("ApplicationExtension", app_extensions);

        app_node.setAttribute("applicationID", "NETSCAPE");
        app_node.setAttribute("authenticationCode", "2.0");
        app_node.setUserObject(new byte[]{0x1, (byte) (0 & 0xFF), (byte) ((0 >> 8) & 0xFF)});

        app_extensions.appendChild(app_node);
        root.appendChild(app_extensions);
    }


    private static IIOMetadataNode getNode(String node_name, IIOMetadataNode root) {
        IIOMetadataNode node = null;

        for (int i = 0; i < root.getLength(); i++) {
            if (root.item(i).getNodeName().compareToIgnoreCase(node_name) == 0) {
                node = (IIOMetadataNode) root.item(i);
                return node;
            }
        }

        node = new IIOMetadataNode(node_name);
        root.appendChild(node);

        return node;
    }

}