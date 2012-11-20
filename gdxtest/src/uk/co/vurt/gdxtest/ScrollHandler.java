package uk.co.vurt.gdxtest;

public interface ScrollHandler {

	public void scrollUp();
	public void scrollDown();
	public void scrollLeft();
	public void scrollRight();
	public void dragReset();
	public void scroll(int xVector, int yVector);
}
