package info.ups.fr.puzzlegame_template;

import android.graphics.Point;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  leocances
 */
public class Grid {
    private List<Point> points;
    private int caseWidth, caseHeight;

    public Grid() {
        this.points = new ArrayList<Point>();
    }

    public Grid(int nbPiece, int width, int height) {
        this.caseHeight         = width / (nbPiece + 2);
        this.caseWidth          = height / (nbPiece + 2);
        final int edgeNbCase    = (int) Math.sqrt(nbPiece + 2);
        this.points = new ArrayList<Point>(nbPiece + 2);

        for (int r = 0; r < edgeNbCase; ++r) {
            for (int c = 0; c < edgeNbCase; ++c) {
                points.add(new Point(r * caseHeight, c * caseWidth));
            }
        }
    }

    public Point closestPoint(Point point) {


        for (Point p : points) {
            if (p.x < point.x && point.x < p.x + caseWidth) {
                if (p.y < point.y && point.y < p.y + caseHeight) {
                    return p;
                }
            }
        }

        return null;
    }

    public Point closestPoint(int x, int y) {
        return closestPoint(new Point(x, y));
    }

    public void setCaseDim(int width, int height) {
        this.caseWidth = width;
        this.caseHeight = height;
    }

    public List<Point> getPoints() {return this.points;}
}
