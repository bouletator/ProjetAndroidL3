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
                Log.v("TEST", "x = " + r*caseHeight + ". y = " + c*caseWidth);
                points.add(new Point(r * caseHeight, c * caseWidth));
            }
        }
    }

    public Point closestPoint(Point point) {
        Point out   = new Point(0, 0);
        double dist, mini; int dx, dy;

        mini = 10000;
        for (Point p : points) {
            dx = point.x - p.x;
            dy = point.y - p.y;

            dist = Math.sqrt(dx*dx + dy*dy);
            if (dist < mini) {
                mini = dist;
                out = p;
            }
        }

        return out;
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
