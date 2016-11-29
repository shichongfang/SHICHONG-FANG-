

void drawFrame(){
  pushMatrix();
  translate(0,0,1);
  
  noFill();
  stroke(#499B57);
  strokeWeight(6);
  line(width/2+110,height/2,width-20,height/2);
  
  line(width/2,height/2-110,width/2,20);
  line(20,20,width-20,20);
  
  line(width/2-110,height/2,20,height/2);
  
  
  drawText("Corresponse",width-200,height/2);
  drawText("Map",width-200,20);
  drawText("Radar",20,20);
  drawText("Status",20,height/2);
  
  popMatrix();
}


void drawText(String txt,float x,float y){
  noStroke();
  fill(#499B57);
  rect(x,y,180,30,8);
  fill(255);
  textSize(18);
  textAlign(CENTER,CENTER);
  text(txt,x+90,y+15);
  
}