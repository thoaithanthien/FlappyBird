package States;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateManager {
	private Stack<State> states;	
	public GameStateManager () {
	    states = new Stack<State>();
	}
	public void push(State state) {
	    states.push(state);
	}
	public void pop() {
	    states.pop().dispose();
	}
	public void set(State state) {
		states.pop();
		states.push(state);
	}
	public void update (float dt) {
	   states.peek().update (dt);
	}
	
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		states.peek().render(batch);
	}
}
