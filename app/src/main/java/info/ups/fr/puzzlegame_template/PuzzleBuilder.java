package info.ups.fr.puzzlegame_template;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author maignial
 */
public class PuzzleBuilder {

    /* Attributs */
    private int subdivisonLevel;
    private Bitmap rawPicture;
    private ArrayList<Integer> piecesIds = new ArrayList<Integer>();
    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    /* Constructor */
    public PuzzleBuilder(int subdivisionlvl, Bitmap picture) {
        this.subdivisonLevel = subdivisionlvl;
        this.rawPicture = picture;
        this.shuffle();
        this.dividePicture();
    }

    /**
     * Met à jour le niveau
     * @param subdivisionLevel
     */
    public void setSubdivisionLevel(int subdivisionLevel) {
        this.subdivisonLevel = subdivisionLevel;
        this.dividePicture();
    }

    /**
     * Met à jour l'image du puzzle
     * @param picture la nouvelle image
     */
    public void setPicture(Bitmap picture) {
        this.rawPicture = picture;
        this.dividePicture();
    }

    /**
     * Mélange les pièces, l'utilisateur devras récupérer le nouveau tableau de pièces mélangées
     */
    public void shuffle() {
        this.piecesIds.clear();
        final int listSize = this.subdivisonLevel * this.subdivisonLevel;

        for (int i=0 ; i<listSize ; ++i)
            this.piecesIds.add(i);


        Random r = new Random();

        int alea1;
        int alea2;
        int tmp;
        for (int i=0 ; i<listSize ; ++i) {
            alea1 = r.nextInt(listSize);
            alea2 = r.nextInt(listSize);

            tmp = this.piecesIds.get(alea1);
            this.piecesIds.set(alea1, this.piecesIds.get(alea2));
            this.piecesIds.set(alea2, tmp);
        }

        Log.v("TEST", piecesIds.toString());
    }

    /**
     * @return la taille d'une sous image du puzzle
     */
    public int getSubSize() {
        return this.pieces.get(0).getImage().getWidth();
    }

    /**
     * Renvoie la liste des pièces
     * @return pieces
     */
    public ArrayList<Piece> getPieces() {
        Grid grid = new Grid(subdivisonLevel*subdivisonLevel, this.getSubSize(), this.getSubSize());
        for (int i=0 ; i<this.pieces.size() ; ++i) {
            this.pieces.get(i).setIdG(this.piecesIds.get(i));
            this.pieces.get(i).setCoord(grid.getPoint(this.piecesIds.get(i)));
        }

        return this.pieces;
    }

    /**
     * Permet de définir l'ordre des pièces
     * @param values une chaine de caractères au format [[id,idG], ...]
     * @return false en cas de soucis avec la chaine de définition, true sinon
     */
    public boolean setPiecesIds(String values) {
        String[] tab = values.split("\\], \\[");
        tab[0] = tab[0].substring(2);
        tab[tab.length-1] = tab[tab.length-1].substring(0,tab[tab.length-1].length()-2);

        if(this.piecesIds.size() != tab.length){
            return false;
        }

        String[] couple;
        this.piecesIds.clear();
        for (String str : tab) {
            couple = str.split(",");
            piecesIds.add(Integer.parseInt(couple[0]), Integer.parseInt(couple[1]));
        }

        return true;
    }

    /**
     * Revoie une image formatée et découpable pour le puzzle
     * @param rawPicture image de base
     * @return formated picture
     */
    private Bitmap createPicture(Bitmap rawPicture) {
        Bitmap result;

        /* rend l'image carrée */
        int size = (rawPicture.getHeight() < rawPicture.getWidth())?
                rawPicture.getHeight() :
                rawPicture.getWidth();
        int xPadding = (rawPicture.getWidth()-size)/2;
        int yPadding = (rawPicture.getHeight()-size)/2;
        result = Bitmap.createBitmap(rawPicture, xPadding, yPadding, size, size);

        /* redimensionnement de l'image */
        int maxSize = (Puzzle.height> Puzzle.width)?
                Puzzle.width :
                Puzzle.height;

        if (maxSize>size)
            maxSize = size;

        while (maxSize%this.subdivisonLevel != 0) // on rend l'image divisible en part égales
            --maxSize;

        return Bitmap.createScaledBitmap(result, maxSize, maxSize, true);
    }

    /**
     * Divise l'image de base en carrés et construit une liste ordonée avec les morceaux créés
     */
    private void dividePicture() {
        Bitmap picture = this.createPicture(this.rawPicture);

        int size = picture.getWidth();
        int subSize = size / this.subdivisonLevel;
        int endSubSize = size - subSize*(this.subdivisonLevel -1);

        int xSize;
        int ySize;

        for(int j,i=0 ; i<this.subdivisonLevel; ++i) {
            for(j=0 ; j<this.subdivisonLevel; ++j) {
                xSize = (i<this.subdivisonLevel -1)? subSize : endSubSize;
                ySize = (j<this.subdivisonLevel -1)? subSize : endSubSize;

                this.pieces.add(new Piece(i*subdivisonLevel+j, 0,
                        Bitmap.createBitmap(picture, i*subSize, j*subSize, xSize, ySize)
                ));
            }
        }
    }
}