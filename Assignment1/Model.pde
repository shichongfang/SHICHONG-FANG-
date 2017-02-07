PShape ship;
float roty=0;


void grid(float xstart, float xend, float ystart, float yend, float unit) {
  pushMatrix();
  //translate(0,0,2);
  stroke(200);
  strokeWeight(0.5);
  for (float i=xstart; i<xend; i+=unit) {
    line(i, ystart, i, yend);
  }
  for (float i=ystart; i<yend; i+=unit) {
    line(xstart, i, xend, i);
  }
  
  pushStyle();
  fill(#499B57);
  noStroke();
  textAlign(LEFT,CENTER);
  textSize(12);
  text("Stability",20,410);
  text("Power",20,430);
  text("Shield",20,450);
  text("Boost",20,470);
  text("Scan",20,490);
  
  for(int i=0;i<5;i++){
    rect(75,405+i*20,map(noise(i*2,(frameCount+6000)*0.005),0,1,20,150),8);
  }
  popStyle();
  popMatrix();
}

void display_noMouse() {
  grid(0, width/2-69, height/2, height, 30);
  roty+=0.01;

  pushMatrix();
  translate(width/4, height*3/4);
  //scale(0.8);
  rotateY(roty);
  shape(ship, 0, 0);
  popMatrix();
}

void display_mouse() {
  grid(0, width/2-69, height/2, height, 30);
  roty+=0.01;

  pushMatrix();
  translate(width/4, height*3/4);
  //scale(0.8);
  rotateY(map(mouseX, width/10, width*4/10, 0, TWO_PI));
  rotateX(map(mouseY, height*6/10, height*9/10, 0, TWO_PI));
  shape(ship, 0, 0);
  popMatrix();
}

boolean mouseInShipArea() {
  if (mouseX>width/10 && mouseX<width*4/10 &&
    mouseY>height*6/10 && mouseY<height*9/10) {
    return true;
  } else {
    return false;
  }
}