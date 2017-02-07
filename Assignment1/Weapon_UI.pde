
PFont font;
void setup() {
  size(1280, 720,P3D);
  colorMode(HSB);
  route=new Route();
  corr=new Corresponse();
  radar=new Radar();
  meter=new Meter();
  font=loadFont("AlTarikh-48.vlw");
  textFont(font);
  myFont=loadFont("AlNile-Bold-48.vlw");
  
  ship=loadShape("atmo_fighter2.obj");
}

void draw() {
  background(0);
  //grid(0,width,0,height,30);
  
  route.drawGrid();
  route.drawBase();
  route.update();
  route.drawTrail();
  route.drawHalo();
  route.drawText();
  
  corr.mouseEvent();
  corr.drawCorresponse();
  
  if(mouseInShipArea()){
    display_mouse();
  }else{
    display_noMouse();
  }
  
  radar.update();
  radar.display();
  
  if(mousePressed){
    meter.onSpeed();
  }
  meter.update();
  meter.display();
  
  
  drawFrame();
}