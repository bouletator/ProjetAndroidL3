package info.ups.fr.puzzlegame_template;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author maignial
 */
public class PuzzleBuilder {

    /* Attributs */
    private int level;
    private Bitmap fullPicture;
    private ArrayList<Integer> piecesIds;

    /* Constructor */
    public PuzzleBuilder(int lvl, Bitmap picture) {
        this.level = lvl;
        this.createPicture(picture);
        this.piecesIds = null;
    }

    public PuzzleBuilder(int lvl, Bitmap picture, String piecesIndex) {
        this.level = lvl;
        this.createPicture(picture);
        this.piecesIds = this.parsePiecesIds(piecesIndex);
    }

    private void createPicture(Bitmap rawPicture) {
        /* rend l'image carrée */
        int size = (rawPicture.getHeight() < rawPicture.getWidth())?
                rawPicture.getHeight() :
                rawPicture.getWidth();
        int xPadding = (rawPicture.getWidth()-size)/2;
        int yPadding = (rawPicture.getHeight()-size)/2;
        this.fullPicture = Bitmap.createBitmap(rawPicture, xPadding, yPadding, size, size);

        /* redimensionnement de l'image */
        int maxSize = (Puzzle.height> Puzzle.width)?
                Puzzle.width :
                Puzzle.height;

        if (maxSize>size)
            maxSize = size;

        while (maxSize%this.level != 0) // on rend l'image divisible en part égales
            --maxSize;

        this.fullPicture = Bitmap.createScaledBitmap(this.fullPicture, maxSize, maxSize, true);
    }



    /**
     * @return la taille d'une sous image du puzzle
     */
    public int getSubSize() {
        return this.fullPicture.getWidth() / this.level;
    }

    /**
     * Divise l'image de base en carrés et construit une liste ordonée avec les morceaux créés
     */
    private ArrayList<Bitmap> dividePicture() {
        ArrayList<Bitmap> pictures = new ArrayList<Bitmap>();
        int size = this.fullPicture.getWidth();
        int subSize = size / this.level;
        int endSubSize = size - subSize*(this.level-1);

        int xSize;
        int ySize;

        for(int j,i=0 ; i<this.level ; ++i) {
            for(j=0 ; j<this.level ; ++j) {
                xSize = (i<this.level-1)? subSize : endSubSize;
                ySize = (j<this.level-1)? subSize : endSubSize;

                pictures.add(
                        Bitmap.createBitmap(this.fullPicture, i*subSize, j*subSize, xSize, ySize)
                );
            }
        }

        return pictures;
    }

    /**
     *
     * @return Une liste d'Integer mélangés
     */
    private ArrayList<Integer> shuffle() {
        Random r = new Random();
        int listSize = this.level*this.level;
        ArrayList<Integer> randomList = new ArrayList<Integer>(listSize);

        for (int i=0 ; i<listSize ; ++i){
            randomList.add(i);
        }

        int alea1;
        int alea2;
        int tmp;
        for (int i=listSize*2 ; i>=0 ; --i) {
            alea1 = r.nextInt(listSize-1);
            alea2 = r.nextInt(listSize-1);
            tmp = randomList.get(alea1);
            randomList.set(alea1, randomList.get(alea2));
            randomList.set(alea2, tmp);
        }

        return randomList;
    }

    /* NE PAS CHERCHER A COMPRENDRE, ATTENTION DANGEUREUX POUR VOTRE CERVEAU!!!!! */
    private ArrayList<Integer> parsePiecesIds(String values) {
        String[] tab = values.split("\\], \\[");
        tab[0] = tab[0].substring(2);
        tab[tab.length-1] = tab[tab.length-1].substring(0,tab[tab.length-1].length()-2);

        String[] couple;
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (String str : tab) {
            couple = str.split(",");
            result.add(Integer.parseInt(couple[0]), Integer.parseInt(couple[1]));
        }

        return result;
    }

    /**
     *
     * @return Une liste de pièces dont les positions sont mélangées
     */
    public ArrayList<Piece> getPieces() {
        ArrayList<Piece> pieces = new ArrayList<Piece>();

        ArrayList<Bitmap> pictures = this.dividePicture();
        ArrayList<Integer> indexes = (this.piecesIds==null)? this.shuffle() : this.piecesIds;

        Grid grid = new Grid(pictures.size(), this.getSubSize(), this.getSubSize());

        for (int i=0 ; i<pictures.size() ; ++i) {
            pieces.add(new Piece(i, indexes.get(i), pictures.get(i)));
        }

        for (Piece p : pieces) {
            p.setCoord(grid.getPoint(p.getIdG()));
        }

        return pieces;
    }
}