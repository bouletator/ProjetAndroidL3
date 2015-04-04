package info.ups.fr.puzzlegame_template;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Léo Cances ~~~
 */
public class Puzzle extends View {
    public static int width, height, left, top;
    private static Map<String, Object> dragInfo = new HashMap<String, Object>();
    public static List<Piece> pieces = new ArrayList<Piece>();
    private static PuzzleBuilder myPuzzle;
    private static Grid grid;
    private GameActivity gameActivity;
    private boolean takeASleep = false;

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
        gameActivity = (GameActivity) getContext();
        for (Piece p : pieces) {
            p.draw(canvas);
        }

        if (dragInfo.containsKey("piece")) {
            Piece p = (Piece) dragInfo.get("piece");
            p.draw(canvas);
        }

        if (takeASleep) {
            updatePreferences();
            Intent intent = new Intent(gameActivity, GameWinActivity.class);
            gameActivity.startActivity(intent);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        gameActivity = (GameActivity) getContext();
        // Récupération taille de la vue
        width = getWidth();
        height = getHeight();
        left = getLeft();
        top = getTop();

        this.initPuzzle();
    }

    public void initPuzzle() {
        SharedPreferences preferences = getContext().getSharedPreferences("preferences", 0);
        gameActivity.setTitle("Level "+(preferences.getInt("current_level",0)+1));
        // calcul des paramètres:
        int level = preferences.getInt("current_level",0);
        int imageId = R.drawable.plancton;
        switch (level%5) {
            case 0  :
                imageId  = R.drawable.plancton;
                break;
            case 1  :
                imageId  = R.drawable.fusee;
                break;
            case 2  :
                imageId  = R.drawable.chat;
                break;
            case 3  :
                imageId  = R.drawable.aigle;
                break;
            case 4  :
                imageId  = R.drawable.coquelicot;
                break;
        }

        int subdivizionNumber = 4+level/5;

        // création du puzzle

        if (preferences.contains("pieces_ids")) {
            myPuzzle = new PuzzleBuilder(subdivizionNumber, BitmapFactory.decodeResource(getResources(), imageId), preferences.getString("pieces_ids", ""));
        }
        else {
            myPuzzle = new PuzzleBuilder(subdivizionNumber, BitmapFactory.decodeResource(getResources(), imageId));
        }

        pieces = myPuzzle.getPieces();
        grid = new Grid(pieces.size(), myPuzzle.getSubSize(), myPuzzle.getSubSize());

        this.invalidate();
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
                    this.dragInfo.clear();


                    SharedPreferences preferences = getContext().getSharedPreferences("preferences", 0);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("pieces_ids", this.pieces.toString());
                    editor.commit();

                    if (this.isGameFinished()) {
                        takeASleep = true;
                        invalidate();

                    }
                }
                    invalidate();
                    return false;
            }

        return super.onTouchEvent(event);
    }

    private void updatePreferences() {
        SharedPreferences preferences = getContext().getSharedPreferences("preferences", 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("current_level", preferences.getInt("current_level", 0) + 1);
        editor.apply();

        editor.remove("pieces_ids");
        editor.apply();

        if (preferences.getInt("current_level", 0) > preferences.getInt("unlock", 0)) {
            editor.putInt("unlock", preferences.getInt("current_level", 0));
            editor.apply();
        }
        editor.commit();
    }

    public static void shuffle() {
        pieces = myPuzzle.getPieces();
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
