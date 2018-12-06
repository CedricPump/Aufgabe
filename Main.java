import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * BMT Aufgabe XY
 */
public class Main {
    public static String eingabe[] = {}; // simulates public variable eingabe

    /**
     * Main executable
     *
     * @param args parameters
     */
    public static void main(String[] args) {
/*
         int[][] m = {{1,2,3},{4,5,6},{7,8,9}};
         System.out.println(matrixToString(m));
         System.out.println(matrixToString(deleteMatrixRowColumn(m,1)));
         String str1 = "BBE";
         String str2 = "ABB";
         System.out.println(getOverlap(str1,str2));
         System.out.println(concatFragments(str1,str2));
*/
        String input ="";
        String[] strArr = {"AABB", "CCDD", "CDDE", "CDDEE"};
        String sep = " ";
        System.out.print("Type fragments separated by \""+sep+"\": ");
        try {
            input = new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(input.length() != 0) {
            strArr = StringToStingArray(input, " ");
        }
        System.out.println(aufgabe(strArr));
    }



    /**
     * Concatenates Fragments using the maximum overlap
     *
     * @param ein Array of fragments
     * @return concatenated String containing the fragments
     */
    public static String aufgabe(String[] ein) {
        eingabe = ein;
        //String ergebnis = "";
        // Your Code Begin
        //--------------------------------------------------------------------------------------------------------------
/*Du hast als Eingabe String[] eingabe (Datentyp: Array von Strings) und sollst den SCS herausfinden.
 *Die Länge eines Arrays bekommst du mit dem Befehl length, in diesem Fall also:
 *int test = eingabe.length;
 */

        //Das Ergebnis ist der String, der am Ende in der Variable ergebnis steht./

        String ergebnis = "";

/*
 *Berechnt dabei den Overlap einmalig und speichert ihn in einer Überlappungsmatrix:
 *int[][] hilfsMatrix = makeIntMatrix(Länge, Länge);
 *
 *Zeilen und Spalten löscht ihr mit dem Befehl deleteMatrixRowColumn(MATRIX, INDEX), z.B. für die 0. Zeile:
 *hilfsMatrix = deleteMatrixRowColumn(matrix, 0);
 *Aktualisiert Overlap-Werte für das neue Fragment vorher, da die Zeile und Spalte endgültig gelöscht werden.
 */

        int[][] hilfsMatrix = null;


/*
 *Hier wird ein Superstring berechnet, der nicht der Shortest Common Superstring ist:
 *Der triviale Superstring der aus der einfachen Konkatenation der Fragmente entsteht.
 */

        for (int i = 0; i < eingabe.length; i++) {
            ergebnis = ergebnis + eingabe[i];
        }

        int[][] ueberlappungsMatrix = makeIntMatrix(eingabe.length, eingabe.length);//matrix genauso lange wie mein eingabe array
        int max_ovlp = 0;
        int max_i = 0;
        int max_j = 0;
        String eneu = "";
/*ueberlappungsMatrix[0][0]= -1;//wollen wir nicht vergleichen, weiß noch nicht was dahin muss
ueberlappungsMatrix[0][1]= 1;
ueberlappungsMatrix[0][2]= 0;
ueberlappungsMatrix[1][0]= 0;
ueberlappungsMatrix[1][1]= -1;
ueberlappungsMatrix[1][2]= 2;
ueberlappungsMatrix[2][0]= 0;
ueberlappungsMatrix[2][1]= 0;
ueberlappungsMatrix[2][2]= -1;*/

        System.out.println("Eingabe: "+StrArrayToString(eingabe));

// Matrix aufbuauen
        for (int i = 0; i < eingabe.length; i++) {
            for (int j = 0; j < eingabe.length; j++) {
                if (i != j) {
                    ueberlappungsMatrix[i][j] = getOverlap(eingabe[i], eingabe[j]);

                } else {
                    ueberlappungsMatrix[i][j] = -1;
                }

            }
        }

        System.out.println("INIT: \n"+ matrixToString(ueberlappungsMatrix));

        //matrix untersuchen und Fragmente verknuepfen
        while (eingabe.length > 1) {
            //neues max in Matrix suchen
            max_ovlp = 0;
            for (int i = 0; i < eingabe.length; i++) {
                for (int j = 0; j < eingabe.length; j++) {
                    if (ueberlappungsMatrix[i][j] >= max_ovlp) {
                        max_ovlp = ueberlappungsMatrix[i][j];
                        max_i = i;
                        max_j = j;
                    }
                }
            }

            System.out.println("Max overlap: "+ max_ovlp +"\nMax_i: " + max_i + " Max_j: "+ max_j);

            // fragmente verknüfen
            eneu = concatFragments(eingabe[max_i], eingabe[max_j]);

            System.out.println("Concatenated \""+eingabe[max_i] +"\" an \""+ eingabe[max_j] + "\" to: \"" + eneu +"\"");

            eingabe[max_i] = eneu;

            // Overlaps aktualisieren
            //ueberlappungsMatrix[max_i][max_j] = ueberlappungsMatrix[max_i][max_i] + ueberlappungsMatrix[max_j][max_j];
            //ueberlappungsMatrix[max_j][max_i] = ueberlappungsMatrix[max_i][max_i] + ueberlappungsMatrix[max_j][max_j];

            // Zele spalte löschen
            ueberlappungsMatrix = deleteMatrixRowColumn(ueberlappungsMatrix, max_j);

            System.out.println("new Matrix: \n"+ matrixToString(ueberlappungsMatrix));

            // fragment aus eingabe löschen
            eingabe = removeElement(max_j);

            System.out.println("Eingabe Array:" + StrArrayToString(eingabe));

        }
        ergebnis = eneu;

        //--------------------------------------------------------------------------------------------------------------
        //Your Code End
        return ergebnis;
    }

    /**
     * Deletes column and row
     * If index out of bounds return null
     *
     * @param matrix matrix
     * @param index  index of column and row
     * @return new matrix without indexed
     */
    public static int[][] deleteMatrixRowColumn(int[][] matrix, int index) {
        if (index < 0 || index > matrix.length - 1)
            return null;
        int[][] retMatrix = makeIntMatrix(matrix.length - 1, matrix.length - 1);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i < index && j < index)
                    retMatrix[i][j] = matrix[i][j];
                if (i > index && j < index)
                    retMatrix[i - 1][j] = matrix[i][j];
                if (i < index && j > index)
                    retMatrix[i][j - 1] = matrix[i][j];
                if (i > index && j > index)
                    retMatrix[i - 1][j - 1] = matrix[i][j];
            }
        }
        return retMatrix;

    }

    /**
     * Concatenates two Strings using the max overlap
     *
     * @param str1 front String
     * @param str2 rear String
     * @return concatenated String
     */
    public static String concatFragments(String str1, String str2) {
        if (str2.length() != 0) {
            return str1 + str2.substring(getOverlap(str1, str2), str2.length());
        } else {
            return str1;
        }
    }

    /**
     * finds max overlap of two strings
     *
     * @param str1 front String
     * @param str2 rear String
     * @return overlapping characters as int
     */
    public static int getOverlap(String str1, String str2) {
        int ovlp = 0;
        String subString = "";
        for (int i = str2.length(); i >= 0; i--) {
            subString = str2.substring(0, i);
            if (str1.lastIndexOf(subString) >= 0 && str1.length() - str1.lastIndexOf(subString) > ovlp && str1.lastIndexOf(subString) + i == str1.length()) {
                ovlp = i;
            }
        }
        return ovlp;
    }

    /**
     * creates int Matrix as "new int[x][y]" would do
     * why capulating this? no clue
     *
     * @param x length
     * @param y depth
     * @return two dimensional int array
     */
    public static int[][] makeIntMatrix(int x, int y) {
        return new int[x][y];
    }

    /**
     * String representation of int array
     *
     * @param m matrix
     * @return columns separated by tab, rows separated by newline
     */
    public static String matrixToString(int[][] m) {
        String ret = "";
        for (int[] n : m) {
            for (int i : n) {
                ret += "\t" + (i<0?""+i:" "+i);
            }
            ret += "\n";
        }
        return ret;
    }

    /**
     * Readable Array representation
     * @param s array
     * @return  String
     */
    public static String StrArrayToString(String[] s) {
        String ret = "{";
        for (String str : s) {
           ret += "\"" + str + "\",";
        }
        if(s.length > 0)
            ret = ret.substring(0,ret.length()-1);
        return ret + "}";
    }

    /**
     * Why do you use Array?
     * @param eingabe Array
     * @param index index of string to be removed
     * @return new Array
     */
    public static String[] removeElement(String[] eingabe, int index){
        if(eingabe.length > 0) {
            String[] arr = new String[eingabe.length-1];
            for(int i = 0; i < eingabe.length; i++){
                if(i!=index) {
                    if (i < index)
                        arr[i] = eingabe[i];
                    else
                        arr[i - 1] = eingabe[i];
                }
            }
            return arr;
        }else {
            return eingabe;
        }
    }

    /**
     * Arrrrg, what a bullshit
     * Noone would ever do this
     * @param index index of string to be removed
     * @return new Array
     */
    public static String[] removeElement(int index){
        if(eingabe.length > 0) {
            String[] arr = new String[eingabe.length-1];
            for(int i = 0; i < eingabe.length; i++){
                if(i!=index) {
                    if (i < index)
                        arr[i] = eingabe[i];
                    else
                        arr[i - 1] = eingabe[i];
                }
            }
            return arr;
        }else {
            return eingabe;
        }
    }

    /**
     * Turns String into Array of String separated by separator
     * @param s     String
     * @param sep   separator
     * @return      Array
     */
    public static String[] StringToStingArray(String s, String sep){
        return s.split(sep);
    }
}