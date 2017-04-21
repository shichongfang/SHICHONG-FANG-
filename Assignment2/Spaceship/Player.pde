class Player {
  float xpos;
  float ypos;
  float xspeed;
  float yspeed;
  
  boolean engage = true;
  int delay = 0;
  
  Player(float tempXpos, float tempYpos, float tempXspeed, float tempYspeed) {
    xpos = tempXpos;
    ypos = tempYpos;
    xspeed = tempXspeed;
    yspeed = tempYspeed;
  }
  
  void display() {
    stroke(0);
    image(spaceship, xpos, ypos);
    
    // Prevent spaceship from leaving top of screen
    if (ypos < 0 && (keyPressed && key == 'w')) {
      ypos = 0;
    }
    // Prevent spaceship from leaving bottom of screen
    if (ypos + spaceship_height > height && (keyPressed && key == 's')) {
      ypos = height - spaceship_height;
    }
    // Prevent spaceship from leaving left of screen
    if (xpos + spaceship_height < 0 && (keyPressed && key == 'a')) {
      xpos = width;
    }
    // Prevent spaceship from leaving right of screen
    if (xpos + spaceship_height > width && (keyPressed && key == 'd')) {
      xpos = 0;
    }
  }

  void move() {
    if (keyPressed && key == 'w') ypos -= 5;
    if (keyPressed && key == 's') ypos += 5;
    if (keyPressed && key == 'a') xpos -= 5;
    if (keyPressed && key == 'd') xpos += 5;
  }
  
  void shoot() {
    if (keyPressed && key == 'j' && engage == true) {
      yspeed = -10;
      
      pew = minim.loadFile("pew.aiff");
      pew.play();
      
      Laser.add(new Laser(xpos + (spaceship_width/3), ypos - spaceship_height, yspeed));
      engage = false;
      delay = 0;
    }
    
    delay++;
    if (delay >= 20) {
      engage = true;
    }
  } // end shoot()
} // end player