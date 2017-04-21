class Laser {
  color c;
  float xpos;
  float ypos;
  float yspeed;

  Laser(float tempXpos, float tempYpos, float tempYspeed) {
    xpos = tempXpos;
    ypos = tempYpos;
    yspeed = tempYspeed;
  }
  
  void display() {
    image(laser, xpos, ypos);
  }
  
  void move() {
    ypos += yspeed;
  }
  
  void boom() {
    // Loop through each laser
    for (int i = 0; i < Laser.size(); i++) 
    {
      Laser laser = (Laser) Laser.get(i);
      // Collision between laser and each enemy
      for (int j = 0; j < enemies.length; j++) 
      {
        if (laser.xpos > (enemies[j].xpos - 10) && laser.xpos < (enemies[j].xpos + asteroid_width) && laser.ypos > enemies[j].ypos && laser.ypos < (enemies[j].ypos + asteroid_height)) 
        {
          Laser.remove(i);
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