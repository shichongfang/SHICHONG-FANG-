class Bullet {
  color c;
  float xpos;
  float ypos;
  float yspeed;

  Bullet(float tempXpos, float tempYpos, float tempYspeed) {
    xpos = tempXpos;
    ypos = tempYpos;
    yspeed = tempYspeed;
  }
  
  void display() {
    image(red_laser, xpos, ypos);
  }
  
  void move() {
    ypos += yspeed;
  }
  
  void boom() {
    // Loop through each bullet
    for (int i = 0; i < bullets.size(); i++) 
    {
      Bullet bullet = (Bullet) bullets.get(i);
      // Collision between bullet and each enemy
      for (int j = 0; j < enemies.length; j++) 
      {
        if (bullet.xpos > (enemies[j].xpos - 10) && bullet.xpos < (enemies[j].xpos + asteroid_width) && bullet.ypos > enemies[j].ypos && bullet.ypos < (enemies[j].ypos + asteroid_height)) 
        {
          bullets.remove(i);
          explosion = minim.loadFile("explosion.aiff");
          explosion.play();
          // Reset enemy position
          enemies[j].xpos = (int) random(0, width/100) * 100;
          enemies[j].ypos = (int) random(-10, -2) * 100;
        }
      } // end inner for()
    } // end outer for()
  } // end boom()
}
