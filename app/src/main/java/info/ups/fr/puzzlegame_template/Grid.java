package info.ups.fr.puzzlegame_template;

import android.graphics.Point;
import android.util.Log;

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
        this.caseHeight         = height;
        this.caseWidth          = width;
        final int edgeNbCase    = (int) Math.sqrt(nbPiece);
        this.points = new ArrayList<Point>(nbPiece);

        for (int r = 0; r < edgeNbCase; ++r) {
            for (int c = 0; c < edgeNbCase; ++c) {
                points.add(new Point(   r*caseWidth + (PieceClassTest.width-edgeNbCase*this.caseWidth)/2,
                                        c * caseHeight + (PieceClassTest.height-edgeNbCase*this.caseHeight)/2));
            }
        }
    }

    private Point closest(Point point) {
        Point out   = new Point(0, 0);
        double dist, mini; int dx, dy;

        mini = 10000;
        for (Point p : points) {
            dx = p.x - point.x;
            dy = p.y - point.y;

            dist = Math.abs(Math.sqrt(dx*dx + dy*dy));
            if (dist < mini) {
                mini = dist;
                out = p;
            }
        }

        Log.v("TEST", "trouvé ! départ : " + point.toString() + ". arrivé : " + out.toString());
        return out;
    }

    /**
     * Retourne les coordonnées de la case situé directement sous le doigt de l'utilisateur
     * Sinon retourne les coordonnées de la case la plus proche.
     * @param point
     * @return Point
     */
    public Point closestPoint(Point point) {
        for (Point p : points) {
            if (p.x < point.x && point.x < p.x + caseWidth) {
                if (p.y < point.y && point.y < p.y + caseHeight) {
                    return p;
                }
            }
        }

        return closest(point);
    }

    /**
     * Retourne les coordonnées de la case situé directement sous le doigt de l'utilisateur
     * Sinon retourne les coordonnées de la case la plus proche.
     * @param x
     * @param y
     * @return Point
     */
    public Point closestPoint(int x, int y) {
        return closestPoint(new Point(x, y));
    }

    public void setCaseDim(int width, int height) {
        this.caseWidth = width;
        this.caseHeight = height;
    }

    /**
     * Retourne une liste contenant les points qui forment la grille
     * @return
     */
    public List<Point> getPoints() {
        return this.points;
    }

    /**
     * Retourne le point correspondant au numéro de case passé en paramètre
     * @param id
     * @return Point
     */
    public Point getPoint(int id) {
        return this.points.get(id);
    }

    /**
     * Fais correspondre un point à son identifiant sur la grille
     * @param p
     * @return id du point si le point appartient à la grille, -1 sinon.
     */
    public int getId(Point p) {
        Point point;
        for (int i=0 ; i<this.points.size() ; ++i) {
            point = this.points.get(i);
            if (point.equals(p))
                return i;
        }
        return -1;
    }
}
