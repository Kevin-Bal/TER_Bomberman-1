package map;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

public class Map {
	
	public static int NORTH = 0;
	public static int SOUTH = 1;
	public static int EAST = 2;
	public static int WEST = 3;
	public static int STOP = 4;
	public static int BOMB = 5;

	
	private int size_x;
	private int size_y;
	private boolean walls[][];
	private boolean brokable_walls[][];
	
	protected ArrayList<Integer> ennemy_start_x;
	protected ArrayList<Integer> ennemy_start_y;
	
	public Map(String filename) throws Exception{
		
		try{
			System.out.println("Le fichier chargé : "+filename);
		//On lit le fichier pour determiner les différents éléments de la map
		
		InputStream flux =new FileInputStream(filename); 
		InputStreamReader lecture =new InputStreamReader(flux);
		BufferedReader tampon =new BufferedReader(lecture);
		
		//definition la ligne pour en claculer par la suite ça longueur
		String ligne;
		
		//attribut permettant de connaitre la longueur et la largeur de la map
		int nbX=0;
		int nbY=0;
		
		//lecture et parssage du fichier pour en déterminer la taille de la map
		while ((ligne = tampon.readLine())!=null)
		{
			ligne = ligne.trim();//permet d'enlever les espace de la ligne à la fin et au debut de la ligne (" %%%%%% ") => ("%%%%%%")
			if (nbX==0) {nbX = ligne.length();}
			else if (nbX != ligne.length()) throw new Exception("Toutes les lignes doivent avoir la même longueur");
			nbY++;
		}			
		tampon.close(); 
		System.out.println("### Taille de la map "+nbX+" ; "+nbY);
		
		//implémentation des différentes valeurs dans les variable correspondante
		
		size_x = nbX;
		size_y = nbY;
		
		walls = new boolean [size_x][size_y];
		brokable_walls  = new boolean [size_x][size_y];
		ennemy_start_x = new  ArrayList<Integer>();
		ennemy_start_y = new  ArrayList<Integer>();
		
		flux = new FileInputStream(filename); 
		lecture = new InputStreamReader(flux);
		tampon = new BufferedReader(lecture);
		int y=0;
		
		//Permet d'initialiser les différents éléments de la carte  
		while ((ligne=tampon.readLine())!=null)
		{
			ligne=ligne.trim();

			for(int x=0;x<ligne.length();x++)
			{
				if (ligne.charAt(x)=='%') 
					walls[x][y]=true; 
				else walls[x][y]=false;
				
				if (ligne.charAt(x)=='$') 
					brokable_walls[x][y]=true; 
				else brokable_walls[x][y]=false;
				
				//On rentre les coordonnée des ennemies dans differents ArrayList
				
				if (ligne.charAt(x)=='E') {
					System.out.println(ligne.charAt(x));
					
					ennemy_start_x.add(x);
					//System.out.println(ennemy_start_x.get(0));
					ennemy_start_y.add(y);
				}
			}
			y++;
		}			
		tampon.close(); 
		
		//On verifie que le labyrinthe est clos			
		for(int x=0;x<size_x;x++) if (!walls[x][0]) throw new Exception("Mauvais format du fichier: la carte doit etre close");
		for(int x=0;x<size_x;x++) if (!walls[x][size_y-1]) throw new Exception("Mauvais format du fichier: la carte doit etre close");
		for(y=0;y<size_y;y++) if (!walls[0][y]) throw new Exception("Mauvais format du fichier: la carte doit etre close");
		for(y=0;y<size_y;y++) if (!walls[size_x-1][y]) throw new Exception("Mauvais format du fichier: la carte doit etre close");
		System.out.println("### Carte chargée.");
		
		
		}catch (Exception e){
			System.out.println("Erreur : "+e.getMessage());
		}
	}
	
	
	//renvoie la largeur de la map
	public int getSizeX() {return(size_x);}

	//renvoie la hauteur de la map
	public int getSizeY() {return(size_y);}
	
	//verifie à un ecoordonnée si c'est un mur ou non 
	public boolean isWall(int x,int y) 
	{
		assert((x>=0) && (x<size_x));
		assert((y>=0) && (y<size_y));
		return(walls[x][y]);
	}
	
	//verifie à une coordonnée si c'est un mur destructible ou non 
	public boolean isBrokable_Wall(int x,int y) 
	{
		assert((x>=0) && (x<size_x));
		assert((y>=0) && (y<size_y));
		return(brokable_walls[x][y]);
	}
	
	
	//Renvoie le nb d'ennemies
	public int getNumber_of_ennemies(){
		return ennemy_start_x.size();
	}
	
	//Renvoie la position x de l'ennemi voulu
	public int getEnnemy_start_x(int i){
		return ennemy_start_x.get(i);
	}
	
	//Renvoie la position y de l'ennemi voulu
	public int getEnnemy_start_y(int i){
		return ennemy_start_y.get(i);
	}
	
}
