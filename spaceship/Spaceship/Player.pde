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
    image(rocket, xpos, ypos);
    
    // Prevent rocket from leaving top of screen
    if (ypos < 0 && (keyPressed && key == 'w')) {
      ypos = 0;
    }
    // Prevent rocket from leaving bottom of screen
    if (ypos + rocket_height > height && (keyPressed && key == 's')) {
      ypos = height - rocket_height;
    }
    // Prevent rocket from leaving left of screen
    if (xpos + rocket_height < 0 && (keyPressed && key == 'a')) {
      xpos = width;
    }
    // Prevent rocket from leaving right of screen
    if (xpos + rocket_height > width && (keyPressed && key == 'd')) {
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
      
      bullets.add(new Bullet(xpos + (rocket_width/3), ypos - rocket_height, yspeed));
      engage = false;
      delay = 0;
    }
    
    delay++;
    if (delay >= 20) {
      engage = true;
    }
  } // end shoot()
} // end player
