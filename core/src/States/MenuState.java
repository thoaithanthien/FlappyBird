package States;

import com.amzuni.game.FlappyBirdMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {
	private Texture background;
	private Texture playBtn;
	public MenuState(GameStateManager gsm) {
		super(gsm);
		cam.setToOrtho(false, FlappyBirdMain.WIDTH / 2, FlappyBirdMain.HEIGHT / 2);
		background = new Texture("bg.png");
		playBtn = new Texture("playbtn.png");
	}
	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(Gdx.input.justTouched()) {
			gsm.set(new PlayState(gsm));
		}
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
        sb.draw(background, 0,0);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);
        sb.end();
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		background.dispose();
		playBtn.dispose();
		System.out.println("Menu State Dispose");
	}

}
