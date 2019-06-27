import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static double point[][]=new double[2][1000];
    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        int num=Integer.parseInt(args[0]);
        String local=args[1];
        String link = args[2];
        int strt=Integer.parseInt(args[3]);
        int end =Integer.parseInt(args[4]);
        double data[][]=new double[1000][1000];
        for(int i=0;i<1000;i++){
            for(int j=0;j<1000;j++){
                data[i][j]=Double.MAX_VALUE/3;
            }
        }
        int[][] path=new int[1000][1000];
        for(int i=0;i<1000;i++){
            for(int j=0;j<1000;j++)
                path[i][j]=j;
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader("src\\"+local));
            BufferedReader br2=new BufferedReader(new FileReader("src\\"+link));
            String line;
            for(int i=0;i<1000;i++){
                String[] s=br.readLine().split("\\s+");
                point[0][i]=Double.parseDouble(s[1]);
                point[1][i]=Double.parseDouble(s[2]);
            }
            while((line= br2.readLine())!=null){
               String[] s=line.split("\\s+");
               int a1=Integer.parseInt(s[0]);
               int a2=Integer.parseInt(s[1]);
               data[a1][a2]=Math.pow(Math.pow(point[0][a2]-point[1][a2],2)+Math.pow(point[0][a1]-point[1][a1],2),0.5);
               data[a2][a1]=data[a1][a2];
            }
        }catch (IOException e){
            System.out.println("error");
        }
        if(num==1) {
            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 1000; j++) {
                    for (int k = 0; k < 1000; k++) {
                        if (data[i][j] > data[i][k] + data[k][j]) {
                            data[i][j] = data[i][k] + data[k][j];
                            path[i][j] = path[i][k];
                        }
                    }
                }
            }
            if (data[end][strt] != Double.MAX_VALUE / 3) {
                System.out.println("length: " + data[end][strt]);
                System.out.print("path: " + strt + "-");
                while (path[strt][end] != end) {
                    System.out.print(path[strt][end] + "-");
                    strt = path[strt][end];
                }
                System.out.print(end);
            } else {
                System.out.print("No such path!");
            }
        }else if(num==2){
            List<Integer> open=new ArrayList<>();
            List<Integer> closed=new ArrayList<>();
            open.add(strt);
            int head;
            boolean issuccess=false;
            while(true){

                head=open.get(0);
                open.remove(0);
                List<Integer> a=new ArrayList<>();
                for(int i=0;i<1000;i++){
                    if(head!=i&&(data[head][i]+g(i,end)<=g(head,end)*2)){
                        if(!open.contains(i)&&!closed.contains(i)){
                        a.add(i);
                        }
                    }
                }
                if(a.contains(end)){
                    issuccess=true;
                    closed.add(head);
                    break;
                }
                if(a.size()==0){
                    if(open.size()!=0)
                        continue;
                    else {
                        issuccess=false;
                        break;
                    }
                }else{
                    closed.add(head);
                    for(int j:a){
                        open.add(j);
                    }
                    for(int x=0;x<open.size();x++){
                        for(int y=0;y<open.size()-1;y++){
                            if(data[head][open.get(y)]+g(head,open.get(y))>data[head][open.get(y+1)]+g(head,open.get(y+1))){
                                Collections.swap(open,y,y+1);
                            }
                        }
                    }
                }
            }
            if(issuccess){
                int tail=end;
                List<Integer> p=new ArrayList<>();
                p.add(end);
                for(int i=closed.size()-1;i>=0;i--){
                    if(data[closed.get(i)][tail]!=Double.MAX_VALUE / 3){
                        p.add(closed.get(i));
                        tail=closed.get(i);
                    }
                }
                double[][] ndata=new double[p.size()][p.size()];
                for(int i=0;i<p.size();i++){
                    for(int j=0;j<p.size();j++)
                        ndata[i][j]=data[p.get(i)][p.get(j)];
                }
                int size=p.size();
                int[][] npath=new int[1000][1000];
                for(int i=0;i<size;i++){
                    for(int j=0;j<size;j++)
                        npath[i][j]=j;
                }
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        for (int k = 0; k < size; k++) {
                            if (ndata[i][j] > ndata[i][k] + ndata[k][j]) {
                                ndata[i][j] = ndata[i][k] + ndata[k][j];
                                npath[i][j] = npath[i][k];
                            }
                        }
                    }
                }
                strt=0;
                end=size-1;
                if (ndata[0][p.size()-1] != Double.MAX_VALUE / 3) {
                    System.out.println("length: " + ndata[0][p.size()-1]);
                    System.out.print("path: " + p.get(strt) + "-");
                    while (npath[strt][end] != end) {
                        System.out.print(p.get(npath[strt][end]) +"-");
                        strt = npath[strt][end];
                    }
                    System.out.print(p.get(end));
                } else {
                    System.out.print("No such path!");
                }



            }else{
                System.out.print("No such path!");
            }

        }else if(num==3){
            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 1000; j++) {
                    for (int k = 0; k < 1000; k=k+2) {
                        if (data[i][j] > data[i][k] + data[k][j]) {
                            data[i][j] = data[i][k] + data[k][j];
                            path[i][j] = path[i][k];
                        }
                    }
                }
            }
            if (data[end][strt] != Double.MAX_VALUE / 3) {
                System.out.println("length: " + data[end][strt]);
                System.out.print("path: " + strt + "-");
                while (path[strt][end] != end) {
                    System.out.print(path[strt][end] + "-");
                    strt = path[strt][end];
                }
                System.out.print(end);
            } else {
                System.out.print("No such path!");
            }
        }else{
            System.out.print("invalid input number!");
        }
        long endTime = System.currentTimeMillis();
        System.out.println();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");

    }
    public static double g(int i,int j){
        return Math.pow(Math.pow(point[0][i]-point[1][j],2)+Math.pow(point[0][i]-point[1][j],2),0.5);
    }
}
