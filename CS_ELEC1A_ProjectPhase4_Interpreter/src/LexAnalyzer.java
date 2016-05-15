import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class LexAnalyzer {
	public static String out=""; //output
	public static void mainCall(){
		try {
			File in=new File("src/in.txt");
			System.out.println(" --SOF-- ");
			Scanner sc=new Scanner(new FileReader(in.getPath()));
			System.out.print("Input: ");
			while(sc.hasNext()){
				System.out.print(sc.next()+" ");
			}
			System.out.println();
			System.out.print("Output: ");
			sc.close();
			sc=new Scanner(new FileReader(in.getPath()));
			int i=0;
			while(sc.hasNext()){
				i++; 
				String next=sc.next();
				switch(next){
				case "START_GAME":out+="GAME STARTED\n";gameBlock(sc, i);break;
				default: progBlock(sc, i, next); break;				
				}
			}
			System.out.println(out);
			PrintWriter writer=new PrintWriter("src/in2.txt", "UTF-8");
			writer.println(out);
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found\n --EOF--");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void progBlock(Scanner sc, int i, String next){
		try{
			if(isNumeric(next)){
				next="INT";out+=next+" ";
			}else if(isSymbol(next)){
				switch(next){
				case "+": next="ADD";out+=next+" ";break;
				case "-": next="SUBT";out+=next+" ";break;
				case "*": next="MULT";out+=next+" ";break;
				case "/": next="DIV";out+=next+" ";break;
				case "^": next="EXP";out+=next+" ";break;
				case "=": next="EQUALS";out+=next+" ";break;
				
				case "<=":next="LESSEQUAL ";out+=next+" ";break;
				case ">=":next="GREATEREQUAL ";out+=next+" ";break;
				case "==":next="EQUAL ";out+=next+" ";break;
				case "(":next="LP";out+=next+" ";break;				
				case ")":next="RP";out+=next+" ";break;

			}
			}else if(isLetter(next)){
				next="VARIABLE";out+=next+" ";
			}
			else{
				switch(next){
				case"IF":next="IF ";out+=next+" ";break;
				case"ELSE":next="ELSE ";out+=next+" ";break;
				case"FOR":next="FOR ";out+=next+" ";break;
				case"WHILE":next="WHILE ";out+=next+" ";break;
				default:System.out.print("ERROR");System.exit(0);
				}
			}
			while(sc.hasNext()){
				next=sc.next();
				if(isNumeric(next)){
					next="INT";out+=next+" ";
				}else if(isSymbol(next)){
					switch(next){
					case "+": next="ADD";out+=next+" ";break;
					case "-": next="SUBT";out+=next+" ";break;
					case "*": next="MULT";out+=next+" ";break;
					case "/": next="DIV";out+=next+" ";break;
					case "^": next="EXP";out+=next+" ";break;
					case "=": next="EQUALS";out+=next+" ";break;
					
					case "<=":next="LESSEQUAL ";out+=next+" ";break;
					case ">=":next="GREATEREQUAL ";out+=next+" ";break;
					case "==":next="EQUAL ";out+=next+" ";break;
					case "(":next="LP";out+=next+" ";break;
					case ")":next="RP";out+=next+" ";break;
				}
				}else if(isLetter(next)){
					next="VARIABLE";out+=next+" ";break;
				}else{
					switch(next){
					case"IF":next="IF ";out+=next+" ";break;
					case"ELSE":next="ELSE ";out+=next+" ";break;
					case"FOR":next="FOR ";out+=next+" ";break;
					case"WHILE":next="WHILE ";out+=next+" ";break;
					default:System.out.print("ERROR");System.exit(0);
					}
				}
			}
		}
			catch(Exception e){
			System.out.println("\n --EOF--");
		}
	}
	
	public static void gameBlock(Scanner sc, int i){
		//Game block scanner + interpret
		while(sc.hasNext()){
			i++;
			switch(sc.next()){
			case "MOVE": move(sc, i);break;
			case "COUNT": count(sc, i);break;
			case "PASS": pass(sc, i);break;
			case "DISPLAY_BOARD": display(sc, i);break;
			case "DISPLAY_WINNER": displayW(sc, i);break;
			case "CHECK": check(sc,i);break;
			case "END_GAME": out+="Game Ended";System.out.println(" --EOF--");;break;
			default: System.out.println(i+"		ERROR\n --EOF--"); System.exit(0);
			}
		}
	}
	
	private static void displayW(Scanner sc, int i) {
		out+="DISPLAY WINNER\n";
		gameBlock(sc,i);
	}

	private static void check(Scanner sc, int i) {
		String check=sc.nextLine();
		String[] checkA=check.split(" ");
		if((check.contains("WHITE") || check.contains("BLACK")) && (check.contains("RECTANGLE") || check.contains("LINE"))){
			out+="CHECK "+ checkA[1]+" "+checkA[2]+"\n";
			gameBlock(sc, i);
		}else{
			System.out.println(i+"		ERROR\n --EOF--");
			System.exit(0);
		}
		
	}

	private static void count(Scanner sc, int i) {
		String count=sc.nextLine();
		String[] countA=count.split(" ");
		if((count.contains("WHITE")|| count.contains("BLACK"))){
			out+="COUNT "+ countA[1]+"\n";
			gameBlock(sc, i);
		}else{
			System.out.println(i+"		ERROR\n --EOF--");
			System.exit(0);
		}	
	}

	private static void display(Scanner sc, int i) {
		out+="DRAW BOARD\n";
		gameBlock(sc, i);
		
	}

	public static void move(Scanner sc, int i){
		String move=sc.nextLine();
		String[] moveA=move.split(" ");
		if((move.contains("WHITE")||move.contains("BLACK")) && move.contains("TO")){
			out+="MOVE "+ moveA[1]+" CELL\n";
			gameBlock(sc, i);
		}else{
			System.out.println(i+"		ERROR\n --EOF--");
			System.exit(0);
		}		
	}
	
	public static void pass(Scanner sc, int i){
		String pass=sc.nextLine();
		String[] passA=pass.split(" ");
		if((pass.contains("WHITE")|| pass.contains("BLACK"))){
			out+="PASSED "+ passA[1]+"\n";
			gameBlock(sc, i);
		}else{
			System.out.println(i+"		ERROR\n --EOF--");
			System.exit(0);
		}	
	}
	private static boolean isSymbol(String string) {
		if(string.equals("+")||string.equals("-")||string.equals("/")||string.equals("*")||string.equals("^")||
				string.contains("=")||string.equals("(")||string.equals(")")){
			return true;
		}else{
			return false;
		}
	}

	private static boolean isNumeric(String string) {
		try{
			Integer.parseInt(string);
			return true;
		}catch(NumberFormatException nfe){
			return isDouble(string);
		}
	}
	private static boolean isDouble(String string){
		try{
			Double.parseDouble(string);
			return true;
		}
		catch(NumberFormatException nfe){
			return false;
		}
	}
	private static boolean isLetter(String string){
		for(int i=0;i<string.length();i++){
			if(Character.hashCode(string.charAt(i))>64){
				return true;
			}
		}
		return false;
	}
}