package codec;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    private static final String FORMAT_NAME = "gif";

    public static void generateFromBI(final BufferedImage[] images, String output, int delay, boolean loop)
            throws IOException {
        final var gifWriter = getWriter();
        final var ios = getImageOutputStream(output);
        final var metadata = getMetadata(gifWriter, delay, loop);

        gifWriter.setOutput(ios);
        gifWriter.prepareWriteSequence(null);
        for (BufferedImage img : images) {
            final var temp = new IIOImage(img, null, metadata);
            gifWriter.writeToSequence(temp, null);
        }
        gifWriter.endWriteSequence();
    }

    private static ImageWriter getWriter() throws IIOException {
        final var itr = ImageIO.getImageWritersByFormatName(FORMAT_NAME);
        if (itr.hasNext())
            return itr.next();

        throw new IIOException("GIF writer doesn't exist on this JVM!");
    }

    private static ImageOutputStream getImageOutputStream(String output) throws IOException {
        final var outfile = new File(output);
        return ImageIO.createImageOutputStream(outfile);
    }

    private static IIOMetadata getMetadata(final ImageWriter writer, int delay, boolean loop)
            throws IIOInvalidTreeException {
        final var imgType = ImageTypeSpecifier.createFromBufferedImageType(TYPE_INT_ARGB);
        final var metadata = writer.getDefaultImageMetadata(imgType, null);
        final var nativeFormat = metadata.getNativeMetadataFormatName();
        final var nodeTree = (IIOMetadataNode) metadata.getAsTree(nativeFormat);

        setupGraphicsNode(delay, nodeTree);

        if (loop)
            makeLoopy(nodeTree);

        metadata.setFromTree(nativeFormat, nodeTree);

        return metadata;
    }

    private static void setupGraphicsNode(int delay, final IIOMetadataNode nodeTree) {
        final var graphicsNode = getNode("GraphicControlExtension", nodeTree);
        graphicsNode.setAttribute("delayTime", String.valueOf(delay));
        graphicsNode.setAttribute("disposalMethod", "none");
        graphicsNode.setAttribute("userInputFlag", "FALSE");
    }

    private static void makeLoopy(final IIOMetadataNode root) {
        final var appExtensions = getNode("ApplicationExtensions", root);
        final var appNode = setupAppNode(appExtensions);
        appExtensions.appendChild(appNode);
        root.appendChild(appExtensions);
    }

    private static IIOMetadataNode setupAppNode(IIOMetadataNode appExtensions) {
        final var appNode = getNode("ApplicationExtension", appExtensions);
        appNode.setAttribute("applicationID", "NETSCAPE");
        appNode.setAttribute("authenticationCode", "2.0");
        appNode.setUserObject(new byte[]{0x1, (byte) (0 & 0xFF), (byte) ((0 >> 8) & 0xFF)});
        return appNode;
    }

    private static IIOMetadataNode getNode(String nodeName, final IIOMetadataNode root) {
        for (int i = 0; i < root.getLength(); i++) {
            final var rootNodeName = root.item(i).getNodeName();
            if (nameMatch(nodeName, rootNodeName))
                return (IIOMetadataNode) root.item(i);
        }

        final var node = new IIOMetadataNode(nodeName);
        root.appendChild(node);
        return node;
    }

    private static boolean nameMatch(String nodeName, String rootNodeName) {
        return rootNodeName.compareToIgnoreCase(nodeName) == 0;
    }
}