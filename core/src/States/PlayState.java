package States;



import com.amzuni.game.FlappyBirdMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import Sprites.Bird;
import Sprites.Tube;

public class PlayState extends State {
	private static final int TUBE_SPACING = 125;
	  private static final int TUBE_COUNT = 4;
	  private static final int GROUND_Y_OFFSET = -25;
	  public static final float VIEWPORT_GUI_HEIGHT = 650f;
	  public static final float VIEWPORT_GUI_WIDTH = 400f;
	  private OrthographicCamera cameraGUI;

	  private Bird bird;
	  private Texture bg;
	  private Texture ground;
	  private Vector2 groundPos1, groundPos2;
	  private int point = 0;
	  private SpriteBatch batch;
	  BitmapFont font;
	  private boolean gameover;
	  private Texture gameoverImg;

	  private Array < Tube > tubes;
	  protected PlayState(GameStateManager gsm) {
	    super(gsm);
	    init();
	    bird = new Bird(50, 300);
	    bg = new Texture("bg.png");
	    gameoverImg = new Texture("gameover.png");
	    ground = new Texture("ground.png");
	    cam.setToOrtho(false, FlappyBirdMain.WIDTH / 2, FlappyBirdMain.HEIGHT / 2);
	    font = new BitmapFont();
	    font.setColor(Color.WHITE);
	    font.getData().setScale(2, -2);
	    groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
	    groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
	    tubes = new Array < Tube > ();

	    for (int i = 2; i <= TUBE_COUNT; i++) {
	      tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
	    }
	    gameover = false;
	  }
	  private void init() {
	    batch = new SpriteBatch();
	    cameraGUI = new OrthographicCamera(VIEWPORT_GUI_WIDTH,
	      VIEWPORT_GUI_HEIGHT);
	    cameraGUI.position.set(0, 0, 0);
	    cameraGUI.setToOrtho(true); // flip y-axis
	    cameraGUI.update();
	  }
	  public void resize(int width, int height) {
	    cameraGUI.viewportHeight = VIEWPORT_GUI_HEIGHT;
	    cameraGUI.viewportWidth = (VIEWPORT_GUI_HEIGHT /
	      (float) height) * (float) width;
	    cameraGUI.position.set(cameraGUI.viewportWidth / 2,
	      cameraGUI.viewportHeight / 2, 0);
	    cameraGUI.update();
	  }

	  @Override
	  protected void handleInput() {
	    if (Gdx.input.justTouched()) {
	      if (gameover) {
	        gsm.set(new PlayState(gsm));
	      } else
	        bird.jump();
	    }
	  }

	  @Override
	  public void update(float dt) {
	    handleInput();
	    updateGround();
	    bird.update(dt);
	    cam.position.set(bird.getPosition().x + 80, cam.viewportHeight / 2, 0);  

	    for (Tube tube: tubes) {
	      if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
	        tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
	        tube.setIsBehindBird(false);
	      }
	      if (tube.collides(bird.getBounds()))
	        gameover = true;

	    }
	    if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
	      gameover = true;

	    for (int i = 0; i < tubes.size; i++)
	      if (bird.getPosition().x > tubes.get(i).getPosBotTube().x && !tubes.get(i).getIsBehindBird() && !gameover) {
	        point++;
	        // tang diem
	        
	        tubes.get(i).setIsBehindBird(true);
	      }
	    cam.update();
	  }

	  private void renderGui(SpriteBatch sb) {
	    sb.setProjectionMatrix(cameraGUI.combined);
	    sb.begin();
	    font.draw(sb, String.valueOf(point), cameraGUI.viewportWidth / 15, 4);
	    sb.end();

	    // diem
	  }
	  private void renderWorld(SpriteBatch sb) {
	    sb.setProjectionMatrix(cam.combined);
	    sb.begin();
	    sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
	    for (Tube tube: tubes) {
	      sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
	      sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
	    }

	    sb.draw(ground, groundPos1.x, groundPos1.y);
	    sb.draw(ground, groundPos2.x, groundPos2.y);
	    sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
	    if (gameover) {
	      sb.draw(gameoverImg, cam.position.x - gameoverImg.getWidth() / 2, cam.position.y);
	    }
	    sb.end();
	    // game over
	  }
	  @Override
	  public void render(SpriteBatch sb) {
	    renderWorld(sb);
	    renderGui(sb);
	  }

	  @Override
	  public void dispose() {
	    bg.dispose();
	    bird.dispose();
	    ground.dispose();
	    for (Tube tube: tubes)
	      tube.dispose();
	    System.out.println("Play State Dispose");
	  }
	  private void updateGround() {
	    if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
	      groundPos1.add(ground.getWidth() * 2, 0);
	    if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
	      groundPos2.add(ground.getWidth() * 2, 0);
	  }

	}
