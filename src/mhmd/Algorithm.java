package mhmd;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Algorithm {

    private Water water = null;

    public void setData(File filedata) {
        double[][] data = null;
        ArrayList<Titik> listTitikPusat = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(filedata);
            Scanner sc = new Scanner(fis, "UTF-8");

            // baca nCols
            int nCols = 0;
            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splitLine = line.split("\\s+");//menghapus spasi
                String value = splitLine[1];//membaca data pada kolom-1 di array splitLine
                nCols = Integer.parseInt(value);
            }

            //baca nRows
            int nRows = 0;
            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splitLine = line.split("\\s+");//menghapus spasi
                String value = splitLine[1];//membaca data pada kolom-1 di array splitLine
                nRows = Integer.parseInt(value);
            }

            //baca data
            if (nRows > 0 && nCols > 0) {
                double[][] newData = new double[nRows][];
                sc.nextLine();
                sc.nextLine();
                sc.nextLine();
                sc.nextLine();

                //membaca data baris demi baris
                for (int i = 0; i < nRows; i++) {
                    if (sc.hasNextLine()) {
                        String line = sc.nextLine();
                        String[] splitLine = line.split("\\s+");
                        newData[i] = new double[splitLine.length];
                        for (int j = 0; j < newData[i].length; j++) {
                            String value = splitLine[j];
                            double dValue = 0;
                            try {
                                dValue = Double.parseDouble(value);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            newData[i][j] = dValue;
                        }
                    }
                }
                data = newData;
            }
            if (data != null) {
                water = new Water(data, listTitikPusat, null, null);
            }
            sc.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of setData()

    public void resetListTitikPusat() {
        water.resetListTitikPusat();
    }

    public void insertTitikPusat(Titik titik) {
        water.insertTitikPusat(titik);
    }

    public void flowDirectionD8() {
        int[][] result = null;
        int[][] v = null;
        ArrayList<Titik[]> graph = null;
        double dataKecepatann[][] = null;
        double[][] data = water.getData();
        ArrayList<Titik> listTitikPusat = water.getListTitikPusat();
        Titik[][] titikAliran = new Titik[listTitikPusat.size()][];

        if (data != null && listTitikPusat != null && !listTitikPusat.isEmpty()) {
            int rows = data.length;
            int cols = data[0].length;
            result = new int[rows][cols];
            v = new int[rows][cols];
            dataKecepatann = new double[rows][cols];
            //set nilai deafault untuk array result = -1 dan data kecepatan = 0
            for (int i = 0; i < rows; i++) {
                Arrays.fill(v[i], -1);
                Arrays.fill(result[i], -1);
                Arrays.fill(dataKecepatann[i], 0);
            }

            //inisialisasi graph
            graph = new ArrayList<>();

            //membuat antrian titik untuk dievaluasi arah alirannya
            Queue<Titik> antrianTitik = new LinkedList<>();

            for (int z = 0; z < listTitikPusat.size(); z++) {
                ArrayList<Titik> aliran = new ArrayList<>();
                Titik titik = listTitikPusat.get(z);
                if (titik.getX() >= 0 && titik.getX() < rows && titik.getY() >= 0 && titik.getY() < cols && !antrianTitik.contains(titik) && data[titik.getX()][titik.getY()] == 1.0) {
                    result[titik.getX()][titik.getY()] = 1;
                    antrianTitik.add(titik);
                }

                while (!antrianTitik.isEmpty()) {
                    Titik center = antrianTitik.poll();//baca titik center
                    aliran.add(center);
                    int i = center.getX();
                    int j = center.getY();

                    //ALGORITMA D8
                    int arah = 0;
                    boolean status = false;
                    //D1
                    int I = i - 1;
                    int J = j;
                    if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] == 1 && v[I][J] == -1 && !status) {
                        arah = 1;
                        status = true;
                        v[I][J] = 1;
                    }

                    //D2
                    I = i - 1;
                    J = j + 1;
                    if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] == 1 && v[I][J] == -1 && !status) {
                        arah = 2;
                        status = true;
                        v[I][J] = 1;
                    }

                    //D3
                    I = i;
                    J = j + 1;
                    if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] == 1 && v[I][J] == -1 && !status) {
                        arah = 3;
                        status = true;
                        v[I][J] = 1;
                    }

                    //D4
                    I = i + 1;
                    J = j + 1;
                    if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] == 1 && v[I][J] == -1 && !status) {
                        arah = 4;
                        status = true;
                        v[I][J] = 1;
                    }

                    //D5
                    I = i + 1;
                    J = j;
                    if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] == 1 && v[I][J] == -1 && !status) {
                        arah = 5;
                        status = true;
                        v[I][J] = 1;
                    }

                    //D6
                    I = i + 1;
                    J = j - 1;
                    if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] == 1 && v[I][J] == -1 && !status) {
                        arah = 6;
                        status = true;
                        v[I][J] = 1;
                    }

                    //D7
                    I = i;
                    J = j - 1;
                    if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] == 1 && v[I][J] == -1 && !status) {
                        arah = 7;
                        status = true;
                        v[I][J] = 1;
                    }

                    //D8
                    I = i - 1;
                    J = j - 1;
                    if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] == 1 && v[I][J] == -1 && !status) {
                        arah = 8;
                        status = true;
                        v[I][J] = 1;
                    }

                    //SET ARAH ALIRAN
                    if (!status) {
                        arah = 0;
                        result[i][j] = arah;
                    } else {
                        result[i][j] = arah;
                        Titik origin = center;
                        Titik destination = getTitik(i, j, arah);
                        Titik[] od = {origin, destination};
                        graph.add(od);
                        if ( !antrianTitik.contains(destination)) {
                            antrianTitik.offer(destination);//tambahkan titik destination ke dalam antrian titik
                            int originX = origin.getX();
                            int originY = origin.getY();
                            int destinationX = destination.getX();
                            int destinationY = destination.getY();
                            double kecepatan = Math.sqrt(2*9.81*(data[originX][originY] - data[destinationX][destinationY]));
                            dataKecepatann[destinationX][destinationY] = kecepatan;
                        }
                    }
                }
                Titik[] alir = new Titik[aliran.size()];
                for (int i = 0; i < alir.length; i++) {
                    alir[i] = aliran.get(i);
                }
                titikAliran[z] = alir;
            }
        }
        //set result di water
        water.setResult(result);
        water.setGraph(graph);
        water.setDataKecepatan(dataKecepatann);
        water.setTitikAliran(titikAliran);
    }

    private Titik getTitik(int cx, int cy, int arah) {
        int x = -1;
        int y = -1;
        if (arah == 0) {
            x = cx;
            y = cy;
        } else if (arah == 1) {
            x = cx - 1;
            y = cy;
        } else if (arah == 2) {
            x = cx - 1;
            y = cy + 1;
        } else if (arah == 3) {
            x = cx;
            y = cy + 1;
        } else if (arah == 4) {
            x = cx + 1;
            y = cy + 1;
        } else if (arah == 5) {
            x = cx + 1;
            y = cy;
        } else if (arah == 6) {
            x = cx + 1;
            y = cy - 1;
        } else if (arah == 7) {
            x = cx;
            y = cy - 1;
        } else if (arah == 8) {
            x = cx - 1;
            y = cy - 1;
        }
        Titik destination = new Titik(x, y);
        return destination;
    }


    public Water getWater() {
        return this.water;
    }

    public String getStringData() {
        String sData = "NULL";
        if (this.water != null && this.water.getData() != null) {
            double[][] data = this.water.getData();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    sb.append(data[i][j] + "\t");
                }
                sb.append("\n");
            }
            sData = sb.toString();
        }
        return sData;
    }

    public String getDataKecepatanAliran(){
        String pesan = "NULL";
        Titik[][] titikAliran = this.water.getTitikAliran();
        double[][] dataKecepatan = this.water.getDataKecepatan();
        
        if(dataKecepatan != null && titikAliran!= null){
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < titikAliran.length; i++) {
                sb.append("TITIK PUSAT "+(i+1)+"\n");
                double total = 0;
                for (int j = 1; j < titikAliran[i].length; j++) {
                    Titik origin = titikAliran[i][j-1];
                    Titik destination = titikAliran[i][j];
                    double kecepatan = dataKecepatan[destination.getX()][destination.getY()];
                    total+=kecepatan;
                    sb.append(j+"."+origin+" -> "+destination+" = "+kecepatan+"\n");
                }
                sb.append("Rata-Rata Kecepatan = "+(total/ (titikAliran[i].length-1)+"\n"));
                sb.append("\n");
            }
            pesan = sb.toString();
        }
        return pesan;
    }

}
