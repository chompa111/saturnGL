package graphical.basics.gobject;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.task.SingleStepTask;
import graphical.basics.task.Task;
import graphical.basics.value.DoubleHolder;
import graphical.basics.value.NumberHolder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Image extends Gobject {

    Location location;

    NumberHolder opacity= new DoubleHolder(1);

    int width;
    int heith;

    java.awt.Image image;

    public Image(Location location,java.awt.Image image) {
        this.location=location;
        this.image = image;
        width=image.getWidth(null);
        heith=image.getHeight(null);
    }

    public Image(Location location,String path){
        this.location=location;
        try {
            image = ImageIO.read(new File(path));
            width=image.getWidth(null);
            heith=image.getHeight(null);
        } catch (IOException e) {
            throw  new RuntimeException("não foi possivel carregar a imagem :"+path);
        }
    }

    public Image(Location location,File file){
        this.location=location;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw  new RuntimeException("não foi possivel carregar a imagem :"+file.getAbsolutePath());
        }
    }

    @Override
    public void paint(Graphics g) {
        var beforeComposite=  ((Graphics2D) g).getComposite();
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) opacity.getValue()));

        g.drawImage(image, (int) location.getX() - (width / 2), (int) location.getY() - (heith / 2), null);
        ((Graphics2D) g).setComposite(beforeComposite);
    }

    public LocationPair getBorders() {
        return new LocationPair(Location.at(location.getX() - (width * scale.getValue()*0.5), location.getY() - (heith * scale.getValue()*0.5)), Location.at(location.getX() + (width * scale.getValue()*0.5), location.getY() + (heith * scale.getValue()*0.5)));
    }

    @Override
    public List<ColorHolder> getColors() {
        return Arrays.asList();
    }

    @Override
    public List<Location> getRefereceLocations() {
        return Arrays.asList(location);
    }

    public NumberHolder getOpacity() {
        return opacity;
    }

    public void setOpacity(NumberHolder opacity) {
        this.opacity = opacity;
    }
}
