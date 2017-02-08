// Sound libraries 
import ddf.minim.spi.*;
import ddf.minim.signals.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.ugens.*;
import ddf.minim.effects.*;

Minim minim;
AudioPlayer beats;
AudioPlayer pew;
AudioPlayer explosion;
AudioPlayer pickup;
AudioPlayer hit;

// General global variables
int lives = 3;
int score = 0;
char choice = 'A';
String[] temp = new String[1];

boolean pause = false;
boolean cont = false;

// File IO
int[] highscore;

// Load images
PImage spaceship;
PImage asteroid;
PImage diamond;
PImage laser;
PImage start;
PImage background;
PImage gameOver;

// Object dimensions
int spaceship_width = 30;
int spaceship_height = 50;

int asteroid_width = 50;
int asteroid_height = 50;

int diamond_width = 50;
int diamond_height = 67;

int laser_width = 30;
int laser_height = 31;

// Inintialise classes
Player player;
Enemy[] enemies = new Enemy[8];
Friendly[] friendlies = new Friendly[3];
ArrayList<Laser> Laser = new ArrayList<Laser>();

void setup() 
{
  size(600, 800);
  
  // Player
  player = new Player(width/2 - 20, height - height/4, 0, 0);
  
  // Enemies
  for (int i = 0; i < enemies.length; i++) {
    enemies[i] = new Enemy(width, height, 4);
  } 
  
  // Friendlies
  for (int i = 0; i < friendlies.length; i++) {
    friendlies[i] = new Friendly(width, height, 3);
  }
  
  // Define sprites
  spaceship = loadImage("spaceship.png");
  asteroid = loadImage("asteroid.png");
  diamond  = loadImage("diamond.png");
  laser = loadImage("laser.png"); 
  start = loadImage("start.png");
  background = loadImage("stars.png");
  gameOver = loadImage("game_over.png");
  
  // Generate new sound classes
  minim = new Minim(this);
  minim = new Minim(this);
  minim = new Minim(this);
  minim = new Minim(this);
  minim = new Minim(this);
  
  // Play music
  beats = minim.loadFile("music.mp3");
  beats.rewind();
  beats.play();
  
  loadData();
  
} // end setup()

void draw() 
{
  switch (choice) 
  {
    case 'A':
      background(start);
      score = 0;
      // Menu options
      if (mousePressed && mouseX > 110 && mouseX < 510 && mouseY > 160 && mouseY < 240) {
        choice = 'B';
      }
      
      if (mousePressed && mouseX > 160 && mouseX < 440 && mouseY > 420 && mouseY < 500) {
        exit();
      }
      
      break;
      
    case 'B':
      background(background);
      
      fill(255);
      textSize(32);
      text("Lives: " + lives, width - 150, height/20);
      text("Score: " + score, width/20, height/20);
    
      // Player
      player.display();
      player.move();
      player.shoot();
      
      // Enemies
      for (int i = 0; i < enemies.length; i++) {
        enemies[i].display();
        enemies[i].drop();
        enemies[i].collision();
      }
      
      // Friendlies
      for (int i = 0; i < friendlies.length; i++) {
        friendlies[i].display();
        friendlies[i].drop();
        friendlies[i].collision();
      }
      
      // Lazerz
      for (int i = 0; i < Laser.size(); i++) {
        Laser.get(i).display();
        Laser.get(i).move();
        Laser.get(i).boom();
      }
      
      if (lives == 0) {
        lives = 3;
        player.xpos = width/2 - 20;
        player.ypos = height - height/4;
        choice = 'C';
      }
      
      break;
      
      case 'C':
        background(gameOver);
        
        // Update highscore
        int tempscore = int(highscore[0]);
        if (score > tempscore) {
          temp[0] = str(score);
          saveStrings("highscore.txt", temp);
        }
        
        loadData();
        
        fill(103, 202, 101);
        text(highscore[0], 408, 382);
        
        if (keyPressed && key == 'y') {
          choice = 'A';
          break;
        }
        
        if (keyPressed && key == 'n') {
          exit();
        }
        
        if (cont == true) {
          choice = 'A';
        }
        
  } // end switch()
} // end draw()

void loadData() {
  String[] lines = loadStrings("highscore.txt");
  highscore = new int[lines.length];
  
  for (int i = 0; i < lines.length; i++) {
    String[] data = split(lines[i], ",");
    highscore[i] = Integer.parseInt(data[0]);
  }
}

void keyPressed() {
  
  if (keyCode == ' ') {
    if (looping) {
      noLoop();
    }
    else { 
      loop();
    }
  } // end if()
} // end keyPressed()