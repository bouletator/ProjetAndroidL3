package info.ups.fr.puzzlegame_template;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Léo Cances
 */
public class Puzzle extends View {
    public static int width, height, left, top;
    private Map<String, Object> dragInfo = new HashMap<String, Object>();
    private List<Piece> pieces = new ArrayList<Piece>();
    private PuzzleBuilder myPuzzle;
    private Grid grid;


    public Puzzle(Context context) {
        super(context);
    }

    public Puzzle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Puzzle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Piece p : pieces) {
            p.draw(canvas);
        }

        if (dragInfo.containsKey("piece")) {
            Piece p = (Piece) dragInfo.get("piece");
            p.draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Récupération taille de la vue
        width = getWidth();
        height = getHeight();
        left = getLeft();
        top = getTop();

        // création du puzzle

        myPuzzle = new PuzzleBuilder(4, LevelChooserActivity.puzzleImage);
        pieces = myPuzzle.getPieces();
        grid = new Grid(pieces.size(), myPuzzle.getSubSize(), myPuzzle.getSubSize());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        Piece p;

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                p = getClosestPiece(x, y);
                if (p != null) {
                    dragInfo.put("piece", (Piece) p);
                    dragInfo.put("posInit", new Point(p.getPosX(), p.getPosY()));
                    return true;
                }

            case MotionEvent.ACTION_MOVE:
                if (dragInfo.containsKey("piece")) {
                    p = (Piece) dragInfo.get("piece");
                    p.setMoveCoord(x, y);
                    invalidate();
                }
                return true;

            case MotionEvent.ACTION_UP:
                if (dragInfo.containsKey("piece")) {
                    // swap
                    Piece toSwap = getClosestPiece(x, y);
                    p = (Piece) dragInfo.get("piece");
                    Point coords;
                    if (toSwap != null) {
                        coords = (Point) dragInfo.get("posInit");
                        toSwap.setCoord(coords);
                        toSwap.setIdG(this.grid.getId(coords));
                        // positionnement de la première piece
                        coords = grid.closestPoint(x, y);
                    } else {
                        coords = (Point) dragInfo.get("posInit");
                    }

                    p.setCoord(coords);
                    p.setIdG(this.grid.getId(coords));

                    // supression des dragInfos
                    this.dragInfo.clear();

                    invalidate();
                    if (this.isGameFinished())
                        Log.v("TEST: ", "jeu finis.");

                    return false;
                }
        }

        return super.onTouchEvent(event);
    }

    /**
     * Contrôle si le jeu est terminé, à savoir si les pièces sont toutes à la bonne place
     * @return vrai si le jeu est fini, faux sinon
     */
    private boolean isGameFinished() {
        for (Piece p : this.pieces) {
            if (p.getIdG() != p.getIdentifiant())
                return false;
        }
        return true;
    }

    private Piece getClosestPiece(int x, int y) {
        Piece selected = null;
        if (dragInfo.containsKey("piece"))
            selected = (Piece)dragInfo.get("piece");

        for (Piece p : pieces) {
            if (p.contains(x, y) && ! p.equals(selected)) {
                return p;
            }
        }
        return null;
    }

    // DEBUG ---------
}
