/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base64work.principalvj;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Jorge
 */
public class Sprites_String {
    // Variables de tamaño
    private int width = 0;
    private int height = 0;
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
    // -----------------------------------------------------------------
    
    // Función que devuelve el ancho para cada sprite
    public int getW() {
        return this.width;
    }
    
    // Función que devuelve el alto para cada sprite
    public int getH() {
        return this.height;
    }
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    
    private ArrayList<String> getStrImgsIn (String strSprites) {
        // Variables
        String[] strArySprites = strSprites.split("NEXT");
        ArrayList<String> listStrOfImgs = new ArrayList<String>();
        // Recorrer liista de archivos para IDLE
        for (int i = 0; i < strArySprites.length; i++) {
            String strSP = strArySprites[i];
            if ((strSP != null) && !strSP.equals("")) {
                listStrOfImgs.add(strSP);
            }
        }
        // Resultado
        return listStrOfImgs;
    }
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    
    private BufferedImage strImgToBufferImg (String base64Image) {
        // Variable
        BufferedImage myBffImg = null;
        // Seguro
        try {
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
            myBffImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Retorno
        return myBffImg;
    }
    
    // -----------------------------------------------------------------
    // -----------------------------------------------------------------
    // Constructor
    public Sprites_String(String strSprites, Long velIdle, Long velWalk) {
        // Seguro
        try {
            String[] strArySprites = strSprites.split("LIST");
            // Variables temporales
            ArrayList<String> listStrImgsIdle = getStrImgsIn(strArySprites[0]);
            ArrayList<String> listStrImgsWalk = getStrImgsIn(strArySprites[1]);
            // Definir el número de filas
            int numRows = 3;
            // Definir el número de columnas
            int numColumns = listStrImgsIdle.size();
            
            if (numColumns < listStrImgsWalk.size()) {
                numColumns = listStrImgsWalk.size();
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
                int maxNNI = listStrImgsIdle.size();
                for (int i = 0; i < maxNNI; i++) {
                    // Cargar imagen
                    BufferedImage spriteFile = strImgToBufferImg(listStrImgsIdle.get(i));
                    // Asignar nuevo tamaño
                    int newW = spriteFile.getWidth();
                    if (this.width < newW) {
                        this.width = newW;
                        this.height = spriteFile.getHeight();
                    }
                    // Asignar Sprite en arreglo de Sprites
                    sprites[0][i] = spriteFile;
                }
                // Recorrer nombre de imagenes de WALK
                maxNNI = listStrImgsWalk.size();
                for (int i = 0; i < maxNNI; i++) {
                    // Cargar imagen
                    BufferedImage spriteFile = strImgToBufferImg(listStrImgsWalk.get(i));
                    // Asignar nuevo tamaño
                    int newW = spriteFile.getWidth();
                    if (this.width < newW) {
                        this.width = newW;
                        this.height = spriteFile.getHeight();
                    }
                    // Asignar Sprite en arreglo de Sprites
                    sprites[1][i] = spriteFile;
                }
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
}
