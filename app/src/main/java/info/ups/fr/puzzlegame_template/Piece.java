package info.ups.fr.puzzlegame_template;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

/**
 * @author Léo cances
 */
public class Piece extends Drawable{
    private int idG, posX, posY;
    private final int id;
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

    /**
     * Positionne la pièce en fonction du doigh
     * @param x
     * @param y
     */
    public void setMoveCoord(int x, int y) {
        posX = x - image.getWidth() / 2;
        posY = y - image.getHeight() / 2;

        checkEdge();
    }


    /**
     * Position la pièce en fonction de son coin supérieur gauche
     * @param x
     * @param y
     */
    public void setCoord(int x, int y) {
        posX = x;
        posY = y;

        checkEdge();
    }

    /**
     * Positionne la pièce en fonction du doigh
     * @param p
     */
    public void setMoveCoord(Point p) {
        setMoveCoord(p.x, p.y);
    }

    /**
     * Positionne la pièce en fonction de son coin supérieur gauche
     * @param p
     */
    public void setCoord(Point p) {
        setCoord(p.x, p.y);
    }

    private void checkEdge() {
        if (posX < Puzzle.left)
            posX = Puzzle.left;
        else if (posX > Puzzle.width)
            posX = Puzzle.width;

        if (posY < Puzzle.top)
            posY = Puzzle.top;
        else if (posY > Puzzle.height)
            posY = Puzzle.height;
    }

    /**
     * Modifie l'image affiché par la pièce
     * @param image
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Modifie l'identifiant de grille de la pièce
     * @param pos
     */
    public void setIdG(int pos) {
        this.idG = pos;
    }

    /**
     * Retourne vrai si la pièce est dans la bonne position
     * @return boolean
     */
    public boolean isWellPlaced() {return id == idG;}

    /**
     * Retourne vrai si les coordonnées se trouve à l'intérieur de l'image
     * @param x
     * @param y
     * @return boolean
     */
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

    /**
     * Dessine la piece.
     * @param canvas
     */
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