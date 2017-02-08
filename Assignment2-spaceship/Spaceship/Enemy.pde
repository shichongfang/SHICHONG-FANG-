class Enemy {
  float xpos;
  float ypos;
  float yspeed;
  
  // Constructor
  Enemy(float tempXpos, float tempYpos, float tempYspeed) {
    xpos = tempXpos;
    ypos = tempYpos;
    yspeed = tempYspeed;
  }
  
  void display() {
    stroke(0);

    image(asteroid, xpos, ypos);
  }
  
  void drop() {
    ypos += yspeed;
    // Restart block from top
    if (ypos > height) 
    {
      xpos = (int) random(0, width/100) * 100;
      ypos = (int) random(-10, -2) * 100;
    } // end if()
    
    // Increase speed at intervals
    if (score == 0) {
      yspeed = 4;
    }
    if (score == 10) {
      yspeed = 6;
    }
    if (score == 25) {
      yspeed = 7;
    }
    if (score == 50) {
      yspeed = 8;
    }
  } // end drop()
  
  void collision() {
    // checks for collision between player and each enemy
    for (int i = 0; i < enemies.length; i++) {
      if ((player.xpos + spaceship_width) > enemies[i].xpos && player.xpos < (enemies[i].xpos + spaceship_width) && (player.ypos + spaceship_height) > enemies[i].ypos && player.ypos < (enemies[i].ypos + spaceship_height)) 
      {
        hit = minim.loadFile("hit.wav");
        hit.play();
        // Sets new position of asteroid
        enemies[i].xpos = (int) random(0, width/100) * 100;
        enemies[i].ypos = (int) random(-10, -2) * 100;
        // Deduct a live
        lives -= 1;
      } // end if()
    } // end for()
  } // end collision()
} // end enemy