/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base64work.principalvj;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Jorge
 */
public class Sprites_Player {
    // Variables de posición con respesto a una lista de posiciones
    private int columnIni = 0;
    private int columnEnd = 0;
    private int column = 0;
    private int row = 0;
    // Variables - Sprites
    BufferedImage[][] sprites = null;
    // Variables - Tiempo
    private Long startTime = 0l;
    private Long[] elapsedsTime = null;
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    
    private ArrayList<String> getNomsOfImgsIn (String ruta) {
        // Variables
        ArrayList<String> listNomsOfImgs = new ArrayList<String>();
        // Instanciar folder - IDLE
        File folder = new File(ruta);
        File[] listOfFiles = folder.listFiles();
        // Recorrer liista de archivos para IDLE
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                listNomsOfImgs.add(listOfFiles[i].getName());
            }
        }
        // Resultado
        return listNomsOfImgs;
    }
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // Constructor
    public Sprites_Player(String ruta, Long velIdle, Long velWalk) {
        // Seguro
        try {
            String rutaIdle = ruta + "/Idle";
            String rutaWalk = ruta + "/Walk";
            // Variables temporales
            ArrayList<String> listNomImgsIdle = getNomsOfImgsIn(rutaIdle);
            ArrayList<String> listNomImgsWalk = getNomsOfImgsIn(rutaWalk);
            // Definir el número de filas
            int numRows = 3;
            // Definir el número de columnas
            int numColumns = listNomImgsIdle.size();
            
            if (numColumns < listNomImgsWalk.size()) {
                numColumns = listNomImgsWalk.size();
            }
            // Asignaciones de tiempo para el control de animaciónes
            this.elapsedsTime = new Long[]{velIdle, velWalk};
            this.startTime = System.currentTimeMillis();
            this.columnEnd = numColumns;
            // TRY - CATCH
            try {
                // Inicialiar arreglo de sprites
                sprites = new BufferedImage[numRows][numColumns];
                // Recorrer nombre de imagenes de IDLE
                int maxNNI = listNomImgsIdle.size();
                for (int i = 0; i < maxNNI; i++) {
                    // Recuperar nombre de imagen
                    String nameFile = rutaIdle + "/" + listNomImgsIdle.get(i);
                    // Cargar imagen
                    BufferedImage spriteFile = ImageIO.read(new File(nameFile));
                    // Asignar Sprite en arreglo de Sprites
                    sprites[0][i] = spriteFile;
                }
                // Recorrer nombre de imagenes de WALK
                maxNNI = listNomImgsWalk.size();
                for (int i = 0; i < maxNNI; i++) {
                    // Recuperar nombre de imagen
                    String nameFile = rutaWalk + "/" + listNomImgsWalk.get(i);
                    // Cargar imagen
                    BufferedImage spriteFile = ImageIO.read(new File(nameFile));
                    // Asignar Sprite en arreglo de Sprites
                    sprites[1][i] = spriteFile;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
		e.printStackTrace();
	}
    }
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // Establecer la fila de sprites que se desea revisar
    public void setSpritesRow(int r){
        this.row = r;
    }
    // Función que establece la columna desde la cual se tomaran los sprites
    public void setSpritesColumnIni(int cIni) {
        this.columnIni = cIni;
    }
    
    // Función que establece la columna hasta la cual se tomaran los sprites
    public void setSpritesColumnEnd(int cEnd) {
        this.columnEnd = cEnd;
    }
    
    // Función que establece la columna actual de la cual se tomara el sprite
    public void setSpritesColumnActual(int cAct) {
        this.column = cAct;
    }
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    
    // Funcion que devuelve el sprite actual
    public BufferedImage getActualSprite() {
        // Variables
        BufferedImage actualSprite = null;
        // Recuperar sprite
        while (actualSprite == null) {
            // Recuperar sprite actual
            actualSprite = sprites[this.row][this.column];
            // Validar
            Long elapsedTime = this.elapsedsTime[this.row];
            Long passedTime = elapsedTime;
            // Validar
            if (actualSprite != null) {
                // Recuperar tiempo transcurrido
                passedTime = System.currentTimeMillis() - this.startTime;
            }
            // Validar 
            if ((actualSprite == null) || (elapsedTime <= passedTime)) {
                // Avanzar la posición de columna para el siguiente sprite
                if ((this.column + 1) < this.columnEnd) {
                    this.column++;
                } else {
                    this.column = this.columnIni;
                }
                // Actualizar tiempo
                if (elapsedTime <= passedTime) {
                    this.startTime = System.currentTimeMillis();
                }
            }
        }
        return actualSprite;
    }
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    
    public String getAllSpritesToString() {
        // VARIBALES
        String strSprites = "";
        int numRows = this.sprites.length;
        int numColumns = this.sprites[0].length;
        // Recorrer Sprites
        for (int f = 0; f < numRows; f++) {
            for (int c = 0; c < numColumns; c++) {
                // Recuperar Sprite
                BufferedImage sprite = this.sprites[f][c];
                // Validar
                if (sprite != null) {
                    // Seguro
                    try {
                        // Variables
                        String imageString = null;
                        BASE64Encoder encoder = new BASE64Encoder();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        // Conversión
                        ImageIO.write(sprite, "png", bos);
                        byte[] imageBytes = bos.toByteArray();
                        imageString = encoder.encode(imageBytes);
                        bos.close();
                        // Validar
                        if ((imageString != null) && !imageString.equals("")) {
                            if ((strSprites != null) && !strSprites.equals("")) {
                                strSprites += "NEXT" + imageString;
                            } else {
                                strSprites = imageString;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if ((strSprites != null) && !strSprites.equals("")) {
                strSprites += "LIST";
            }
        }
        // Resultado
        return strSprites;
    }
}
