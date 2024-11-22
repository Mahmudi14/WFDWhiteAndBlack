package mhmd;

import java.awt.Color;
import java.util.ArrayList;

public class Water {

    //INPUT
    private double[][] data = null;
    private int rows = 0;
    private int cols = 0;
    private ArrayList<Titik> listTitikPusat = new ArrayList<>();

    //COLORING MAP
    private double MIN = 0;
    private double MAX = 0;
    private double Threshold = 0;

    //DATA KECEPATAN ALIRAN
    private double[][] dataKecepatan = null;
    private double MINKECEPATAN = 0;
    private double MAXKECEPATAN = 0;

    //Warna Air
    private Color c10 = Color.decode("#076b8f");
    private Color c11 = Color.decode("#087da7");
    private Color c12 = Color.decode("#098fbe");
    private Color c13 = Color.decode("#0aa1d6");
    private Color c14 = Color.decode("#23bbf0");
    private Color c15 = Color.decode("#3cc2f1");
    private Color c16 = Color.decode("#54caf3");
    private Color c17 = Color.decode("#6dd1f5");
    private Color c18 = Color.decode("#85d9f7");

    private Color[] warnaKecepatan = {c18, c17, c16, c15, c14, c13, c12, c11, c10};
    private double[] batasKecepatan = new double[warnaKecepatan.length];

    //Output
    private int[][] result = null;
    private ArrayList<Titik[]> graph = null;
    private Titik[][] titikAliran = null;

    public Water(double[][] data, ArrayList<Titik> listTitikPusat, int[][] result, ArrayList<Titik[]> graph) {
        this.setData(data);
        this.listTitikPusat = listTitikPusat;
        this.result = result;
        this.graph = graph;
    }

    public void setData(double[][] data) {
        if (data != null) {
            this.data = data;
            this.rows = data.length;
            this.cols = data[0].length;
            ///hitung MIN MAX
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    double value = data[i][j];
                    if (value > max) {
                        max = value;
                    }
                    if (value < min) {
                        min = value;
                    }
                }
            }
            this.MAX = max;
            this.MIN = min;
            
            this.Threshold = ((this.MAX-this.MAX)/2.0)+1.0;
            double[][] d = this.data;
            for (int i = 0; i < d.length; i++) {
                for (int j = 0; j < d[i].length; j++) {
                    double e = d[i][j];
                    if(e<this.Threshold){
                        e = 0;
                    }else{
                        e = 1;
                    }
                    d[i][j] = e;
                }
            }
            this.data = d;
        }
    }

    public double[][] getData() {
        return data;
    }

    public void setDataKecepatan(double[][] dataKecepatan) {
        if (dataKecepatan != null) {
            this.dataKecepatan = dataKecepatan;
            ///hitung MIN MAX
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            for (int i = 0; i < dataKecepatan.length; i++) {
                for (int j = 0; j < dataKecepatan[i].length; j++) {
                    double value = dataKecepatan[i][j];
                    if (value > max) {
                        max = value;
                    }
                    if (value < min && value > 0) {
                        min = value;
                    }
                }
            }
            this.MAXKECEPATAN = max;
            this.MINKECEPATAN = min;
            double satuan = (this.MAXKECEPATAN - this.MINKECEPATAN) / batasKecepatan.length;
            for (int i = 0; i < batasKecepatan.length; i++) {
                batasKecepatan[i] = MINKECEPATAN + (i + 1) * satuan;
            }
        }
    }



    public double[][] getDataKecepatan() {
        return this.dataKecepatan;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public ArrayList<Titik> getListTitikPusat() {
        return listTitikPusat;
    }

    public void setListTitikPusat(ArrayList<Titik> listTitikPusat) {
        this.listTitikPusat = listTitikPusat;
    }

    public int[][] getResult() {
        return result;
    }

    public void setResult(int[][] result) {
        this.result = result;
    }

    public ArrayList<Titik[]> getGraph() {
        return graph;
    }

    public void setGraph(ArrayList<Titik[]> graph) {
        this.graph = graph;
    }

    public Titik[][] getTitikAliran() {
        return titikAliran;
    }

    public void setTitikAliran(Titik[][] titikAliran) {
        this.titikAliran = titikAliran;
    }

    public double[] getBatasKecepatan() {
        return batasKecepatan;
    }

    public Color[] getWarnaKecepatan() {
        return warnaKecepatan;
    }

    public void resetListTitikPusat() {
        listTitikPusat = new ArrayList<>();
    }

    public void insertTitikPusat(Titik titik) {
        if (data != null && !listTitikPusat.contains(titik) && titik.getX() >= 0 && titik.getX() < rows && titik.getY() >= 0 && titik.getY() < cols) {
            listTitikPusat.add(titik);
        }
    }

}
