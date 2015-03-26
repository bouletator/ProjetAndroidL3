package info.ups.fr.puzzlegame_template;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Piece extends Drawable{
    private final int id;
    private int idG, posX, posY;
    private Bitmap image;

    public Piece(int id, int idG, Bitmap image) {
        this.id     = id;
        this.idG    = idG;
        this.posX   = 0;
        this.posY   = 0;
        this.image  = image;
    }

    public int getIdentifiant() {return id;}
    public int getIdG() {return idG;}
    public int getPosX() {return posX;}
    public int getPosY() {return posY;}
    public Bitmap getImage() {return image;}

    public void setCoord(int x, int y) {
        posX = x - image.getWidth() / 2;
        posY = y - image.getHeight() / 2;
        if (posX < 0)           posX = 0;
        else if (posX > 300)    posX = 300;

        if (posY < 0)           posY = 0;
        else if (posY > 300)    posY = 300;
    }

    public void setCoord(Point p) {
        setCoord(p.x, p.y);
    }

    public void setImage(Bitmap image) {this.image = image;}
    public void setPos(int pos) {this.idG = pos;}

    public boolean isWellPlaced() {return id == idG;}

    public boolean contains(int x, int y) {
        final int dx = image.getWidth();
        final int dy = image.getHeight();

        if (posX < x && x < posX + dx) {
            if (posY < y && y < posY + dy) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, posX, posY, null);
    }

    @Override
    public void setAlpha(int alpha) {}

    @Override
    public void setColorFilter(ColorFilter cf) {}

    @Override
    public int getOpacity() {return 0;}
}