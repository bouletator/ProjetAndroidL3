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
public class PieceClassTest extends View {
    public static int width, height, left, top;
    private Map<String, Object> dragInfo = new HashMap<String, Object>();
    private List<Piece> pieces = new ArrayList<Piece>();
    private PuzzleBuilder myPuzzle;
    private Grid grid;


    public PieceClassTest(Context context) {
        super(context);
    }

    public PieceClassTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PieceClassTest(Context context, AttributeSet attrs, int defStyle) {
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
        Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.mesange);
        myPuzzle = new PuzzleBuilder(4, img);
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
                        Log.v("Coord: ", "have a swapp piece");
                        toSwap.setCoord((Point) dragInfo.get("posInit"));
                        // positionnement de la première piece
                        coords = grid.closestPoint(x, y);
                    } else {
                        coords = (Point) dragInfo.get("posInit");
                    }

                    p.setCoord(coords);

                    // supression des dragInfos
                    this.dragInfo.clear();

                    invalidate();
                    return false;
                }
        }

        return super.onTouchEvent(event);
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
