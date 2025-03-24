var good = createSprite(500, 500);
var good1 = createSprite(200, 200);
var park = createSprite(155, -60);
var park2 = createSprite(155, -60);
var park1 = createSprite(155, 66);
park1.setAnimation("park1");
var sprite = createSprite(500, 500);
sprite.setAnimation("explosion");
var carbl = createSprite(200, 335);
carbl.setAnimation("carbl");
var carb = createSprite(55, 66);
carb.setAnimation("carb");
var carr = createSprite(255, 66);
carr.setAnimation("carr");
var cary = createSprite(352, 66);
cary.setAnimation("cary");
playSound("sound://category_background/stride.mp3", false);
function draw() {
  background("grey");
  fill("yellow");
  rect(0, 0, 10, 135);
  rect(100, 0, 10, 135);
  rect(200, 0, 10, 135);
  rect(300, 0, 10, 135);
  rect(389, 0, 10, 135);
  fill("grey");
  if (keyDown("w")) {
    carbl.y = carbl.y - 5;
  }
  if (keyDown("s")) {
    carbl.y = carbl.y + 5;
  }
  if (keyDown("d")) {
    carbl.x = carbl.x + 5;
  }
  if (keyDown("a")) {
    carbl.x = carbl.x - 5;
  }
  if (carbl.collide(cary)) {
    cary.setAnimation("explosion");
    carbl.destroy();
    playSound("sound://category_explosion/8bit_explosion.mp3", false);
    stopSound("sound://category_background/stride.mp3");
  }
  if (carbl.collide(carb)) {
    carb.setAnimation("explosion");
    carbl.destroy();
    playSound("sound://category_explosion/8bit_explosion.mp3", false);
    stopSound("sound://category_background/stride.mp3");
  }
  if (carbl.collide(carr)) {
    carr.setAnimation("explosion");
    carbl.destroy();
    playSound("sound://category_explosion/8bit_explosion.mp3", false);
    stopSound("sound://category_background/stride.mp3");
  }
  if (carbl.overlap(park)) {
    good1.setAnimation("good1");
    carbl.overlap(park);
    playSound("sound://category_achievements/lighthearted_bonus_objective_1.mp3", false);
    carbl.collide(park2);
    stopSound("sound://category_achievements/lighthearted_bonus_objective_1.mp3");
  }
  carbl.collide(park2);
  drawSprites();
}
