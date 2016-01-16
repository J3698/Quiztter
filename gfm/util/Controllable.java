package gfm.util;

public interface Controllable {
	void up();
	void down();
	void left();
	void right();

	void releaseUp();
	void releaseDown();
	void releaseLeft();
	void releaseRight();
	
	void firstAction();
	void secondAction();
	void thirdAction();

	void releaseFirstAction();
	void releaseSecondAction();
	void releaseThirdAction();
}
