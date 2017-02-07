Route route;

class Route {

  float x=width/2;
  float y=0;
  float wd=width/2;
  float ht=height/2;

  float gridUnit=30;

  //int qty=100;
  //PVector[]stops=new PVector[qty];
  float p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y;

  float factor=0;
  float xp, yp;

  Route() {
    p1x=random(wd/16, wd*3/16)+x;
    p1y=random(ht/16, ht/2)+y;

    p2x=random(wd*5/16, wd*7/16)+x;
    p2y=random(ht/2, ht*15/16)+y;

    p3x=random(wd*9/16, wd*11/16)+x;
    p3y=random(ht/16, ht/2)+y;

    p4x=random(wd*13/16, wd*15/16)+x;
    p4y=random(ht/2, ht*15/16)+y;



    //for(int i=0;i<qty;i++){
    //  float xpos=(i+1)*wd/(qty+1)+x;
    //  float ypos=map(noise(xpos*0.001),0,1,0,ht)+y;
    //  stops[i]=new PVector(xpos,ypos);
    //}
  }

  void drawHalo() {
    float size=map(sin(frameCount*0.1), -1, 1, 1, 2);
    for (int i=0; i<25; i++) {
      noStroke();
      fill(#9FF8FA, 13);

      ellipse(xp, yp, i*size, i*size);
    }
  }

  void update() {
    factor+=0.0001;
    if (factor>=1) {
      factor=0;
    }
  }

  void drawTrail() {
    noFill();
    strokeWeight(8);
    stroke(#82EFF2, 200);

    beginShape();
    float xb, yb;
    for (float f=0; f<factor; f+=0.01) {
      xb=bezierPoint(p1x, p2x, p3x, p4x, f);
      yb=bezierPoint(p1y, p2y, p3y, p4y, f);
      vertex(xb, yb);
    }
    xp=bezierPoint(p1x, p2x, p3x, p4x, factor);
    yp=bezierPoint(p1y, p2y, p3y, p4y, factor);
    vertex(xp, yp);
    endShape();
  }

  void drawBase() {
    noFill();
    strokeWeight(6);
    stroke(#75B0E3, 150);

    bezier(p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y);
    //beginShape();
    //for(int i=0;i<qty;i++){
    //  vertex(stops[i].x,stops[i].y);
    //}
    //endShape();
  }


  void drawGrid() {
    stroke(200);
    strokeWeight(0.5);
    for (float i=x; i<=x+wd; i+=gridUnit) {
      line(i, y, i, y+ht);
    }
    for (float i=y; i<=y+ht; i+=gridUnit) {
      line(x, i, x+wd, i);
    }
  }
  
  void drawText(){
    pushStyle();
    fill(#499B57);
    noStroke();
    textAlign(LEFT, CENTER);
    textSize(12);
    //text("Range", 20, 110);
    //text("Freq", 20, 70);
    //text("Granularity", 20, 90);
    text("Detecing    ", width-150, 70);
    text("Covered    ", width-150, 90);
    text(int(noise((frameCount+26000)*0.01)*235),width-50,70);
    text(int(noise((frameCount+36000)*0.001)*100),width-50,90);

    //for (int i=0; i<3; i++) {
    //  rect(100, 70+i*20, map(noise(i*2, (frameCount+16000)*0.005), 0, 1, 20, 100), 8);
    //}
    popStyle();
  }
}