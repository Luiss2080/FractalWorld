package Capa_Logica;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
/**
 *
 * @author LuissxD
 */
public class Clase_Graficos {
    private JPanel objVentana;
    private Graphics objGrafico;
    private int x,y;
    public Clase_Graficos(JPanel obj) {
        this.objVentana = obj;
        this.objGrafico = this.objVentana.getGraphics();
        this.x = 100;
        this.y = 100;
    }
    
    public void graficarCuadrado (){
        this.objGrafico.setColor(Color.BLACK);
        this.objGrafico.drawRect(x, y, 50, 50);
    }
    
    public void BorrarCuadrado (){
        
    this.objGrafico.setColor(this.objVentana.getBackground());
    this.objGrafico.drawRect(x, y, 50, 50);
    
    }
    public void moverDerecha () {
        this.BorrarCuadrado();
        this.x = this.x + 5;
        graficarCuadrado();
    }
    
    public void moverIzquierda() {
        this.x = this.x -5;
    }
    
    public void graficarCopoNieve(int n) {
        this.objGrafico.clearRect(0, 0, 500, 700);
        graficarCopoNieve(50, 400, 250, 54, 60, n);
        graficarCopoNieve(250, 54, 450, 400, 300, n);
        graficarCopoNieve(450, 400, 50, 400, 180, n);
        
    }
    
    private void graficarLinea (int x1, int y1, int x2, int y2) {
        
        this.objGrafico.drawLine(x1, y1, x2, y2);
    }
    
    private void graficarCopoNieve (int x1, int y1, int x2, int y2, int tita, int n) {
              
        if(n==1){
            
            graficarLinea(x1, y1, x2, y2);
            
        } else {
            //calculo de punto medio uno razon = 1/2
        double r1 = 0.5, r2 =2, dis, dx, dy;
        int xm1, ym1, xm2, xm3, ym2, ym3;
        xm1 = (int)((x1+r1*x2)/(1+r1));
        ym1 = (int)((y1+r1*y2)/(1+r1));
        
        
        //calculo de la distancia
        dis = Math.sqrt((xm1-x1)*(xm1-x1)+ (ym1-y1)*(ym1-y1));
        dx = dis*Math.cos((tita+60)*Math.PI/180);
        dy = dis*Math.sin((tita+60)*Math.PI/180);
        
        //calculo de punto medio dos
        xm2 = (int)(xm1+dx);
        ym2 = (int)(ym1 - dy);

        xm3 = (int)((x1+r2*x2)/(1+r2));
        ym3 = (int)((y1+r2*y2)/(1+r2));
        

        
        //Proceso Recursivo
            graficarCopoNieve(x1, y1, xm1, ym1, tita, n-1);
            graficarCopoNieve(xm1, ym1, xm2, ym2, tita+60, n-1);
            graficarCopoNieve(xm2, ym2, xm3, ym3, tita+300, n-1);
            graficarCopoNieve(xm3, ym3, x2, y2, tita, n-1);
        }
            

    }
}