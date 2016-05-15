import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Interpreter {
	private static String board[][]=new String[9][9];
	private static boolean boardf[][]=new boolean[9][9];
	private static int x,y=0;
	private static boolean flag=true;
	private static String color;
	private static int whiteRec=0,blackRec=0,whiteLine=0,blackLine=0;
	private static Scanner nsc;
	//white = x true|| black = o false
	//System.out.println("CHECK");
	public static void mainCall() throws FileNotFoundException{
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				boardf[i][j]=false;
			}
		}
		File in=new File("src/in.txt");
		Scanner sc=new Scanner(in);
		String next=sc.nextLine();
		if(next.contains("START_GAME")){
			if(gameBlock(sc)==true){
				checkBoard();
				printBoard();
			}
			
		}else{
			programBlock(sc); //Go to line 79
		}
		sc.close();
	}
	
	public static boolean gameBlock(Scanner sc){
		while(sc.hasNext()){
			String next=sc.next();
			switch(next){
			case "MOVE": color=sc.next();sc.next();String cell=sc.next();
				x=cellFinder(cell)[0];
				y=cellFinder(cell)[1];
				//System.out.println(color+cell+x+y+flag);
				if(color.equals("WHITE") && flag==true){
					if(board[x][y]==null){
						board[x][y]="X";
						flag=false;
					}else{
						System.out.println("Error, cell "+cell+" occupied.");
						flag=false;
					}
				}else if(color.equals("BLACK") && flag==false){
					if(board[x][y]==null){
						board[x][y]="O";
						flag=true;
					}else{
						System.out.println("Error, cell "+cell+" occupied.");
						flag=true;
					}
				}else{
					System.out.println("Error, two consecutive moves.");
				}
				break;
			case "PASS": color=sc.next();
				if(color.equals("WHITE") && flag==true){
					flag=false;
				}else if(color.equals("BLACK") && flag==false){
					flag=true;
				}else{
					System.out.println("Error, two consecutive moves.");
				}
				break;
				
		}
	}
		return true;
}
		
	
	public static void programBlock(Scanner sc) throws FileNotFoundException, NumberFormatException{
		File in=new File("src/in.txt");
		nsc = new Scanner(in);
		Hashtable<String, Double> reference=new Hashtable<String, Double>();
		while(nsc.hasNextLine()){
			String nextLine=nsc.nextLine();
			//value declarations
			if(nextLine.contains("=") && !nextLine.contains("grade") && !nextLine.contains("average")){
				String temp[]=nextLine.split("=");
				if(temp.length==2){
					if(isNumeric(temp[1].charAt(1))){
						reference.put(temp[0].trim(), Double.parseDouble(temp[1].trim()));
					}
				}
			}else if(nextLine.contains("WHILE")){
				//Power Problem
				while(reference.get("exponent")>0){
					reference.replace("power", reference.get("power")*reference.get("base"));
					reference.replace("exponent", reference.get("exponent")-1);
					}
				}
			else if(nextLine.contains("average")){
				//Average Problem
				String a[]=nextLine.split("=");
				String b[]=a[1].trim().split("\\/");
				String c=b[0].substring(1, b[0].length()-1);
				String d[]=c.split("\\+");
				String dcount=Integer.toString(d.length);
				double sum=0;
				reference.replace(b[1],Double.parseDouble(dcount));
				for(int i=0;i<d.length;i++){
					sum+=reference.get(d[i]);
				}
				reference.put(a[0].trim(), sum/reference.get(b[1]));
			}
			else{
			//Grading Problem
			String[] a=nextLine.split("=");
			String[] b=a[1].trim().split("\\+");
			double sum=0;
			for(int i=0;i<=3;i++){
				String temp[]=b[i].split("\\*");
				if(reference.containsKey(temp[0])&&reference.containsKey(temp[1])){
					sum+=reference.get(temp[0])*reference.get(temp[1]);
					reference.put("grade",sum);
				}
			}
		}
		}
		if(reference.get("power")==null && reference.get("average")==null){
			System.out.println("OUTPUT: "+reference.get("grade"));
		}else if(reference.get("grade")==null && reference.get("average")==null){
			System.out.println("OUTPUT: "+reference.get("power"));
		}else{
			System.out.println("OUTPUT: "+reference.get("average"));
		}
		}
	private static boolean isNumeric(char c) {
		if(Character.isLetter(c)){
			return false;
		}
		return true;
	}
	public static int[] cellFinder(String cell){
		int[] xy=new int[2];
		xy[0]=Character.getNumericValue(cell.charAt(1))-1;
		xy[1]=Character.hashCode(cell.charAt(0))-65;
		return xy;
	}
	public static void printBoard(){
		System.out.println("	A	B	C	D	E	F	G	H	I");
		for(int i=0;i<9;i++){
			System.out.print((i+1)+"	");
			for(int j=0;j<9;j++){
				if(board[i][j]==null){
					System.out.print(" 	");
				}else{
					System.out.print(board[i][j]+"	");
				}
			}
			System.out.println();
		}
		System.out.println("White Rectangles: "+whiteRec+" White Lines: "+whiteLine);
		System.out.println("Black Rectangles: "+blackRec+" Black Lines: "+blackLine);
	}
	public static void checkBoard(){
		//WHITE
				for(int i=1;i<=7;i++){
					for(int j=1;j<=8;j++){
						if(board[i-1][j-1]=="X"&&board[i][j-1]=="X"&&board[i+1][j-1]=="X"&&
								boardf[i-1][j-1]==false&&boardf[i][j-1]==false&&boardf[i+1][j-1]==false&&
								board[i-1][j]=="X"&&board[i][j]=="X"&&board[i+1][j]=="X"&&
								boardf[i-1][j]==false&&boardf[i][j]==false&&boardf[i+1][j]==false){
							System.out.println("White Rectangle - Vertical FROM "+(i)+", "+(j));
							whiteRec++;
							boardf[i-1][j-1]=boardf[i][j-1]=boardf[i+1][j-1]=boardf[i-1][j]=boardf[i][j]=boardf[i+1][j]=true;
						}
					}
				}
				for(int i=1;i<=8;i++){
					for(int j=1;j<=7;j++){
						if(board[i-1][j-1]=="X"&&board[i-1][j]=="X"&&board[i-1][j+1]=="X"&&
								boardf[i-1][j-1]==false&&boardf[i-1][j]==false&&boardf[i-1][j+1]==false&&
								board[i][j-1]=="X"&&board[i][j]=="X"&&board[i][j+1]=="X"&&
								boardf[i][j-1]==false&&boardf[i][j]==false&&boardf[i][j+1]==false){
							System.out.println("White Rectangle - Horizontal FROM "+(i)+", "+(j));
							whiteRec++;
							boardf[i-1][j-1]=boardf[i-1][j]=boardf[i-1][j+1]=boardf[i][j-1]=boardf[i][j]=boardf[i][j+1]=true;
						}
					}
				}
				for(int i=1;i<=9;i++){
					for(int j=1;j<=7;j++){
						if(board[i-1][j-1]=="X"&&board[i-1][j]=="X"&&board[i-1][j+1]=="X"&&
								boardf[i-1][j-1]==false&&boardf[i-1][j]==false&&boardf[i-1][j+1]==false){
							System.out.println("White Line - Horizontal FROM "+(i)+", "+(j));
							whiteLine++;
							boardf[i-1][j-1]=boardf[i-1][j]=boardf[i-1][j+1]=true;
						}
					}
				}
				for(int i=1;i<=7;i++){
					for(int j=1;j<=9;j++){
						if(board[i-1][j-1]=="X"&&board[i][j-1]=="X"&&board[i+1][j-1]=="X"&&
								boardf[i-1][j-1]==false&&boardf[i][j-1]==false&&boardf[i+1][j-1]==false){
							System.out.println("White Line - Vertical FROM "+(i)+", "+(j));
							whiteLine++;
							boardf[i-1][j-1]=boardf[i][j-1]=boardf[i+1][j-1]=true;
						}
					}
				}
				//BLACK
				for(int i=1;i<=7;i++){
					for(int j=1;j<=8;j++){
						if(board[i-1][j-1]=="O"&&board[i][j-1]=="O"&&board[i+1][j-1]=="O"&&
								boardf[i-1][j-1]==false&&boardf[i][j-1]==false&&boardf[i+1][j-1]==false&&
								board[i-1][j]=="O"&&board[i][j]=="O"&&board[i+1][j]=="O"&&
								boardf[i-1][j]==false&&boardf[i][j]==false&&boardf[i+1][j]==false){
							System.out.println("Black Rectangle - Vertical FROM "+(i)+", "+(j));
							blackRec++;
							boardf[i-1][j-1]=boardf[i][j-1]=boardf[i+1][j-1]=boardf[i-1][j]=boardf[i][j]=boardf[i+1][j]=true;
						}
					}
				}
				for(int i=1;i<=8;i++){
					for(int j=1;j<=7;j++){
						if(board[i-1][j-1]=="O"&&board[i-1][j]=="O"&&board[i-1][j+1]=="O"&&
								boardf[i-1][j-1]==false&&boardf[i-1][j]==false&&boardf[i-1][j+1]==false&&
								board[i][j-1]=="O"&&board[i][j]=="O"&&board[i][j+1]=="O"&&
								boardf[i][j-1]==false&&boardf[i][j]==false&&boardf[i][j+1]==false){
							System.out.println("Black Rectangle - Horizontal FROM "+(i)+", "+(j));
							blackRec++;
							boardf[i-1][j-1]=boardf[i-1][j]=boardf[i-1][j+1]=boardf[i][j-1]=boardf[i][j]=boardf[i][j+1]=true;
						}
					}
				}
				for(int i=1;i<=9;i++){
					for(int j=1;j<=7;j++){
						if(board[i-1][j-1]=="O"&&board[i-1][j]=="O"&&board[i-1][j+1]=="O"&&
								boardf[i-1][j-1]==false&&boardf[i-1][j]==false&&boardf[i-1][j+1]==false){
							System.out.println("Black Line - Horizontal FROM "+(i)+", "+(j));
							blackLine++;
							boardf[i-1][j-1]=boardf[i-1][j]=boardf[i-1][j+1]=true;
						}
					}
				}
				for(int i=1;i<=7;i++){
					for(int j=1;j<=9;j++){
						if(board[i-1][j-1]=="O"&&board[i][j-1]=="O"&&board[i+1][j-1]=="O"&&
								boardf[i-1][j-1]==false&&boardf[i][j-1]==false&&boardf[i+1][j-1]==false){
							System.out.println("Black Line - Vertical FROM "+(i)+", "+(j));
							blackLine++;
							boardf[i-1][j-1]=boardf[i][j-1]=boardf[i+1][j-1]=true;
						}
					}
				}
	}
	public static void main(String args[]) throws FileNotFoundException{
		Main.mainCall();
		mainCall();
	}
}
