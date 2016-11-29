Corresponse corr;

class Corresponse {

  float xpos=width*3/4-80;
  float ypos=height*3/4;
  float radius=160;
  float rr=8;

  int qty=40;
  color[]cls;

  PVector[]ptloc;
  PVector cen;

  ArrayList<PVector>talking;
  boolean prev_mouseOn=false;

  IntList log;

  Corresponse() {
    cen=new PVector(xpos, ypos);
    cls=new color[qty];
    ptloc=new PVector[qty];
    for (int i=0; i<qty; i++) {
      cls[i]=color(random(255), 60,200);

      ptloc[i]=new PVector(xpos+radius*cos(i*TWO_PI/qty), ypos+radius*sin(i*TWO_PI/qty));
    }

    talking=new ArrayList<PVector>();

    for (int i=0; i<5; i++) {
      talking.add(generatePair());
    }
    
    log=new IntList();
  }

  void mouseEvent() {
    if (prev_mouseOn==false) {
      if (mouseOn()!=-1) {
        prev_mouseOn=true;
        log=randomGroup(mouseOn());
      }
    }else{
      if(mouseOn()==-1){
        prev_mouseOn=false;
        log.clear();
      }
      
    }
  }
  
  void drawCorresponse(){
    if(prev_mouseOn){
      drawLog(mouseOn(),log);
    }else{
      drawFreeTalking();
    }
    drawPoints();
  }


  PVector generatePair() {
    PVector pair=new PVector(random(qty), random(qty));
    while (int(pair.x)==int(pair.y)) {
      pair=new PVector(random(qty), random(qty));
    }
    return pair;
  }

  void drawPoints() {
    noStroke();
    for (int i=0; i<qty; i++) {
      fill(cls[i]);      
      ellipse(ptloc[i].x, ptloc[i].y, rr*2, rr*2);
    }
  }

  void drawFreeTalking() {
    if (talking.size()<qty/2) {
      if (random(1)<0.2) {
        talking.add(generatePair());
      }
    }

    if (talking.size()>0) {
      if (random(1)<0.2) {
        talking.remove(0);
      }
    }

    for (PVector one : talking) {
      int ii=int(one.x);
      drawBezier(ii, int(one.y), 3);
      
      light(1100+(ii%5)*32,420+(ii/5)*32,cls[ii]);
    }

  }


  int mouseOn() {
    for (int i=0; i<qty; i++) {
      if (dist(mouseX, mouseY, ptloc[i].x, ptloc[i].y)<rr) {
        return i;
      }
    }
    return -1;
  }

  IntList randomGroup(int index) {
    int num=int(random(qty/2));
    IntList temp=new IntList();
    int count=0;

    while (count<num) {
      int g=int(random(qty));
      if (g!=index) {
        temp.append(g);
        count++;
      }
    }

    return temp;
  }

  void drawLog(int index, IntList list) {
    for (int each : list) {
      drawBezier(index, each, 3);
    }
  }

  void drawBezier(int index1, int index2, float wt) {
    noFill();
    stroke(cls[index1],150);
    strokeWeight(wt);
    PVector control1=PVector.lerp(cen, ptloc[index1], 0.3);
    PVector control2=PVector.lerp(cen, ptloc[index2], 0.3);
    bezier(ptloc[index1].x, ptloc[index1].y, 
      control1.x, control1.y, control2.x, control2.y, 
      ptloc[index2].x, ptloc[index2].y);
  }
  
  void light(float x,float y,color c){
    noStroke();
    fill(c,16);
    float r=2;
    for(int i=0;i<12;i++){
      ellipse(x,y,r,r);
      r*=1.16;
    }
  }
}