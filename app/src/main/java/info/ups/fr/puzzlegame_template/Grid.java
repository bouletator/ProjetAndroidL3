package info.ups.fr.puzzlegame_template;

import android.graphics.Point;
import android.util.Log;

/**
 * Created by leocances on 25/03/15.
 */
public class Grid {
    private Point[] points;
    private int caseWidth, caseHeight;

    public Grid() {
        this.points = new Point[10];
    }

    public Grid(int nbPiece, int width, int height) {
        final int columnWidth   = width / nbPiece;
        final int rowHeight     = height / nbPiece;
        final int edgeNbCase    = (int) Math.sqrt(nbPiece + 2);
        this.points = new Point[nbPiece+2];
        this.caseHeight = rowHeight;
        this.caseWidth = columnWidth;

        int compteur = 0;
        for (int r = 0; r < edgeNbCase; ++r) {
            for (int c = 0; c < edgeNbCase; ++c) {
                points[compteur] = new Point(r*rowHeight, c*columnWidth);
                compteur += 1;
            }
        }
    }

    public Point closestPoint(Point point) {
        int posX, posY;

        for (Point p : points) {
            posX = p.x;
            posY = p.y;

            if (posX < point.x && point.x < posX + caseWidth) {
                if (posY < point.y && point.y < posY + caseHeight) {
                    Log.v("TEST", p.toString());
                    return p;
                }
            }
        }

        return null;
    }

    public Point closestPoint(int x, int y) {
        return closestPoint(new Point(x, y));
    }

    public Point[] getPoints() {return this.points;}
}
